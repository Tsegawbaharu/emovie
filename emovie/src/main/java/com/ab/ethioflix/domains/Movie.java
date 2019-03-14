package com.ab.ethioflix.domains;



import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Lob;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;


import lombok.Data;

@Data
@Entity
public class Movie {
		@Id
		@GeneratedValue(strategy = GenerationType.IDENTITY)
		private Long id;
	    private String title;
	    @NotBlank(message="Content can't be empty")
		@NotNull
		@Lob
		@Size(min=5,  max =5000, message="Description must be at least 5 characters long")
		private String description;
	 private String picturePath;
	 private Date createdAt = new Date();

}
