package com.entities;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the LOCALES database table.
 * 
 */
@Entity
@Table(name="LOCALES")
@NamedQuery(name="Local.findAll", query="SELECT l FROM Local l")
public class Local implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="LOCALES_LOCID_GENERATOR", sequenceName="SEQ_LOC_ID")
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="LOCALES_LOCID_GENERATOR")
	@Column(name="LOC_ID")
	private long locId;

	@Column(name="LOC_DESCRIPCION")
	private String locDescripcion;

	@Column(name="LOC_DIRECCION")
	private String locDireccion;

	@Column(name="LOC_TIPO")
	private String locTipo;

	//bi-directional many-to-one association to Almacenamiento
	@OneToMany(mappedBy="local")
	private List<Almacenamiento> almacenamientos;

	//bi-directional many-to-one association to Ciudad
	@ManyToOne
	@JoinColumn(name="CIUDADES_CIU_ID")
	private Ciudad ciudade;

	public Local() {
	}

	public long getLocId() {
		return this.locId;
	}

	public void setLocId(long locId) {
		this.locId = locId;
	}

	public String getLocDescripcion() {
		return this.locDescripcion;
	}

	public void setLocDescripcion(String locDescripcion) {
		this.locDescripcion = locDescripcion;
	}

	public String getLocDireccion() {
		return this.locDireccion;
	}

	public void setLocDireccion(String locDireccion) {
		this.locDireccion = locDireccion;
	}

	public String getLocTipo() {
		return this.locTipo;
	}

	public void setLocTipo(String locTipo) {
		this.locTipo = locTipo;
	}

	public List<Almacenamiento> getAlmacenamientos() {
		return this.almacenamientos;
	}

	public void setAlmacenamientos(List<Almacenamiento> almacenamientos) {
		this.almacenamientos = almacenamientos;
	}

	public Almacenamiento addAlmacenamiento(Almacenamiento almacenamiento) {
		getAlmacenamientos().add(almacenamiento);
		almacenamiento.setLocal(this);

		return almacenamiento;
	}

	public Almacenamiento removeAlmacenamiento(Almacenamiento almacenamiento) {
		getAlmacenamientos().remove(almacenamiento);
		almacenamiento.setLocal(null);

		return almacenamiento;
	}

	public Ciudad getCiudade() {
		return this.ciudade;
	}

	public void setCiudade(Ciudad ciudade) {
		this.ciudade = ciudade;
	}

}