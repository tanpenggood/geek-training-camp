package com.itplh.projects.user.repository;


import com.itplh.projects.user.domain.User;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DatabaseUserRepository implements UserRepository, BaseRepository {

    private Logger logger = Logger.getLogger(DatabaseUserRepository.class.getName());

    /**
     * 通用处理方式
     */
    private Consumer<Throwable> COMMON_EXCEPTION_HANDLER = e -> logger.log(Level.SEVERE, e.getMessage(), e);

    public static final String INSERT_USER_DML_SQL = "INSERT INTO users(name,password,email,phoneNumber) VALUES(?,?,?,?)";

    public static final String QUERY_ALL_USERS_DML_SQL = "SELECT id,name,password,email,phoneNumber FROM users";

    @Override
    public boolean save(User user) {
        int insertRow = executeUpdate(INSERT_USER_DML_SQL,
                COMMON_EXCEPTION_HANDLER,
                user.getName(),
                user.getPassword(),
                user.getEmail(),
                user.getPhoneNumber());
        return insertRow == 1;
    }

    @Override
    public boolean deleteById(Long userId) {
        int deleteRow = executeUpdate("DELETE FROM users WHERE id = ?",
                COMMON_EXCEPTION_HANDLER, userId);
        return deleteRow == 1;
    }

    @Override
    public boolean update(User user) {
        return false;
    }

    @Override
    public User getById(Long userId) {
        return null;
    }

    @Override
    public User getByNameAndPassword(String name, String password) {
        return executeQuery("SELECT id,name,password,email,phoneNumber FROM users WHERE name=? and password=?",
                resultSet -> buildUsers(resultSet).stream().findFirst().orElse(null),
                COMMON_EXCEPTION_HANDLER, name, password);
    }

    @Override
    public User getByName(String name) {
        return executeQuery("SELECT id,name,password,email,phoneNumber FROM users WHERE name=?",
                resultSet -> buildUsers(resultSet).stream().findFirst().orElse(null),
                COMMON_EXCEPTION_HANDLER, name);
    }

    @Override
    public Collection<User> getAll() {
        return executeQuery(QUERY_ALL_USERS_DML_SQL, resultSet -> buildUsers(resultSet), COMMON_EXCEPTION_HANDLER);
    }

    public List<User> buildUsers(ResultSet resultSet) throws IntrospectionException, SQLException, NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        // BeanInfo -> IntrospectionException
        BeanInfo userBeanInfo = Introspector.getBeanInfo(User.class, Object.class);
        List<User> users = new ArrayList<>();
        while (resultSet.next()) { // 如果存在并且游标滚动 // SQLException
            User user = new User();
            for (PropertyDescriptor propertyDescriptor : userBeanInfo.getPropertyDescriptors()) {
                String fieldName = propertyDescriptor.getName();
                Class fieldType = propertyDescriptor.getPropertyType();
                String methodName = resultSetMethodMappings.get(fieldType);
                // 可能存在映射关系（不过此处是相等的）
                String columnLabel = mapColumnLabel(fieldName);
                Method resultSetMethod = ResultSet.class.getMethod(methodName, String.class);
                // 通过放射调用 getXXX(String) 方法
                Object resultValue = resultSetMethod.invoke(resultSet, columnLabel);
                // 获取 User 类 Setter方法
                // PropertyDescriptor ReadMethod 等于 Getter 方法
                // PropertyDescriptor WriteMethod 等于 Setter 方法
                Method setterMethodFromUser = propertyDescriptor.getWriteMethod();
                // 以 id 为例，  user.setId(resultSet.getLong("id"));
                setterMethodFromUser.invoke(user, resultValue);
            }
            users.add(user);
        }
        return users;
    }

    private static String mapColumnLabel(String fieldName) {
        return fieldName;
    }

    /**
     * 数据类型与 ResultSet 方法名映射
     */
    static Map<Class, String> resultSetMethodMappings = new HashMap<>();

    static {
        resultSetMethodMappings.put(Long.class, "getLong");
        resultSetMethodMappings.put(String.class, "getString");
    }

}
