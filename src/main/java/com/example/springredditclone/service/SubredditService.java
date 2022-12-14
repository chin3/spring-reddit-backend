package com.example.springredditclone.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.springredditclone.dto.SubredditDto;
import com.example.springredditclone.exceptions.SpringRedditException;
import com.example.springredditclone.mapper.SubredditMapper;
import com.example.springredditclone.model.Subreddit;
import com.example.springredditclone.repository.SubredditRepository;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@AllArgsConstructor
@Slf4j
public class SubredditService {
	private final SubredditRepository subredditRepository;
	private final SubredditMapper subredditMapper;
	@Transactional
	public SubredditDto save(SubredditDto subredditDto) {
		Subreddit save = subredditRepository.save(subredditMapper.mapDtoToSubreddit(subredditDto));
		subredditDto.setId(save.getId());
		return subredditDto;
	}


	@Transactional(readOnly = true)
	public List<SubredditDto> getAll() {
		return subredditRepository.findAll().stream()
		.map(subredditMapper::mapSubredditToDto)
		.collect(Collectors.toList());
	}


	public SubredditDto getSubreddit(Long id) {
		Subreddit subreddit = subredditRepository.findById(id)
				.orElseThrow(()-> new SpringRedditException("No Subreddit Found with id - " +id));
		
		return subredditMapper.mapSubredditToDto(subreddit);
	}
//	private SubredditDto mapToDto(Subreddit subreddit) {
//		return SubredditDto.builder().name(subreddit.getName())
//				.id(subreddit.getId())
//				.numberOfPosts(subreddit.getPosts().size())
//				.build();
//	}
//	public Subreddit mapSubredditDto(SubredditDto subredditDto) {
//		return Subreddit.builder().name(subredditDto.getName()).description(subredditDto.getDescription()).build();
//	}
}
