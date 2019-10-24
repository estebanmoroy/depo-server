package com.entities;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;


/**
 * The persistent class for the MOVIMIENTOS database table.
 * 
 */
@Entity
@Table(name="MOVIMIENTOS")
@NamedQuery(name="Movimiento.findAll", query="SELECT m FROM Movimiento m")
public class Movimiento implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="MOVIMIENTOS_MOVID_GENERATOR", sequenceName="SEQ_MOV_ID")
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="MOVIMIENTOS_MOVID_GENERATOR")
	@Column(name="MOV_ID")
	private long id;

	@Column(name="MOV_CANTIDAD")
	private BigDecimal cantidad;

	@Column(name="MOV_COSTO")
	private BigDecimal costo;

	@Column(name="MOV_DESCRIPCION")
	private String descripcion;

	@Temporal(TemporalType.DATE)
	@Column(name="MOV_FECHA")
	private Date fecha;

	@Column(name="MOV_TIPO")
	private String tipo;

	//bi-directional many-to-one association to Almacenamiento
	@ManyToOne
	@JoinColumn(name="ALMACENAMIENTOS_ALM_ID")
	private Almacenamiento almacenamiento;

	//bi-directional many-to-one association to Lote
	@ManyToOne
	@JoinColumn(name="LOTES_LOT_ID")
	private Lote lote;

	public Movimiento() {
	}

	public long getId() {
		return this.id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public BigDecimal getCantidad() {
		return this.cantidad;
	}

	public void setCantidad(BigDecimal cantidad) {
		this.cantidad = cantidad;
	}

	public BigDecimal getCosto() {
		return this.costo;
	}

	public void setCosto(BigDecimal costo) {
		this.costo = costo;
	}

	public String getDescripcion() {
		return this.descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public Date getFecha() {
		return this.fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	public String getTipo() {
		return this.tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public Almacenamiento getAlmacenamiento() {
		return this.almacenamiento;
	}

	public void setAlmacenamiento(Almacenamiento almacenamiento) {
		this.almacenamiento = almacenamiento;
	}

	public Lote getLote() {
		return this.lote;
	}

	public void setLote(Lote lote) {
		this.lote = lote;
	}

}