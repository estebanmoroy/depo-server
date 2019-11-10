package com.services;

import java.util.List;

import javax.ejb.Remote;

import com.entities.*;
import com.exception.ServicesException;

@Remote
public interface FamiliaBeanRemote {
	
	boolean crear(Familia familia/*, Usuario usuario*/) throws ServicesException;
	boolean crear(String codigo, String descripcion, String incompatible, String nombre/*, Usuario usuario*/) throws ServicesException;
	
	boolean actualizar(Familia familia/*, Usuario usuario*/) throws ServicesException;
	
	boolean eliminar(Familia familia/*, Usuario usuario*/) throws ServicesException;
	boolean eliminar(Long id/*, Usuario usuario*/) throws ServicesException;
	
	List<Familia> obtenerTodos();
	Familia obtenerPorId(Long id);
	List<Familia> obtenerTodosPorCodigo(String filtro);
	List<Familia> obtenerTodosPorNombre(String filtro);
	List<Familia> obtenerTodosPorDescripcion(String filtro);
	
	boolean validarAlta(Familia familia/*, String nombrePerfil*/) throws ServicesException;
	boolean validarModificacion(Familia familia/*, String nombrePerfil*/) throws ServicesException;
	boolean validarBaja(Familia familia/*, String nombrePerfil*/) throws ServicesException;

	//Métodos para validar ABM
	//boolean usuarioTienePermisos(String nombrePerfil) throws ServicesException;
	boolean codigoExiste(String codigo) throws ServicesException;
	boolean nombreExiste(String nombre) throws ServicesException;
	boolean descripcionExiste(String descripcion) throws ServicesException;
	boolean descripcionSuperaMaximoDeCaracteres(String descripcion) throws ServicesException;
	boolean tieneProductoAsociado(Familia familia) throws ServicesException;

}
