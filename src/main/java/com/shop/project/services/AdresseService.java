package com.shop.project.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.shop.project.model.Adresse;
import com.shop.project.model.User;
import com.shop.project.repositories.AdresseRepository;

@Service
public class AdresseService implements IModelService<Adresse> {

	@Autowired
	private AdresseRepository adresseRepository;

	@Override
	public List<Adresse> getList(Integer object, Integer limit) {
		// TODO Auto-generated method stub

		PageRequest page = PageRequest.of(object, limit);

		return adresseRepository.findAllByPageUser(page);
	}

	@Override
	public Adresse getOneById(Integer id) {
		// TODO Auto-generated method stub

		Optional<Adresse> adresse = adresseRepository.findById(id);

		if (!adresse.isPresent()) {
			return null;
		}

		return adresse.get();
	}

	@Override
	public Adresse create(Adresse entity) {
		// TODO Auto-generated method stub

		adresseRepository.save(entity);

		return entity;
	}

	@Override
	public Adresse update(Integer id, Adresse entity) {
		// TODO Auto-generated method stub

		if (getOneById(id) == null) {
			return null;
		}

		entity.setId(id);
		entity.setUser(getOneById(id).getUser());

		adresseRepository.save(entity);

		return entity;
	}

	@Override
	public Boolean delete(Integer id) {
		// TODO Auto-generated method stub

		try {

			if (getOneById(id) == null) {
				return false;
			}

			adresseRepository.delete(getOneById(id));

		} catch (Exception e) {
			// TODO: handle exception

			return false;
		}

		return true;
	}

	@Override
	public List<Adresse> getListSearch(Integer obj, Integer limit, String search) {
		// TODO Auto-generated method stub

		PageRequest page = PageRequest.of(obj, limit);

		return adresseRepository.search(page, search);
	}

	@Override
	public List<Adresse> getListUser(Integer obj, Integer limit, User user) {
		// TODO Auto-generated method stub

		PageRequest page = PageRequest.of(obj, limit);

		return adresseRepository.findAllByUserPage(page, user);
	}

	@Override
	public List<Adresse> getListSearchUser(Integer obj, Integer limit, String search, User user) {
		// TODO Auto-generated method stub

		PageRequest page = PageRequest.of(obj, limit);

		return adresseRepository.searchByUser(page, search, user);
	}

	@Override
	public boolean deleteByUser(Integer obj, Integer limit, User user) {
		// TODO Auto-generated method stub

		if (user.getId() != null && user.getId() > 0) {
			List<Adresse> deleteAdresses = getListUser(obj, limit, user);

			if (deleteAdresses != null && !deleteAdresses.isEmpty()) {
				adresseRepository.deleteAll(deleteAdresses);
				return true;
			}
		}

		return false;
	}
}