package com.shop.project.repositories;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

//import com.projet.etna.model.Adresse;
import com.shop.project.model.Facturation;
//import com.projet.etna.model.Panier;
import com.shop.project.model.User;

@Repository
public interface FacturationRepository extends PagingAndSortingRepository<Facturation, Integer> {

	@Query("SELECT f FROM Facturation f ORDER BY f.id ASC")
	public List<Facturation> findAllFacturation(Pageable page);

	@Query("SELECT f FROM Facturation f WHERE (f.panier.produit.nom = :search OR f.adresse.user.username = :search) ORDER BY f.id ASC")
	public List<Facturation> search(Pageable page, String search);

	@Query("SELECT f FROM Facturation f WHERE adresse.user = :user ORDER BY f.id ASC")
	public List<Facturation> findByUser(Pageable page, User user);

	@Query("SELECT f FROM Facturation f WHERE (f.panier.produit.nom = :search OR f.adresse.user.username = :search) AND f.adresse.user = :user ORDER BY f.id ASC")
	public List<Facturation> searchByUser(Pageable page, String search, User user);

}
