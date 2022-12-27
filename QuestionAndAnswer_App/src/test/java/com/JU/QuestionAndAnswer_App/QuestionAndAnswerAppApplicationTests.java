package com.JU.QuestionAndAnswer_App;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.AfterEach; 
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.ModelAndViewAssert;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.web.servlet.ModelAndView;

import com.JU.QuestionAndAnswer_App.service.PostService;

@TestPropertySource("/application-test.properties")// this will allow me to load properties during 
@AutoConfigureMockMvc
@SpringBootTest(classes=QuestionAndAnswerAppApplication.class)
class QuestionAndAnswerAppApplicationTests {

	@Autowired
	private PostService postService;
	
	@Autowired
	private JdbcTemplate jdbc;
	
	@Autowired
	private MockMvc mockMvc;
	
	
	@BeforeEach()
	void beforeEach() {
		

		// insert sample data- we'll make use of JDBC template and execute JDBC operations
	this.jdbc.execute("INSERT INTO posts (title, url, content, shortDescription, date_created)\n"
			+ "VALUES ('Array?', 'https://www.mygreatlearning.com/blog/what-is-an-array-learn-more-in-one-read/', "
			+ "'An array is a collection of similar data elements stored at contiguous memory locations. It is the simplest "
			+ "data structure where each data element can be accessed directly by only using its index number.', "
			+ "'need to research about arrays', NOW())");
	
	
	this.jdbc.execute("INSERT INTO posts (title, url, content, shortDescription, date_created)\n"
			+ "VALUES ('If Statement?', 'https://www.thinkautomation.com/eli5/a-beginners-guide-to-if-statements/', "
			+ "'If statements are logical blocks used within programming. "
			+ "They’re conditional statements that tell a computer what to do with certain information. "
			+ "In other words, they let a program make decisions while it’s running.', "
			+ "'need to refresh about If statement', NOW())");
	
	
	this.jdbc.execute("INSERT INTO posts (title, url, content, shortDescription, date_created)\n"
			+ "VALUES ('Do you write your unit tests before or after coding a piece of functionality?', "
			+ "'https://www.thinkautomation.com/eli5/a-beginners-guide-to-if-statements/', "
			+ "'I was wondering when most people wrote their unit tests, if at all. I usually write tests after writing my "
			+ "initial code to make sure it works like its supposed to. I then fix what is broken.', "
			+ "'need to clarity about testing', NOW())");

	}
	
	
	@AfterEach
	public void setupAfterTransaction() { 
		// delete the sample data after each test so clean uo
		 jdbc.execute("DELETE FROM posts");
	
	}
	
	
	@Test
	 void getPostHttpRequest() throws Exception{
		
		// we should have 3 data on H2 Embeded database
		
		assertEquals(3, this.postService.getAllPosts().size());	
		
		// Web related testing
		 MvcResult mvcResult = this.mockMvc.perform(MockMvcRequestBuilders.get("/admin/posts"))
	                .andExpect(status().isOk()).andReturn();
		 
		 // with MVC result i can get the results here to get the model and view
		 ModelAndView mav = mvcResult.getModelAndView();
		 
		 // now I have the model and view we can perform some assets which is to test
		 ModelAndViewAssert.assertViewName(mav, "Posts_For_Admin");
		
	}

}
