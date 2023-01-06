package com.JU.QuestionAndAnswer_App.entity;

import java.util.ArrayList;  
import java.util.List;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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
@Table(name="_roles") 
public class Role {

	@Id // to tell this field is ID meaning primary key which is uniques as well can not contain null value
	@GeneratedValue(strategy=GenerationType.IDENTITY) // autoincrement
	@Column(name="role_id")
	private Long id;
	
	@Column(name="name", nullable = false) 
	private String name;
	
	@ManyToMany(mappedBy="roles") // EAGER when we retrieve User object from database, then it also retrieves its roles
	private List<User> users = new ArrayList<>(); 
	
}
