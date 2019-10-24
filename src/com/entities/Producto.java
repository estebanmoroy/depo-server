package com.entities;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;

import java.lang.reflect.Field;


/**
 * La clase de persistencia para la tabla PRODUCTOS de la base de datos
 * 
 */
@Entity
@Table(name="PRODUCTOS")
//@NamedQuery(name="Producto.findAll", query="SELECT p FROM Producto p")
public class Producto implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="PRODUCTOS_PROID_GENERATOR", sequenceName="SEQ_PRO_ID", allocationSize = 1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="PRODUCTOS_PROID_GENERATOR")
	@Column(name="PRO_ID")
	private long id;

	@Column(name="PRO_CODIGO")
	private String codigo;

	@Column(name="PRO_ESTIBA")
	private String estiba;

	@Column(name="PRO_NOMBRE")
	private String nombre;

	@Column(name="PRO_PESO")
	private BigDecimal peso;

	@Column(name="PRO_SEGMENTACION")
	private String segmentacion;

	@Column(name="PRO_STK_MINIMO")
	private BigDecimal stockMinimo;

	@Column(name="PRO_STK_TOTAL")
	private BigDecimal stockTotal;

	@Column(name="PRO_VOLUMEN")
	private BigDecimal volumen;

	//bi-directional many-to-one association to Familia
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="FAMILIAS_FAM_ID")
	private Familia familia;

	//bi-directional many-to-one association to Usuario
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="USUARIOS_USU_ID")
	private Usuario usuario;

	public Producto() {
	}
	
	public Producto(String codigo, String estiba, String nombre, BigDecimal peso, String segmentacion,
			BigDecimal stockMinimo, BigDecimal stockTotal, BigDecimal volumen) {
		super();
		this.codigo = codigo;
		this.estiba = estiba;
		this.nombre = nombre;
		this.peso = peso;
		this.segmentacion = segmentacion;
		this.stockMinimo = stockMinimo;
		this.stockTotal = stockTotal;
		this.volumen = volumen;
	}

	//Pasa un objeto Producto a String para imprimir en consola
	public String toString() {
		StringBuilder result = new StringBuilder();
		String newLine = System.getProperty("line.separator");

		result.append( "Producto {" );
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

	public String getCodigo() {
		return this.codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public String getEstiba() {
		return this.estiba;
	}

	public void setEstiba(String estiba) {
		this.estiba = estiba;
	}

	public String getNombre() {
		return this.nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public BigDecimal getPeso() {
		return this.peso;
	}

	public void setPeso(BigDecimal peso) {
		this.peso = peso;
	}

	public String getSegmentacion() {
		return this.segmentacion;
	}

	public void setSegmentacion(String segmentacion) {
		this.segmentacion = segmentacion;
	}

	public BigDecimal getStockMinimo() {
		return this.stockMinimo;
	}

	public void setStockMinimo(BigDecimal stockMinimo) {
		this.stockMinimo = stockMinimo;
	}

	public BigDecimal getStockTotal() {
		return this.stockTotal;
	}

	public void setStockTotal(BigDecimal proStockTotal) {
		this.stockTotal = proStockTotal;
	}

	public BigDecimal getVolumen() {
		return this.volumen;
	}

	public void setVolumen(BigDecimal volumen) {
		this.volumen = volumen;
	}

	public Familia getFamilia() {
		return this.familia;
	}

	public void setFamilia(Familia familia) {
		this.familia = familia;
	}

	public Usuario getUsuario() {
		return this.usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

}