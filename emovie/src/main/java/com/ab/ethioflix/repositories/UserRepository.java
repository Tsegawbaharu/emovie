package com.ab.ethioflix.repositories;

import org.springframework.data.repository.CrudRepository;

import com.ab.ethioflix.domains.User;

public interface UserRepository extends CrudRepository<User, Long>{
	User findByUsername(String username);

}
