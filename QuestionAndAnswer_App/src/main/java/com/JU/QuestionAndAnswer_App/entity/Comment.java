package com.JU.QuestionAndAnswer_App.entity;

import java.util.Date;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
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
@Entity // to tell spring this is entity class that is mapped to database
@Table(name="comments") 
public class Comment {
	
	@Id // to tell this field is ID meaning primary key which is uniques as well can not contain null value
	@GeneratedValue(strategy=GenerationType.IDENTITY) // autoincrement
	@Column(name="comment_id") 
	private Long id;
	
	@Column(name="name", nullable = false) 
	private String name;
	
	@Column(name="email", nullable = false)
	private String email;
	
	@Column(name="content", nullable = false) 
	private String content;
	
	@Column(name="date_created") // to map the fields to the customer table columns and need to 
	@CreationTimestamp // these are special annotations Hibernate will automatically manage the timestamps, 
	//so there no need for the developer to mannually call these method or set these fields here
	private Date dateCreated;
	
	@Column(name="last_updated") // to map the fields to the customer table columns and need to 
	@UpdateTimestamp // these are special annotations Hibernate will automatically manage the timestamps, 
	//so there no need for the developer to manually call these method or set these fields here
	private Date lastUpdated;
	
	@ManyToOne() // many commments belont to 1 post
	@JoinColumn(name="post_id", nullable = false)
	private Post post;



}
