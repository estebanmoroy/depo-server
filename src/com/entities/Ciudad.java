package com.entities;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the CIUDADES database table.
 * 
 */
@Entity
@Table(name="CIUDADES")
@NamedQuery(name="Ciudad.findAll", query="SELECT c FROM Ciudad c")
public class Ciudad implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="CIUDADES_CIUID_GENERATOR", sequenceName="SEQ_CIU_ID")
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="CIUDADES_CIUID_GENERATOR")
	@Column(name="CIU_ID")
	private long ciuId;

	@Column(name="CIU_NOMBRE")
	private String ciuNombre;

	//bi-directional many-to-one association to Local
	@OneToMany(mappedBy="ciudade")
	private List<Local> locales;

	public Ciudad() {
	}

	public long getCiuId() {
		return this.ciuId;
	}

	public void setCiuId(long ciuId) {
		this.ciuId = ciuId;
	}

	public String getCiuNombre() {
		return this.ciuNombre;
	}

	public void setCiuNombre(String ciuNombre) {
		this.ciuNombre = ciuNombre;
	}

	public List<Local> getLocales() {
		return this.locales;
	}

	public void setLocales(List<Local> locales) {
		this.locales = locales;
	}

	public Local addLocale(Local locale) {
		getLocales().add(locale);
		locale.setCiudade(this);

		return locale;
	}

	public Local removeLocale(Local locale) {
		getLocales().remove(locale);
		locale.setCiudade(null);

		return locale;
	}

}