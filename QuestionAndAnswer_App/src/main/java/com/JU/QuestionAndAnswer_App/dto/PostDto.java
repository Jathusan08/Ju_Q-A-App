package com.JU.QuestionAndAnswer_App.dto;

import java.util.Date;

import lombok.Builder;
import lombok.Data;

@Data // lombook
@Builder
public class PostDto {


	private Long id;	
	
	private String title;
	
	private String url;
	
	private String content;
	
	private String shortDescription;
	
	private Date dateCreated;
	
	private Date lastUpdated;
	
	
}
