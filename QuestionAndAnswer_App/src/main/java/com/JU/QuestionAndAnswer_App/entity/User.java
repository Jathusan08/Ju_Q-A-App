package com.JU.QuestionAndAnswer_App.entity;



import java.util.ArrayList;   
import java.util.List;

import jakarta.persistence.JoinColumn;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity // to tell spring this is entity class that is mapped to database
@Table(name="_users") 
public class User {
	
	@Id // to tell this field is ID meaning primary key which is uniques as well can not contain null value
	@GeneratedValue(strategy=GenerationType.IDENTITY) // autoincrement
	@Column(name="user_id")
	private Long id;
	
	@Column(name="name", nullable = false) 
	private String name;
	
	@Column(name="email", nullable = false, unique=true) 
	private String email;
	
	@Column(name="passwords", nullable = false) 
	private String password;
	
	
	//settting up realtionsip MANY TO MANY
	@ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL) // EAGER when we retrieve User object from database, then it also retrieves its roles
	@JoinTable(name="_users_roles", 
			   joinColumns = {@JoinColumn(name="user_id", referencedColumnName = "user_id")},
			   inverseJoinColumns= {@JoinColumn(name="role_id", referencedColumnName = "role_id")})
	private List<Role> roles = new ArrayList<>(); 

}
