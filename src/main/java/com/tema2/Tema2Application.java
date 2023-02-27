package com.tema2;

import com.tema2.Models.Laptop;
import com.tema2.Repositories.LaptopRepository;
import org.apache.catalina.core.ApplicationContext;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableSwagger2
public class Tema2Application {

	@Value("${app.message}")
	private String profile;

	public static void main(String[] args) {

		SpringApplication.run(Tema2Application.class, args);
	}

	@Bean
	CommandLineRunner commandLineRunner(LaptopRepository laptopRepository){
		System.out.println(profile);
		return args -> {
			Laptop laptop1 = new Laptop("HP","notebook-15");
			Laptop laptop2 = new Laptop("asus","notebook-17");
			Laptop laptop3 = new Laptop("dell","notebook-19");

			laptopRepository.save(laptop1);
			laptopRepository.save(laptop2);
			laptopRepository.save(laptop3);
		};

	}
}
