package com.entities;

import java.io.Serializable;
import java.lang.reflect.Field;

import javax.persistence.*;


/**
 * La clase de persistencia para la tabla USUARIOS de la base de datos
 * 
 */
@Entity
@Table(name="USUARIOS")
//@NamedQuery(name="Usuario.findAll", query="SELECT u FROM Usuario u")
public class Usuario implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="USUARIOS_USUID_GENERATOR", sequenceName="SEQ_USU_ID", allocationSize = 1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="USUARIOS_USUID_GENERATOR")
	@Column(name="USU_ID")
	private long id;

	@Column(name="USU_APELLIDO")
	private String apellido;

	@Column(name="USU_CONTRASENA")
	private String contrasena;

	@Column(name="USU_CORREO")
	private String correo;

	@Column(name="USU_NOM_ACCESO")
	private String nombreAcceso;

	@Column(name="USU_NOMBRE")
	private String nombre;

	//bi-directional many-to-one association to Perfil
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="PERFILES_PER_ID")
	private Perfil perfil;

	public Usuario() {}
	
	public Usuario(String apellido, String contrasena, String correo, String nombreAcceso, String nombre) {
		super();
		this.apellido = apellido;
		this.contrasena = contrasena;
		this.correo = correo;
		this.nombreAcceso = nombreAcceso;
		this.nombre = nombre;
	}

	//Pasa un objeto Usuario a String para imprimir en consola
	public String toString() {
		StringBuilder result = new StringBuilder();
		String newLine = System.getProperty("line.separator");
			
		result.append( "Usuario {" );
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

	public String getApellido() {
		return this.apellido;
	}

	public void setApellido(String apellido) {
		this.apellido = apellido;
	}

	public String getContrasena() {
		return this.contrasena;
	}

	public void setContrasena(String contrasena) {
		this.contrasena = contrasena;
	}

	public String getCorreo() {
		return this.correo;
	}

	public void setCorreo(String correo) {
		this.correo = correo;
	}

	public String getNombreAcceso() {
		return this.nombreAcceso;
	}

	public void setNombreAcceso(String nombreAcceso) {
		this.nombreAcceso = nombreAcceso;
	}

	public String getNombre() {
		return this.nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public Perfil getPerfil() {
		return this.perfil;
	}

	public void setPerfil(Perfil perfil) {
		this.perfil = perfil;
	}

}