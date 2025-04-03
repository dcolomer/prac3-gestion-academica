package entidades;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Matricula implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private long idMatricula;
	private Date fechaInicio;
	private int idAlumno;
	private int idCurso;
	
	public Matricula(int idAlumno, int idCurso) {
		this(idAlumno, idCurso, new Date());
	}
	
	public Matricula(int idAlumno, int idCurso, Date fechaInicio) {
		this(0L, idAlumno, idCurso, fechaInicio);
	}
	
	public Matricula(long idMatricula, int idAlumno, int idCurso, Date fechaInicio) {
		this.idMatricula = idMatricula;
		this.idAlumno = idAlumno;
		this.idCurso = idCurso;
		this.fechaInicio = fechaInicio;
	}
		
	public long getIdMatricula() {
		return idMatricula;
	}

	public void setIdMatricula(long idMatricula) {
		this.idMatricula = idMatricula;
	}

	public Date getFechaInicio() {
		return fechaInicio;
	}

	public void setFechaInicio(Date fechaInicio) {
		this.fechaInicio = fechaInicio;
	}

	public int getIdAlumno() {
		return idAlumno;
	}

	public void setIdAlumno(int idAlumno) {
		this.idAlumno = idAlumno;
	}

	public int getIdCurso() {
		return idCurso;
	}

	public void setIdCurso(int idCurso) {
		this.idCurso = idCurso;
	}
	
	public String toString() {
		SimpleDateFormat fd = new SimpleDateFormat("dd/MM/yyyy");
		StringBuilder salida = new StringBuilder("ID MAT.: ");		
		salida.append(idMatricula+" - ID ALUMNO: ");
		salida.append(idAlumno+" - ID CURSO: ");
		salida.append(idCurso+" - FECHA MAT: ");
		salida.append(fd.format(fechaInicio));
		return salida.toString();
	}		
}

/**
 * Sí, estás utilizando buenas prácticas al no almacenar directamente las referencias a los objetos Alumno y Curso, 
 * y en su lugar guardar sus respectivos IDs (idAlumno e idCurso). Este enfoque tiene varias ventajas:

1. Desacoplamiento de entidades:
Al almacenar solo los IDs, estás desacoplando la clase Matricula de las clases Alumno y Curso. Esto significa que
 Matricula no necesita conocer la estructura interna de Alumno o Curso, lo que facilita la mantenibilidad y evita 
 problemas de dependencia circular.

2. Serialización más ligera:
Al no incluir las referencias completas a Alumno y Curso, la serialización de la clase Matricula será más ligera, 
ya que solo se almacenan los IDs en lugar de los objetos completos. Esto es especialmente útil si estás trabajando 
con sistemas distribuidos o almacenamiento persistente.

3. Consistencia y normalización de datos:
Este enfoque sigue el principio de normalización de bases de datos, donde las relaciones entre entidades se manejan
mediante claves foráneas (en este caso, los IDs). Esto ayuda a mantener la consistencia de los datos y evita la redundancia.

4. Facilidad de consulta:
Al trabajar con bases de datos relacionales, es más fácil y eficiente realizar consultas cuando las relaciones se 
manejan mediante IDs. Por ejemplo, puedes hacer joins entre tablas usando los IDs de Alumno y Curso sin necesidad 
de cargar objetos completos en memoria.

5. Evita problemas de recursión:
Si Alumno y Curso también tuvieran referencias a Matricula, podrías caer en problemas de recursión infinita al 
serializar o deserializar los objetos. Al usar solo IDs, evitas este problema.

Consideraciones adicionales:
Validación de IDs: Asegúrate de que los IDs de Alumno y Curso sean válidos antes de asignarlos a una Matricula. 
Esto puede implicar consultas a la base de datos o validaciones adicionales.

Lazy Loading: Si en algún momento necesitas acceder a los detalles completos de Alumno o Curso, puedes implementar 
un mecanismo de "lazy loading" para cargar estos objetos solo cuando sea necesario, en lugar de almacenarlos 
directamente en la clase Matricula.

Ejemplo de mejora:
Si en algún momento necesitas acceder a los datos de Alumno o Curso, podrías agregar métodos que permitan obtener 
estos objetos a partir de sus IDs, pero sin almacenarlos directamente en la clase Matricula:

public Alumno getAlumno(AlumnoRepository alumnoRepository) {
return alumnoRepository.findById(idAlumno);
}

public Curso getCurso(CursoRepository cursoRepository) {
return cursoRepository.findById(idCurso);
}

Esto te permite mantener el desacoplamiento y la ligereza de la clase Matricula, mientras que aún puedes acceder a 
los objetos relacionados cuando sea necesario.

En resumen, tu enfoque es correcto y sigue buenas prácticas de diseño orientado a objetos y bases de datos.
*/