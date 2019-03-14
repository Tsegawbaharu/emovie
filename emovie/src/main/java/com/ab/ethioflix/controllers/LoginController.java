package com.ab.ethioflix.controllers;

import java.util.List;
import java.util.Set;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.ab.ethioflix.domains.Role;
import com.ab.ethioflix.domains.User;
import com.ab.ethioflix.services.RoleService;
import com.ab.ethioflix.services.UserService;

@Controller
public class LoginController {
	
	private UserService userService;
	private RoleService roleService;
	@Autowired
	public LoginController(UserService userService,  RoleService roleService) {
		this.userService = userService;
		this.roleService = roleService;
		
	}
	
	@ModelAttribute(name="user")
	public User user() {
		return new User();
	}
	@GetMapping("/login")
	public String showLoginForm() {
		return "login";
	}
	
	@GetMapping("/signup")
    public String showSignupForm(){
        return "signup";
    }
	   @PostMapping("/signup")
	    public String createNewUser(@Valid User user, BindingResult bindingResult, RedirectAttributes atts) {
	        User userExists = userService.findUserByUsername(user.getUsername());
	        if (userExists != null) {
	            bindingResult
	                    .rejectValue("user", "error.user",
	                            "There is already a user registered with the username provided");
	        }
	        if (bindingResult.hasErrors()) {
	            return "signup";
	        } else {
	        	
	            userService.save(user);
	            atts.addFlashAttribute("successMessage", "User has been registered successfully");
	            
	            return "redirect:/login";
	        }
	    }
	   
	   @GetMapping("/adminsignup")
	    public String showAdminSignupForm(){
	        return "adminsignup";
	    }
		   @PostMapping("/adminsignup")
		    public String createNewAdminUser(@Valid User user, BindingResult bindingResult, RedirectAttributes atts) {
		        User userExists = userService.findUserByUsername(user.getUsername());
		        if (userExists != null) {
		            bindingResult
		                    .rejectValue("user", "error.user",
		                            "There is already a user registered with the username provided");
		        }
		        if (bindingResult.hasErrors()) {
		            return "adminsignup";
		        } else {
		        	
		            userService.saveadmin(user);
		            atts.addFlashAttribute("successMessage", "User has been registered successfully");
		            
		            return "redirect:/login";
		        }
		    }
	    
	    @GetMapping("/access-denied")
	    public String accessDenied(){
	        return "access-denied";
	    }
	    @RequestMapping("/default")
	    public String redirectAfterLogin(@AuthenticationPrincipal User user) {
	    	 Set<Role> roles = user.getRoles();
	    	 if(roles.contains(roleService.getByRole("ADMIN"))) {
	    		return "redirect:/admin";
	    	}
	    	else if(roles.contains(roleService.getByRole("USER"))) {
	    		return "redirect:/";
	    	}
	    	else {
	    		return "redirect:/";
	    	}
	    }
}
