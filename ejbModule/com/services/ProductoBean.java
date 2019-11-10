package com.services;

import java.math.BigDecimal;
import java.util.List;
import java.util.ArrayList;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import javax.persistence.TypedQuery;

import com.exception.ServicesException;
import com.entities.*;

@Stateless
@LocalBean
public class ProductoBean implements ProductoBeanRemote {
	
	//Default constructor
	public ProductoBean() {}
		
	//Maximo de caracteres en descripcion
	private static final int MAXIMO_CARACTERES_DESCRIPCION = 50;
	
	//Entity Manager
	@PersistenceContext
	private EntityManager em;

	@Override
	public boolean crear(Producto producto) throws ServicesException {
		try {
			validarAlta(producto);
			em.persist(producto);
			em.flush();
			return true;
		} catch (PersistenceException e) {
			throw new ServicesException("No se pudo crear el producto");
		}
	}
	
	@Override
	public boolean crear(Producto producto, 
			Usuario usuario, 
			Familia familia) throws ServicesException {
		try {
			producto.setUsuario(usuario);
			producto.setFamilia(familia);
			validarAlta(producto);
			em.persist(producto);
			em.flush();
			return true;
		} catch (PersistenceException e) {
			throw new ServicesException("No se pudo crear el producto");
		}
	}

	@Override
	public boolean crear(String codigo, 
			String estiba, 
			String nombre, 
			BigDecimal peso, 
			String segmentacion,
			BigDecimal stockMinimo, 
			BigDecimal stockTotal, 
			BigDecimal volumen, 
			Usuario usuario, 
			Familia familia) throws ServicesException {
		
		try {
			//Crea y asigna un nuevo producto
			Producto producto = new Producto();
			producto.setCodigo(codigo);
			producto.setEstiba(estiba);
			producto.setNombre(nombre);
			producto.setPeso(peso);
			producto.setSegmentacion(segmentacion);
			producto.setStockMinimo(stockMinimo);
			producto.setStockTotal(stockTotal);
			producto.setVolumen(volumen);
			producto.setUsuario(usuario);
			producto.setFamilia(familia);
			
			//Persiste el producto
			validarAlta(producto);
			em.persist(producto);
			em.flush();
			return true;
		} catch (PersistenceException e) {
			throw new ServicesException("No se pudo crear el producto");
		}
	}

	@Override
	public boolean actualizar(Producto producto) throws ServicesException {
		try {
			validarModificacion(producto);
			em.merge(producto);
			em.flush();
			return true;
		} catch (PersistenceException e) {
			throw new ServicesException("No se pudo actualizar el producto");
		}
	}

	@Override
	public boolean eliminar(Producto producto) throws ServicesException {
		try {
			Producto productoFind = em.find(Producto.class, producto.getId());
			validarBaja(productoFind);
			em.remove(productoFind);
			em.flush();
			return true;
		} catch (PersistenceException e) {
			throw new ServicesException(e.getMessage());
		}
	}

	@Override
	public boolean eliminar(Long id) throws ServicesException {
		try {
			Producto productoFind = em.find(Producto.class, id);
			validarBaja(productoFind);
			em.remove(productoFind);
			em.flush();
			return true;
		} catch (PersistenceException e) {
			throw new ServicesException("No se pudo eliminar el producto");
		}
	}

	@Override
	public void asignarFamiliaPorId(Long idProducto, Long idFamilia) throws ServicesException {
		Producto producto = em.find(Producto.class, idProducto);
		producto.setFamilia(em.find(Familia.class, idFamilia));
		em.flush();
	}

	@Override
	public void asignarFamilia(Producto producto, Familia familia) throws ServicesException {
		asignarFamiliaPorId(producto.getId(), familia.getId());
	}

	@Override
	public void asignarUsuarioPorId(Long idProducto, Long idUsuario) throws ServicesException {
		Producto producto = em.find(Producto.class, idProducto);
		producto.setUsuario(em.find(Usuario.class, idUsuario));
		em.flush();
	}

	@Override
	public void asignarUsuario(Producto producto, Usuario usuario) throws ServicesException {
		asignarUsuarioPorId(producto.getId(), usuario.getId());
	}

	@Override
	public List<Producto> obtenerTodos() {
		TypedQuery<Producto> query = em.createQuery("SELECT p FROM Producto p", Producto.class);
		return query.getResultList();
	}

	@Override
	public Producto obtenerPorId(Long id) {
		TypedQuery<Producto> query=em
				.createQuery("SELECT p FROM Producto p WHERE UPPER(p.id) LIKE:id", Producto.class)
				.setParameter("id", id);

		return query.getSingleResult();
	}
	
	@Override
	public List<Producto> obtenerTodosPorCodigo(String filtro) {
		TypedQuery<Producto> query=em
				.createQuery("SELECT p FROM Producto p WHERE UPPER(p.codigo) LIKE :codigo", Producto.class)
				.setParameter("codigo", "%"+filtro.toUpperCase()+"%");
		
		return query.getResultList();
	}

	@Override
	public List<Producto> obtenerTodosPorNombre(String filtro) {
		TypedQuery<Producto> query=em
				.createQuery("SELECT p FROM Producto p WHERE UPPER(p.nombre) LIKE :nombre", Producto.class)
				.setParameter("nombre", "%"+filtro.toUpperCase()+"%");
		
		return query.getResultList();
	}

	@Override
	public boolean validarAlta(Producto producto) throws ServicesException {
		//Lista para almacenar lo que devuelven los métodos de validacion
		ArrayList<Boolean> valores = new ArrayList<Boolean>();
			
		try {
			valores.add(!codigoExiste(producto.getCodigo()));
			valores.add(!nombreExiste(producto.getNombre()));
			valores.add(!nombreSuperaMaximoDeCaracteres(producto.getNombre()));
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
	public boolean validarModificacion(Producto producto) throws ServicesException {
		//Lista para almacenar lo que devuelven los métodos de validacion
		ArrayList<Boolean> valores = new ArrayList<Boolean>();
			
		try {
			valores.add(!nombreSuperaMaximoDeCaracteres(producto.getNombre()));
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
	public boolean validarBaja(Producto producto) throws ServicesException {
		//Lista para almacenar lo que devuelven los métodos de validacion
		ArrayList<Boolean> valores = new ArrayList<Boolean>();

		try {
			valores.add(!tienePedidoAsociado(producto));
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
		TypedQuery<Long> query = em.createQuery("SELECT COUNT(*) FROM Producto p WHERE p.codigo = :codigo", Long.class);
		query.setParameter("codigo", codigo);
		if (query.getSingleResult() <= 0) {
			return false;
		} else {
			throw new ServicesException("El código ya existe");
		}
	}

	@Override
	public boolean nombreExiste(String nombre) throws ServicesException {
		TypedQuery<Long> query = em.createQuery("SELECT COUNT(*) FROM Producto p WHERE p.nombre = :nombre", Long.class);
		query.setParameter("nombre", nombre);
		if (query.getSingleResult() <= 0) {
			return false;
		} else {
			throw new ServicesException("El nombre ya existe");
		}
	}

	@Override
	public boolean nombreSuperaMaximoDeCaracteres(String nombre) throws ServicesException {
		if (nombre.length() <= MAXIMO_CARACTERES_DESCRIPCION) {
			return false;
		} else {
			throw new ServicesException("El nombre supera el máximo de caracteres");
		}
	}

	@Override
	public boolean tienePedidoAsociado(Producto producto) throws ServicesException {
		TypedQuery<Long> query = em.createQuery("SELECT COUNT(*) FROM RenglonPedido r WHERE r.producto.id = :id", Long.class);
		query.setParameter("id", producto.getId());
		if (query.getSingleResult() == 0) {
			return false;
		}else {
		throw new ServicesException("Producto no se puede Eliminar porque existe en Pedidos, elimínelo previamente de Pedidos para luego Eliminar el mismo");
		}
	}
	
}
