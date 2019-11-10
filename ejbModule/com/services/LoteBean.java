package com.services;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import javax.persistence.TypedQuery;

import com.entities.*;
import com.exception.ServicesException;

/**
 * Session Bean implementation class LoteBean
 */
@Stateless
public class LoteBean implements LoteBeanRemote {

	//Default Constructor
    public LoteBean() {}

    //Entity Manager
  	@PersistenceContext
  	private EntityManager em;
  	
  	@Override
	public boolean crear(Lote lote) throws ServicesException {
  		try {
			validarAlta(lote);
			em.persist(lote);
			em.flush();
			return true;
		} catch (PersistenceException e) {
			throw new ServicesException("No se pudo crear el lote");
		}
	}
  	
	@Override
	public boolean crear(Lote lote, Producto producto) throws ServicesException {
		try {
			lote.setProducto(producto);
			validarAlta(lote);
			em.persist(lote);
			em.flush();
			return true;
		} catch (PersistenceException e) {
			throw new ServicesException("No se pudo crear el lote");
		}
	}

	@Override
	public boolean crear(BigDecimal cantidad, 
			String codigo, 
			Date fechaElaboracion, 
			Date fechaVencimiento,
			BigDecimal precio, 
			Producto producto) throws ServicesException {
		
		Lote lote = new Lote();
		lote.setCantidad(cantidad);
		lote.setCodigo(codigo);
		lote.setFechaElaboracion(fechaElaboracion);
		lote.setFechaVencimiento(fechaVencimiento);
		lote.setPrecio(precio);
		
		//Persiste el lote
		validarAlta(lote);
		em.persist(lote);
		em.flush();
		return true;
	}

	@Override
	public boolean actualizar(Lote lote) throws ServicesException {
		try {
			validarModificacion(lote);
			em.merge(lote);
			em.flush();
			return true;
		} catch (PersistenceException e) {
			throw new ServicesException("No se pudo actualizar el lote");
		}
	}

	@Override
	public boolean eliminar(Lote lote) throws ServicesException {
		try {
			Lote loteFind = em.find(Lote.class, lote.getId());
			validarBaja(loteFind);
			em.remove(loteFind);
			em.flush();
			return true;
		} catch (PersistenceException e) {
			throw new ServicesException(e.getMessage());
		}
	}

	@Override
	public boolean eliminar(Long id) throws ServicesException {
		try {
			Lote loteFind = em.find(Lote.class, id);
			validarBaja(loteFind);
			em.remove(loteFind);
			em.flush();
			return true;
		} catch (PersistenceException e) {
			throw new ServicesException("No se pudo eliminar el producto");
		}
	}

	@Override
	public List<Lote> obtenerTodos() {
		TypedQuery<Lote> query = em.createQuery("SELECT l FROM Lote l", Lote.class);
		return query.getResultList();
	}
	
	@Override
	public Lote obtenerPorId(Long id) {
		TypedQuery<Lote> query=em
				.createQuery("SELECT l FROM Lote l WHERE UPPER(l.id) LIKE:id", Lote.class)
				.setParameter("id", id);

		return query.getSingleResult();
	}

	@Override
	public List<Lote> obtenerTodosPorCodigo(String filtro) {
		TypedQuery<Lote> query=em
				.createQuery("SELECT l FROM Lote l WHERE UPPER(l.codigo) LIKE:codigo", Lote.class)
				.setParameter("codigo", "%"+filtro.toUpperCase()+"%");

		return query.getResultList();
	}

	@Override
	public void asignarProductoPorId(Long idLote, Long idProducto) throws ServicesException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void asignarProducto(Lote lote, Producto producto) throws ServicesException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean validarAlta(Lote lote) throws ServicesException {
		//Lista para almacenar lo que devuelven los métodos de validacion
		ArrayList<Boolean> valores = new ArrayList<Boolean>();

		try {
			valores.add(!codigoExiste(lote.getCodigo()));
			valores.add(!fechaVencimientoEsMayor(lote.getFechaElaboracion(), lote.getFechaVencimiento()));
			//Checkea si la lista no contiene ningun valor false
			if (!valores.contains(false)) {
				return true;
			}else {
				return false;
			}
		
		} catch (ServicesException e) {
			throw new ServicesException(e.getMessage());
		}
	}

	@Override
	public boolean validarModificacion(Lote lote) throws ServicesException {
		//Lista para almacenar lo que devuelven los métodos de validacion
		ArrayList<Boolean> valores = new ArrayList<Boolean>();

		try {
			valores.add(!codigoExiste(lote.getCodigo()));
			valores.add(!fechaVencimientoEsMayor(lote.getFechaElaboracion(), lote.getFechaVencimiento()));
			//Checkea si la lista no contiene ningun valor false
			if (!valores.contains(false)) {
				return true;
			}else {
				return false;
			}
		
		} catch (ServicesException e) {
			throw new ServicesException(e.getMessage());
		}
	}

	@Override
	public boolean validarBaja(Lote lote) throws ServicesException {
		//Lista para almacenar lo que devuelven los métodos de validacion
		ArrayList<Boolean> valores = new ArrayList<Boolean>();

		try {
			valores.add(!tieneAlmacenamientoAsociado(lote));
			valores.add(!tienePerdidaAsociada(lote));
			//Checkea si la lista no contiene ningun valor false
			if (!valores.contains(false)) {
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
		TypedQuery<Long> query = em.createQuery("SELECT COUNT(*) FROM Lote l WHERE l.codigo = :codigo", Long.class);
		query.setParameter("codigo", codigo);
		if (query.getSingleResult() <= 0) {
			return false;
		} else {
			throw new ServicesException("El código ya existe");
		}
	}

	@Override
	public boolean fechaVencimientoEsMayor(Date fechaElaboracion, Date fechaVencimiento) throws ServicesException {
		if (fechaVencimiento.after(fechaElaboracion)) {
			return true;
		} else {
			throw new ServicesException("La fecha de Elaboración no puede ser posterior a la de Vencimiento");
		}
	}

	@Override
	public boolean tieneAlmacenamientoAsociado(Lote lote) throws ServicesException {
		TypedQuery<Movimiento> query = em
				.createQuery("SELECT m FROM Movimiento m WHERE m.lote.id = :id AND  m.tipo = :tipo ", Movimiento.class);
		query.setParameter("id", lote.getId()).setParameter("tipo", "M");
		List<Movimiento> listaMovimientos = query.getResultList();
		
		
		if (listaMovimientos.isEmpty()) {
			return false;
		} else {
			TypedQuery<Date> query2 = em.createQuery("SELECT MAX(m.fecha) FROM Movimiento m WHERE m.lote.id = :id AND m.tipo = :tipo", Date.class);
			query2.setParameter("id", lote.getId()).setParameter("tipo", "M");
			Date maxDate = query2.getSingleResult();
			//System.out.println(maxDate);
			String descripcionAlmacenamiento = "";
			for (Movimiento movimiento : listaMovimientos) {
				if (movimiento.getFecha().equals(maxDate)) {
					descripcionAlmacenamiento = movimiento.getAlmacenamiento().getDescripcion();
				}
			}
			throw new ServicesException("Producto no se puede Eliminar porque existe en el Almacenamiento: " +descripcionAlmacenamiento+ ", elimínelo previamente de " +descripcionAlmacenamiento+ " para luego Eliminar el mismo");
		}
	}

	@Override
	public boolean tienePerdidaAsociada(Lote lote) throws ServicesException {
		TypedQuery<Long> query = em.createQuery("SELECT COUNT(*) FROM Movimiento m WHERE m.lote.id = :id AND m.tipo = :tipo", Long.class);
		query.setParameter("id", lote.getId()).setParameter("tipo", "P");
		if (query.getSingleResult() <= 0) {
			return true;
		} else {
			throw new ServicesException("Lote no se puede Eliminar porque existe en Pérdida, elimínelo previamente de Pérdida para luego Eliminar el mismo");
		}
	}

}
