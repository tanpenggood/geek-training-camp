package com.itplh.projects.user.repository;

import com.itplh.function.ThrowableFunction;
import com.itplh.projects.user.sql.DBConnectionManager;
import com.itplh.projects.user.sql.DBConnectionManagerFactory;

import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.logging.Logger;

import static org.apache.commons.lang.ClassUtils.wrapperToPrimitive;

/**
 * @author: tanpenggood
 * @date: 2021-03-03 16:40
 */
public interface BaseRepository {

    Logger logger = Logger.getLogger(BaseRepository.class.getName());

    default <T> T executeQuery(String sql,
                               ThrowableFunction<ResultSet, T> function,
                               Consumer<Throwable> exceptionHandler,
                               Object... args) {
        DBConnectionManager dbConnectionManager = DBConnectionManagerFactory.getInstance().getDBConnectionManager();
        try {
            Connection connection = dbConnectionManager.getConnection();
            PreparedStatement preparedStatement = buildPreparedStatement(connection, sql, args);
            ResultSet resultSet = preparedStatement.executeQuery();
            // 返回一个 POJO List -> ResultSet -> POJO List
            // ResultSet -> T
            return function.apply(resultSet);
        } catch (Throwable e) {
            exceptionHandler.accept(e);
        } finally {
            Optional.ofNullable(dbConnectionManager).ifPresent(d -> d.releaseConnection());
        }
        return null;
    }

    default int executeUpdate(String sql,
                              Consumer<Throwable> exceptionHandler,
                              Object... args) {
        DBConnectionManager dbConnectionManager = DBConnectionManagerFactory.getInstance().getDBConnectionManager();
        try {
            Connection connection = dbConnectionManager.getConnection();
            PreparedStatement preparedStatement = buildPreparedStatement(connection, sql, args);
            return preparedStatement.executeUpdate();
        } catch (Throwable e) {
            exceptionHandler.accept(e);
        } finally {
            Optional.ofNullable(dbConnectionManager).ifPresent(d -> d.releaseConnection());
        }
        return 0;
    }

    default PreparedStatement buildPreparedStatement(Connection connection, String sql, Object[] args) throws Exception {
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        logger.info(String.format(">>>>>>>>>> %s", preparedStatement.toString()));
        // 设置预编译参数
        for (int i = 0; i < args.length; i++) {
            Object arg = args[i];
            Class argType = arg.getClass();

            Class wrapperType = wrapperToPrimitive(argType);

            if (wrapperType == null) {
                wrapperType = argType;
            }

            String methodName = preparedStatementMethodMappings().get(argType);
            Method method = PreparedStatement.class.getMethod(methodName, int.class, wrapperType);
            method.invoke(preparedStatement, i + 1, arg);
        }
        logger.info(String.format("<<<<<<<<<< %s", preparedStatement.toString()));
        return preparedStatement;
    }

    default Map<Class, String> preparedStatementMethodMappings() {
        Map<Class, String> preparedStatementMethodMappings = new HashMap<>();
        preparedStatementMethodMappings.put(Integer.class, "setLong"); // int
        preparedStatementMethodMappings.put(Long.class, "setLong"); // long
        preparedStatementMethodMappings.put(String.class, "setString"); // string
        return preparedStatementMethodMappings;
    }

}
