package com.entities;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;


/**
 * The persistent class for the REN_PEDIDOS database table.
 * 
 */
@Entity
@Table(name="REN_PEDIDOS")
@NamedQuery(name="RenglonPedido.findAll", query="SELECT r FROM RenglonPedido r")
public class RenglonPedido implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="REN_PEDIDOS_RENNRO_GENERATOR", sequenceName="SEQ_REN_NRO")
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="REN_PEDIDOS_RENNRO_GENERATOR")
	@Column(name="REN_NRO")
	private long renNro;

	@Column(name="REN_CANTIDAD")
	private BigDecimal renCantidad;

	//bi-directional many-to-one association to Pedido
	@ManyToOne
	@JoinColumn(name="PEDIDOS_PED_ID")
	private Pedido pedido;

	//bi-directional many-to-one association to Producto
	@ManyToOne
	@JoinColumn(name="PRODUCTOS_PRO_ID")
	private Producto producto;

	public RenglonPedido() {
	}

	public long getRenNro() {
		return this.renNro;
	}

	public void setRenNro(long renNro) {
		this.renNro = renNro;
	}

	public BigDecimal getRenCantidad() {
		return this.renCantidad;
	}

	public void setRenCantidad(BigDecimal renCantidad) {
		this.renCantidad = renCantidad;
	}

	public Pedido getPedido() {
		return this.pedido;
	}

	public void setPedido(Pedido pedido) {
		this.pedido = pedido;
	}

	public Producto getProducto() {
		return this.producto;
	}

	public void setProducto(Producto producto) {
		this.producto = producto;
	}

}