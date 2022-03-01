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
@Table(name = "commentaire")
public class Commentaire {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id", columnDefinition = "INT(11)")
	private Integer id;

	@Column(name = "commentaire", nullable = false, columnDefinition = "VARCHAR(100)")
	@Size(max = 100)
	private String commentaire;

	@Column(name = "note", nullable = false, columnDefinition = "DOUBLE")
	private Double note;

	@ManyToOne
	@JoinColumn(name = "user", nullable = false)
	private User user;

	@ManyToOne
	@JoinColumn(name = "produit", nullable = false)
	private Produit produit;

	public Commentaire() {
	};

	public Commentaire(Integer id, String com, Double no, User uti, Produit product) {
		this.id = id;
		this.commentaire = com;
		this.note = no;
		this.user = uti;
		this.produit = product;
	}

	public Commentaire(String com, Double no, User uti, Produit product) {
		this.commentaire = com;
		this.note = no;
		this.user = uti;
		this.produit = product;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getCommentaire() {
		return commentaire;
	}

	public void setCommentaire(String commentaire) {
		this.commentaire = commentaire;
	}

	public Double getNote() {
		return note;
	}

	public void setNote(Double note) {
		this.note = note;
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
		String toString = String.format("\n[id=%s, commentaire=%s, note=%s, user=%s, produit=%s", id, commentaire, note,
				user.toString(), produit.toString());

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
		if (!(object instanceof Commentaire)) {
			return false;
		}

		Commentaire other = (Commentaire) object;

		if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
			return false;
		}

		return true;
	}
}
