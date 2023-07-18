package com.blog.app.payload;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class CategoryDto {

	private Integer categoryId;
	@NotBlank
	@Size(min=4, message = "Title should be min 4 characters..")
	private String categoryTitle;
	@NotBlank
	@Size(min = 10,message = "description is not sufficient !! ")
	private String categoryDesc;

}
