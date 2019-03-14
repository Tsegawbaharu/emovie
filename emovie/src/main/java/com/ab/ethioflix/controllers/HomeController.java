package com.ab.ethioflix.controllers;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.ab.ethioflix.services.FileStorageService;
import com.ab.ethioflix.services.MovieService;
@Controller
public class HomeController {
	private FileStorageService fileStorageService;
	private MovieService movieService;
	@Autowired
	public HomeController( FileStorageService fileStorageService, MovieService movieService) {
		this.fileStorageService = fileStorageService;
		this.movieService = movieService;
	}
	@GetMapping("/")
	public String home() {
		return "home";	
	}
	@GetMapping("/about")
	public String about() {
		return "about";
	}
	
	@GetMapping("/discription")
	public String discription() {
		return "discription";	
	}
	@GetMapping("/discription/{id}")
	public String discription(@PathVariable("id")  Long id, Model model) {
		model.addAttribute("movie", movieService.getById(id));
		return "discription2";	
	}
	@GetMapping("/tryy")
	public String tryy() {
		return "tryy";	
	}
	@GetMapping("/comingup")
	public String comingup(Model model) {
		model.addAttribute("movies", movieService.getAll());
		return "comingup";	
	}
	
	@GetMapping("/downloadFile/{fileName:.+}")
	public ResponseEntity<Resource> downloadFile(@PathVariable String fileName, HttpServletRequest request){
		Resource resource  = fileStorageService.loadFileAsResource(fileName);
		String contentType = null;
		try {
			contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
		}
		catch(IOException ex) {
			ex.printStackTrace();
		}
		if(contentType == null) {
			contentType = "application/octet-stream";
		}
		return ResponseEntity.ok().contentType(MediaType.parseMediaType(contentType))
				.header(HttpHeaders.CONTENT_DISPOSITION, String.format("attachment;filename=\"%s\"", resource.getFilename()))
				.body(resource);
		
	}
}
