package com.services;

import javax.ejb.Stateless;
import javax.ejb.LocalBean;


import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import javax.persistence.TypedQuery;

import java.util.List;

import com.entities.*;
import com.exception.ServicesException;

/**
 * Session Bean implementation class UsuarioBean
 */
@Stateless
@LocalBean
public class UsuarioBean implements UsuarioBeanRemote {

    //Default Constructor
    public UsuarioBean() {}
    
    @PersistenceContext
    private EntityManager em;
    
    @Override
    public boolean autenticar(String nombreAcceso, String contrasena) throws ServicesException{
    	try {
    		TypedQuery<Usuario> query=em
					.createQuery("SELECT u FROM Usuario u WHERE u.nombreAcceso LIKE:nombreAcceso AND u.contrasena LIKE:contrasena", Usuario.class)
					.setParameter("nombreAcceso", nombreAcceso)
					.setParameter("contrasena", contrasena);
    		if( query.getResultList().isEmpty()) {
    			throw new ServicesException("Usuario o contraseña incorrectos");
    		}else {
    			return true;
    		}
    	}catch (Exception e) {
    		throw new ServicesException(e.getMessage());
    	}
    }

	@Override
	public Usuario getObjetoUsuario(String nombreAcceso, String contrasena) throws ServicesException {
		try {
			TypedQuery<Usuario> query=em
					.createQuery("SELECT u FROM Usuario u WHERE u.nombreAcceso LIKE:nombreAcceso AND u.contrasena LIKE:contrasena", Usuario.class)
					.setParameter("nombreAcceso", nombreAcceso)
					.setParameter("contrasena", contrasena);
		    Usuario usuario = query.getResultList().get(0);
		    return usuario;
		} catch (Exception e) {
			throw new ServicesException(e.getMessage());
		}
	}

	@Override
	public void crear(Usuario usuario) throws ServicesException {
		try {
			//Se asigna por defecto el perfil Operador
			usuario.setPerfil(em.find(Perfil.class, 3L));
			em.persist(usuario);
			em.flush();
		} catch (PersistenceException e) {
			throw new ServicesException("No se pudo crear el usuario");
		}
	}
	
	@Override
	public void crear(String nombre, String apellido, String nombreAcceso, String contrasena, String correo)
			throws ServicesException {
		try {
			Usuario usuario = new Usuario(apellido, contrasena, correo, nombreAcceso, nombre);
			//Se asigna por defecto el perfil Operador
			usuario.setPerfil(em.find(Perfil.class, 3L));
			em.persist(usuario);
			em.flush();
		} catch (PersistenceException e) {
			throw new ServicesException("No se pudo crear el usuario");
		}
	}

	@Override
	public void actualizar(Usuario usuario) throws ServicesException {
		try {
			em.merge(usuario);
			em.flush();
		} catch (PersistenceException e) {
			throw new ServicesException("No se pudo actualizar el usuario");
		}
	}

	@Override
	public void eliminar(Usuario usuario) throws ServicesException {
		try {
			em.remove(usuario);
			em.flush();
		} catch (PersistenceException e) {
			throw new ServicesException("No se pudo eliminar el usuario");
		}
	}

	@Override
	public void eliminar(Long id) throws ServicesException {
		try {
			Usuario usuarioFind = em.find(Usuario.class, id);
			em.remove(usuarioFind);
			em.flush();
		} catch (PersistenceException e) {
			throw new ServicesException("No se pudo eliminar el usuario");
		}
	}

	@Override
	public void asignarPerfilPorId(Long idUsuario, Long idPerfil) throws ServicesException {
		try {
			Usuario usuario = em.find(Usuario.class, idUsuario);
			usuario.setPerfil(em.find(Perfil.class, idPerfil));
			em.flush();
		} catch (PersistenceException e) {
			throw new ServicesException("No se pudo asignar el perfil al usuario");
		}
	}

	@Override
	public void asignarPerfil(Usuario usuario, String nombrePerfil) throws ServicesException {
		try {
			if (nombrePerfil.contentEquals("Administrador") || nombrePerfil.contentEquals("Supervisor") || nombrePerfil.contentEquals("Operador")) {
				
				switch(nombrePerfil) {
				case "Administrador":
					asignarPerfilPorId(usuario.getId(), 1L);
					break;
				case "Supervisor":
					asignarPerfilPorId(usuario.getId(), 2L);
					break;
				case "Operador":
					asignarPerfilPorId(usuario.getId(), 3L);
					break;
				}
				
			} else {
				throw new ServicesException("Nombre de perfil incorrecto");
			}
		} catch (PersistenceException e) {
			throw new ServicesException("No se pudo asignar el perfil al usuario");
		}
	}

	@Override
	public List<Usuario> obtenerTodos() {
		TypedQuery<Usuario> query = em.createQuery("SELECT u FROM Usuario u", Usuario.class);
		return query.getResultList();
	}

	@Override
	public List<Usuario> obtenerTodosPorNombre(String filtro) {
		TypedQuery<Usuario> query=em.createQuery("SELECT u FROM Usuario u WHERE u.nombre LIKE:nombre", Usuario.class).setParameter("nombre", filtro);
		return query.getResultList();
	}

	@Override
	public List<Usuario> obtenerTodosPorApellido(String filtro) {
		TypedQuery<Usuario> query=em.createQuery("SELECT u FROM Usuario u WHERE u.apellido LIKE:apellido", Usuario.class).setParameter("apellido", filtro);
		return query.getResultList();
	}

	@Override
	public List<Usuario> obtenerTodosPorNombreAcceso(String filtro) {
		TypedQuery<Usuario> query=em.createQuery("SELECT u FROM Usuario u WHERE u.nombreAcceso LIKE:nombreAcceso", Usuario.class).setParameter("nombreAcceso", filtro);
		return query.getResultList();
	}
    
}
