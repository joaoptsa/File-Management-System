package com.senfiles.version1.service;

import java.util.Arrays;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.senfiles.version1.Dto.Userdto;
import com.senfiles.version1.Model.RoleModel;
import com.senfiles.version1.Model.UserModel;
import com.senfiles.version1.enums.RoleName;
import com.senfiles.version1.repository.UserRepository;

import jakarta.transaction.Transactional;

@Service
public class UserService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Transactional
	public void saveUser(Userdto user) {

		RoleModel role = new RoleModel(RoleName.ADMIN);
		//RoleModel role = new RoleModel(RoleName.USER);


		UserModel us = new UserModel(passwordEncoder.encode(user.getPassword()), user.getUsername(),
				Arrays.asList(role));
		userRepository.save(us);

	}

	public UserModel getUserById(Long id) {
		return userRepository.getReferenceById(id);
	}

	public void deleteUserById(Long id) {
		userRepository.deleteById(id);
	}

	public Optional<UserModel> findUser(String username) {
		return userRepository.findByUsername(username);
	}

}
