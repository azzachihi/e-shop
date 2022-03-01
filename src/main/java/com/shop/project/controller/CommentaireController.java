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

import com.shop.project.model.Commentaire;
import com.shop.project.model.User;
import com.shop.project.model.User.UserRole;
import com.shop.project.services.CommentaireService;
import com.shop.project.services.UserService;

@CrossOrigin
@RestController
@RequestMapping("/commentaire")
public class CommentaireController {

	@Autowired
	private CommentaireService commentaireService;

	@Autowired
	private UserService userService;

	private List<Commentaire> commentaireList = new ArrayList<Commentaire>();

	@GetMapping()
	public ResponseEntity<List<Commentaire>> getList(@RequestParam(defaultValue = "1") Integer page,
			@RequestParam(defaultValue = "100") Integer limit) {

		if (userService.currentUser() == null) {
			return new ResponseEntity<List<Commentaire>>(commentaireList, HttpStatus.UNAUTHORIZED);
		}

		if (userService.currentUser().getUserRole() == UserRole.ROLE_ADMIN) {
			commentaireList = commentaireService.getList(page - 1, limit);
			return new ResponseEntity<List<Commentaire>>(commentaireList, HttpStatus.OK);
		} else if (userService.currentUser().getUserRole() == UserRole.ROLE_USER) {
			commentaireList = commentaireService.getListUser(page - 1, limit, userService.currentUser());
			return new ResponseEntity<List<Commentaire>>(commentaireList, HttpStatus.OK);
		}

		return new ResponseEntity<List<Commentaire>>(commentaireList, HttpStatus.UNAUTHORIZED);
	}

	@GetMapping("/{id}")
	public ResponseEntity<Commentaire> getbyID(@PathVariable("id") Integer id) {

		if (userService.currentUser() == null) {
			return new ResponseEntity<Commentaire>(HttpStatus.UNAUTHORIZED);
		}

		Commentaire commentaire = commentaireService.getOneById(id);

		if (commentaire == null) {
			return new ResponseEntity<Commentaire>(commentaire, HttpStatus.NOT_FOUND);
		}

		if (!commentaire.getUser().equals(userService.currentUser())
				&& userService.currentUser().getUserRole() == UserRole.ROLE_USER) {
			return new ResponseEntity<Commentaire>(HttpStatus.UNAUTHORIZED);
		}

		if ((commentaire.getUser().equals(userService.currentUser())
				|| userService.currentUser().getUserRole() == UserRole.ROLE_ADMIN)) {
			return new ResponseEntity<Commentaire>(commentaire, HttpStatus.OK);
		}

		return new ResponseEntity<Commentaire>(commentaire, HttpStatus.NOT_FOUND);
	}

	@PostMapping()
	public ResponseEntity<Commentaire> createCommentaire(@RequestBody Commentaire commentaire) {

		if (userService.currentUser() == null) {
			return new ResponseEntity<Commentaire>(HttpStatus.UNAUTHORIZED);
		}

		if (userService.currentUser() != null) {
			commentaire.setUser(userService.currentUser());
			return new ResponseEntity<Commentaire>(commentaireService.create(commentaire), HttpStatus.CREATED);
		}

		return new ResponseEntity<Commentaire>(HttpStatus.NOT_FOUND);
	}

	@PutMapping("/{id}")
	public ResponseEntity<Commentaire> updateCommentaire(@PathVariable("id") Integer id,
			@RequestBody Commentaire commentaire) {

		if (userService.currentUser() == null) {
			return new ResponseEntity<Commentaire>(HttpStatus.UNAUTHORIZED);
		}

		Commentaire comment = commentaireService.getOneById(id);

		if (comment == null) {
			return new ResponseEntity<Commentaire>(commentaire, HttpStatus.NOT_FOUND);
		}

		if (!comment.getUser().equals(userService.currentUser())
				&& userService.currentUser().getUserRole() == UserRole.ROLE_USER) {
			return new ResponseEntity<Commentaire>(commentaire, HttpStatus.UNAUTHORIZED);
		}

		if (comment != null && (comment.getUser().equals(userService.currentUser())
				|| userService.currentUser().getUserRole() == UserRole.ROLE_ADMIN)) {
			return new ResponseEntity<Commentaire>(commentaireService.update(id, commentaire), HttpStatus.OK);
		}

		return new ResponseEntity<Commentaire>(HttpStatus.UNAUTHORIZED);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Commentaire> deleteCommentaire(@PathVariable("id") Integer id) {

		if (userService.currentUser() == null) {
			return new ResponseEntity<Commentaire>(HttpStatus.UNAUTHORIZED);
		}

		Commentaire comment = commentaireService.getOneById(id);

		if (comment == null) {
			return new ResponseEntity<Commentaire>(comment, HttpStatus.NOT_FOUND);
		}

		if (!comment.getUser().equals(userService.currentUser())
				&& userService.currentUser().getUserRole() == UserRole.ROLE_USER) {
			return new ResponseEntity<Commentaire>(comment, HttpStatus.UNAUTHORIZED);
		}

		if (comment != null && (comment.getUser().equals(userService.currentUser())
				|| userService.currentUser().getUserRole() == UserRole.ROLE_ADMIN)) {

			Boolean sucess = commentaireService.delete(id);

			if (sucess) {
				return new ResponseEntity<Commentaire>(HttpStatus.OK);
			}
		}

		return new ResponseEntity<Commentaire>(HttpStatus.UNAUTHORIZED);
	}

	@DeleteMapping("/deleteByUser/{id}")
	public ResponseEntity<Commentaire> deleteCommentaireByUser(@RequestParam(defaultValue = "1") Integer page,
			@RequestParam(defaultValue = "100") Integer limit, @PathVariable("id") Integer id) {

		if (userService.currentUser() == null) {
			return new ResponseEntity<Commentaire>(HttpStatus.UNAUTHORIZED);
		}

		User user = userService.getOneById(id);

		if (user.getId() == null || user.getId() < 0)
			return new ResponseEntity<Commentaire>(HttpStatus.NOT_FOUND);

		if ((user.equals(userService.currentUser())
				|| userService.currentUser().getUserRole() == UserRole.ROLE_ADMIN)) {

			Boolean sucess = commentaireService.deleteByUser(page - 1, limit, user);

			if (sucess) {
				return new ResponseEntity<Commentaire>(HttpStatus.OK);
			}
		}

		return new ResponseEntity<Commentaire>(HttpStatus.UNAUTHORIZED);
	}

	@GetMapping("/search/{search}")
	public ResponseEntity<List<Commentaire>> search(@RequestParam(defaultValue = "1") Integer page,
			@RequestParam(defaultValue = "100") Integer limit, @PathVariable("search") String search) {

		if (userService.currentUser() == null) {
			return new ResponseEntity<List<Commentaire>>(commentaireList, HttpStatus.UNAUTHORIZED);
		}

		if (search != null && !search.isEmpty()) {

			if (userService.currentUser().getUserRole() == UserRole.ROLE_ADMIN) {
				return new ResponseEntity<List<Commentaire>>(commentaireService.getListSearch(page, limit, search),
						HttpStatus.OK);
			} else if (userService.currentUser().getUserRole() == UserRole.ROLE_USER) {
				return new ResponseEntity<List<Commentaire>>(
						commentaireService.getListSearchUser(page, limit, search, userService.currentUser()),
						HttpStatus.OK);
			}

		} else {
			return new ResponseEntity<List<Commentaire>>(commentaireList, HttpStatus.OK);
		}

		return new ResponseEntity<List<Commentaire>>(commentaireList, HttpStatus.UNAUTHORIZED);
	}
}
