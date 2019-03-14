package com.ab.ethioflix.repositories;


import org.springframework.data.repository.PagingAndSortingRepository;

import com.ab.ethioflix.domains.Movie;

public interface MovieRepository extends PagingAndSortingRepository<Movie, Long>{

}
