package com.JU.QuestionAndAnswer_App.service;

import java.util.ArrayList; 
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.JU.QuestionAndAnswer_App.dao.PostRepository;
import com.JU.QuestionAndAnswer_App.dto.PostDto;
import com.JU.QuestionAndAnswer_App.entity.Post;
import com.JU.QuestionAndAnswer_App.mapper.PostMapper;

@Service
public class PostServiceImpl implements PostService {
	
	private PostRepository postRepository;
	
	
	public PostServiceImpl() {}
	
	@Autowired
	public PostServiceImpl(PostRepository postRepository) { // injecting the dependency
		
		this.postRepository =  postRepository;
		
	}

	@Override
	public List<PostDto> getAllPosts() {
	
		List<Post> posts = this.postRepository.findAll();
		
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
		
		this.postRepository.save(newPost);
		
	}

	@Override
	public PostDto findPostById(Long postId) {
		
		Post post = this.postRepository.findById(postId).get();
		
		//convert Post entity to PostDto 
		
		return PostMapper.mapToPostDto(post);
			
	
	}

	@Override
	public void updatePost(PostDto postDto) {
		
		// convert PostDto to Post entity 
		Post updatePost = PostMapper.mapToPost(postDto);
		
		this.postRepository.save(updatePost);
		
	}

	@Override
	public void deletePost(Long postId) {
		
		// now delete
		this.postRepository.deleteById(postId);
		
	
		
	}

	@Override
	public PostDto findPostByUrl(String postUrl) {
	
		Post post = this.postRepository.findByUrl(postUrl).get();
		
		// convert PostDto to Post entity 
		return PostMapper.mapToPostDto(post);
		
	}

}
