package com.liveme;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

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

		Role adminRole = new Role(1, "Админ");
		Role managerRole = new Role(2, "Менеджер");
		Role clientRole = new Role(3, "Клиент");

		roleRepository.save(adminRole);
		roleRepository.save(managerRole);
		roleRepository.save(clientRole);

		UserInfo admin1 = new UserInfo(1, "shkipper", "shygaev.xxx@gmail.com", "password", "+996507755011",
				adminRole,
				null);
		UserInfo admin2 = new UserInfo(2, "admin", "admin@gmail.com", "password", "+996555666777", adminRole,
				null);
		UserInfo manager1 = new UserInfo(3, "manager1", "manager1@gmail.com", "manager1password", "555555555",
				managerRole, null);
		UserInfo manager2 = new UserInfo(4, "manager2", "manager2@gmail.com", "manager2password", "666666666",
				managerRole, null);
		UserInfo client = new UserInfo(5, "user", "user@gmail.com", "userpassword", "666666666",
				clientRole, null);

		userInfoRepository.save(admin1);
		userInfoRepository.save(admin2);
		userInfoRepository.save(manager1);
		userInfoRepository.save(manager2);
		userInfoRepository.save(client);
	}
}
