package com.ead.course.dtos;

import java.io.Serializable;
import java.util.UUID;

import com.ead.course.enums.CourseLevel;
import com.ead.course.enums.CourseStatus;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class CourseDto implements Serializable{

	private static final long serialVersionUID = 6869066271147026227L;

	@NotBlank
	private String name;

	@NotBlank
	private String description;
	
	private String imageUrl;

	@NotNull
	private CourseStatus courseStatus;

	@NotNull
	private UUID userInstructor;

	@NotNull
	private CourseLevel courseLevel;
}
