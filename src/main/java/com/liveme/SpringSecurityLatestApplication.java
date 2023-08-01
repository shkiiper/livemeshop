package com.liveme;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.liveme.entity.Role;
import com.liveme.entity.UserInfo;
import com.liveme.repository.RoleRepository;
import com.liveme.repository.UserInfoRepository;

@SpringBootApplication
public class SpringSecurityLatestApplication {

	public static void main(String[] args) {
		ConfigurableApplicationContext context = SpringApplication.run(SpringSecurityLatestApplication.class, args);

		RoleRepository roleRepository = context.getBean(RoleRepository.class);
		UserInfoRepository userInfoRepository = context.getBean(UserInfoRepository.class);
		PasswordEncoder passwordEncoder = context.getBean(PasswordEncoder.class); // Внедряем PasswordEncoder

		Role adminRole = new Role(1, "admin");
		Role managerRole = new Role(2, "manager");
		Role clientRole = new Role(3, "client");

		roleRepository.save(adminRole);
		roleRepository.save(managerRole);
		roleRepository.save(clientRole);

		// Создаем пользователей и хешируем пароли
		UserInfo admin1 = new UserInfo(1, "shkipper", "shygaev.xxx@gmail.com", passwordEncoder.encode("password"),
				"+996507755011", adminRole, null);
		UserInfo admin2 = new UserInfo(2, "admin", "admin@gmail.com", passwordEncoder.encode("password"),
				"+996555666777", adminRole, null);
		UserInfo manager1 = new UserInfo(3, "manager1", "manager1@gmail.com",
				passwordEncoder.encode("manager1password"),
				"555555555", managerRole, null);
		UserInfo manager2 = new UserInfo(4, "manager2", "manager2@gmail.com",
				passwordEncoder.encode("manager2password"),
				"666666666", managerRole, null);
		UserInfo client = new UserInfo(5, "user", "user@gmail.com", passwordEncoder.encode("userpassword"),
				"666666666", clientRole, null);

		userInfoRepository.save(admin1);
		userInfoRepository.save(admin2);
		userInfoRepository.save(manager1);
		userInfoRepository.save(manager2);
		userInfoRepository.save(client);
	}
}
