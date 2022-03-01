package com.shop.project.repositories;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.shop.project.model.Panier;
//import com.projet.etna.model.Produit;
import com.shop.project.model.User;

@Repository
public interface PanierRepository extends PagingAndSortingRepository<Panier, Integer> {

	@Query("SELECT p FROM Panier p ORDER BY p.id ASC")
	public List<Panier> findAllPanier(Pageable page);

	@Query("SELECT p FROM Panier p WHERE (p.produit.nom = :search OR p.user.username = :search) ORDER BY p.id DESC")
	public List<Panier> search(Pageable page, String search);

	@Query("SELECT p FROM Panier p WHERE p.user = :user ORDER BY p.id ASC")
	public List<Panier> findByUser(Pageable page, User user);

	@Query("SELECT p FROM Panier p WHERE (p.produit.nom = :search OR p.user.username = :search) AND p.user = :user ORDER BY p.id DESC")
	public List<Panier> searchByUser(Pageable page, String search, User user);
}
