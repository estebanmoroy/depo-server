package com.services;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import javax.persistence.TypedQuery;

import com.entities.*;
import com.exception.ServicesException;

/**
 * Session Bean implementation class FamiliaBean
 */
@Stateless
@LocalBean
public class FamiliaBean implements FamiliaBeanRemote {

	//Default constructor.
	public FamiliaBean() {}

	@PersistenceContext
	private EntityManager em;
	
	//Máximo de caracteres en descripcion
	private static final int MAXIMO_CARACTERES_DESCRIPCION = 50;
	
	@Override
	public boolean crear(Familia familia) throws ServicesException {
		try {
			validarAlta(familia);
			em.persist(familia);
			em.flush();
			return true;
		} catch (PersistenceException e) {
			throw new ServicesException("No se pudo crear la familia");
		}
	}

	@Override
	public boolean crear(String codigo,
			String descripcion, 
			String incompatible, 
			String nombre) throws ServicesException {
		
		try {
			Familia familia = new Familia();
			familia.setCodigo(codigo);
			familia.setDescripcion(descripcion);
			familia.setIncompatible(incompatible);
			familia.setNombre(nombre);
			
			validarAlta(familia);
			em.persist(familia);
			em.flush();
			return true;
		} catch (PersistenceException e) {
			throw new ServicesException("No se pudo crear la familia");
		}
	}

	@Override
	public boolean actualizar(Familia familia) throws ServicesException {
		try {
			validarModificacion(familia);
			em.merge(familia);
			em.flush();
			return true;
		} catch (PersistenceException e) {
			throw new ServicesException("No se pudo actualizar la familia");
		}
	}

	@Override
	public boolean eliminar(Familia familia) throws ServicesException {
		try {
			Familia familiaFind = em.find(Familia.class, familia.getId());
			validarBaja(familiaFind);
			em.remove(familiaFind);
			em.flush();
			return true;
		} catch (PersistenceException e) {
			throw new ServicesException("No se pudo eliminar la familia");
		}
	}

	@Override
	public boolean eliminar(Long id) throws ServicesException {
		try {
			Familia familiaFind = em.find(Familia.class, id);
			validarBaja(familiaFind);
			em.remove(familiaFind);
			em.flush();
			return true;
		} catch (PersistenceException e) {
			throw new ServicesException("No se pudo eliminar la familia");
		}
	}

	@Override
	public List<Familia> obtenerTodos() {
		TypedQuery<Familia> query = em.createQuery("SELECT f FROM Familia f", Familia.class);
		return query.getResultList();
	}

	@Override
	public Familia obtenerPorId(Long id) {
		TypedQuery<Familia> query=em
				.createQuery("SELECT f FROM Familia f WHERE UPPER(f.id) LIKE:id", Familia.class)
				.setParameter("id", id);

		return query.getSingleResult();
	}
	
	@Override
	public List<Familia> obtenerTodosPorCodigo(String filtro) {
		TypedQuery<Familia> query=em
				.createQuery("SELECT f FROM Familia f WHERE UPPER(f.codigo) LIKE:codigo", Familia.class)
				.setParameter("codigo", "%"+filtro.toUpperCase()+"%");

		return query.getResultList();
	}

	@Override
	public List<Familia> obtenerTodosPorNombre(String filtro) {
		TypedQuery<Familia> query=em
				.createQuery("SELECT f FROM Familia f WHERE UPPER(f.nombre) LIKE:nombre", Familia.class)
				.setParameter("nombre", "%"+filtro.toUpperCase()+"%");
		
		return query.getResultList();
	}

	@Override
	public List<Familia> obtenerTodosPorDescripcion(String filtro) {
		TypedQuery<Familia> query=em
				.createQuery("SELECT f FROM Familia f WHERE UPPER(f.descripcion) LIKE:descripcion", Familia.class)
				.setParameter("descripcion", "%"+filtro.toUpperCase()+"%");
		
		return query.getResultList();
	}

	@Override
	public boolean validarAlta(Familia familia) throws ServicesException {
		ArrayList<Boolean> valoresValidaciones = new ArrayList<Boolean>();
		try {
			valoresValidaciones.add(!codigoExiste(familia.getCodigo()));
			valoresValidaciones.add(!nombreExiste(familia.getNombre()));
			valoresValidaciones.add(!descripcionExiste(familia.getDescripcion()));
			valoresValidaciones.add(!descripcionSuperaMaximoDeCaracteres(familia.getDescripcion()));
			if (!valoresValidaciones.contains(false)) {
				return true;
			}else {
				return false;
			}
		} catch (ServicesException e) {
			throw new ServicesException(e.getMessage());
		}
	}

	@Override
	public boolean validarModificacion(Familia familia) throws ServicesException {
		ArrayList<Boolean> valoresValidaciones = new ArrayList<Boolean>();
		try {
			if (
					familia.getDescripcion().equals(
							obtenerPorId(familia.getId()).getDescripcion()
					)) {
				valoresValidaciones.add(true);
			}else {
				valoresValidaciones.add(!descripcionExiste(familia.getDescripcion()));
			}
			valoresValidaciones.add(!descripcionSuperaMaximoDeCaracteres(familia.getDescripcion()));
			if (!valoresValidaciones.contains(false)) {
				return true;
			}else {
				return false;
			}
		} catch (ServicesException e) {
			throw new ServicesException(e.getMessage());
		}
	}

	@Override
	public boolean validarBaja(Familia familia) throws ServicesException {
		ArrayList<Boolean> valoresValidaciones = new ArrayList<Boolean>();
		try {
			valoresValidaciones.add(!tieneProductoAsociado(familia));
			if (!valoresValidaciones.contains(false)) {
				return true;
			}else {
				return false;
			}
		} catch (ServicesException e) {
			throw new ServicesException(e.getMessage());
		}
	}

	@Override
	public boolean codigoExiste(String codigo) throws ServicesException {
		TypedQuery<Long> query = em.createQuery("SELECT COUNT(*) FROM Familia f WHERE f.codigo = :codigo", Long.class);
		query.setParameter("codigo", codigo);
		if (query.getSingleResult() <= 0) {
			return false;
		} else {
			throw new ServicesException("El código ya existe");
		}
	}

	@Override
	public boolean nombreExiste(String nombre) throws ServicesException {
		TypedQuery<Long> query = em.createQuery("SELECT COUNT(*) FROM Familia f WHERE f.nombre = :nombre", Long.class);
		query.setParameter("nombre", nombre);
		if (query.getSingleResult() <= 0) {
			return false;
		} else {
			throw new ServicesException("El nombre ya existe");
		}
	}

	@Override
	public boolean descripcionExiste(String descripcion) throws ServicesException {
		TypedQuery<Long> query = em.createQuery("SELECT COUNT(*) FROM Familia f WHERE f.descripcion = :descripcion", Long.class);
		query.setParameter("descripcion", descripcion);
		if (query.getSingleResult() <= 0) {
			return false;
		} else {
			throw new ServicesException("La descripción ya existe");
		}
	}

	@Override
	public boolean descripcionSuperaMaximoDeCaracteres(String descripcion) throws ServicesException {
		if (descripcion.length() <= MAXIMO_CARACTERES_DESCRIPCION) {
			return true;
		} else {
			throw new ServicesException("La descripción supera el máximo de caracteres");
		}
	}

	@Override
	public boolean tieneProductoAsociado(Familia familia) throws ServicesException {
		TypedQuery<Long> query = em.createQuery("SELECT COUNT(*) FROM Producto p WHERE p.familia.id = :id", Long.class);
		query.setParameter("id", familia.getId());
		if (query.getSingleResult() == 0) {
			return false;
		} else {
			throw new ServicesException("Familia no se puede Eliminar porque está asociada a un Producto, elimínelo previamente de Productos para luego Eliminar la misma");
		}
	}

}
