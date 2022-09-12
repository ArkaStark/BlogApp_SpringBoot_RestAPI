package com.springboot.blog.springbootblogrestapi;

import com.springboot.blog.springbootblogrestapi.entity.Role;
import com.springboot.blog.springbootblogrestapi.repository.RoleRepository;
import io.micrometer.core.aop.TimedAspect;
import io.micrometer.core.instrument.MeterRegistry;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class SpringbootBlogRestapiApplication implements CommandLineRunner {


	@Bean
	public ModelMapper modelMapper(){
		return new ModelMapper();
	}

	@Bean
	public TimedAspect timedAspect(MeterRegistry registry) {
		return new TimedAspect(registry);
	}


	public static void main(String[] args) {
		SpringApplication.run(SpringbootBlogRestapiApplication.class, args);
	}


	@Autowired
	private RoleRepository roleRepository;
	@Override
	public void run(String... args) throws Exception {
		Role adminRole = new Role();
		adminRole.setName("ROLE_ADMIN");
		roleRepository.save(adminRole);

		Role userRole = new Role();
		userRole.setName("ROLE_USER");
		roleRepository.save(userRole);
	}
}
