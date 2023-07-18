package com.blog.app.entity;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@NoArgsConstructor
@Getter
@Setter
@Table(name = "Category_Tbl")
public class Categories {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ctg_id")
	private Integer categoryId;
	@Column(name = "title", length = 100, nullable = false)
	private String categoryTitle;
	@Column(name = "description", length = 250)
	private String categoryDesc;

	@OneToMany(mappedBy = "category", cascade = CascadeType.ALL,fetch = FetchType.LAZY)
	private List<Post> posts;
}
