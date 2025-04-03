package run;

import java.util.Collection;
import java.util.Iterator;

import entidades.Alumno;
import entidades.Curso;
import entidades.Matricula;

import servicios.AcademiaService;
import servicios.AcademiaServiceImpl;

public class BorrarHelper {

	public static void main(String[] args) {

		AcademiaService srv = new AcademiaServiceImpl();		
		
		// Recuperar las Matriculas de la base de datos.
		Collection<Matricula> collecMatriculas = srv.cargarMatriculas();
				
		Iterator<Matricula> itMat = collecMatriculas.iterator();
		
		while (itMat.hasNext())
			srv.borrarMatricula(itMat.next().getIdMatricula());
		
		// Recuperar los alumnos de la base de datos.
		Collection<Alumno> collecAlumnos = srv.cargarAlumnos();		
		
		Iterator<Alumno> itAl = collecAlumnos.iterator();
		
		while (itAl.hasNext())
			srv.borrarAlumno(itAl.next().getIdAlumno());
		
		// Recuperar los cursos de la base de datos.
		Collection<Curso> collecCursos = srv.cargarCursos();		
		
		Iterator<Curso> itCu = collecCursos.iterator();
		
		while (itCu.hasNext())
			srv.borrarCurso(itCu.next().getIdCurso());		
		
		System.out.println("\nfin del programa.");
	}
}
