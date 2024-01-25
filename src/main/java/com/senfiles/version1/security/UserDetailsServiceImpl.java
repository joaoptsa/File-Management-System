package com.senfiles.version1.security;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import org.springframework.stereotype.Service;
import com.senfiles.version1.Model.UserModel;
import org.springframework.security.core.userdetails.User;
import com.senfiles.version1.repository.UserRepository;
import jakarta.transaction.Transactional;

@Service
@Transactional
public class UserDetailsServiceImpl implements UserDetailsService {

  @Autowired
  private UserRepository userRepository;
  @Autowired
  private LoginAttemptService loginAttemptService;

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    if (loginAttemptService.isBlocked())
      throw new UserBlockedException("Error -> User is blocked");

    Optional<UserModel> userModel = userRepository.findByUsername(username);

    if (userModel.isPresent()) {
      return new User(userModel.get().getUsername(), userModel.get().getPassword(), true, true, true, true,
          userModel.get().getAuthorities());

    } else {
      throw new UsernameNotFoundException("Invalid name or password");
    }

  }

}
