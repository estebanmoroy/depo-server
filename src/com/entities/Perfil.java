package com.entities;

import java.io.Serializable;
import java.lang.reflect.Field;

import javax.persistence.*;


/**
 * The persistent class for the PERFILES database table.
 * 
 */
@Entity
@Table(name="PERFILES")
@NamedQuery(name="Perfil.findAll", query="SELECT p FROM Perfil p")
public class Perfil implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="PERFILES_PERID_GENERATOR", sequenceName="SEQ_PER_ID", allocationSize = 1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="PERFILES_PERID_GENERATOR")
	@Column(name="PER_ID")
	private long id;

	@Column(name="PER_DESCRIPCION")
	private String descripcion;

	@Column(name="PER_NOMBRE")
	private String nombre;

	public Perfil() {}

	public Perfil(String descripcion, String nombre) {
		super();
		this.descripcion = descripcion;
		this.nombre = nombre;
	}

	//Pasa un objeto Perfil a String para imprimir en consola
	public String toString() {
		StringBuilder result = new StringBuilder();
		String newLine = System.getProperty("line.separator");
		
		result.append( "Perfil {" );
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

	public String getDescripcion() {
		return this.descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public String getNombre() {
		return this.nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

}