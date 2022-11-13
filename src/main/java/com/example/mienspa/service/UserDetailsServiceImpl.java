package com.example.mienspa.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.mienspa.models.UserDetailsImpl;
import com.example.mienspa.models.Users;


@Service
public class UserDetailsServiceImpl implements UserDetailsService {
  @Autowired
  UserService service;

  @Override
  @Transactional
  public UserDetails loadUserByUsername(String email) {
    try {
    	Users user = service.getByEmail(email);
        return UserDetailsImpl.build(user);
	} catch (UsernameNotFoundException e) {
		return null;
	}
  }
}
