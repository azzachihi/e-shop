package com.shop.project.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.shop.project.model.Produit;
import com.shop.project.model.User;
import com.shop.project.repositories.ProduitRepository;

@Service
public class ProduitService implements IModelService<Produit> {

	@Autowired
	private ProduitRepository produitRepository;

	@Override
	public List<Produit> getList(Integer object, Integer limit) {
		// TODO Auto-generated method stub

		PageRequest page = PageRequest.of(object, limit);

		return produitRepository.findAllProduit(page);
	}

	@Override
	public Produit getOneById(Integer id) {
		// TODO Auto-generated method stub

		Optional<Produit> produit = produitRepository.findById(id);

		if (!produit.isPresent()) {
			return null;
		}

		return produit.get();
	}

	@Override
	public Produit create(Produit entity) {
		// TODO Auto-generated method stub

		produitRepository.save(entity);

		return entity;
	}

	@Override
	public Produit update(Integer id, Produit entity) {
		// TODO Auto-generated method stub

		if (getOneById(id) == null) {
			return null;
		}

		entity.setId(id);
		entity.setUser(getOneById(id).getUser());
		produitRepository.save(entity);

		return entity;
	}

	@Override
	public Boolean delete(Integer id) {
		// TODO Auto-generated method stub

		try {

			if (getOneById(id) == null) {
				return false;
			}

			produitRepository.delete(getOneById(id));

		} catch (Exception e) {
			// TODO: handle exception

			return false;
		}

		return true;
	}

	@Override
	public List<Produit> getListSearch(Integer obj, Integer limit, String search) {
		// TODO Auto-generated method stub

		PageRequest page = PageRequest.of(obj, limit);

		return produitRepository.search(page, search);
	}

	@Override
	public List<Produit> getListUser(Integer obj, Integer limit, User user) {
		// TODO Auto-generated method stub

		PageRequest page = PageRequest.of(obj, limit);

		return produitRepository.findByUser(page, user);
	}

	@Override
	public List<Produit> getListSearchUser(Integer obj, Integer limit, String search, User user) {
		// TODO Auto-generated method stub

		PageRequest page = PageRequest.of(obj, limit);

		return produitRepository.searchByUser(page, search, user);
	}

	@Override
	public boolean deleteByUser(Integer obj, Integer limit, User user) {
		// TODO Auto-generated method stub

		if (user.getId() != null && user.getId() > 0) {
			List<Produit> deleteProduit = getListUser(obj, limit, user);

			if (deleteProduit != null && !deleteProduit.isEmpty()) {
				produitRepository.deleteAll(deleteProduit);
				return true;
			}
		}

		return false;
	}
}
