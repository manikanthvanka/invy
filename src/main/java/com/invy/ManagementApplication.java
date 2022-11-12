package com.invy;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import com.invy.entity.User;
import com.invy.repository.UserRepository;

@SpringBootApplication
@Controller
public class ManagementApplication  extends SpringBootServletInitializer {

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(ManagementApplication.class);
	}
	public static void main(String[] args) {
		SpringApplication.run(ManagementApplication.class, args);
	}
	@Autowired
	UserRepository userRepo;
	
	@PostConstruct
	public void saveUsers() {
		User user = new User();
		user.setName("manikanth");
		user.setRole("admin");
		
		
		userRepo.save(user);
		
		User user2 = new User();
		user2.setName("admin");
		user2.setRole("admin");
		
		userRepo.save(user2);
		
		User user3 = new User();
		user3.setName("jacob");
		user3.setRole("user");
		
		userRepo.save(user3);
		
		User user4 = new User();
		user4.setName("john");
		user4.setRole("john");
		userRepo.save(user4);
		
	}
	
	@GetMapping("/")
	public ModelAndView home() {
		return new ModelAndView("index");
	}

}
