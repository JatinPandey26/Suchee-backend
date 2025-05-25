package com.suchee.app;

import com.suchee.app.logging.Trace;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.web.servlet.error.ErrorMvcAutoConfiguration;
import org.springframework.context.annotation.Import;

@SpringBootApplication
@EnableAutoConfiguration(exclude = {ErrorMvcAutoConfiguration.class})

public class SucheeApplication {

	public static void main(String[] args) {

		Trace.log("Suchee Application starting");

		SpringApplication.run(SucheeApplication.class, args);
	}

}
