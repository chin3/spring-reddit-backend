package com.example.springredditclone.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.springredditclone.model.Post;
import com.example.springredditclone.model.User;
import com.example.springredditclone.model.Vote;

@Repository
public interface VoteRepository extends JpaRepository<Vote,Long>{
	Optional<Vote> findTopByPostAndUserOrderByVoteIdDesc(Post post,User currentUser);
}
