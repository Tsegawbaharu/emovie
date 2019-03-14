package com.ab.ethioflix.controllers;
import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.ab.ethioflix.domains.Movie;
import com.ab.ethioflix.exception.FileStorageException;
import com.ab.ethioflix.services.FileStorageService;
import com.ab.ethioflix.services.MovieService;
import com.ab.ethioflix.services.MovieServiceImpl;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping("/admin")
public class AdminMovieController {
	private FileStorageService fileStorageService;
	private MovieService movieService;
	
	@Autowired
	public AdminMovieController(MovieService movieService, FileStorageService fileStorageService) {
		this.fileStorageService = fileStorageService;
		this.movieService = movieService;
		}
	
	@ModelAttribute(name="movie")
	public Movie wp(Model model) {
		return new Movie();
	}
	
	@GetMapping
	public String showForm(Model model) {
		model.addAttribute("movie", new Movie());
		return "admin";
	}
	
	@RequestMapping("deleteWp")
	public String deleteWp(@RequestParam("id") Long id) {
		Movie wp = new Movie();
		wp.setId(id);
		movieService.delete(wp);
		return "redirect:/admin/wantedPerson/#wp_list";
		}
	
	@PostMapping
	public String processPost( @RequestParam("file") MultipartFile f, @Valid Movie wantedPerson, Errors errors) throws IOException{
		if (errors.hasErrors()) {
			return "admin";
		}
		String fileName;
        try {
        	 fileName = fileStorageService.storeFile(f);
        }
        
        catch (FileStorageException e) {
        	//return "about";
        	throw(e);
        }
		
		String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath().path("/downloadFile/").path(fileName).toUriString();
		wantedPerson.setPicturePath(fileDownloadUri);
		Movie savedWp = movieService.save(wantedPerson);
		log.info("News object after persisting: " + savedWp);
		
		return "redirect:/admin";	
	}
	
	@GetMapping("/edit/{id}")
	public String ShowEditForm(@PathVariable("id") Long id, Model model) {
		Movie wantedPerson = movieService.getById(id);
		model.addAttribute("wantedPerson",wantedPerson);
		return "admin_edit_wp";	
	}
	
	@PostMapping("/edit/{id}")
	public String processUpdate(@PathVariable("id") Long id, @RequestParam("file") MultipartFile f,@Valid Movie wantedPerson, Errors errors){
		if (errors.hasErrors()) {
			wantedPerson.setId(id);
			return "admin_edit_wp";
		}
		String fileName;
		try {
			fileName = fileStorageService.storeFile(f);
			String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath().path("/downloadFile/").path(fileName).toUriString();
			wantedPerson.setPicturePath(fileDownloadUri);
		}
		catch(FileStorageException e){
			wantedPerson.setPicturePath(movieService.getById(id).getPicturePath());
			movieService.update(wantedPerson);
		}
		catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		movieService.update(wantedPerson);
		return "redirect:/admin/wantedPerson/#wp_list";	
	}

}

