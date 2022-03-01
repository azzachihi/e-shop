package com.shop.project.repositories;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.shop.project.model.Produit;
import com.shop.project.model.User;

@Repository
public interface ProduitRepository extends PagingAndSortingRepository<Produit, Integer> {

	@Query("SELECT p FROM Produit p ORDER BY p.id ASC")
	public List<Produit> findAllProduit(Pageable page);

	@Query("SELECT p FROM Produit p WHERE (p.nom = :search OR p.user.username = :search) ORDER BY p.id ASC")
	public List<Produit> search(Pageable page, String search);

	@Query("SELECT p FROM Produit p WHERE p.user = :user ORDER BY p.id ASC")
	public List<Produit> findByUser(Pageable page, User user);

	@Query("SELECT p FROM Produit p WHERE (p.nom = :search OR p.user.username = :search) AND p.user = :user ORDER BY p.id ASC")
	public List<Produit> searchByUser(Pageable page, String search, User user);

}
