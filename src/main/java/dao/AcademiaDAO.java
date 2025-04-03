package dao;

import java.util.Collection;
import entidades.Alumno;
import entidades.Curso;
import entidades.Matricula;

public interface AcademiaDAO {
	
	public static final String 
		ID_ALUMNO = "id_alumno", NOM_ALUMNO = "nombre_alumno", FOTO = "foto",
		ID_CURSO = "id_curso", NOM_CURSO = "nombre_curso",
		ID_MATRICULA = "id_matricula", FECHA_INICIO = "fecha_inicio";
			
	public static final String FIND_ALL_ALUMNOS_SQL = 
		"select id_alumno, nombre_alumno, foto from alumnos";
	
	public static final String FIND_ALL_CURSOS_SQL = 
		"select id_curso, nombre_curso from cursos";
	
	public static final String FIND_ALL_MATRICULAS_SQL = 
		"select m.id_matricula, m.id_alumno, a.nombre_alumno, "
		+ " m.id_curso, c.nombre_curso, m.fecha_inicio " 
		+ " from matriculas m, alumnos a, cursos c "
		+ " where m.id_alumno=a.id_alumno and m.id_curso=c.id_curso ";

	public static final String ADD_ALUMNO_SQL = "insert into alumnos"
		+ " (id_alumno, nombre_alumno, foto) "
		+ " values (?,?,?) ";
	
	public static final String ADD_CURSO_SQL = "insert into cursos"
		+ " (id_curso, nombre_curso) "
		+ " values (?,?) ";
	
	public static final String ADD_MATRICULA_SQL = "insert into matriculas"
		+ " (id_alumno, id_curso, fecha_inicio) "
		+ " values (?,?,?) ";
	
	public static final String UPDATE_ALUMNO_SQL = "update alumnos set nombre_alumno=?, foto=? "
		+ " where id_alumno=? ";
	
	public static final String UPDATE_CURSO_SQL = "update cursos set nombre_curso=? "
		+ " where id_curso=? ";
	
	public static final String UPDATE_MATRICULA_SQL = "update matriculas set fecha_inicio=? "
		+ " where id_alumno=? and id_curso=? ";
		
	
	public static final String DELETE_ALUMNO_SQL = "delete from alumnos where id_alumno = ? ";
	
	public static final String DELETE_CURSO_SQL = "delete from cursos where id_curso = ? ";
	
	public static final String DELETE_MATRICULA_SQL = "delete from matriculas where id_matricula = ? ";


	public static final String GET_ALUMNO_SQL = 
		"select id_alumno, nombre_alumno, foto "
		+ " from alumnos " 
		+ " where id_alumno = ?";
	
	public static final String GET_CURSO_SQL = 
		"select id_curso, nombre_curso "
		+ " from cursos " 
		+ " where id_curso = ?";
	
	public static final String GET_IDMATRICULA_SQL = 
		"select id_matricula "
		+ " from matriculas "
		+ " where id_alumno = ? and id_curso = ? ";
	
	public static final String GET_MATRICULA_SQL = 
		"select m.id_matricula, m.id_alumno, a.nombre_alumno, "
		+ " m.id_curso, c.nombre_curso, m.fecha_inicio " 
		+ " from matriculas m, alumnos a, cursos c "
		+ " where m.id_alumno=a.id_alumno and m.id_curso=c.id_curso "
		+ " and m.id_matricula = ?";
	
	public Collection<Alumno> cargarAlumnos();
	public Alumno getAlumno(int idAlumno);	
	public int grabarAlumno(Alumno alumno);
	public int actualizarAlumno(Alumno alumno);
	public int borrarAlumno(int idAlumno);
	
	public Collection<Curso> cargarCursos();
	public Curso getCurso(int idCurso);
	public int grabarCurso(Curso curso);
	public int actualizarCurso(Curso curso);
	public int borrarCurso(int idCurso);
	
	public Collection<Matricula> cargarMatriculas();
	public long getIdMatricula(int idAlumno, int idCurso);
	public Matricula getMatricula(long idMatricula);
	public int grabarMatricula(Matricula matricula);
	public int actualizarMatricula(Matricula matricula);
	public int borrarMatricula(long idMatricula);
		
}
