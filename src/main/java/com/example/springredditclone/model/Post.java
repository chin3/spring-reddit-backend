package com.example.springredditclone.model;


import java.time.Instant;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotBlank;

import javax.persistence.Id;
import org.springframework.lang.Nullable;

import lombok.AllArgsConstructor;//Generates all args
import lombok.Builder;//Generates builder methods
import lombok.Data;//Getters and setters
import lombok.NoArgsConstructor;//Generates no args
import static javax.persistence.FetchType.LAZY;
import static javax.persistence.GenerationType.IDENTITY;

@Data//Getters and Setters from Lambok
@Entity//JPA Entity for pesistance in db
@Builder//Creates a builder method for complex objects
@AllArgsConstructor//Creates an all args constructor
@NoArgsConstructor
public class Post {
	@Id
	@GeneratedValue(strategy=IDENTITY)
	private Long postId;
	
	@NotBlank(message = "Post Name cannot be empty or Null")
	private String postName;
	@Nullable
	private String url;
	@Nullable
	@Lob
	private String description;
	private Integer voteCount;
	@ManyToOne(fetch=LAZY)
	@JoinColumn(name="userId",referencedColumnName="userId")
	private User user;
	private Instant createdDate;
	@ManyToOne(fetch=LAZY)
	@JoinColumn(name="id",referencedColumnName="id")
	private Subreddit subreddit;
}
