package com.shop.project.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

@Entity
@Table(name = "panier")
public class Panier {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id", columnDefinition = "INT(11)")
	private Integer id;

	@Column(name = "quantite", nullable = false, columnDefinition = "INT(11)")
	private Integer quantite;

	@OneToOne
	@JoinColumn(name = "user", nullable = false)
	private User user;

	@ManyToOne
	@JoinColumn(name = "produit", nullable = false)
	private Produit produit;

	public Panier() {
	};

	public Panier(Integer id, Integer nb, User uti, Produit product) {
		this.id = id;
		this.quantite = nb;
		this.user = uti;
		this.produit = product;

	}

	public Panier(Integer nb, User uti, Produit product) {
		this.quantite = nb;
		this.user = uti;
		this.produit = product;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getQuantite() {
		return quantite;
	}

	public void setQuantite(Integer quantite) {
		this.quantite = quantite;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Produit getProduit() {
		return produit;
	}

	public void setProduit(Produit produit) {
		this.produit = produit;
	}

	@Override
	public String toString() {
		String toString = String.format("\n[id=%s, quantite=%s, user=%s, produit=%s", id, quantite, user.toString(),
				produit.toString());

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
		if (!(object instanceof Panier)) {
			return false;
		}

		Panier other = (Panier) object;

		if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
			return false;
		}

		return true;
	}

}
