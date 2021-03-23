package com.itplh.web.context;

import com.itplh.initializer.MyApplicationInitializer;
import com.itplh.initializer.annotation.Order;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import java.util.logging.Logger;

/**
 * {@link ComponentContext} 初始化器
 *
 * @author: tanpenggood
 * @date: 2021-03-24 00:27
 */
@Order(Integer.MIN_VALUE)
public class ComponentContextInitializer implements MyApplicationInitializer {

    private Logger logger = Logger.getLogger(this.getClass().getName());

    @Override
    public void onStartup(ServletContext servletContext) throws ServletException {
        logger.info("ComponentContextInitializer start.");
        ComponentContext componentContext = new ComponentContext();
        componentContext.init(servletContext);
        logger.info("ComponentContextInitializer done.");
    }
}
