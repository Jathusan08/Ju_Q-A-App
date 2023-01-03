package com.JU.QuestionAndAnswer_App;

import static org.junit.jupiter.api.Assertions.assertEquals; 
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertIterableEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.ModelAndViewAssert;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.web.servlet.ModelAndView;


import com.JU.QuestionAndAnswer_App.dto.PostDto;
import com.JU.QuestionAndAnswer_App.service.CommentService;
import com.JU.QuestionAndAnswer_App.service.PostService;


@TestPropertySource("/application-test.properties")// this will allow me to load properties during 
@AutoConfigureMockMvc
@SpringBootTest(classes=QuestionAndAnswerAppApplication.class)
class QuestionAndAnswerAppApplicationTests {
	
	// private static MockHttpServletRequest request;
	
	private  MockHttpServletRequest request;

	@Autowired
	private PostService postService;
	
	@Autowired
	private CommentService commentService;
	
	@Autowired
	private JdbcTemplate jdbc;
	
	@Autowired
	private MockMvc mockMvc;
	
	
	@BeforeEach()
	void beforeEach() {
		

		// insert sample data- we'll make use of JDBC template and execute JDBC operations
	this.jdbc.execute("INSERT INTO posts (post_id, title, url, content, shortDescription, date_created)\n"
			+ "VALUES (1, 'What is ARRAY1?', 'What is ARRAY2?', 'What is ARRAY3?', 'What is ARRAY4?', NOW())");
	
	
	this.jdbc.execute("INSERT INTO posts (post_id,title, url, content, shortDescription, date_created)\n"
			+ "VALUES (2, 'If Statement?', 'https://www.thinkautomation.com/eli5/a-beginners-guide-to-if-statements/', "
			+ "'If statements are logical blocks used within programming. "
			+ "They’re conditional statements that tell a computer what to do with certain information. "
			+ "In other words, they let a program make decisions while it’s running.', "
			+ "'need to refresh about If statement', NOW())");
	
	
	this.jdbc.execute("INSERT INTO posts (post_id,title, url, content, shortDescription, date_created)\n"
			+ "VALUES (3, 'Do you write your unit tests before or after coding a piece of functionality?', "
			+ "'https://www.thinkautomation.com/eli5/a-beginners-guide-to-if-statements/', "
			+ "'I was wondering when most people wrote their unit tests, if at all. I usually write tests after writing my "
			+ "initial code to make sure it works like its supposed to. I then fix what is broken.', "
			+ "'need to clarity about testing', NOW())");
	
	
	this.jdbc.execute("INSERT INTO comments (comment_id, name, email, content, date_created, post_id )\n"
			+ "VALUES (1, 'Jeff', 'Jeff@hotmail.com', 'Array can hold more than 1 values', NOW(),1)");
	
	
	this.jdbc.execute("INSERT INTO comments (comment_id, name, email, content, date_created, post_id )\n"
			+ "VALUES (2, 'Chris', 'Chris@hotmail.com', 'If statement are set of instructions that you a computer or machine what to do and what not to do', NOW(),2)\n"
			+ "");
	
	
	this.jdbc.execute("INSERT INTO comments (comment_id, name, email, content, date_created, post_id )\n"
			+ "VALUES (3, 'Billa', 'Billa@hotmail.com', 'it’s recommended to write test using Test Driven Development so "
			+ "that you can fix any issues earlier before the coding goes to the production', NOW(),3)");
	
	
	this.jdbc.execute("INSERT INTO _users (user_id, name, email, passwords)\n"
			+ "VALUES (1, 'John', 'John@hotmail.com', 'test123')");

	
	this.jdbc.execute("INSERT INTO _users (user_id, name, email, passwords)\n"
			+ "VALUES (2, 'Mike', 'Mike@hotmail.com', 'test123')");
	
	this.jdbc.execute("INSERT INTO _users (user_id, name, email, passwords)\n"
			+ "VALUES (3, 'David', 'David@hotmail.com', 'test123')");

	this.jdbc.execute("INSERT INTO _roles(role_id, name)\n"
			+ "VALUES (1, 'ADMIN')");
	
	this.jdbc.execute("INSERT INTO _roles(role_id, name)\n"
			+ "VALUES (2, 'MEMBER')");
	
	this.jdbc.execute("INSERT INTO _roles(role_id, name)\n"
			+ "VALUES (3, 'GUEST')");
	
	}
	
	
	@AfterEach
	public void setupAfterTransaction() { 
		// delete the sample data after each test so clean uo
		 jdbc.execute("DELETE FROM comments");
		 jdbc.execute("DELETE FROM posts");
		 jdbc.execute("DELETE FROM _users_roles");
		 jdbc.execute("DELETE FROM _users");
		 jdbc.execute("DELETE FROM _roles");
		
	
	}
	
//	@BeforeAll() // this is a setup annotation that we can use it to run only for this test class method must be declared as static Also must be public and return void
//	public static void setup() {
//		request = new MockHttpServletRequest(); // this is object that we can use to make mock servlet request and I can populate this request 
//												// with some data, so here we set up some parameters for firstname, lastname and email. This is
//												// like a reusable object that we can make use of for sending request to a guven controller or 
//												// endpoint
//		request.setParameter("title", "Which Programming Lanagauge to learn for beginner?");
//		request.setParameter("shortDescription", "Programming Lanagauge");
//		request.setParameter("content", "I would like to know what Programming Lanagauge to learn for beginner?. Also can "
//				+ "you kinldy send me a resource where I can able to start with it?");
//	}
	
	
	@Test
	 void showAllPostsForAdminHttpRequest() throws Exception{
		
		// we should have 3 data on H2 Embeded database
		
		assertEquals(3, this.postService.getAllPosts().size());	
		
		// Web related testing
		 MvcResult mvcResult = this.mockMvc.perform(MockMvcRequestBuilders.get("/admin/posts"))
	                .andExpect(status().isOk()).andReturn();
		 
		 // with MVC result i can get the results here to get the model and view
		 ModelAndView mav = mvcResult.getModelAndView();
		 
		 // now I have the model and view we can perform some assets which is to test
		 ModelAndViewAssert.assertViewName(mav, "Admin/Posts_For_Admin");
		
	}
	
	@Test
	 void showPostFormForAdminHttpRequest() throws Exception {
		
		
		// Web related testing
				 MvcResult mvcResult = this.mockMvc.perform(MockMvcRequestBuilders.get("/admin/posts/newPost"))
			                .andExpect(status().isOk()).andReturn();
				 
				 // with MVC result i can get the results here to get the model and view
				 ModelAndView mav = mvcResult.getModelAndView();
				 
				 // now I have the model and view we can perform some assets which is to test
				 ModelAndViewAssert.assertViewName(mav, "Admin/Create_post_For_Admin");
		
		
	}
	
	
	@Test
	 void createInvalidPostForAdminHttpRequest() throws Exception {
		
		
		request = new MockHttpServletRequest(); // this is object that we can use to make mock servlet request and I can populate this request 
//		// with some data, so here we set up some parameters for title, shortDescription and content. This is
//		// like a reusable object that we can make use of for sending request to a guven controller or 
//		// endpoint
		
	request.setParameter("title", "");
	request.setParameter("shortDescription", "");
	request.setParameter("content", "");
		

//		// create a post using an HTTP request and post data to a mapping on our controller and veorfy using DAO
		MvcResult mvcResult = this.mockMvc.perform(post("/admin/posts")
				  .contentType(MediaType.APPLICATION_JSON)
				  .param("title", request.getParameterValues("title"))
				  .param("shortDescription", request.getParameterValues("shortDescription"))
				  .param("content", request.getParameterValues("content")))
				.andExpect(status().isOk()).andReturn();
				
              
		 // with MVC result i can get the results here to get the model and view
		 ModelAndView mav = mvcResult.getModelAndView();
		 
		 // test to see
		 ModelAndViewAssert.assertViewName(mav, "Admin/Create_post_For_Admin");
		 

	}
	
	@Test
	 void createValidPostForAdminHttpRequest() throws Exception {
		
		
		request = new MockHttpServletRequest(); // this is object that we can use to make mock servlet request and I can populate this request 
//		// with some data, so here we set up some parameters for title, shortDescription and content. This is
//		// like a reusable object that we can make use of for sending request to a guven controller or 
//		// endpoint
		
		request.setParameter("title", "Which Programming Lanagauge to learn for beginner?");
		request.setParameter("shortDescription", "Programming Lanagauge");
		request.setParameter("content", "I would like to know what Programming Lanagauge to learn for beginner?. Also can "
				+ "you kinldy send me a resource where I can able to start with it?");
		

//		// create a post using an HTTP request and post data to a mapping on our controller 
		MvcResult mvcResult = this.mockMvc.perform(post("/admin/posts")
				  .contentType(MediaType.APPLICATION_JSON)
				  .param("title", request.getParameterValues("title"))
				  .param("shortDescription", request.getParameterValues("shortDescription"))
				  .param("content", request.getParameterValues("content")))
				.andExpect(status().is3xxRedirection()).andReturn();
				
             
		 // with MVC result i can get the results here to get the model and view
		 ModelAndView mav = mvcResult.getModelAndView();
		 
		 // test to see
		 ModelAndViewAssert.assertViewName(mav, "redirect:/admin/posts");
		 
		 
		 // let check if the new post been added as we already have 3 posts already now we should 1 more
		 
			assertEquals(4, this.postService.getAllPosts().size());	

	}
	
	@Test
	 void showEditPostFormForAdminHttpRequest() throws Exception {
		
		int i = 1;
		Long id =Long.valueOf( i);
		
		this.postService.findPostById(id).getId();
		
		// test if Id 1 exist
		 assertEquals(1,this.postService.findPostById(id).getId());
		
		// perform HTTP request - /admin/posts/{postId}/edit
				 MvcResult mvcResult = this.mockMvc.perform(MockMvcRequestBuilders.get("/admin/posts/{postId}/edit",1))
						  .andExpect(status().isOk()).andReturn();
				 
				// with MVC result i can get the results here to get the model and view
				 ModelAndView mav = mvcResult.getModelAndView();
				 
				 ModelAndViewAssert.assertViewName(mav, "Admin/Edit_post_For_Admin"); 
		
	}
	
	
	@Test
	 void editValidPostFormForAdminHttpRequest() throws Exception {
		
		int i = 1;
		Long id =Long.valueOf( i);
		
		this.postService.findPostById(id).getId();
		
		// test if Id 1 exist
		 assertEquals(1,this.postService.findPostById(id).getId());
		
		// perform HTTP request - /admin/posts/{postId}/edit
				 MvcResult mvcResult = this.mockMvc.perform(MockMvcRequestBuilders.post("/admin/posts/{postId}",1)
						  .contentType(MediaType.APPLICATION_JSON)
						  .param("title", "What is OOP1?")
						  .param("shortDescription", "What is OOP2?")
						  .param("content", "What is OOP3?"))
						 .andExpect(status().is3xxRedirection()).andReturn();
				 
				// with MVC result i can get the results here to get the model and view
				 ModelAndView mav = mvcResult.getModelAndView();
				 
				 ModelAndViewAssert.assertViewName(mav, "redirect:/admin/posts"); 
				 
				 // now let see if the content changed
				 assertEquals("What is OOP1?",this.postService.findPostById(id).getTitle());
				 assertEquals("What is OOP2?",this.postService.findPostById(id).getShortDescription());
				 assertEquals("What is OOP3?",this.postService.findPostById(id).getContent());
		
	}
	
	@Test
	 void editInValidPostFormForAdminHttpRequest() throws Exception {
		
		int i = 1;
		Long id =Long.valueOf( i);
		
		this.postService.findPostById(id).getId();
		
		// test if Id 1 exist
		 assertEquals(1,this.postService.findPostById(id).getId());
		
		// perform HTTP request - /admin/posts/{postId}/edit
				 MvcResult mvcResult = this.mockMvc.perform(MockMvcRequestBuilders.post("/admin/posts/{postId}",1)
						  .contentType(MediaType.APPLICATION_JSON)
						  .param("title", "")
						  .param("shortDescription", "")
						  .param("content", ""))
						 .andExpect(status().isOk()).andReturn();
				 
				// with MVC result i can get the results here to get the model and view
				 ModelAndView mav = mvcResult.getModelAndView();
				 
				 ModelAndViewAssert.assertViewName(mav, "Admin/Edit_post_For_Admin"); 
				 
				 // now let see if the content changed , it shoudldnt as given empty string
				 assertEquals("What is ARRAY1?",this.postService.findPostById(id).getTitle());
				 assertEquals("What is ARRAY3?",this.postService.findPostById(id).getContent());
				 assertEquals("What is ARRAY4?", this.postService.findPostById(id).getShortDescription());
				
	}
	
	@Test
	 void updatePostFormForAdminHttpRequest() throws Exception {
		
		
		int i = 1;
		Long id =Long.valueOf(i);
		
		PostDto postDto = this.postService.findPostById(id);
		
		postDto.setTitle("What is OOP1?");
		postDto.setShortDescription("What is OOP2?");
		postDto.setContent("What is OOP3?");
		
		this.postService.updatePost(postDto);
		
		
		// test to see if the update is succesfull
		 assertEquals("What is OOP1?",this.postService.findPostById(id).getTitle());
		 assertEquals("What is OOP2?",this.postService.findPostById(id).getShortDescription());
		 assertEquals("What is OOP3?", this.postService.findPostById(id).getContent());
		
		
	}
	
	
	@Test 
	 void deletePostFormForAdminHttpRequest() throws Exception {
		
		int i = 1;
		Long id =Long.valueOf(i);
		
		
		// Delete the grade via HTTP request 
		 MvcResult mvcResult = this.mockMvc.perform(MockMvcRequestBuilders.get("/admin/posts/{postId}/delete",id))
				 .andExpect(status().is3xxRedirection()).andReturn();
		 
		 // with MVC result i can get the results here to get the model and view
		 ModelAndView mav = mvcResult.getModelAndView();
	 
//		// now I have the model and view we can perform some assets which is to test
		 ModelAndViewAssert.assertViewName(mav, "redirect:/admin/posts"); // the grade we passed is invalid as it does not exist so it should return to 'error' view page insteaed
		 
//		 // now we should not have id 2 question blog in our H2 database as we have deleted 1 already
		 
		 assertEquals(2, this.postService.getAllPosts().size());

	}
	
	@Test 
	 void viewPostFormbyUrlForAdminHttpRequest() throws Exception {
		
		int i = 1;
		Long id =Long.valueOf(i);
		
	//	String url = this.postService.findPostById(id).getUrl();
		
	//	System.out.println("url: " + url);
		
	//	assertEquals(url,this.postService.findPostById(id).getUrl());
		
	 MvcResult mvcResult = this.mockMvc.perform(MockMvcRequestBuilders.get("/admin/posts/{postUrl}/view",this.postService.findPostById(id).getUrl()))
			 .andExpect(status().isOk()).andReturn();
	 
	 // with MVC result i can get the results here to get the model and view
	 ModelAndView mav = mvcResult.getModelAndView();
 
//	// now I have the model and view we can perform some assets which is to test
	 ModelAndViewAssert.assertViewName(mav, "Admin/View_post_For_Admin");
		
		
	}
	
	
	@Test 
	 void searchPostFormForAdminHttpRequest() throws Exception {
		
		 MvcResult mvcResult = this.mockMvc.perform(MockMvcRequestBuilders.get("/admin/posts/search?query={query}","S"))
				 .andExpect(status().isOk()).andReturn();
		 
		 // with MVC result i can get the results here to get the model and view
		 ModelAndView mav = mvcResult.getModelAndView();
	 
//		// now I have the model and view we can perform some assets which is to test
		 ModelAndViewAssert.assertViewName(mav, "Admin/Posts_For_Admin");
		
		
	}
	
	
	@Test
	 void showAllCommentsForAdminHttpRequest() throws Exception{
		
		// we should have 3 data on H2 Embeded database
		
		assertEquals(3, this.commentService.findAllComments().size());	
		
		// Web related testing
		 MvcResult mvcResult = this.mockMvc.perform(MockMvcRequestBuilders.get("/admin/posts/comments"))
	                .andExpect(status().isOk()).andReturn();
		 
		 // with MVC result i can get the results here to get the model and view
		 ModelAndView mav = mvcResult.getModelAndView();
		 
		 // now I have the model and view we can perform some assets which is to test
		 ModelAndViewAssert.assertViewName(mav, "Admin/View_comments_For_Admin");
		
	}
	
	@Test 
	 void deleteACommentForAdminHttpRequest() throws Exception {
		
		int i = 1;
		Long id =Long.valueOf(i);
		
		
		// Delete the grade via HTTP request 
		 MvcResult mvcResult = this.mockMvc.perform(MockMvcRequestBuilders.get("/admin/posts/comments/{commentId}/delete",id))
				 .andExpect(status().is3xxRedirection()).andReturn();
		 
		 // with MVC result i can get the results here to get the model and view
		 ModelAndView mav = mvcResult.getModelAndView();
	 
//		// now I have the model and view we can perform some assets which is to test
		 ModelAndViewAssert.assertViewName(mav, "redirect:/admin/posts/comments"); // the grade we passed is invalid as it does not exist so it should return to 'error' view page insteaed
		 
//		 // now we should not have id 1 comment in our H2 database as we have deleted 1 already
		 
		 assertEquals(2, this.commentService.findAllComments().size());

	}
	
//////////////////////// CLIENT /////////////////////////////////////////	
	
	@Test
	 void showAllQAPostsForClientHttpRequest() throws Exception{
				
		// Web related testing
		 MvcResult mvcResult = this.mockMvc.perform(MockMvcRequestBuilders.get("/"))
	                .andExpect(status().isOk()).andReturn();
		 
		 // with MVC result i can get the results here to get the model and view
		 ModelAndView mav = mvcResult.getModelAndView();
		 
		 // now I have the model and view we can perform some assets which is to test
		 ModelAndViewAssert.assertViewName(mav, "QA/QA_Post_For_Client");
		
	}
	

	
	
	@Test 
	 void searchQAPostFormForClientHttpRequest() throws Exception {
		
		 MvcResult mvcResult = this.mockMvc.perform(MockMvcRequestBuilders.get("/page/search?query={query}","S"))
				 .andExpect(status().isOk()).andReturn();
		 
		 // with MVC result i can get the results here to get the model and view
		 ModelAndView mav = mvcResult.getModelAndView();
	 
//		// now I have the model and view we can perform some assets which is to test
		 ModelAndViewAssert.assertViewName(mav, "QA/QA_Post_For_Client");	
		
	}
	
	
	@Test 
	 void viewQAPostFormbyUrlForClientHttpRequest() throws Exception {
		
		int i = 1;
		Long id =Long.valueOf(i);
		
		
	 MvcResult mvcResult = this.mockMvc.perform(MockMvcRequestBuilders.get("/post/{postUrl}",this.postService.findPostById(id).getUrl()))
			 .andExpect(status().isOk()).andReturn();
	 
	 // with MVC result i can get the results here to get the model and view
	 ModelAndView mav = mvcResult.getModelAndView();

//	// now I have the model and view we can perform some assets which is to test
	 ModelAndViewAssert.assertViewName(mav, "QA/View_post_For_Client");
		
		
	}
	
	@Test
	 void createACommentforPostForClientHttpRequest() throws Exception {
		
		int i = 1;
		Long id =Long.valueOf( i);
		
		PostDto post = this.postService.findPostById(id);
		
		
		// perform HTTP request - /admin/posts/{postId}/edit
				 MvcResult mvcResult = this.mockMvc.perform(MockMvcRequestBuilders.post("/{postUrl}/comments",post.getUrl())
						  .contentType(MediaType.APPLICATION_JSON)
						  .param("name", "Rocky")
						  .param("email", "Rocky@hotmail.com")
						  .param("content", "Nice one !"))
						 .andExpect(status().is3xxRedirection()).andReturn();
				 
				// with MVC result i can get the results here to get the model and view
				 ModelAndView mav = mvcResult.getModelAndView();
				 
				 ModelAndViewAssert.assertViewName(mav, "redirect:/post/"+post.getUrl()); 
				 				 						
	}
	
	
	@Test
	 void createAnInvalidCommentforPostForClientHttpRequest() throws Exception {
		
		int i = 1;
		Long id =Long.valueOf( i);
		
		PostDto post = this.postService.findPostById(id);
		
	
		
		// perform HTTP request - /admin/posts/{postId}/edit
				 MvcResult mvcResult = this.mockMvc.perform(MockMvcRequestBuilders.post("/{postUrl}/comments",post.getUrl())
						  .contentType(MediaType.APPLICATION_JSON)
						  .param("name", " ")
						  .param("email", " ")
						  .param("content", " "))
						 .andExpect(status().isOk()).andReturn();
				 
				 
				// with MVC result i can get the results here to get the model and view
				 ModelAndView mav = mvcResult.getModelAndView();
				 
				 ModelAndViewAssert.assertViewName(mav, "QA/View_post_For_Client"); 
				 
			
	}
	
	
	@Test
	 void showSignUpFormForClientHttpRequest() throws Exception{
		
		// we should have 3 data on H2 Embeded database
		
		assertEquals(3, this.postService.getAllPosts().size());	
		
		// Web related testing
		 MvcResult mvcResult = this.mockMvc.perform(MockMvcRequestBuilders.get("/signUp"))
	                .andExpect(status().isOk()).andReturn();
		 
		 // with MVC result i can get the results here to get the model and view
		 ModelAndView mav = mvcResult.getModelAndView();
		 
		 // now I have the model and view we can perform some assets which is to test
		 ModelAndViewAssert.assertViewName(mav, "SignUp");
		
	}
	
	@Test
	 void SignUpAnUserHttpRequest() throws Exception{
		
		request = new MockHttpServletRequest(); // this is object that we can use to make mock servlet request and I can populate this request 
//		// with some data, so here we set up some parameters for title, shortDescription and content. This is
//		// like a reusable object that we can make use of for sending request to a guven controller or 
//		// endpoint
		
		request.setParameter("firstName", "David");
		request.setParameter("lastName", "Billa");
		request.setParameter("email", "DavidBilla@hotmail.com");
		request.setParameter("password", "test123");
		

//		// create a post using an HTTP request and post data to a mapping on our controller 
		MvcResult mvcResult = this.mockMvc.perform(post("/signUp/save")
				  .contentType(MediaType.APPLICATION_JSON)
				  .param("firstName", request.getParameterValues("firstName"))
				  .param("lastName", request.getParameterValues("lastName"))
				  .param("email", request.getParameterValues("email"))
				 .param("password", request.getParameterValues("password")))
				.andExpect(status().is3xxRedirection()).andReturn();
				
             
		 // with MVC result i can get the results here to get the model and view
		 ModelAndView mav = mvcResult.getModelAndView();
		 
		 // test to see
		 ModelAndViewAssert.assertViewName(mav, "redirect:/signUp?success");
		
	}

	
	@Test
	 void invalidSignupFields() throws Exception{
		
		request = new MockHttpServletRequest(); // this is object that we can use to make mock servlet request and I can populate this request 
//		// with some data, so here we set up some parameters for title, shortDescription and content. This is
//		// like a reusable object that we can make use of for sending request to a guven controller or 
//		// endpoint
		
		request.setParameter("firstName", "");
		request.setParameter("lastName", "");
		request.setParameter("email", "");
		request.setParameter("password", "");
		

//		// create a post using an HTTP request and post data to a mapping on our controller 
		MvcResult mvcResult = this.mockMvc.perform(post("/signUp/save")
				  .contentType(MediaType.APPLICATION_JSON)
				  .param("firstName", request.getParameterValues("firstName"))
				  .param("lastName", request.getParameterValues("lastName"))
				  .param("email", request.getParameterValues("email"))
				 .param("password", request.getParameterValues("password")))
				.andExpect(status().isOk()).andReturn();
				
            
		 // with MVC result i can get the results here to get the model and view
		 ModelAndView mav = mvcResult.getModelAndView();
		 
		 // test to see
		 ModelAndViewAssert.assertViewName(mav, "SignUp");
		
	}
	
	
	@Test
	 void userAlredySignedUp() throws Exception{
		
		request = new MockHttpServletRequest(); // this is object that we can use to make mock servlet request and I can populate this request 
//		// with some data, so here we set up some parameters for title, shortDescription and content. This is
//		// like a reusable object that we can make use of for sending request to a guven controller or 
//		// endpoint
		
		request.setParameter("firstName", "");
		request.setParameter("lastName", "");
		request.setParameter("email", "John@hotmail.com");
		request.setParameter("password", "");
		

//		// create a post using an HTTP request and post data to a mapping on our controller 
		MvcResult mvcResult = this.mockMvc.perform(post("/signUp/save")
				  .contentType(MediaType.APPLICATION_JSON)
				  .param("firstName", request.getParameterValues("firstName"))
				  .param("lastName", request.getParameterValues("lastName"))
				  .param("email", request.getParameterValues("email"))
				 .param("password", request.getParameterValues("password")))
				.andExpect(status().isOk()).andReturn();
				
           
		 // with MVC result i can get the results here to get the model and view
		 ModelAndView mav = mvcResult.getModelAndView();
		 
		 // test to see
		 ModelAndViewAssert.assertViewName(mav, "SignUp");
		
	}

}
