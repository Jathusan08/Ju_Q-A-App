package com.JU.QuestionAndAnswer_App.dto;

import java.util.Date; 


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CommentDto {

	private Long id;
	
	@NotEmpty(message="Name should not be empty") // Validation
	private String name;
	
	@NotEmpty(message="Email should not be empty") // Validation
	@Email( message="please use the correct format")
	private String email;
	
	@NotEmpty(message="Content should not be empty") // Validation
	private String content;
	
	private Date dateCreated;

	private Date lastUpdated;

	
}
