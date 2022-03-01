package com.shop.project.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.shop.project.model.Panier;
//import com.projet.etna.model.Produit;
import com.shop.project.model.User;
import com.shop.project.repositories.PanierRepository;

@Service
public class PanierService implements IModelService<Panier> {

	@Autowired
	private PanierRepository panierRepository;

	@Override
	public List<Panier> getList(Integer object, Integer limit) {
		// TODO Auto-generated method stub

		PageRequest page = PageRequest.of(object, limit);

		return panierRepository.findAllPanier(page);
	}

	@Override
	public Panier getOneById(Integer id) {
		// TODO Auto-generated method stub

		Optional<Panier> panier = panierRepository.findById(id);

		if (!panier.isPresent()) {
			return null;
		}

		return panier.get();
	}

	@Override
	public Panier create(Panier entity) {
		// TODO Auto-generated method stub

		panierRepository.save(entity);

		return entity;
	}

	@Override
	public Panier update(Integer id, Panier entity) {
		// TODO Auto-generated method stub

		if (getOneById(id) == null) {
			return null;
		}

		entity.setId(id);
		entity.setUser(getOneById(id).getUser());
		entity.setProduit(getOneById(id).getProduit());

		panierRepository.save(entity);

		return entity;
	}

	@Override
	public Boolean delete(Integer id) {
		// TODO Auto-generated method stub

		try {

			if (getOneById(id) == null) {
				return false;
			}

			panierRepository.delete(getOneById(id));

		} catch (Exception e) {
			// TODO: handle exception

			return false;
		}

		return true;
	}

	@Override
	public List<Panier> getListSearch(Integer obj, Integer limit, String search) {
		// TODO Auto-generated method stub

		PageRequest page = PageRequest.of(obj, limit);

		return panierRepository.search(page, search);
	}

	@Override
	public List<Panier> getListUser(Integer obj, Integer limit, User user) {
		// TODO Auto-generated method stub

		PageRequest page = PageRequest.of(obj, limit);

		return panierRepository.findByUser(page, user);
	}

	@Override
	public List<Panier> getListSearchUser(Integer obj, Integer limit, String search, User user) {
		// TODO Auto-generated method stub

		PageRequest page = PageRequest.of(obj, limit);

		return panierRepository.searchByUser(page, search, user);
	}

	@Override
	public boolean deleteByUser(Integer obj, Integer limit, User user) {
		// TODO Auto-generated method stub

		if (user.getId() != null && user.getId() > 0) {
			List<Panier> deletePanier = getListUser(obj, limit, user);

			if (deletePanier != null && !deletePanier.isEmpty()) {
				panierRepository.deleteAll(deletePanier);
				return true;
			}
		}

		return false;
	}
}
