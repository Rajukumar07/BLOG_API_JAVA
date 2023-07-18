package com.blog.app.apprunner;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.blog.app.config.AppConstants;
import com.blog.app.entity.Role;
import com.blog.app.repositories.RoleRepo;

@Component
public class AppRunner implements CommandLineRunner {

	@Autowired
	private RoleRepo repo;

	@Override
	public void run(String... args) throws Exception {

		try {
			Role role1 = new Role();
			role1.setRoleId(AppConstants.ADMIN_USER);
			role1.setName("ROLE_ADMIN");

			Role role2 = new Role();
			role2.setRoleId(AppConstants.NORMAL_USER);
			role2.setName("ROLE_NORMAL");
			// List<Role> roles = Arrays.asList(role1,role2);
			List<Role> roles = List.of(role1, role2);
			repo.saveAll(roles);

		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
	}

}
