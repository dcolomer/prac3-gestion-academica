package servicios;

import java.util.Collection;

import entidades.Alumno;
import entidades.Curso;
import entidades.Matricula;

public interface AcademiaService {

	Collection<Alumno> cargarAlumnos();

	Alumno getAlumno(int idAlumno);

	int grabarAlumno(Alumno alumno);
	
	int actualizarAlumno(Alumno alumno);

	int borrarAlumno(int idAlumno);

	Collection<Curso> cargarCursos();

	Curso getCurso(int idCurso);

	int grabarCurso(Curso curso);
	
	int actualizarCurso(Curso curso);

	int borrarCurso(int idCurso);

	Collection<Matricula> cargarMatriculas();

	long getIdMatricula(int id_alumno, int id_curso);

	Matricula getMatricula(long idMatricula);

	int grabarMatricula(Matricula matricula);
	
	int actualizarMatricula(Matricula matricula);

	int borrarMatricula(long idMatricula);
}