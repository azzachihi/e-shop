package com.shop.project.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.Size;

@Entity
@Table(name = "adresse")
public class Adresse {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id", columnDefinition = "INT(11)")
	private Integer id;

	@Column(name = "rue", nullable = false, columnDefinition = "VARCHAR(100)")
	@Size(max = 100)
	private String rue;

	@Column(name = "postalCode", nullable = false, columnDefinition = "VARCHAR(100)")
	@Size(max = 100)
	private String postalCode;

	@Column(name = "city", nullable = false, columnDefinition = "VARCHAR(100)")
	@Size(max = 100)
	private String city;

	@Column(name = "country", nullable = false, columnDefinition = "VARCHAR(100)")
	@Size(max = 100)
	private String country;

	@ManyToOne
	@JoinColumn(name = "user", nullable = false)
	private User user;

	public Adresse() {
	};

	public Adresse(Integer id, String street, String codePostal, String ville, String pays, User uti) {
		this.id = id;
		this.rue = street;
		this.postalCode = codePostal;
		this.city = ville;
		this.country = pays;
		this.user = uti;
	}

	public Adresse(String street, String codePostal, String ville, String pays, User uti) {
		this.rue = street;
		this.postalCode = codePostal;
		this.city = ville;
		this.country = pays;
		this.user = uti;
	}

	public Adresse(String street, String codePostal, String ville, String pays) {
		this.rue = street;
		this.postalCode = codePostal;
		this.city = ville;
		this.country = pays;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getRue() {
		return rue;
	}

	public void setRue(String rue) {
		this.rue = rue;
	}

	public String getPostalCode() {
		return postalCode;
	}

	public void setPostalCode(String postalCode) {
		this.postalCode = postalCode;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	@Override
	public String toString() {
		String toString = String.format("\n[id=%s, rue=%s, postalCode=%s, city=%s, country=%s, user=%s ", id, rue,
				postalCode, city, country, user.toString());

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

		if (!(object instanceof Adresse)) {
			return false;
		}

		Adresse other = (Adresse) object;

		if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
			return false;
		}

		return true;
	}
}