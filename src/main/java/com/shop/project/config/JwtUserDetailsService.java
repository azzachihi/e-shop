package com.shop.project.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.shop.project.model.JwtUserDetails;
import com.shop.project.model.User;
import com.shop.project.repositories.UserRepository;

@Service
public class JwtUserDetailsService implements UserDetailsService {

	@Autowired
	private UserRepository userRepository;

	@Override
	public JwtUserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		// TODO Auto-generated method stub

		User user = userRepository.findByUsername(username);
		if (user != null) {
			return new JwtUserDetails(user);
		} else {
			throw new UsernameNotFoundException("User not found with username: " + username);
		}
	}
}
