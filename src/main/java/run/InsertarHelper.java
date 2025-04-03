package run;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Calendar;
import java.util.Collection;
import java.util.GregorianCalendar;

import servicios.AcademiaService;
import servicios.AcademiaServiceImpl;

import entidades.Alumno;
import entidades.Curso;
import entidades.Matricula;

public class InsertarHelper {
	
	AcademiaService srv = new AcademiaServiceImpl();
	
	/*
	 * Alumnos
	 */
	private void insertarAlumno(int id, String nombre, String rutaFoto) {
		
		System.out.println("\nCreando un alumno...");
		Alumno alumno = new Alumno(id, nombre);
		
		// Leemos la foto del disco, la guardamos en el
		// objeto Alumno y posteriormente se graba en la BD
		File file = new File(rutaFoto);
		
		byte[] foto = null;
		try {
			foto = getBytesFromFile(file);
		} catch (IOException e) { e.printStackTrace(); }
		
		alumno.setFoto(foto);
		
		if (srv.grabarAlumno(alumno) == 1) {
			System.out.print("Alumno insertado correctamente");
		} else {
			System.out.print("Error al insertar el alumno");
		}
	}
	
	private void modificarAlumno(int id, String nombre, String rutaFoto) {
		
		// Recuperamos al alumno a partir de su id
		Alumno alumno = srv.getAlumno(id);
		
		System.out.println("\nModificando el nombre del alumno con id: " + 
				id + " y nombre: " + alumno.getNombreAlumno());
		
		alumno.setNombreAlumno(nombre);
		
		// Si se ha pasado la ruta de la foto...
		if (rutaFoto != null) {
			System.out.println("\nModificando la foto del alumno con id: " + 
					id + " y nombre: "+alumno.getNombreAlumno());
			// Leemos la foto del disco, la guardamos en el
			// objeto Alumno y posteriormente se graba en la BD
			File file = new File(rutaFoto);
			
			byte[] foto = null;
			try {
				foto = getBytesFromFile(file);
			} catch (IOException e) { e.printStackTrace(); }
			alumno.setFoto(foto);
		}
		
		if (srv.actualizarAlumno(alumno) == 1) {
			System.out.print("Alumno actualizado correctamente");
		} else {
			System.out.print("Error al actualizar el alumno");
		}
	}
	
	/*
	 * Cursos
	 */
	
	private void insertarCurso(int id, String nombre) {
		
		System.out.println("\nCreando un curso...");
		Curso curso = new Curso(id, nombre);
		
		if (srv.grabarCurso(curso) == 1) {
			System.out.print("Curso insertado correctamente");
		} else {
			System.out.print("Error al insertar el curso");
		}		
	}
	
	private void modificarCurso(int id, String nombre) {
		
		// Recuperamos al curso a partir de su id
		Curso curso = srv.getCurso(id);
		
		System.out.println("\nModificando el nombre del curso con id: " + 
				id + " y nombre: " + curso.getNombreCurso());
		curso.setNombreCurso(nombre);
		
		if (srv.actualizarCurso(curso) == 1) {
			System.out.print("Curso actualizado correctamente");
		} else {
			System.out.print("Error al actualizar el curso");
		}
	}
	
	/*
	 * Matriculas
	 */
			
	private void insertarMatricula(int id_alumno, int id_curso) {
		
		System.out.println("\nCreando una matricula...");
		Matricula matricula = new Matricula(id_alumno, id_curso);
		
		// Recuperamos al alumno a partir de su id
		//Alumno alumno=dao.getAlumno(id_alumno);
		
		// Recuperamos al curso a partir de su id
		//Curso curso=dao.getCurso(id_curso);
		
		/*matricula.setAlumno(alumno);
		matricula.setCurso(curso);*/
		
		if (srv.grabarMatricula(matricula) > 0) {
			System.out.print("Matricula insertada correctamente.");
		} else {
			System.out.print("Error al insertar la matricula");
		}
	}
	
	private void modificarMatricula(int id_alumno, int id_curso, java.util.Date fecha) {
						
		// A partir del id del alumno y del id del curso 
		// obtenemos el id de matricula
		long id_matricula = srv.getIdMatricula(id_alumno, id_curso);
		
		// Mediante el id de matricula obtenemos la matricula
		// que queremos modificar
		Matricula matricula = srv.getMatricula(id_matricula);
		
		// Cambiamos la fecha de inicio de la matricula
		matricula.setFechaInicio(fecha);
						
		System.out.println("\nModificando fecha de la matricula...");
		if (srv.actualizarMatricula(matricula) == 1) {
			System.out.print("Matricula modificada correctamente");
		} else {
			System.out.print("Error al modificar la matricula");
		}
	}
	
	private void showAllData() {
		showData(srv.cargarAlumnos(),"Alumnos");
		showData(srv.cargarCursos(),"Cursos");
		showData(srv.cargarMatriculas(),"Matriculas");		
	}
	
	private void showData(Collection<?> coleccion, String entidad) {
		
		System.out.println("\nMostrando..."+entidad);
		
		for (Object obj:coleccion) 
			System.out.println(obj);		
	}
	
	public static void main(String[] args) {
		
		InsertarHelper programa = new InsertarHelper();
				
		/*
		 * Insertar alumnos
		 */
		
		programa.insertarAlumno(1000,"Daniel","imagenes/cara2.jpg");
		programa.insertarAlumno(1001,"Francisco","imagenes/cara4.jpg");
				
		// Cambiarle el nombre al primer alumno creado
		programa.modificarAlumno(1000, "Ezequiel",null);
		
		// Volverle a cambiar el nombre y ahora la foto
		programa.modificarAlumno(1000, "Agapito","imagenes/cara1.jpg");
		
		/*
		 * Insertar cursos
		 */
		programa.insertarCurso(500, "Java");
		programa.insertarCurso(501, ".NET");
		
		// Modificar el curso creado		
		programa.modificarCurso(500, "Java avanzado");		
			
		/*
		 * Insertar matriculas
		 */		
		programa.insertarMatricula(1000, 500);
		programa.insertarMatricula(1000, 501);
		programa.insertarMatricula(1001, 500);		
		
		/*
		 * Modificar fecha de la segunda matricula
		 */		
		Calendar fecha=GregorianCalendar.getInstance();
		fecha.set(Calendar.MONTH, 11);
		programa.modificarMatricula(1001, 500, fecha.getTime());
						
		/*
		 * Mostrar lo que hemos grabado
		 * 
		 */
		programa.showAllData();
				
		System.out.println("\nfin del programa.");
	}
	
	/*
	 * Devuelve el contenido del fichero (la foto)
	 * en un array de bytes
	 */
	
	private static byte[] getBytesFromFile(File file) throws IOException {
	    InputStream is = new FileInputStream(file);

	    // Obtener el tamano del fichero
	    long length = file.length();

	    // No podemos crear un array usando un tipo long.
	    // Es necesario que sea un tipo int.
	    // Antes de convertirlo a int, comprobamos
	    // que el fichero no es mayor que Integer.MAX_VALUE
	    if (length > Integer.MAX_VALUE) {	        
	    	System.out.println("Fichero demasiado grande!");
	    	System.exit(1);
	    }

	    // Creamos el byte array que almacenara 
	    // temporalmente los datos leidos
	    byte[] bytes = new byte[(int)length];

	    // Leemos
	    int offset = 0;
	    int numRead = 0;
	    while (offset < bytes.length
	           && (numRead=is.read(bytes, offset, bytes.length-offset)) >= 0) {
	        offset += numRead;
	    }

	    // Comprobacion de que todos los bytes se han leido
	    if (offset < bytes.length) {
	    	is.close();
	        throw new IOException("No se ha podido leer completamente el fichero "+file.getName());
	    }

	    // Cerrar el input stream y devolver los bytes
	    is.close();
	    return bytes;
	}

}
