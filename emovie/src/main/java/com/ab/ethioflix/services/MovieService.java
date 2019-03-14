package com.ab.ethioflix.services;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import com.ab.ethioflix.domains.Movie;

public interface MovieService {
	public Movie save (Movie movie);


	public Movie update(Movie movie);


	public void delete(Movie movie);
	public void deleteById(Long id);

	public Movie getById(Long id);


	public List<Movie> getAll();


}
