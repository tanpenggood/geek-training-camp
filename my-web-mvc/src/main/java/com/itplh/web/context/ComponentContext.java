package com.itplh.web.context;


import com.itplh.function.ThrowableFunction;
import com.itplh.web.context.listener.ComponentContextInitializerListener;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.annotation.Resource;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NameClassPair;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.servlet.ServletContext;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * 组件上下文（Web 应用全局使用）
 * <p>
 * 初始化ComponentContext {@link ComponentContextInitializerListener}
 *
 * @author: tanpenggood
 * @date: 2021-03-10 01:22
 */
public class ComponentContext {

    private Logger logger = Logger.getLogger(CONTEXT_NAME);

    public static final String CONTEXT_NAME = ComponentContext.class.getName();

    private static final String COMPONENT_ENV_ROOT = "java:comp/env";

    // Component Env Context
    private Context envContext;

    private ClassLoader classLoader;

    /**
     * K JNDI name
     * V class instance
     */
    private Map<String, Object> componentsMap = new LinkedHashMap<>();

    /**
     * K class name
     * V class instance
     */
    private Map<String, Object> classNameMap = new LinkedHashMap<>();

    /**
     * 请注意
     * 假设一个 Tomcat JVM 进程，三个 Web Apps，servletContext会不会相互冲突？（不会冲突）
     * static 字段是 JVM 缓存吗？（是 ClassLoader 缓存）
     */
    private static ServletContext servletContext;

    /**
     * 获取ComponentContext
     *
     * @return
     */
    public static ComponentContext getInstance() {
        return ((ComponentContext) servletContext.getAttribute(CONTEXT_NAME));
    }

    /**
     * 通过名称进行依赖查找
     *
     * @param name JNDI name
     * @param <C>
     * @return
     */
    public <C> C getComponent(String name) {
        return (C) componentsMap.get(name);
    }

    /**
     * 通过类名进行依赖查找
     *
     * @param clazz clazz
     * @param <C>
     * @return
     */
    public <C> C getComponent(Class clazz) {
        return (C) classNameMap.get(clazz.getName());
    }

    /**
     * 通过Class进行依赖查找
     * 应用场景：通过接口查找实现类
     *
     * @param targetClass
     * @return
     */
    public <C> List<C> getComponents(Class<C> targetClass) {
        return classNameMap.values().stream()
                .filter(clazz -> targetClass.isAssignableFrom(clazz.getClass()))
                .map(targetClass::cast)
                .collect(Collectors.toList());
    }

    /**
     * 获取所有的组件名称
     *
     * @return
     */
    public List<String> getComponentNames() {
        return new ArrayList<>(componentsMap.keySet());
    }

    /**
     * ComponentContext初始化方法
     *
     * @param servletContext
     * @throws RuntimeException
     */
    public void init(ServletContext servletContext) throws RuntimeException {
        servletContext.setAttribute(CONTEXT_NAME, this);
        ComponentContext.servletContext = servletContext;
        // 获取当前 ServletContext（WebApp）ClassLoader
        this.classLoader = servletContext.getClassLoader();
        // 初始化envContext
        initEnvContext();
        // 组件生命周期
        lifeCycleComponents();
        logger.info("all component----------------------------");
        componentsMap.forEach((k, v) -> System.out.println(String.format("%s %s", k, v)));
    }

    /**
     * 初始化envContext
     *
     * @throws RuntimeException
     */
    private void initEnvContext() throws RuntimeException {
        if (this.envContext == null) {
            Context context = null;
            try {
                context = new InitialContext();
                this.envContext = (Context) context.lookup(COMPONENT_ENV_ROOT);
            } catch (NamingException e) {
                throw new RuntimeException(e);
            } finally {
                close(context);
            }
        }
    }

    /**
     * 执行组件生命周期（支持 Java 标准 Commons Annotation 生命周期）
     * <ol>
     * <li>实例化组件阶段 - JNDI查找</li>
     * <li>依赖注入阶段 - {@link Resource}</li>
     * <li>PostConstruct回调阶段 - {@link PostConstruct}</li>
     * <li>PreDestroy回调阶段 - {@link PreDestroy}</li>
     * </ol>
     */
    private void lifeCycleComponents() {
        // 实例化组件 JNDI查找
        instantiateComponents();
        // 依赖注入阶段 - {@link Resource} 内部查找
        injectComponents();
        // PostConstruct回调阶段 - {@link PostConstruct}
        postConstructComponents();
    }

    /**
     * 实例化组件
     */
    private void instantiateComponents() {
        // 遍历获取所有的组件名称
        List<String> componentNames = listAllComponentNames();
        // 通过依赖查找，实例化对象（ Tomcat BeanFactory setter 方法的执行，仅支持简单类型）
        componentNames.forEach(name -> componentsMap.put(name, lookupComponent(name)));
        // 通过 {class name: class instance} 键值队的形式存储所有Component，方便通过class name查找
        componentsMap.values().forEach(clazz -> classNameMap.put(clazz.getClass().getName(), clazz));
    }

    /**
     * 依赖注入阶段 - {@link Resource}
     */
    private void injectComponents() {
        componentsMap.values().forEach(component -> injectComponents(component, component.getClass()));
    }

    /**
     * PostConstruct回调阶段 - {@link PostConstruct}
     */
    private void postConstructComponents() {
        componentsMap.values().forEach(component -> processPostConstruct(component, component.getClass()));
    }

    /**
     * JNDI查找
     *
     * @param name
     * @param <C>
     * @return
     */
    private <C> C lookupComponent(String name) {
        return executeInContext(context -> (C) context.lookup(name));
    }

    /**
     * 依赖注入阶段 - {@link Resource}
     *
     * @param component
     * @param componentClass
     */
    private void injectComponents(Object component, Class<?> componentClass) {
        Stream.of(componentClass.getDeclaredFields())
                .filter(field -> {
                    // default-0 public-1 private-2 protected-4 static-8 final-16
                    // 过滤满足注入的属性
                    return !Modifier.isStatic(field.getModifiers()) // 非static属性
                            && field.isAnnotationPresent(Resource.class); // 属性被Resource标记
                })
                .forEach(field -> {
                    Resource resource = field.getAnnotation(Resource.class);
                    String resourceName = resource.name();
                    // 直接从componentsMap中查找注入对象
                    Object injectObject = getComponent(resourceName);
                    try {
                        field.setAccessible(true);
                        // 注入目标对象
                        field.set(component, injectObject);
                        field.setAccessible(false);
                    } catch (IllegalAccessException e) {
                        // ignore
                    }
                });
    }

    /**
     * PostConstruct回调阶段 - {@link PostConstruct}
     *
     * @param component
     * @param componentClass
     */
    private void processPostConstruct(Object component, Class<?> componentClass) {
        Arrays.stream(componentClass.getDeclaredMethods())
                .filter(method -> {
                    // 过滤满足执行PostConstruct回调的方法
                    return Modifier.isPublic(method.getModifiers()) // public方法
                            && Objects.equals(void.class, method.getReturnType()) // 返回值为void
                            && method.getParameterCount() == 0 // 没有参数
                            && method.isAnnotationPresent(PostConstruct.class); // 方法被PostConstruct注解标记
                })
                .forEach(method -> {
                    try {
                        method.invoke(component);
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                });
    }

    private List<String> listAllComponentNames() {
        return listComponentNames("/");
    }

    private List<String> listComponentNames(String name) {
        return executeInContext(context -> {
            NamingEnumeration<NameClassPair> node = executeInContext(context, ctx -> ctx.list(name), true);

            // 目录 - Context
            // 节点 -
            if (node == null) { // 当前 JNDI 名称下没有子节点
                return Collections.emptyList();
            }

            List<String> fullNames = new LinkedList<>();
            while (node.hasMoreElements()) {
                NameClassPair element = node.nextElement();
                String className = element.getClassName();
                String elementName = element.getName();
                Class<?> currentClass = classLoader.loadClass(className);
                // 如果当前类是Context的实现类，则是目录，再进行递归查找
                boolean isDirectory = Context.class.isAssignableFrom(currentClass);
                if (isDirectory) {
                    fullNames.addAll(listComponentNames(elementName));
                } else {
                    // 否则，当前名称绑定目标类型的话，添加该名称到集合中
                    String fullName = name.startsWith("/") ? elementName : name + "/" + elementName;
                    fullNames.add(fullName);
                }
            }
            return fullNames;
        });
    }

    /**
     * 在 Context 中执行，通过指定 ThrowableFunction 返回计算结果
     *
     * @param function ThrowableFunction
     * @param <R>      返回结果类型
     * @return 返回
     * @see ThrowableFunction#apply(Object)
     */
    private <R> R executeInContext(ThrowableFunction<Context, R> function) {
        return executeInContext(function, false);
    }

    /**
     * 在 Context 中执行，通过指定 ThrowableFunction 返回计算结果
     *
     * @param function         ThrowableFunction
     * @param ignoredException 是否忽略异常
     * @param <R>              返回结果类型
     * @return 返回
     * @see ThrowableFunction#apply(Object)
     */
    private <R> R executeInContext(ThrowableFunction<Context, R> function, boolean ignoredException) {
        return executeInContext(this.envContext, function, ignoredException);
    }

    private <R> R executeInContext(Context context, ThrowableFunction<Context, R> function, boolean ignoredException) {
        R result = null;
        try {
            result = ThrowableFunction.execute(context, function);
        } catch (Throwable e) {
            if (ignoredException) {
                logger.warning(e.getMessage());
            } else {
                throw new RuntimeException(e);
            }
        }
        return result;
    }

    public void destroy() {
        close(this.envContext);
        // PreDestroy回调阶段 - {@link PreDestroy}
        componentsMap.values().forEach(component -> preDestroyComponents(component, component.getClass()));
    }

    /**
     * PreDestroy回调阶段 - {@link PreDestroy}
     *
     * @param component
     * @param componentClass
     */
    private void preDestroyComponents(Object component, Class<?> componentClass) {
        Arrays.stream(componentClass.getDeclaredMethods())
                .filter(method -> {
                    // 过滤满足执行PreDestroy回调的方法
                    return Modifier.isPublic(method.getModifiers()) // public方法
                            && Objects.equals(void.class, method.getReturnType()) // 返回值为void
                            && method.getParameterCount() == 0 // 没有参数
                            && method.isAnnotationPresent(PreDestroy.class); // 方法被PreDestroy注解标记
                })
                .forEach(method -> {
                    try {
                        method.invoke(component);
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                });
    }

    private void close(Context context) {
        if (context != null) {
            try {
                context.close();
            } catch (NamingException e) {
                throw new RuntimeException(e);
            }
        }
    }

}
