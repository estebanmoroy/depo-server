package com.entities;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;


/**
 * The persistent class for the ALMACENAMIENTOS database table.
 * 
 */
@Entity
@Table(name="ALMACENAMIENTOS")
@NamedQuery(name="Almacenamiento.findAll", query="SELECT a FROM Almacenamiento a")
public class Almacenamiento implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="ALMACENAMIENTOS_ALMID_GENERATOR", sequenceName="SEQ_ALM_ID")
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="ALMACENAMIENTOS_ALMID_GENERATOR")
	@Column(name="ALM_ID")
	private long id;

	@Column(name="ALM_CAP_ESTIBA")
	private String capacidadEstiba;

	@Column(name="ALM_CAP_PESO")
	private BigDecimal capacidadPeso;

	@Column(name="ALM_CODIGO")
	private String codigo;

	@Column(name="ALM_COS_OPERATIVO")
	private BigDecimal costoOperativo;

	@Column(name="ALM_DESCRIPCION")
	private String descripcion;

	@Column(name="ALM_VOLUMEN")
	private BigDecimal volumen;

	//bi-directional many-to-one association to Local
	@ManyToOne
	@JoinColumn(name="LOCALES_LOC_ID")
	private Local local;

	//bi-directional many-to-one association to Movimiento
	@OneToMany(mappedBy="almacenamiento")
	private List<Movimiento> movimientos;

	public Almacenamiento() {
	}

	public long getId() {
		return this.id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getCapacidadEstiba() {
		return this.capacidadEstiba;
	}

	public void setCapacidadEstiba(String capacidadEstiba) {
		this.capacidadEstiba = capacidadEstiba;
	}

	public BigDecimal getCapacidadPeso() {
		return this.capacidadPeso;
	}

	public void setCapacidadPeso(BigDecimal capacidadPeso) {
		this.capacidadPeso = capacidadPeso;
	}

	public String getCodigo() {
		return this.codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public BigDecimal getCostoOperativo() {
		return this.costoOperativo;
	}

	public void setCostoOperativo(BigDecimal costoOperativo) {
		this.costoOperativo = costoOperativo;
	}

	public String getDescripcion() {
		return this.descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public BigDecimal getVolumen() {
		return this.volumen;
	}

	public void setVolumen(BigDecimal volumen) {
		this.volumen = volumen;
	}

	public Local getLocal() {
		return this.local;
	}

	public void setLocal(Local local) {
		this.local = local;
	}

	public List<Movimiento> getMovimientos() {
		return this.movimientos;
	}

	public void setMovimientos(List<Movimiento> movimientos) {
		this.movimientos = movimientos;
	}

	public Movimiento addMovimiento(Movimiento movimiento) {
		getMovimientos().add(movimiento);
		movimiento.setAlmacenamiento(this);

		return movimiento;
	}

	public Movimiento removeMovimiento(Movimiento movimiento) {
		getMovimientos().remove(movimiento);
		movimiento.setAlmacenamiento(null);

		return movimiento;
	}

}