package com.entities;

import java.io.Serializable;
import javax.persistence.*;

import java.lang.reflect.Field;

/**
 * La clase de persistencia para la tabla FAMILIAS de la base de datos
 * 
 */
@Entity
@Table(name="FAMILIAS")
//@NamedQuery(name="Familia.findAll", query="SELECT f FROM Familia f")
public class Familia implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="FAMILIAS_FAMID_GENERATOR", sequenceName="SEQ_FAM_ID", allocationSize = 1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="FAMILIAS_FAMID_GENERATOR")
	@Column(name="FAM_ID")
	private Long id;

	@Column(name="FAM_CODIGO")
	private String codigo;

	@Column(name="FAM_DESCRIPCION")
	private String descripcion;

	@Column(name="FAM_INCOMPATIBLE")
	private String incompatible;

	@Column(name="FAM_NOMBRE")
	private String nombre;

	public Familia() {
	}

	public Familia(String codigo, String descripcion, String incompatible, String nombre) {
		super();
		this.codigo = codigo;
		this.descripcion = descripcion;
		this.incompatible = incompatible;
		this.nombre = nombre;
	}
	
	public String toString() {
		return String.format("%s[id=%d]", getClass().getSimpleName(), getId());
	}
	
	@Override
    public boolean equals(Object other) {
        return (other != null && getClass() == other.getClass() && id != null)
            ? id.equals(((Familia) other).id)
            : (other == this);
    }

    @Override
    public int hashCode() {
        return (id != null) 
            ? (getClass().hashCode() + id.hashCode())
            : super.hashCode();
    }
		
	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCodigo() {
		return this.codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public String getDescripcion() {
		return this.descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public String getIncompatible() {
		return this.incompatible;
	}

	public void setIncompatible(String incompatible) {
		this.incompatible = incompatible;
	}

	public String getNombre() {
		return this.nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

}