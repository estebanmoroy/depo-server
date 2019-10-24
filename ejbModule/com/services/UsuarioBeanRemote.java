package com.services;

import java.util.List;

import javax.ejb.Remote;

import com.entities.Usuario;
import com.exception.ServicesException;

@Remote
public interface UsuarioBeanRemote {

	boolean autenticar(String nombreAcceso, String contrasena) throws ServicesException;
	Usuario getObjetoUsuario (String nombreAcceso, String contrasena) throws ServicesException;
	void crear(Usuario usuario) throws ServicesException;
	void crear(String nombre, 
			String apellido, 
			String nombreAcceso,
			String contrasena,
			String correo
			) throws ServicesException;
	void actualizar(Usuario usuario) throws ServicesException;
	void eliminar(Usuario usuario) throws ServicesException;
	void eliminar(Long id) throws ServicesException;
	void asignarPerfilPorId(Long idUsuario, Long idPerfil) throws ServicesException;
	void asignarPerfil(Usuario usuario, String nombrePerfil) throws ServicesException;
	List<Usuario> obtenerTodos();
	List<Usuario> obtenerTodosPorNombre(String filtro);
	List<Usuario> obtenerTodosPorApellido(String filtro);
	List<Usuario> obtenerTodosPorNombreAcceso(String filtro);
	
}