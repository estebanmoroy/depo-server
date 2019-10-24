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
	private long id;

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

	//Pasa un objeto Familia a String para imprimir en consola
	public String toString() {
		StringBuilder result = new StringBuilder();
		String newLine = System.getProperty("line.separator");
		
		result.append( "Familia {" );
		result.append(newLine);

		//determina campos de la clase
		Field[] fields = this.getClass().getDeclaredFields();

		//imprimir campos junto con sus valores
		for ( Field field : fields  ) {
			result.append("  ");
			try {
				result.append( field.getName() );
				result.append(": ");
				result.append( field.get(this) );
			} catch ( IllegalAccessException ex ) {
				System.out.println(ex);
			}
			result.append(newLine);
		}
		result.append("}");
			return result.toString();
	}
		
	public long getId() {
		return this.id;
	}

	public void setId(long id) {
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