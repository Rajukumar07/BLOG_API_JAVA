package com.blog.app.entity;

import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "Comment_Tbl")
@Getter
@Setter
public class Comment {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer commentId;
//	private String name;
//	private String email;
	@ManyToOne
	@JoinColumn(name = "post_id")
	private Post post;
	@CreationTimestamp
	private LocalDate createdOn;
	@UpdateTimestamp
	private LocalDate updated;
}
