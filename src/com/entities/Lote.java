package com.entities;

import java.io.Serializable;
import java.lang.reflect.Field;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;


/**
 * La clase de persistencia para la tabla LOTES de la base de datos
 * 
 */
@Entity
@Table(name="LOTES")
//@NamedQuery(name="Lote.findAll", query="SELECT l FROM Lote l")
public class Lote implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="LOTES_LOTID_GENERATOR", sequenceName="SEQ_LOT_ID", allocationSize = 1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="LOTES_LOTID_GENERATOR")
	@Column(name="LOT_ID")
	private long id;

	@Column(name="LOT_CANTIDAD")
	private BigDecimal cantidad;

	@Column(name="LOT_CODIGO")
	private String codigo;

	@Temporal(TemporalType.DATE)
	@Column(name="LOT_FEC_ELABORACION")
	private Date fechaElaboracion;

	@Temporal(TemporalType.DATE)
	@Column(name="LOT_FEC_VENCIMIENTO")
	private Date fechaVencimiento;

	@Column(name="LOT_PRECIO")
	private BigDecimal precio;

	//bi-directional many-to-one association to Producto
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="PRODUCTOS_PRO_ID")
	private Producto producto;

//	//bi-directional many-to-one association to Movimiento
//	@OneToMany(mappedBy="lote")
//	private List<Movimiento> movimientos;

	public Lote() {}

	public Lote(BigDecimal cantidad, String codigo, Date fechaElaboracion, Date fechaVencimiento, BigDecimal precio) {
		super();
		this.cantidad = cantidad;
		this.codigo = codigo;
		this.fechaElaboracion = fechaElaboracion;
		this.fechaVencimiento = fechaVencimiento;
		this.precio = precio;
	}
	
	//Pasa un objeto Lote a String para imprimir en consola
	public String toString() {
		StringBuilder result = new StringBuilder();
		String newLine = System.getProperty("line.separator");
		
		result.append( "Lote {" );
		result.append(newLine);

		//determina campos de la clase
		Field[] fields = this.getClass().getDeclaredFields();

		//imprimir campos junto con sus valores
		for ( Field field : fields  ) {
			result.append("  ");
			try {
				result.append( field.getName() );
				result.append(": ");
				result.append( field.get(this) );
			} catch ( IllegalAccessException ex ) {
				System.out.println(ex);
			}
			result.append(newLine);
		}
		result.append("}");
			return result.toString();
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

	public String getCodigo() {
		return this.codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public Date getFechaElaboracion() {
		return this.fechaElaboracion;
	}

	public void setFechaElaboracion(Date fechaElaboracion) {
		this.fechaElaboracion = fechaElaboracion;
	}

	public Date getFechaVencimiento() {
		return this.fechaVencimiento;
	}

	public void setFechaVencimiento(Date fechaVencimiento) {
		this.fechaVencimiento = fechaVencimiento;
	}

	public BigDecimal getPrecio() {
		return this.precio;
	}

	public void setPrecio(BigDecimal precio) {
		this.precio = precio;
	}

	public Producto getProducto() {
		return this.producto;
	}

	public void setProducto(Producto producto) {
		this.producto = producto;
	}

//	public List<Movimiento> getMovimientos() {
//		return this.movimientos;
//	}
//
//	public void setMovimientos(List<Movimiento> movimientos) {
//		this.movimientos = movimientos;
//	}
//
//	public Movimiento addMovimiento(Movimiento movimiento) {
//		getMovimientos().add(movimiento);
//		movimiento.setLote(this);
//
//		return movimiento;
//	}
//
//	public Movimiento removeMovimiento(Movimiento movimiento) {
//		getMovimientos().remove(movimiento);
//		movimiento.setLote(null);
//
//		return movimiento;
//	}

}