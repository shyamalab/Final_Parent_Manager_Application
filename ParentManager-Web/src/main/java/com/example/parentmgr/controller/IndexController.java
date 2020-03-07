package com.example.parentmgr.controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/home")
public class IndexController {
	
	@GetMapping
    public String sayHello() {
		return "forward:/index.html";
    }
	
//    @GetMapping
//    public String index(Model model){		
//        return "Welcome to Project Manager Application Page";
//    }

}
