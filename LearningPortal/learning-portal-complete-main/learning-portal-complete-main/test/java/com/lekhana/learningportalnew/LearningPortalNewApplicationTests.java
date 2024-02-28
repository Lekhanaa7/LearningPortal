package com.lekhana.learningportalnew;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import com.lekhana.learningportalnew.dto.UserDTO;
import com.lekhana.learningportalnew.entity.UserEntity;
import com.lekhana.learningportalnew.entity.UserEntity.Role;
import com.lekhana.learningportalnew.mapper.UserMapper;
import com.lekhana.learningportalnew.repository.UserRepository;
import com.lekhana.learningportalnew.service.UserService;

@RunWith(SpringRunner.class)
@SpringBootTest
class LearningPortalNewApplicationTests {

	@Autowired
	private UserService userService;
	@Autowired
	private UserMapper userMapper;
	@MockBean
	private UserRepository userRepository;

	@Test
	void testGetAllUsers() {
		when(userRepository.findAll()).thenReturn(Stream.of(new UserDTO("123", "Sam", Role.ADMIN))
				.map(userMapper::toEntity).collect(Collectors.toList()));

		assertEquals(1, userService.getAllUsers().size());
	}

	@Test
	void testRegister() {
		UserDTO userDTO = new UserDTO("!@#", "manya", Role.ADMIN);
		when(userRepository.save(any(UserEntity.class))).thenAnswer(invocation -> {
			UserEntity savedEntity = invocation.getArgument(0);

			return savedEntity;
		});

		assertEquals(userDTO, userService.registerUser(userDTO));
	}

	@Test
	void testDeleteUser() {
		Long deleteId = 1L;

		UserEntity adminUser = new UserEntity();

		adminUser.setRole(Role.ADMIN);

		when(userRepository.findById(deleteId)).thenReturn(Optional.of(adminUser));
		userService.deleteUser(deleteId);

		verify(userRepository, times(1)).findById(deleteId);
		verify(userRepository, times(1)).deleteById(deleteId);
	}
}
