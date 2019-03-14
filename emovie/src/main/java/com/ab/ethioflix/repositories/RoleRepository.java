package com.ab.ethioflix.repositories;

import org.springframework.data.repository.CrudRepository;

import com.ab.ethioflix.domains.Role;

public interface RoleRepository extends CrudRepository<Role, Long>{
	Role findByRole(String role);

}
