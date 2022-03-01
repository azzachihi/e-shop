package com.shop.project.repositories;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.shop.project.model.Adresse;
import com.shop.project.model.User;

@Repository
public interface AdresseRepository extends PagingAndSortingRepository<Adresse, Integer> {

	@Query("SELECT a FROM Adresse a WHERE a.user = :user")
	public List<Adresse> findAllByUser(User user);

	@Query("SELECT a FROM Adresse a WHERE (a.rue = :search OR a.city = :search OR a.postalCode = :search OR a.country = :search) ORDER BY a.id ASC")
	public List<Adresse> search(Pageable page, String search);

	@Query("SELECT a FROM Adresse a WHERE (a.rue = :search OR a.city = :search OR a.postalCode = :search OR a.country = :search) AND a.user = :user ORDER BY a.id ASC")
	public List<Adresse> searchByUser(Pageable page, String search, User user);

	@Query("SELECT a FROM Adresse a ORDER BY a.id ASC")
	public List<Adresse> findAllByPageUser(Pageable page);

	@Query("SELECT a FROM Adresse a WHERE a.user = :user ORDER BY a.id ASC")
	public List<Adresse> findAllByUserPage(Pageable page, User user);
}
