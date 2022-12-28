package com.JU.QuestionAndAnswer_App.dto;

import java.util.Date;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data // lombook
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PostDto {


	private Long id;	
	
	@NotEmpty(message="Post title should not be empty") // Validation
	private String title;
	
	private String url;
	
	@NotEmpty(message="Post content should not be empty") // Validation
	private String content;
	
	@NotEmpty(message="Post short description should not be empty") // Validation
	private String shortDescription;
	
	private Date dateCreated;
	
	private Date lastUpdated;
	
	
}
