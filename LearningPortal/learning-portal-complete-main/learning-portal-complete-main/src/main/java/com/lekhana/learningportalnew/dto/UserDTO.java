package com.lekhana.learningportalnew.dto;

import com.lekhana.learningportalnew.entity.UserEntity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class UserDTO {

	private String username;
	private String password;
	private UserEntity.Role role;

}
