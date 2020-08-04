package com.demos.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author Administrator
 * @version 1.0.0
 * @date 2020/8/4 10:08
 */
@Controller
public class WebController {
    @RequestMapping("/index")
    public String index(){
        return "index";
    }
}
