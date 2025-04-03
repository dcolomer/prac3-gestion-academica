package servicios;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Collection;

import dao.AcademiaDAO;
import dao.AcademiaDAOFactoria;
import entidades.Alumno;
import entidades.Curso;
import entidades.Matricula;

public class AcademiaServiceImpl implements AcademiaService {
	
	private static AcademiaDAO academiaDAO;
	
	static {
		try {
			academiaDAO = AcademiaDAOFactoria.getAcademiaDAO();
		} catch (SQLException | IOException e) {
			e.printStackTrace();
		}
	}
	
	public AcademiaServiceImpl() {
		System.out.println("Creando el servicio..." + this.getClass().getSimpleName());
	}
	
	@Override
	public Collection<Alumno> cargarAlumnos() {
		return academiaDAO.cargarAlumnos();		
	}
	
	@Override
	public Alumno getAlumno(int idAlumno) {
		return academiaDAO.getAlumno(idAlumno);
	}
	
	@Override
	public int grabarAlumno(Alumno alumno) {
		System.out.println("Dando de alta nuevo alumno...");			
		return academiaDAO.grabarAlumno(alumno);		
	}
	
	@Override
	public int actualizarAlumno(Alumno alumno) {
		System.out.println("Actualizando alumno...");
		return academiaDAO.actualizarAlumno(alumno);	
	}
	
	@Override
	public int borrarAlumno(int idAlumno) {
		System.out.println("Borrando alumno " + idAlumno);
		return academiaDAO.borrarAlumno(idAlumno);
	}
	
	/*
	 * OPERACIONES CURSO *****************************************************************
	 */
	
	@Override
	public Collection<Curso> cargarCursos() {
		return academiaDAO.cargarCursos();		
	}
	
	@Override
	public Curso getCurso(int idCurso) {
		return academiaDAO.getCurso(idCurso);
	}
	
	@Override
	public int grabarCurso(Curso curso) {
		return academiaDAO.grabarCurso(curso);
	}
	
	@Override
	public int actualizarCurso(Curso curso) {
		return academiaDAO.actualizarCurso(curso);
	}
	
	@Override
	public int borrarCurso(int idCurso) {
		System.out.println("Borrando curso " + idCurso);
		return academiaDAO.borrarCurso(idCurso);
	}
	
	/*
	 * OPERACIONES MATRICULA *************************************************************
	 */
	
	@Override
	public Collection<Matricula> cargarMatriculas() {
		return academiaDAO.cargarMatriculas();
	}
	
	@Override
	public long getIdMatricula(int id_alumno, int id_curso) {
		return academiaDAO.getIdMatricula(id_alumno, id_curso);
	}
	
	@Override
	public Matricula getMatricula(long idMatricula) {
		return academiaDAO.getMatricula(idMatricula);
	}
	
	@Override
	public int grabarMatricula(Matricula matricula) {
		return academiaDAO.grabarMatricula(matricula);
	}
	
	@Override
	public int actualizarMatricula(Matricula matricula) {
		return academiaDAO.actualizarMatricula(matricula);
	}
	
	@Override
	public int borrarMatricula(long idMatricula) {
		System.out.println("Borrando matricula " + idMatricula);
		return academiaDAO.borrarMatricula(idMatricula);
	}

}
