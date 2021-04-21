package com.itplh.oauth.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author: tanpenggood
 * @date: 2021-04-21 04:45
 */
@Controller
@RequestMapping("/resource")
public class ResourceController {

    /**
     * 受保护的资源页面
     *
     * @return
     */
    @GetMapping("/index")
    public String index() {
        return "index";
    }

}
