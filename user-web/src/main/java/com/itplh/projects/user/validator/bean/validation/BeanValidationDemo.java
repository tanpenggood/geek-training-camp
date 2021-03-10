package com.itplh.projects.user.validator.bean.validation;


import com.itplh.projects.user.domain.User;

import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

public class BeanValidationDemo {

    public static void main(String[] args) {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        // cache the factory somewhere
        Validator validator = factory.getValidator();

        User user = new User();

        user.setId(0L);
        user.setName("");
        user.setPassword("****");
        user.setPhoneNumber("123456");
        user.setEmail("123456");

        // 校验结果
        validator.validate(user).forEach(c -> System.out.println(String.format("%s %s", c.getPropertyPath(), c.getMessage())));
    }
}
