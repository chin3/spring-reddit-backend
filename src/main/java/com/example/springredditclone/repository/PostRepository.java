package com.example.springredditclone.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.springredditclone.model.Post;
import com.example.springredditclone.model.Subreddit;
import com.example.springredditclone.model.User;

@Repository
public interface PostRepository extends JpaRepository<Post,Long>{
	List<Post> findAllBySubreddit(Subreddit subreddit);
	List<Post> findByUser(User user);
}
