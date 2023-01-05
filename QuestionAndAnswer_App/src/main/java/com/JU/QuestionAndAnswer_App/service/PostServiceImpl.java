package com.JU.QuestionAndAnswer_App.service;

import java.util.ArrayList; 
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.JU.QuestionAndAnswer_App.dao.PostRepository;
import com.JU.QuestionAndAnswer_App.dao.UserRepository;
import com.JU.QuestionAndAnswer_App.dto.PostDto;
import com.JU.QuestionAndAnswer_App.entity.Post;
import com.JU.QuestionAndAnswer_App.entity.User;
import com.JU.QuestionAndAnswer_App.mapper.PostMapper;
import com.JU.QuestionAndAnswer_App.util.SecurityUtils;

@Service
public class PostServiceImpl implements PostService {
	
	private PostRepository postRepository;
	private UserRepository userRepository;
	
	
	public PostServiceImpl() {}
	
	@Autowired
	public PostServiceImpl(PostRepository postRepository,  UserRepository userRepository) { // injecting the dependency
		
		this.postRepository =  postRepository;
		this.userRepository =  userRepository;
		
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
		
		// SecurityContextHolder has a current logged-in user email ID.
		String email = SecurityUtils.getCurrentUser().getUsername();
		
		// let retrieve the user 
		User user = this.userRepository.findByEmail(email);
		
		// convert PostDto to Post entity 
		Post newPost = PostMapper.mapToPost(postDto);
		
		// now let set the user
		newPost.setCreatedBy(user);
		
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
		
		// SecurityContextHolder has a current logged-in user email ID.
				String email = SecurityUtils.getCurrentUser().getUsername();
				
				// let retrieve the user 
				User user = this.userRepository.findByEmail(email);
		
		// convert PostDto to Post entity 
		Post updatePost = PostMapper.mapToPost(postDto);
		
		updatePost.setCreatedBy(user);
		
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

	@Override
	public List<PostDto> searchPosts(String query) {
		// TODO Auto-generated method stub
		List<Post> posts = this.postRepository.searchPosts(query);
		
		List<PostDto> postDtos = new ArrayList<>();
		
		for(Post post: posts) {
			
			postDtos.add(PostMapper.mapToPostDto(post));
		}
		
		return postDtos;
	}

	@Override
	public List<PostDto> findPostsByUser() {
		
		/// get the current logged user email
		String email = SecurityUtils.getCurrentUser().getUsername();
		
		User createdBy = this.userRepository.findByEmail(email);
		
		// now let get the useId
		Long userId = createdBy.getId();
		
		List<Post> posts = this.postRepository.findPostsByUser(userId);
		
		// let convert post to postDto
		List<PostDto> postDtos = new ArrayList<>();
		
		for(Post post: posts){
			
			postDtos.add(PostMapper.mapToPostDto(post));
		}
		
		return postDtos;
	}

}
