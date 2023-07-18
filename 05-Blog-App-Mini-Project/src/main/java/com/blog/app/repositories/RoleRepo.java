package com.blog.app.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.blog.app.entity.Role;

public interface RoleRepo extends JpaRepository<Role, Integer>{

}
