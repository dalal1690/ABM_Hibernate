package hibernate.test.dto;

import java.io.Serializable;
import java.sql.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity
@Table(name = "PERSONASABM", uniqueConstraints = { @UniqueConstraint(columnNames = "ID") }) // AQUI SOLO VA EL PRIMARY KEY
public class PersonaEntity implements Serializable {

	private static final long serialVersionUID = -1798070786993154676L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID", unique = true, nullable = false)
	private Integer id;

	@Column(name = "NOMBRE", unique = false, nullable = false, length = 100)
	private String nombre;

	@Column(name = "FECHA_DE_NACIMIENTO", unique = false, nullable = false, length = 100)
	private String fechadenacimiento;
	// se escriben con _ los nombres de la tabla y todo junto para los atributos
	@Column(name = "EDAD", unique = false, nullable = false, length = 100)
	private int edad;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getFechadenacimiento() {
		return fechadenacimiento;
	}

	public void setFechaDeNacimiento(String fechadenacimiento) {
		this.fechadenacimiento = fechadenacimiento;
	}

	public int getEdad() {
		return edad;
	}

	public void setEdad(int edad) {
		this.edad = edad;

	}

	public boolean isEmpty() {
		// TODO Auto-generated method stub
		return false;
	}
}
