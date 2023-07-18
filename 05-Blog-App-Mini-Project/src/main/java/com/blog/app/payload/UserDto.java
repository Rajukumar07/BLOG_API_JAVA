package com.blog.app.payload;

import java.util.HashSet;
import java.util.Set;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UserDto {

	private Integer userId;
	@NotEmpty(message = "Please Enter First Name ")
	@Size(min = 3, message = "name must be min of 4  characters")
	private String fname;
	@NotEmpty(message = "Please Enter Last Name")
	private String lname;
	@NotEmpty(message = "About should not be empty")
	private String about;
	@Email(message = "please enter valid email")
	@NotEmpty(message = "email must not be empty")
	private String email;
	@NotEmpty
	@Pattern(regexp = "^[a-zA-Z0-9_.-]*$", message = "password must be contain number and character only")
	@Size(min = 4, max = 8, message = "password must be atleast 4 or less than 8 character ")
	private String pwd;

	private Set<RoleDto> roles = new HashSet<>();

}
