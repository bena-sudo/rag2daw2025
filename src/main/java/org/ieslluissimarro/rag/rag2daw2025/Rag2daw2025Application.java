package org.ieslluissimarro.rag.rag2daw2025;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling  // Habilita el uso de @Scheduled
public class Rag2daw2025Application {

	public static void main(String[] args) {
		SpringApplication.run(Rag2daw2025Application.class, args);
	}

}
