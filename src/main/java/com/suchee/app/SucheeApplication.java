package com.suchee.app;

import com.suchee.app.logging.Trace;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SucheeApplication {

	public static void main(String[] args) {

		Trace.log("Suchee Application starting");

		SpringApplication.run(SucheeApplication.class, args);
	}

}
