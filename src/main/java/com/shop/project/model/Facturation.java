package com.shop.project.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

@Entity
@Table(name = "facturation")
public class Facturation {

	public enum Payement {
		VALIDE, REJECT
	}

	public enum Livraison {
		EN_COURS, LIVREE, NON_LIVREE
	}

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id", columnDefinition = "INT(11)")
	private Integer id;

	@Column(name = "totalQt", nullable = false, columnDefinition = "INT(11)")
	private Integer totalQt;

	@Column(name = "totalPrix", nullable = false, columnDefinition = "DOUBLE")
	private Double totalPrix;

	@Enumerated(EnumType.STRING)
	@Column(name = "payement", columnDefinition = "VARCHAR(100)")
	private Payement payement = Payement.VALIDE;

	@Enumerated(EnumType.STRING)
	@Column(name = "livraison", columnDefinition = "VARCHAR(100)")
	private Livraison livraison = Livraison.EN_COURS;

	@OneToOne
	@JoinColumn(name = "panier", nullable = false)
	private Panier panier;

	@OneToOne
	@JoinColumn(name = "adresse", nullable = false)
	private Adresse adresse;

	public Facturation() {
	};

	public Facturation(Integer id, Integer tQt, Double tPrix, Payement paye, Livraison send, Panier basket,
			Adresse addre) {
		this.id = id;
		this.totalQt = tQt;
		this.totalPrix = tPrix;
		this.payement = paye;
		this.livraison = send;
		this.panier = basket;
		this.adresse = addre;

	}

	public Facturation(Integer tQt, Double tPrix, Payement paye, Livraison send, Panier basket, Adresse addre) {
		this.totalQt = tQt;
		this.totalPrix = tPrix;
		this.payement = paye;
		this.livraison = send;
		this.panier = basket;
		this.adresse = addre;
	}

	public Facturation(Integer tQt, Double tPrix, Panier basket, Adresse addre) {
		this.totalQt = tQt;
		this.totalPrix = tPrix;
		this.panier = basket;
		this.adresse = addre;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getTotalQt() {
		return totalQt;
	}

	public void setTotalQt(Integer totalQt) {
		this.totalQt = totalQt;
	}

	public Double getTotalPrix() {
		return totalPrix;
	}

	public void setTotalPrix(Double totalPrix) {
		this.totalPrix = totalPrix;
	}

	public Payement getPayement() {
		return payement;
	}

	public void setPayement(Payement payement) {
		this.payement = payement;
	}

	public Livraison getLivraison() {
		return livraison;
	}

	public void setLivraison(Livraison livraison) {
		this.livraison = livraison;
	}

	public Panier getPanier() {
		return panier;
	}

	public void setPanier(Panier panier) {
		this.panier = panier;
	}

	public Adresse getAdresse() {
		return adresse;
	}

	public void setAdresse(Adresse adresse) {
		this.adresse = adresse;
	}

	@Override
	public String toString() {
		String toString = String.format(
				"\n[id=%s, totQt=%s, totPrix=%s, payement=%s, livraison=%s, panier=%s, adresse=%s", id, totalQt,
				totalPrix, payement, livraison, panier.toString(), adresse.toString());

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
		if (!(object instanceof Facturation)) {
			return false;
		}

		Facturation other = (Facturation) object;

		if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
			return false;
		}

		return true;
	}
}
