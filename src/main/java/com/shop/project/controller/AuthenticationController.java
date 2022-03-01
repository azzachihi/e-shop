package com.shop.project.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.shop.project.config.JwtTokenUtil;
import com.shop.project.config.JwtUserDetailsService;
import com.shop.project.model.JwtResponse;
import com.shop.project.model.JwtUserDetails;
import com.shop.project.model.User;
import com.shop.project.model.UserDetails;
import com.shop.project.repositories.UserRepository;

@RestController
@CrossOrigin
public class AuthenticationController {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private JwtTokenUtil jwtTokenUtil;

	@Autowired
	private JwtUserDetailsService jwtUserDetailsService;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Value("${jwt.header}")
	private String tokenHeader;

	@Value("${jwt.prefix}")
	private String tokenPrefix;

	@PostMapping("/register")
	public ResponseEntity<UserDetails> register(@RequestBody User user) {

		if (userRepository.findByUsername(user.getUsername()) != null) {
			return new ResponseEntity<UserDetails>(HttpStatus.INTERNAL_SERVER_ERROR);
		}

		user.setPassword(passwordEncoder.encode(user.getPassword()));

		userRepository.save(user);

		return new ResponseEntity<UserDetails>(new UserDetails(user), HttpStatus.CREATED);
	}

	@PostMapping("/authenticate")
	public ResponseEntity<JwtResponse> authenticate(@RequestBody User user) throws Exception {
		authenticate(user.getUsername(), user.getPassword());
		final JwtUserDetails userDetails = jwtUserDetailsService.loadUserByUsername(user.getUsername());
		final String token = jwtTokenUtil.generateToken(userDetails);
		return ResponseEntity.ok(new JwtResponse(token));
	}

	private void authenticate(String username, String password) throws Exception {
		try {
			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
		} catch (DisabledException e) {
			throw new Exception("USER_DISABLED", e);
		} catch (BadCredentialsException e) {
			throw new Exception("INVALID_CREDENTIALS", e);
		}
	}

	@GetMapping(value = "/me", produces = { MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<UserDetails> getAuthenticatedUser(HttpServletRequest request) {
		String token = request.getHeader(tokenHeader).replace(tokenPrefix, "");
		String username = jwtTokenUtil.getUsernameFromToken(token);
		UserDetails user = new UserDetails(userRepository.findByUsername(username));
		return new ResponseEntity<UserDetails>(user, HttpStatus.OK);
	}
}
