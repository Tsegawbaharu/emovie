package com.ab.ethioflix.services;


import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.ab.ethioflix.domains.Movie;
import com.ab.ethioflix.repositories.MovieRepository;

@Service
public class MovieServiceImpl implements MovieService{
	@Autowired
	private MovieRepository repository;


	public Movie save (Movie movie) {
		return repository.save(movie);
	}


	public Movie update(Movie movie) {
		return repository.save(movie);
	}


	public void delete(Movie movie) {
		repository.delete(movie);

	}
	public void deleteById(Long id) {
		repository.deleteById(id);
	}


	public Movie getById(Long id) {
		if(repository.existsById(id)) {
			return repository.findById(id).get();
		}
		return null;
	}
	

	public List<Movie> getAll() {
		 List<Movie> all = (List<Movie>) repository.findAll();
		return all;
	}
	public Iterable<Movie> findAll(Sort sort) {
		return repository.findAll(sort);
	}

	public Page<Movie> findAll(Pageable pageable) {
		return repository.findAll(pageable);
	}


}
