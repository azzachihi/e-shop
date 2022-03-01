package com.shop.project.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

//import com.projet.etna.model.Adresse;
import com.shop.project.model.Facturation;
//import com.projet.etna.model.Panier;
import com.shop.project.model.User;
//import com.projet.etna.model.User;
import com.shop.project.repositories.FacturationRepository;

@Service
public class FacturationService implements IModelService<Facturation> {

	@Autowired
	private FacturationRepository facturationRepository;

	@Override
	public List<Facturation> getList(Integer object, Integer limit) {
		// TODO Auto-generated method stub

		PageRequest page = PageRequest.of(object, limit);

		return facturationRepository.findAllFacturation(page);
	}

	@Override
	public Facturation getOneById(Integer id) {
		// TODO Auto-generated method stub

		Optional<Facturation> facturation = facturationRepository.findById(id);

		if (!facturation.isPresent()) {
			return null;
		}

		return facturation.get();
	}

	@Override
	public Facturation create(Facturation entity) {
		// TODO Auto-generated method stub

		facturationRepository.save(entity);

		return entity;
	}

	@Override
	public Facturation update(Integer id, Facturation entity) {
		// TODO Auto-generated method stub

		if (getOneById(id) == null) {
			return null;
		}

		entity.setId(id);
		entity.setAdresse(getOneById(id).getAdresse());
		entity.setPanier(getOneById(id).getPanier());

		facturationRepository.save(entity);

		return entity;
	}

	@Override
	public Boolean delete(Integer id) {
		// TODO Auto-generated method stub

		try {

			if (getOneById(id) == null) {
				return false;
			}

			facturationRepository.delete(getOneById(id));

		} catch (Exception e) {
			// TODO: handle exception

			return false;
		}

		return true;
	}

	@Override
	public List<Facturation> getListSearch(Integer obj, Integer limit, String search) {
		// TODO Auto-generated method stub

		PageRequest page = PageRequest.of(obj, limit);

		return facturationRepository.search(page, search);
	}

	@Override
	public List<Facturation> getListUser(Integer obj, Integer limit, User user) {
		// TODO Auto-generated method stub

		PageRequest page = PageRequest.of(obj, limit);

		return facturationRepository.findByUser(page, user);
	}

	@Override
	public List<Facturation> getListSearchUser(Integer obj, Integer limit, String search, User user) {
		// TODO Auto-generated method stub

		PageRequest page = PageRequest.of(obj, limit);

		return facturationRepository.searchByUser(page, search, user);
	}

	@Override
	public boolean deleteByUser(Integer obj, Integer limit, User user) {
		// TODO Auto-generated method stub

		if (user.getId() != null && user.getId() > 0) {
			List<Facturation> deleteFacturation = getListUser(obj, limit, user);

			if (deleteFacturation != null && !deleteFacturation.isEmpty()) {
				facturationRepository.deleteAll(deleteFacturation);
				return true;

			}
		}

		return false;
	}
}
