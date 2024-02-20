package com.lekhana.learningportalnew.dto;

import com.lekhana.learningportalnew.entity.CourseEntity;
import com.lekhana.learningportalnew.entity.UserEntity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class RegisteredCourseDTO {

	private Long registrationId;
	private UserEntity user;
	private CourseEntity course;
	private String errorMessage;

	public RegisteredCourseDTO(String errorMessage) {
		this.errorMessage = errorMessage;
	}
}
