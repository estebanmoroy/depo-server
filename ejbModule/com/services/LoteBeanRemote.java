package com.services;

import javax.ejb.Remote;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import com.entities.*;
import com.exception.ServicesException;

@Remote
public interface LoteBeanRemote {

	boolean crear(Lote lote) throws ServicesException;
	boolean crear(Lote lote, Producto producto) throws ServicesException;
	boolean crear(BigDecimal cantidad,
			String codigo, 
			Date fechaElaboracion, 
			Date fechaVencimiento, 
			BigDecimal precio, 
			Producto producto) throws ServicesException;
	
	boolean actualizar(Lote lote) throws ServicesException;
	
	boolean eliminar(Lote lote) throws ServicesException;
	boolean eliminar(Long id) throws ServicesException;
	
	List<Lote> obtenerTodos();
	Lote obtenerPorId(Long id);
	List<Lote> obtenerTodosPorCodigo(String filtro);
	
	void asignarProductoPorId(Long idLote, Long idProducto) throws ServicesException;
	void asignarProducto(Lote lote, Producto producto) throws ServicesException;
	
	boolean validarAlta(Lote lote) throws ServicesException;
	boolean validarModificacion(Lote lote) throws ServicesException;
	boolean validarBaja(Lote lote) throws ServicesException;
	
	boolean codigoExiste(String codigo) throws ServicesException;
	boolean fechaVencimientoEsMayor(Date fechaElaboracion, Date fechaVencimiento) throws ServicesException;
	boolean tieneAlmacenamientoAsociado(Lote lote) throws ServicesException;
	boolean tienePerdidaAsociada(Lote lote) throws ServicesException;
}
