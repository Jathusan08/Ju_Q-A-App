package com.JU.QuestionAndAnswer_App.dto;
 

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
public class SignUpDto {

	private Long id;

	@NotEmpty(message="First Name should not be empty") // Validation
	private String firstName;
	
	@NotEmpty(message="Last Name should not be empty") // Validation
	private String lastName;
	
	@NotEmpty(message="Email should not be empty") // Validation
	@Email( message="please use the correct format")
	private String email;
	
	@NotEmpty(message="Password should not be empty") // Validation
	private String password;
}
