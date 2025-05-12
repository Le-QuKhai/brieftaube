package com.haw.se1lab;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Application {

	// IMPORTANT: This class must lie in the root application package, i.e. next to or above all other classes/packages
	// Spring component scan only recognizes classes below main application class (the one with @SpringBootApplication)

	public static void main(String[] args) {
		// prevent running the application with an inappropriate Java version
		JavaVersionUtil.checkJavaRuntimeVersion();

		SpringApplication.run(Application.class, args);
	}

}
