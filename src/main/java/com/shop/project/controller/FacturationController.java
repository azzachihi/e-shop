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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.shop.project.model.Facturation;
import com.shop.project.model.User;
import com.shop.project.model.User.UserRole;
import com.shop.project.services.FacturationService;
import com.shop.project.services.UserService;

@CrossOrigin
@RestController
@RequestMapping("/facturation")
public class FacturationController {

	@Autowired
	private FacturationService facturationService;

	@Autowired
	private UserService userService;

	private List<Facturation> facturationList = new ArrayList<Facturation>();

	@GetMapping()
	public ResponseEntity<List<Facturation>> getList(@RequestParam(defaultValue = "1") Integer page,
			@RequestParam(defaultValue = "100") Integer limit) {

		if (userService.currentUser() == null) {
			return new ResponseEntity<List<Facturation>>(facturationList, HttpStatus.UNAUTHORIZED);
		}

		if (userService.currentUser().getUserRole() == UserRole.ROLE_ADMIN) {
			facturationList = facturationService.getList(page - 1, limit);
			return new ResponseEntity<List<Facturation>>(facturationList, HttpStatus.OK);
		} else if (userService.currentUser().getUserRole() == UserRole.ROLE_USER) {
			facturationList = facturationService.getListUser(page - 1, limit, userService.currentUser());
			return new ResponseEntity<List<Facturation>>(facturationList, HttpStatus.OK);
		}

		return new ResponseEntity<List<Facturation>>(facturationList, HttpStatus.UNAUTHORIZED);
	}

	@GetMapping("/{id}")
	public ResponseEntity<Facturation> getbyID(@PathVariable("id") Integer id) {

		if (userService.currentUser() == null) {
			return new ResponseEntity<Facturation>(HttpStatus.UNAUTHORIZED);
		}

		Facturation facture = facturationService.getOneById(id);

		if (facture == null) {
			return new ResponseEntity<Facturation>(facture, HttpStatus.NOT_FOUND);
		}

		if (!facture.getAdresse().getUser().equals(userService.currentUser())
				&& userService.currentUser().getUserRole() == UserRole.ROLE_USER) {
			return new ResponseEntity<Facturation>(HttpStatus.UNAUTHORIZED);
		}

		if ((facture.getAdresse().getUser().equals(userService.currentUser())
				|| userService.currentUser().getUserRole() == UserRole.ROLE_ADMIN)) {
			return new ResponseEntity<Facturation>(facture, HttpStatus.OK);
		}

		return new ResponseEntity<Facturation>(facture, HttpStatus.NOT_FOUND);
	}

	@PostMapping()
	public ResponseEntity<Facturation> createFacturation(@RequestBody Facturation facturation) {

		if (userService.currentUser() == null) {
			return new ResponseEntity<Facturation>(HttpStatus.UNAUTHORIZED);
		}

		if (userService.currentUser() != null) {
			// facturation.getAdresse().setUser(userService.currentUser());
			return new ResponseEntity<Facturation>(facturationService.create(facturation), HttpStatus.CREATED);
		}

		return new ResponseEntity<Facturation>(HttpStatus.NOT_FOUND);
	}

	@PutMapping("/{id}")
	public ResponseEntity<Facturation> updateFacturation(@PathVariable("id") Integer id,
			@RequestBody Facturation facturation) {

		if (userService.currentUser() == null) {
			return new ResponseEntity<Facturation>(HttpStatus.UNAUTHORIZED);
		}

		Facturation facture = facturationService.getOneById(id);

		if (facture == null) {
			return new ResponseEntity<Facturation>(facturation, HttpStatus.NOT_FOUND);
		}

		if (!facture.getAdresse().getUser().equals(userService.currentUser())
				&& userService.currentUser().getUserRole() == UserRole.ROLE_USER) {
			return new ResponseEntity<Facturation>(facturation, HttpStatus.UNAUTHORIZED);
		}

		if (facture != null && (facture.getAdresse().getUser().equals(userService.currentUser())
				|| userService.currentUser().getUserRole() == UserRole.ROLE_ADMIN)) {
			return new ResponseEntity<Facturation>(facturationService.update(id, facturation), HttpStatus.OK);
		}

		return new ResponseEntity<Facturation>(HttpStatus.UNAUTHORIZED);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Facturation> deleteFacturation(@PathVariable("id") Integer id) {

		if (userService.currentUser() == null) {
			return new ResponseEntity<Facturation>(HttpStatus.UNAUTHORIZED);
		}

		Facturation facture = facturationService.getOneById(id);

		if (facture == null) {
			return new ResponseEntity<Facturation>(facture, HttpStatus.NOT_FOUND);
		}

		if (!facture.getAdresse().getUser().equals(userService.currentUser())
				&& userService.currentUser().getUserRole() == UserRole.ROLE_USER) {
			return new ResponseEntity<Facturation>(facture, HttpStatus.UNAUTHORIZED);
		}

		if (facture != null && (facture.getAdresse().getUser().equals(userService.currentUser())
				|| userService.currentUser().getUserRole() == UserRole.ROLE_ADMIN)) {

			Boolean sucess = facturationService.delete(id);

			if (sucess) {
				return new ResponseEntity<Facturation>(HttpStatus.OK);
			}
		}

		return new ResponseEntity<Facturation>(HttpStatus.UNAUTHORIZED);
	}

	@DeleteMapping("/deleteByUser/{id}")
	public ResponseEntity<Facturation> deleteFacturationByUser(@RequestParam(defaultValue = "1") Integer page,
			@RequestParam(defaultValue = "100") Integer limit, @PathVariable("id") Integer id) {

		if (userService.currentUser() == null) {
			return new ResponseEntity<Facturation>(HttpStatus.UNAUTHORIZED);
		}

		User user = userService.getOneById(id);

		if (user.getId() == null || user.getId() < 0)
			return new ResponseEntity<Facturation>(HttpStatus.NOT_FOUND);

		if ((user.equals(userService.currentUser())
				|| userService.currentUser().getUserRole() == UserRole.ROLE_ADMIN)) {

			Boolean sucess = facturationService.deleteByUser(page - 1, limit, user);

			if (sucess) {
				return new ResponseEntity<Facturation>(HttpStatus.OK);
			}
		}

		return new ResponseEntity<Facturation>(HttpStatus.UNAUTHORIZED);
	}

	@GetMapping("/search/{search}")
	public ResponseEntity<List<Facturation>> search(@RequestParam(defaultValue = "1") Integer page,
			@RequestParam(defaultValue = "100") Integer limit, @PathVariable("search") String search) {

		if (userService.currentUser() == null) {
			return new ResponseEntity<List<Facturation>>(facturationList, HttpStatus.UNAUTHORIZED);
		}

		if (search != null && !search.isEmpty()) {

			if (userService.currentUser().getUserRole() == UserRole.ROLE_ADMIN) {
				return new ResponseEntity<List<Facturation>>(facturationService.getListSearch(page, limit, search),
						HttpStatus.OK);
			} else if (userService.currentUser().getUserRole() == UserRole.ROLE_USER) {
				return new ResponseEntity<List<Facturation>>(
						facturationService.getListSearchUser(page, limit, search, userService.currentUser()),
						HttpStatus.OK);
			}

		} else {
			return new ResponseEntity<List<Facturation>>(facturationList, HttpStatus.OK);
		}

		return new ResponseEntity<List<Facturation>>(facturationList, HttpStatus.UNAUTHORIZED);
	}
}
