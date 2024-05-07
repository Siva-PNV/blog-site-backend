package com.fse;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages="com.fse")
public class BlogSiteApplication {

	public static void main(String[] args) {
		SpringApplication.run(BlogSiteApplication.class, args);
	}

}
