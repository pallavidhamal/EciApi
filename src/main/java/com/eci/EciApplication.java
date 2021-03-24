package com.eci;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@SpringBootApplication
public class EciApplication  extends SpringBootServletInitializer {

	/*public static void main(String[] args) {
		System.out.println("i m comingggg");
		
		SpringApplication.run(EciApplication.class, args);
	}*/

/*	@RequestMapping("/")
	@ResponseBody
	String home() {
		return "Greetings from Java Tutorial Network";
	}
	public static void main(String[] args) {
		System.out.println("i m comingggg");
		SpringApplication.run(EciApplication.class, args);
	}*/

	
	 @Override
	    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
	        return application.sources(EciApplication.class);
	    }

	    public static void main(String[] args) throws Exception {
	        SpringApplication.run(EciApplication.class, args);
	    }

	
}

