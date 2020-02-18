package kr.lineus.unistars.controller;


import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class AppController {

    private static final Logger log = LoggerFactory.getLogger(AppController.class);


    public static String applicationInstanceName = "Unistars back-end API";

	@RequestMapping("/")
	String home(ModelMap modal, HttpServletResponse response) {
		response.addHeader("Access-Control-Allow-Origin","*");
        modal.addAttribute("title", applicationInstanceName);
		return "index";
	}

	
   
    
}
