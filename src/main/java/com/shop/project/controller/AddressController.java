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

import com.shop.project.model.Adresse;
import com.shop.project.model.User;
import com.shop.project.model.User.UserRole;
import com.shop.project.services.AdresseService;
import com.shop.project.services.UserService;

@CrossOrigin
@RestController
@RequestMapping("/address")
public class AddressController {

	@Autowired
	private AdresseService adresseService;

	@Autowired
	private UserService userService;

	private List<Adresse> adresseList = new ArrayList<Adresse>();

	@GetMapping()
	public ResponseEntity<List<Adresse>> getList(@RequestParam(defaultValue = "1") Integer page,
			@RequestParam(defaultValue = "100") Integer limit) {
		if (userService.currentUser() == null)
			return new ResponseEntity<List<Adresse>>(new ArrayList<>(), HttpStatus.UNAUTHORIZED);

		if (userService.currentUser().getUserRole() == UserRole.ROLE_ADMIN) {
			adresseList = adresseService.getList(page - 1, limit);
			return new ResponseEntity<List<Adresse>>(adresseList, HttpStatus.OK);
		} else if (userService.currentUser().getUserRole() == UserRole.ROLE_USER) {
			adresseList = adresseService.getListUser(page - 1, limit, userService.currentUser());
			return new ResponseEntity<List<Adresse>>(adresseList, HttpStatus.OK);
		}

		return new ResponseEntity<List<Adresse>>(new ArrayList<>(), HttpStatus.UNAUTHORIZED);
	}

	@GetMapping("/{id}")
	public ResponseEntity<Adresse> getbyID(@PathVariable("id") Integer id) {
		if (userService.currentUser() == null)
			return new ResponseEntity<Adresse>(HttpStatus.UNAUTHORIZED);

		Adresse adresse = adresseService.getOneById(id);

		if (adresse == null)
			return new ResponseEntity<Adresse>(adresse, HttpStatus.NOT_FOUND);

		if (!adresse.getUser().equals(userService.currentUser())
				&& userService.currentUser().getUserRole() == UserRole.ROLE_USER)
			return new ResponseEntity<Adresse>(adresse, HttpStatus.UNAUTHORIZED);

		if ((adresse.getUser().equals(userService.currentUser())
				|| userService.currentUser().getUserRole() == UserRole.ROLE_ADMIN))
			return new ResponseEntity<Adresse>(adresse, HttpStatus.OK);

		return new ResponseEntity<Adresse>(adresse, HttpStatus.NOT_FOUND);
	}

	@PostMapping()
	public ResponseEntity<Adresse> createAdresse(@RequestBody Adresse adresse) {
		if (userService.currentUser() == null)
			return new ResponseEntity<Adresse>(adresse, HttpStatus.UNAUTHORIZED);

		if (userService.currentUser() != null) {
			adresse.setUser(userService.currentUser());
			return new ResponseEntity<Adresse>(adresseService.create(adresse), HttpStatus.CREATED);
		}

		return new ResponseEntity<Adresse>(HttpStatus.NOT_FOUND);
	}

	@PutMapping("/{id}")
	public ResponseEntity<Adresse> updateAdresse(@PathVariable("id") Integer id, @RequestBody Adresse adresse) {

		if (userService.currentUser() == null)
			return new ResponseEntity<Adresse>(adresse, HttpStatus.UNAUTHORIZED);

		Adresse adress = adresseService.getOneById(id);

		if (adress == null)
			return new ResponseEntity<Adresse>(adresse, HttpStatus.NOT_FOUND);

		if (!adress.getUser().equals(userService.currentUser())
				&& userService.currentUser().getUserRole() == UserRole.ROLE_USER)
			return new ResponseEntity<Adresse>(adresse, HttpStatus.UNAUTHORIZED);

		if (adress != null && (adress.getUser().equals(userService.currentUser())
				|| userService.currentUser().getUserRole() == UserRole.ROLE_ADMIN))
			return new ResponseEntity<Adresse>(adresseService.update(id, adresse), HttpStatus.OK);

		return new ResponseEntity<Adresse>(HttpStatus.UNAUTHORIZED);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Adresse> deleteAdresse(@PathVariable("id") Integer id) {
		if (userService.currentUser() == null)
			return new ResponseEntity<Adresse>(HttpStatus.UNAUTHORIZED);

		Adresse adress = adresseService.getOneById(id);

		if (adress == null)
			return new ResponseEntity<Adresse>(adress, HttpStatus.NOT_FOUND);

		if (!adress.getUser().equals(userService.currentUser())
				&& userService.currentUser().getUserRole() == UserRole.ROLE_USER)
			return new ResponseEntity<Adresse>(adress, HttpStatus.UNAUTHORIZED);

		if (adress.getUser().equals(userService.currentUser())
				|| userService.currentUser().getUserRole() == UserRole.ROLE_ADMIN) {

			Boolean sucess = adresseService.delete(id);

			if (sucess)
				return new ResponseEntity<Adresse>(HttpStatus.OK); /// ACCEPTED
		}

		return new ResponseEntity<Adresse>(HttpStatus.UNAUTHORIZED);
	}

	@DeleteMapping("/deleteByUser/{id}")
	public ResponseEntity<Adresse> deleteAdresseByUSer(@RequestParam(defaultValue = "1") Integer page,
			@RequestParam(defaultValue = "100") Integer limit, @PathVariable("id") Integer id) {

		if (userService.currentUser() == null)
			return new ResponseEntity<Adresse>(HttpStatus.UNAUTHORIZED);

		User user = userService.getOneById(id);

		if (user.getId() == null || user.getId() < 0)
			return new ResponseEntity<Adresse>(HttpStatus.NOT_FOUND);

		if (user.equals(userService.currentUser()) || userService.currentUser().getUserRole() == UserRole.ROLE_ADMIN) {

			Boolean sucess = adresseService.deleteByUser(page - 1, limit, user);

			if (sucess) {

				return new ResponseEntity<Adresse>(HttpStatus.OK); /// ACCEPTED
			}
		}

		return new ResponseEntity<Adresse>(HttpStatus.UNAUTHORIZED);
	}

	@GetMapping("/search/{search}")
	public ResponseEntity<List<Adresse>> search(@RequestParam(defaultValue = "1") Integer page,
			@RequestParam(defaultValue = "100") Integer limit, @PathVariable("search") String search) {

		if (userService.currentUser() == null)
			return new ResponseEntity<List<Adresse>>(adresseList, HttpStatus.UNAUTHORIZED);

		if (search != null && !search.isEmpty()) {

			if (userService.currentUser().getUserRole() == UserRole.ROLE_ADMIN) {
				return new ResponseEntity<List<Adresse>>(adresseService.getListSearch(page, limit, search),
						HttpStatus.OK);
			} else if (userService.currentUser().getUserRole() == UserRole.ROLE_USER) {
				return new ResponseEntity<List<Adresse>>(
						adresseService.getListSearchUser(page, limit, search, userService.currentUser()),
						HttpStatus.OK);
			}
		} else {
			return new ResponseEntity<List<Adresse>>(adresseList, HttpStatus.OK);
		}

		return new ResponseEntity<List<Adresse>>(adresseList, HttpStatus.UNAUTHORIZED);
	}
}