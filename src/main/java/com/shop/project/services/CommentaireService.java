package com.shop.project.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.shop.project.model.Commentaire;
//import com.projet.etna.model.Produit;
import com.shop.project.model.User;
import com.shop.project.repositories.CommentaireRepository;

@Service
public class CommentaireService implements IModelService<Commentaire> {

	@Autowired
	private CommentaireRepository commentaireRepository;

	@Override
	public List<Commentaire> getList(Integer object, Integer limit) {
		// TODO Auto-generated method stub

		PageRequest page = PageRequest.of(object, limit);

		return commentaireRepository.findByDateDesc(page);
	}

	@Override
	public Commentaire getOneById(Integer id) {
		// TODO Auto-generated method stub

		Optional<Commentaire> commentaire = commentaireRepository.findById(id);

		if (!commentaire.isPresent()) {
			return null;
		}

		return commentaire.get();
	}

	@Override
	public Commentaire create(Commentaire entity) {
		// TODO Auto-generated method stub

		commentaireRepository.save(entity);

		return entity;
	}

	@Override
	public Commentaire update(Integer id, Commentaire entity) {
		// TODO Auto-generated method stub

		if (getOneById(id) == null) {
			return null;
		}

		entity.setId(id);
		entity.setUser(getOneById(id).getUser());
		entity.setProduit(getOneById(id).getProduit());

		commentaireRepository.save(entity);

		return entity;
	}

	@Override
	public Boolean delete(Integer id) {
		// TODO Auto-generated method stub

		try {

			if (getOneById(id) == null) {
				return false;
			}

			commentaireRepository.delete(getOneById(id));

		} catch (Exception e) {
			// TODO: handle exception

			return false;
		}

		return true;
	}

	@Override
	public List<Commentaire> getListSearch(Integer obj, Integer limit, String search) {
		// TODO Auto-generated method stub

		PageRequest page = PageRequest.of(obj, limit);

		return commentaireRepository.search(page, search);
	}

	@Override
	public List<Commentaire> getListUser(Integer obj, Integer limit, User user) {
		// TODO Auto-generated method stub

		PageRequest page = PageRequest.of(obj, limit);

		return commentaireRepository.findByUser(page, user);
	}

	@Override
	public List<Commentaire> getListSearchUser(Integer obj, Integer limit, String search, User user) {
		// TODO Auto-generated method stub

		PageRequest page = PageRequest.of(obj, limit);

		return commentaireRepository.searchByUser(page, search, user);
	}

	@Override
	public boolean deleteByUser(Integer obj, Integer limit, User user) {
		// TODO Auto-generated method stub

		if (user.getId() != null && user.getId() > 0) {
			List<Commentaire> deleteCommentaires = getListUser(obj, limit, user);

			if (deleteCommentaires != null && !deleteCommentaires.isEmpty()) {
				commentaireRepository.deleteAll(deleteCommentaires);
				return true;
			}
		}

		return false;
	}
}
