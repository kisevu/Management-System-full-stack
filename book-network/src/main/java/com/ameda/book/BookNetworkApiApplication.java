package com.ameda.book;

import com.ameda.book.role.Role;
import com.ameda.book.role.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.scheduling.annotation.EnableAsync;

import java.time.LocalDateTime;

@SpringBootApplication
@EnableJpaAuditing(auditorAwareRef = "auditorAware")
@EnableAsync
public class BookNetworkApiApplication{
	@Autowired
	private RoleRepository roleRepository;

	@Bean
	public CommandLineRunner commandLineRunner (
			RoleRepository roleRepository
	){
		return args ->{
			var role = Role.builder()
					.name("USER")
					.build();
			roleRepository.save(role);
		};
	}

	public static void main(String[] args) {
		SpringApplication.run(BookNetworkApiApplication.class, args);
	}
}
