package dao;

/*
 * ¿Cuántas líneas tiene esta clase?
 * ¿El número es mayor o menor que la clase AcademiaDAOImplJDBC?
 * */

import java.io.ByteArrayInputStream;
import java.sql.Blob;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.Date;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.beans.factory.annotation.Autowired;

import entidades.Alumno;
import entidades.Curso;
import entidades.Matricula;

@Repository
public class AcademiaDAOImplSpring implements AcademiaDAO {		
	
	private JdbcTemplate jdbcTemplate;

	public AcademiaDAOImplSpring() {
				
	}
	
	@Autowired
	// Inyección por setter
	// jdbcTemplate es un objeto gestionado por Spring
	// en el fichero applicationContext.xml
    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
	
	/*
	 * OPERACIONES ALUMNO
	 */
	
	/*
	 * Convertir una fila de la tabla Alumnos en un objeto Alumno 
	 */
	private class AlumnoMapper implements RowMapper<Alumno> {
            public Alumno mapRow(ResultSet rs, int rowNum)
                              throws SQLException {
         	  Alumno alumno = new Alumno();

         	  alumno.setIdAlumno(rs.getInt("id_alumno"));
         	  alumno.setNombreAlumno(rs.getString("nombre_alumno"));
         	  Blob foto=rs.getBlob("foto");
         	  
         	  if (foto!=null)
					alumno.setFoto(foto.getBytes(1L, (int)foto.length()));
				  else
					alumno.setFoto(null);
         	  
               return alumno;
            }
	}
	
	@Override
	public Collection<Alumno> cargarAlumnos() {
		return jdbcTemplate.query(FIND_ALL_ALUMNOS_SQL,
            new AlumnoMapper());	
	}
	
	public Alumno getAlumno(int idAlumno) {		
		return jdbcTemplate.queryForObject(
				GET_ALUMNO_SQL, new AlumnoMapper(), idAlumno);
	}

	@Override
	public int grabarAlumno(Alumno alumno) {
		return jdbcTemplate.update(ADD_ALUMNO_SQL, 
			alumno.getIdAlumno(), alumno.getNombreAlumno(),                
            alumno.getFoto()!=null?new ByteArrayInputStream(alumno.getFoto()):null);
	}
	
	@Override
	public int actualizarAlumno(Alumno alumno) {		
		return jdbcTemplate.update(UPDATE_ALUMNO_SQL, 
			alumno.getNombreAlumno(),                
	        alumno.getFoto()!=null?new ByteArrayInputStream(alumno.getFoto()):null,
	        alumno.getIdAlumno());		
	}
	
	@Override
	public int borrarAlumno(int idAlumno) {		
		return jdbcTemplate.update(DELETE_ALUMNO_SQL, idAlumno);
	}
	
	/*
	 * OPERACIONES CURSO
	 */
	
	/*
	 * Convertir una fila de la tabla Cursos en un objeto Curso 
	 */
	private class CursoMapper implements RowMapper<Curso> {
		public Curso mapRow(ResultSet rs, int rowNum)
        	throws SQLException {
				Curso curso = new Curso(rs.getInt("id_curso"),
				rs.getString("nombre_curso")!=null?rs.getString("nombre_curso"):"sin nombre");

				return curso;
		}
	}
	
	@Override
	public Collection<Curso> cargarCursos() {
		return jdbcTemplate.query(FIND_ALL_CURSOS_SQL,
            new CursoMapper());	
	}
	
	public Curso getCurso(int idAlumno) {
		return jdbcTemplate.queryForObject(
				GET_CURSO_SQL, new CursoMapper(), idAlumno);
	}
	
	@Override
	public int grabarCurso(Curso curso) {
		return jdbcTemplate.update(ADD_CURSO_SQL, 
				curso.getIdCurso(), curso.getNombreCurso());
	}
		
	@Override
	public int actualizarCurso(Curso curso) {		
		return jdbcTemplate.update(UPDATE_CURSO_SQL, 
				curso.getNombreCurso(),                	        
				curso.getIdCurso());		
	}
	
	@Override
	public int borrarCurso(int idCurso) {		
		return jdbcTemplate.update(DELETE_CURSO_SQL, idCurso);
	}
		
	/*
	 * OPERACIONES MATRICULA
	 */
	
	/*
	 * Convertir una fila de la tabla Matriculas en un objeto Matricula
	 */
	private class MatriculaMapper implements 
		RowMapper<Matricula> {
		public Matricula mapRow(ResultSet rs, int rowNum)
        throws SQLException {
			long idMatricula= rs.getLong("id_matricula");
			int idAlumno= rs.getInt("id_alumno");			
			int idCurso= rs.getInt("id_curso");
			Date fechaInicio=new Date(rs.getDate("fecha_inicio").getTime());
			
			Matricula matricula = new Matricula(idMatricula, idAlumno, idCurso, fechaInicio);
			matricula.setIdAlumno(idAlumno);
			matricula.setIdCurso(idCurso);            	  			
			
			return matricula;
			}
	}
	
	@Override
	public Collection<Matricula> cargarMatriculas() {
		return jdbcTemplate.query(FIND_ALL_MATRICULAS_SQL,
            new MatriculaMapper());	
	}
	
	public long getIdMatricula(int id_alumno, int id_curso) {
	      return jdbcTemplate
	      	.queryForObject(GET_IDMATRICULA_SQL, Long.class, id_alumno, id_curso);
	}

	@Override
	public Matricula getMatricula(long idMatricula) {
		return jdbcTemplate.queryForObject(
				GET_MATRICULA_SQL, new MatriculaMapper(), idMatricula);		
	}
	
	@Override
	public int grabarMatricula(Matricula matricula) {
		return jdbcTemplate.update(ADD_MATRICULA_SQL, 
				matricula.getIdAlumno(), 
				matricula.getIdCurso(),
				new java.sql.Date(matricula.getFechaInicio().getTime()));
	}
	
	@Override
	public int actualizarMatricula(Matricula matricula) {
		return jdbcTemplate.update(UPDATE_MATRICULA_SQL,
				new java.sql.Date(matricula.getFechaInicio().getTime()),
				matricula.getIdAlumno(), 
				matricula.getIdCurso());
	}
	
	@Override
	public int borrarMatricula(long idMatricula) {		
		return jdbcTemplate.update(DELETE_MATRICULA_SQL, idMatricula);
	}

}
