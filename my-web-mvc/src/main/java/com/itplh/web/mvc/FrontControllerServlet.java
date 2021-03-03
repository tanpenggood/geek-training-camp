package com.itplh.web.mvc;

import com.alibaba.fastjson.JSON;
import com.itplh.web.mvc.controller.Controller;
import com.itplh.web.mvc.controller.PageController;
import com.itplh.web.mvc.controller.RestController;
import org.apache.commons.lang.StringUtils;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.HttpMethod;
import javax.ws.rs.Path;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.ServiceLoader;
import java.util.Set;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import static java.util.Arrays.asList;
import static org.apache.commons.lang.StringUtils.substringAfter;

public class FrontControllerServlet extends HttpServlet {

    private Logger logger = Logger.getLogger(this.getClass().getName());

    /**
     * 请求路径和 Controller 的映射关系缓存
     */
    private Map<String, Controller> controllersMapping = new HashMap<>();

    /**
     * 请求路径和 {@link HandlerMethodInfo} 映射关系缓存
     */
    private Map<String, HandlerMethodInfo> handleMethodInfoMapping = new HashMap<>();

    /**
     * 初始化 Servlet
     *
     * @param servletConfig
     */
    public void init(ServletConfig servletConfig) {
        initHandleMethods();
    }

    /**
     * 读取所有的 RestController 的注解元信息 @Path
     * 利用 ServiceLoader 技术（Java SPI）
     */
    private void initHandleMethods() {
        for (Controller controller : ServiceLoader.load(Controller.class)) {
            Class<?> controllerClass = controller.getClass();
            Path pathFromClass = controllerClass.getAnnotation(Path.class);
            String requestPathByClass = pathFromClass.value();
            List<Method> publicMethodsFromObject = asList(Object.class.getMethods());
            // 获取当前controller的所有public方法
            List<Method> publicMethods = asList(controllerClass.getMethods())
                    .stream()
                    // 排除Object的public方法
                    .filter(m -> !publicMethodsFromObject.contains(m))
                    .collect(Collectors.toList());
            // 处理方法支持的 HTTP 方法集合
            for (Method method : publicMethods) {
                Set<String> supportedHttpMethods = findSupportedHttpMethods(method);
                Path pathFromMethod = method.getAnnotation(Path.class);
                String completeRequestPath = requestPathByClass;
                if (pathFromMethod != null) {
                    completeRequestPath += pathFromMethod.value();
                }
                handleMethodInfoMapping.put(completeRequestPath,
                        new HandlerMethodInfo(completeRequestPath, method, supportedHttpMethods));
                controllersMapping.put(completeRequestPath, controller);
            }
        }
        logger.info("controllersMapping---------------------------------");
        controllersMapping.forEach((k, v) -> logger.info(String.format("%s %s", k, v)));
        logger.info("handleMethodInfoMapping----------------------------");
        handleMethodInfoMapping.forEach((k, v) -> logger.info(String.format("%s %s", k, v.getHandlerMethod().getName())));
    }

    /**
     * 获取处理方法中标注的 HTTP方法集合
     *
     * @param method 处理方法
     * @return
     */
    private Set<String> findSupportedHttpMethods(Method method) {
        Set<String> supportedHttpMethods = new LinkedHashSet<>();
        for (Annotation annotationFromMethod : method.getAnnotations()) {
            HttpMethod httpMethod = annotationFromMethod.annotationType().getAnnotation(HttpMethod.class);
            if (httpMethod != null) {
                supportedHttpMethods.add(httpMethod.value());
            }
        }

        if (supportedHttpMethods.isEmpty()) {
            supportedHttpMethods.addAll(asList(HttpMethod.GET, HttpMethod.POST,
                    HttpMethod.PUT, HttpMethod.DELETE, HttpMethod.HEAD, HttpMethod.OPTIONS));
        }

        return supportedHttpMethods;
    }

    /**
     * SCWCD
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    @Override
    public void service(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // 建立映射关系
        // requestURI = /a/hello/world
        String requestURI = request.getRequestURI();
        // contextPath  = /a or "/" or ""
        String servletContextPath = request.getContextPath();
        String prefixPath = servletContextPath;
        // 映射路径（子路径）
        String requestMappingPath = substringAfter(requestURI,
                StringUtils.replace(prefixPath, "//", "/"));
        // 映射到 Controller
        Controller controller = controllersMapping.get(requestMappingPath);

        if (controller != null) {

            HandlerMethodInfo handlerMethodInfo = handleMethodInfoMapping.get(requestMappingPath);

            try {
                if (handlerMethodInfo != null) {
                    boolean isNotAllowed = !handlerMethodInfo.getSupportedHttpMethods().contains(request.getMethod());
                    if (isNotAllowed) {
                        // HTTP 方法不支持
                        response.setStatus(HttpServletResponse.SC_METHOD_NOT_ALLOWED);
                        return;
                    }

                    if (controller instanceof PageController) {
                        // 调用controller对应的方法
                        String viewPath = (String) handlerMethodInfo.getHandlerMethod().invoke(controller, request, response);
                        // 页面请求 redirect
                        if (viewPath.startsWith("redirect:")) {
                            // 截取重定向地址
                            String redirectPath = viewPath.substring("redirect:".length());
                            // 重定向地址是相对路径
                            if (!redirectPath.startsWith("/")) {
                                Path pathAnnotationByClass = controller.getClass().getAnnotation(Path.class);
                                String pathByClass = pathAnnotationByClass.value();
                                if (pathByClass == null) {
                                    redirectPath = "/" + redirectPath;
                                } else {
                                    redirectPath = pathByClass + "/" + redirectPath;
                                }
                            }
                            // 拼接contextPath
                            if (servletContextPath != null) {
                                redirectPath = servletContextPath + redirectPath;
                            }
                            // 重定向
                            response.sendRedirect(redirectPath);
                            return;
                        }
                        // 页面请求 forward
                        // request -> RequestDispatcher forward
                        // RequestDispatcher requestDispatcher = request.getRequestDispatcher(viewPath);
                        // ServletContext -> RequestDispatcher forward
                        // ServletContext -> RequestDispatcher 必须以 "/" 开头
                        ServletContext servletContext = request.getServletContext();
                        if (!viewPath.startsWith("/")) {
                            viewPath = "/" + viewPath;
                        }
                        RequestDispatcher requestDispatcher = servletContext.getRequestDispatcher(viewPath);
                        requestDispatcher.forward(request, response);
                        return;
                    } else if (controller instanceof RestController) {
                        response.setCharacterEncoding("UTF-8");
                        response.setContentType("application/json");
                        try (PrintWriter writer = response.getWriter()) {
                            String result = JSON.toJSONString(handlerMethodInfo.getHandlerMethod().invoke(controller, request, response));
                            writer.write(result);
                            writer.flush();
                        }
                    }
                }
            } catch (Throwable throwable) {
                if (throwable.getCause() instanceof IOException) {
                    throw (IOException) throwable.getCause();
                } else {
                    throw new ServletException(throwable.getCause());
                }
            }
        }
    }

}
