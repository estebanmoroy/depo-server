package com.entities;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;
import java.util.List;


/**
 * The persistent class for the PEDIDOS database table.
 * 
 */
@Entity
@Table(name="PEDIDOS")
@NamedQuery(name="Pedido.findAll", query="SELECT p FROM Pedido p")
public class Pedido implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="PEDIDOS_PEDID_GENERATOR", sequenceName="SEQ_PED_ID")
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="PEDIDOS_PEDID_GENERATOR")
	@Column(name="PED_ID")
	private long pedId;

	@Column(name="PED_ESTADO")
	private String pedEstado;

	@Temporal(TemporalType.DATE)
	@Column(name="PED_FEC_EST_ENTRADA")
	private Date pedFecEstEntrada;

	@Temporal(TemporalType.DATE)
	@Column(name="PED_FECHA")
	private Date pedFecha;

	@Column(name="PED_REC_COMENTARIO")
	private String pedRecComentario;

	@Temporal(TemporalType.DATE)
	@Column(name="PED_REC_FECHA")
	private Date pedRecFecha;

	//bi-directional many-to-one association to Usuario
	@ManyToOne
	@JoinColumn(name="USUARIOS_USU_ID")
	private Usuario usuario;

	//bi-directional many-to-one association to RenglonPedido
	@OneToMany(mappedBy="pedido")
	private List<RenglonPedido> renPedidos;

	public Pedido() {
	}

	public long getPedId() {
		return this.pedId;
	}

	public void setPedId(long pedId) {
		this.pedId = pedId;
	}

	public String getPedEstado() {
		return this.pedEstado;
	}

	public void setPedEstado(String pedEstado) {
		this.pedEstado = pedEstado;
	}

	public Date getPedFecEstEntrada() {
		return this.pedFecEstEntrada;
	}

	public void setPedFecEstEntrada(Date pedFecEstEntrada) {
		this.pedFecEstEntrada = pedFecEstEntrada;
	}

	public Date getPedFecha() {
		return this.pedFecha;
	}

	public void setPedFecha(Date pedFecha) {
		this.pedFecha = pedFecha;
	}

	public String getPedRecComentario() {
		return this.pedRecComentario;
	}

	public void setPedRecComentario(String pedRecComentario) {
		this.pedRecComentario = pedRecComentario;
	}

	public Date getPedRecFecha() {
		return this.pedRecFecha;
	}

	public void setPedRecFecha(Date pedRecFecha) {
		this.pedRecFecha = pedRecFecha;
	}

	public Usuario getUsuario() {
		return this.usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public List<RenglonPedido> getRenPedidos() {
		return this.renPedidos;
	}

	public void setRenPedidos(List<RenglonPedido> renPedidos) {
		this.renPedidos = renPedidos;
	}

	public RenglonPedido addRenPedido(RenglonPedido renPedido) {
		getRenPedidos().add(renPedido);
		renPedido.setPedido(this);

		return renPedido;
	}

	public RenglonPedido removeRenPedido(RenglonPedido renPedido) {
		getRenPedidos().remove(renPedido);
		renPedido.setPedido(null);

		return renPedido;
	}

}