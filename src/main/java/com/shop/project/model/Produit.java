package com.shop.project.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Size;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

@Entity
@Table(name = "produit")
public class Produit {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id", columnDefinition = "INT(11)")
	private Integer id;

	@Column(name = "reference", nullable = false, columnDefinition = "VARCHAR(100)")
	@Size(max = 100)
	private String reference;

	@Column(name = "prix", nullable = false, columnDefinition = "DOUBLE")
	private Double prix;

	@Column(name = "nom", nullable = false, columnDefinition = "VARCHAR(100)")
	@Size(max = 100)
	private String nom;

	@Column(name = "description", nullable = false, columnDefinition = "TEXT")
	@Size(max = 100)
	private String description;

	@Column(name = "stock", nullable = false, columnDefinition = "INT(11)")
	private Integer stock;

	@Column(name = "url", nullable = false, columnDefinition = "TEXT")
	private String url = "https://www.idealstandard.co.uk/-/media/project/ideal-standard/commerce-websites/shared-website/default-fallback-images/product-tile/product_image_placeholder.png";

	@ManyToOne
	@JoinColumn(name = "user", nullable = false)
	private User user;

	public Produit() {
	};

	public Produit(Integer id, String ref, Double price, String name, String info, Integer nb, String lien, User uti) {
		this.id = id;
		this.reference = ref;
		this.prix = price;
		this.nom = name;
		this.description = info;
		this.stock = nb;
		this.url = lien;
		this.user = uti;
	}

	public Produit(String ref, Double price, String name, String info, Integer nb, String lien, User uti) {
		this.reference = ref;
		this.prix = price;
		this.nom = name;
		this.description = info;
		this.stock = nb;
		this.url = lien;
		this.user = uti;
	}

	public Produit(String ref, Double price, String name, String info, Integer nb, String lien) {
		this.reference = ref;
		this.prix = price;
		this.nom = name;
		this.description = info;
		this.stock = nb;
		this.url = lien;
	}

	public Produit(String ref, Double price, String name, String info, Integer nb) {
		this.reference = ref;
		this.prix = price;
		this.nom = name;
		this.description = info;
		this.stock = nb;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getReference() {
		return reference;
	}

	public void setReference(String reference) {
		this.reference = reference;
	}

	public Double getPrix() {
		return prix;
	}

	public void setPrix(Double prix) {
		this.prix = prix;
	}

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Integer getStock() {
		return stock;
	}

	public void setStock(Integer stock) {
		this.stock = stock;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	@Override
	public String toString() {
		String toString = String.format(
				"\n[id=%s, reference=%s, prix=%s, nom=%s, description=%s, stock=%s, url=%s, user=%s", id, reference,
				prix, nom, description, stock, url, user.toString());

		return toString;
	}

	@Override
	public int hashCode() {
		int hash = 0;

		hash += (id != null ? id.hashCode() : 0);

		return hash;
	}

	@Override
	public boolean equals(Object object) {

		if (!(object instanceof Produit)) {
			return false;
		}

		Produit other = (Produit) object;

		if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
			return false;
		}

		return true;
	}
}