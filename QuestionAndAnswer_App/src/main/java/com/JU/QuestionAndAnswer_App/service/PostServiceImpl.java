package com.JU.QuestionAndAnswer_App.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.JU.QuestionAndAnswer_App.dao.PostRepository;
import com.JU.QuestionAndAnswer_App.dto.PostDto;
import com.JU.QuestionAndAnswer_App.entity.Post;
import com.JU.QuestionAndAnswer_App.mapper.PostMapper;

@Service
public class PostServiceImpl implements PostService {
	
	private PostRepository productRepository;
	
	
	public PostServiceImpl() {}
	
	@Autowired
	public PostServiceImpl(PostRepository productRepository) { // injecting the dependency
		
		this.productRepository =  productRepository;
		
	}

	@Override
	public List<PostDto> getAllPosts() {
	
		List<Post> posts = this.productRepository.findAll();
		
		// convert list of post into list of PostDto.
		
		List<PostDto> postDtos = new ArrayList<>();
		
			
			for(Post post:posts) {
				
				postDtos.add(PostMapper.mapToPostDto(post));
				
			}
		
		return postDtos;
	}

	@Override
	public void createPost(PostDto postDto) {
		
		// convert PostDto to Post entity 
		Post newPost = PostMapper.mapToPost(postDto);
		
		this.productRepository.save(newPost);
		
	}

}
