package com.example.springredditclone.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.springredditclone.dto.PostRequest;
import com.example.springredditclone.dto.PostResponse;
import com.example.springredditclone.exceptions.PostNotFoundException;
import com.example.springredditclone.exceptions.SubredditNotFoundException;
import com.example.springredditclone.exceptions.UsernameNotFoundException;
import com.example.springredditclone.mapper.PostMapper;
import com.example.springredditclone.model.Post;
import com.example.springredditclone.model.Subreddit;
import com.example.springredditclone.model.User;
import com.example.springredditclone.repository.PostRepository;
import com.example.springredditclone.repository.SubredditRepository;
import com.example.springredditclone.repository.UserRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class PostService {
	private final SubredditRepository subredditRepository;
	private final AuthService authService;
	private final PostMapper postMapper;
	private final PostRepository postRepository;
	private final UserRepository userRepository;
	
	@Transactional
	public void save(PostRequest postRequest) {
		Subreddit subreddit = subredditRepository.findByName(postRequest.getSubredditName())
		.orElseThrow(()-> new SubredditNotFoundException(postRequest.getSubredditName()));
		User currentUser = authService.getCurrentUser();//User is not being added correctly
		Post post = postMapper.Map(postRequest, subreddit, currentUser);
		System.out.print(subreddit);
		System.out.print(currentUser.toString());
		System.out.print(post);//HasPostName right but is not saved correctly
		postRepository.save(post);
		
	}
	@Transactional(readOnly=true)
	public PostResponse getPost(Long id) {
		Post post = postRepository.findById(id)
				.orElseThrow(()->new PostNotFoundException(id.toString()));
		return postMapper.mapToDto(post);//Issue was with the Dto not having the same fieldnames as the type
	}
	@Transactional(readOnly = true)
	public List<PostResponse> getAllPosts() {
		return postRepository.findAll()
				.stream()
				.map(postMapper::mapToDto)
				.collect(Collectors.toList());
	}
	@Transactional(readOnly=true)
	public List<PostResponse> getPostsBySubreddit(Long subredditId) {
		//error here looking for subreddit
		Subreddit subreddit = subredditRepository.findById(subredditId)
				.orElseThrow(()-> new SubredditNotFoundException(subredditId.toString()));
		List<Post> posts = postRepository.findAllBySubreddit(subreddit);
		return posts.stream().map(postMapper::mapToDto).collect(Collectors.toList());
				
	}
	@Transactional (readOnly=true)
	public List<PostResponse> getPostsByUsername(String username) {
		User user = userRepository.findByUsername(username)
				.orElseThrow(() -> new UsernameNotFoundException(username));
		return postRepository.findByUser(user)
				.stream() 
				.map(postMapper::mapToDto)
				.collect(Collectors.toList());
	}
}
