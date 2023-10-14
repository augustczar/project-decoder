package com.ead.authuser.dtos;

import java.util.UUID;

import com.ead.authuser.enums.CourseLevel;
import com.ead.authuser.enums.CourseStatus;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class CourseDto {

	private String name;
	
	private String description;
	
	private String imageUrl;
	
	private CourseStatus courseStatus;
	
	private UUID userInstructor;
	
	private CourseLevel courseLevel;
}
