package com.services;

import java.math.BigDecimal;
import java.util.List;

import javax.ejb.Remote;

import com.entities.*;
import com.exception.ServicesException;

@Remote
public interface ProductoBeanRemote {
	
	boolean crear(Producto producto) throws ServicesException;
	boolean crear(Producto producto, Usuario usuario, Familia familia) throws ServicesException;
	boolean crear(String codigo, 
			String estiba, 
			String nombre, 
			BigDecimal peso, 
			String segmentacion, 
			BigDecimal stockMinimo,
			BigDecimal stockTotal,
			BigDecimal volumen, 
			Usuario usuario, 
			Familia familia) throws ServicesException;
	
	boolean actualizar(Producto producto) throws ServicesException;
	
	boolean eliminar(Producto producto) throws ServicesException;
	boolean eliminar(Long id) throws ServicesException;
	
	void asignarFamiliaPorId(Long idProducto, Long idFamilia) throws ServicesException;
	void asignarFamilia(Producto producto, Familia familia) throws ServicesException;
	
	void asignarUsuarioPorId(Long idProducto, Long idUsuario) throws ServicesException;
	void asignarUsuario(Producto producto, Usuario usuario) throws ServicesException;
	
	List<Producto> obtenerTodos();
	List<Producto> obtenerTodosPorCodigo(String filtro);
	List<Producto> obtenerTodosPorNombre(String filtro);
	
	boolean validarAlta(Producto producto) throws ServicesException;
	boolean validarModificacion(Producto producto) throws ServicesException;
	boolean validarBaja(Producto producto) throws ServicesException;
	
	//Métodos utilizadas para validar ABM
	boolean codigoExiste(String codigo) throws ServicesException;
	boolean nombreExiste(String nombre) throws ServicesException;
	boolean nombreSuperaMaximoDeCaracteres(String nombre) throws ServicesException;
	boolean tienePedidoAsociado(Producto producto) throws ServicesException;

}
