package com.ab.ethioflix.services;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.ab.ethioflix.domains.Role;
import com.ab.ethioflix.domains.User;
import com.ab.ethioflix.repositories.RoleRepository;
import com.ab.ethioflix.repositories.UserRepository;

@Service
public class UserService implements UserDetailsService {
	
	
	private UserRepository repository;
	private RoleRepository roleRepository;
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    @Autowired
    public UserService(UserRepository userRepository,
                       RoleRepository roleRepository,
                       BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.repository = userRepository;
        this.roleRepository = roleRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

	public void save (User user) {
		user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        Role userRole = roleRepository.findByRole("USER");
        user.setRoles(new HashSet<Role>(Arrays.asList(userRole)));
        repository.save(user);
	}
	
	public void saveadmin (User user) {
		user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        Role userRole = roleRepository.findByRole("ADMIN");
        user.setRoles(new HashSet<Role>(Arrays.asList(userRole)));
        repository.save(user);
	}


	public User update(User user) {
		return repository.save(user);
	}


	public void delete(User user) {
		repository.delete(user);

	}


	public User findUserByUsername(String username) {
    	return repository.findByUsername(username);
    }


	public List<User> getAll() {
		 List<User> all = (List<User>) repository.findAll();
		return all;
	}


	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		User user = repository.findByUsername(username);
		
		if(user != null) {
			return user;
		}
		throw new UsernameNotFoundException("User '" + username + "' not found");
	}

}
