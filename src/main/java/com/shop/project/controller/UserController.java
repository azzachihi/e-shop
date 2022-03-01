package com.shop.project.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.shop.project.model.User;
import com.shop.project.model.User.UserRole;
import com.shop.project.services.UserService;

@CrossOrigin
@RestController
@RequestMapping("/user")
public class UserController {

	@Autowired
	private UserService userService;

	private List<User> userList = new ArrayList<User>();

	@GetMapping()
	public ResponseEntity<List<User>> getList(@RequestParam(defaultValue = "1") Integer page,
			@RequestParam(defaultValue = "100") Integer limit) {

		if (userService.currentUser() == null) {
			return new ResponseEntity<List<User>>(userList, HttpStatus.UNAUTHORIZED);
		}

		if (userService.currentUser() == null)
			return new ResponseEntity<List<User>>(HttpStatus.UNAUTHORIZED);
		if (userService.currentUser().getUserRole() == UserRole.ROLE_ADMIN) {
			userList = userService.getList(page - 1, limit);
			return new ResponseEntity<List<User>>(userList, HttpStatus.OK);
		}

		return new ResponseEntity<List<User>>(userList, HttpStatus.UNAUTHORIZED);
	}

	@GetMapping("/{id}")
	public ResponseEntity<User> getOneById(@PathVariable("id") Integer id) {
		if (userService.currentUser() == null)
			return new ResponseEntity<User>(HttpStatus.UNAUTHORIZED);

		User user = userService.getOneById(id);

		if (userService.currentUser() == null)
			return new ResponseEntity<User>(user, HttpStatus.UNAUTHORIZED);

		if (user != null) {
			return new ResponseEntity<User>(user, HttpStatus.OK);
		}

		return new ResponseEntity<User>(HttpStatus.NOT_FOUND);
	}

	@PutMapping("/{id}")
	public ResponseEntity<User> updateUser(@PathVariable("id") Integer id, @RequestBody User user) {
		if (userService.currentUser() == null || userService.currentUser().getUserRole() != UserRole.ROLE_ADMIN)
			return new ResponseEntity<User>(user, HttpStatus.UNAUTHORIZED);

		if (userService.currentUser().getId().equals(id)
				|| userService.currentUser().getUserRole() == UserRole.ROLE_ADMIN) {
			User newUser = userService.getOneById(id);
			newUser.setUsername(user.getUsername());
			newUser.setUserRole(user.getUserRole());
			User updatedUser = userService.update(id, newUser);
			if (updatedUser == null)
				return new ResponseEntity<User>(user, HttpStatus.NOT_FOUND);
			return new ResponseEntity<User>(updatedUser, HttpStatus.OK);
		}
		return new ResponseEntity<User>(user, HttpStatus.NOT_FOUND);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<User> deleteUser(@PathVariable("id") Integer id) {
		if (userService.currentUser() == null)
			return new ResponseEntity<User>(HttpStatus.UNAUTHORIZED);

		if ((userService.currentUser().getUserRole() == UserRole.ROLE_ADMIN)) {
			Boolean success = userService.delete(id);

			if (success)
				return new ResponseEntity<User>(HttpStatus.OK);
		}

		return new ResponseEntity<User>(HttpStatus.UNAUTHORIZED);
	}

	@GetMapping("/search/{search}")
	public ResponseEntity<List<User>> search(@RequestParam(defaultValue = "1") Integer page,
			@RequestParam(defaultValue = "100") Integer limit, @PathVariable("search") String search) {
		if (userService.currentUser() == null) {
			return new ResponseEntity<List<User>>(userList, HttpStatus.UNAUTHORIZED);
		}

		if (search != null && !search.isEmpty()) {
			if (userService.currentUser().getUserRole() == UserRole.ROLE_ADMIN) {
				return new ResponseEntity<List<User>>(userService.getListSearch(page, limit, search), HttpStatus.OK);
			}
		}

		return new ResponseEntity<List<User>>(userList, HttpStatus.UNAUTHORIZED);
	}
}