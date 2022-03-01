package com.shop.project.repositories;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.shop.project.model.Commentaire;
//import com.projet.etna.model.Produit;
import com.shop.project.model.User;

@Repository
public interface CommentaireRepository extends PagingAndSortingRepository<Commentaire, Integer> {

	@Query("SELECT c FROM Commentaire c ORDER BY c.id DESC")
	public List<Commentaire> findByDateDesc(Pageable page);

	@Query("SELECT c FROM Commentaire c WHERE (c.produit.nom = :search OR c.user.username = :search) ORDER BY c.id DESC")
	public List<Commentaire> search(Pageable page, String search);

	@Query("SELECT c FROM Commentaire c WHERE c.user = :user ORDER BY c.id DESC")
	public List<Commentaire> findByUser(Pageable page, User user);

	@Query("SELECT c FROM Commentaire c WHERE (c.produit.nom = :search OR c.user.username = :search) AND c.user = :user ORDER BY c.id DESC")
	public List<Commentaire> searchByUser(Pageable page, String search, User user);

}
