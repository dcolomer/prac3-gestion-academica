package dao;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Blob;
import java.sql.Connection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Objects;

import entidades.Alumno;
import entidades.Curso;
import entidades.Matricula;

public class AcademiaDAOImplJDBC implements AcademiaDAO {

    private static final String NOMBRE_POR_DEFECTO = "sin nombre";
	private Connection conexion; // Campo para almacenar la conexión inyectada

    /* 
     * CONSTRUCTORES
     */
    public AcademiaDAOImplJDBC(Connection conexion) {
        this.conexion = conexion; // Asignar la conexión inyectada
    }

    /* 
     * OPERACIONES ALUMNO
     */
    @Override
    public Collection<Alumno> cargarAlumnos() {
        Collection<Alumno> alumnos = new ArrayList<>();
        String query = FIND_ALL_ALUMNOS_SQL;

        try (PreparedStatement ps = conexion.prepareStatement(query);
             ResultSet rs = ps.executeQuery()) 
        {        	
            while (rs.next()) {
                int id = rs.getInt(ID_ALUMNO);
                String nombre = Objects.toString(rs.getString(NOM_ALUMNO), NOMBRE_POR_DEFECTO);
                Blob fotoBlob = rs.getBlob(FOTO);
                
                Alumno alumno = new Alumno(id, nombre);

                if (fotoBlob != null) {
                    alumno.setFoto(blobToBytes(fotoBlob));
                } else {
                    alumno.setFoto(null);
                }

                alumnos.add(alumno);
            }
        } catch (SQLException e) {
            throw new DAOException("Error al cargar los alumnos", e);
        }
        return alumnos;
    }
    
    // Método privado para convertir Blob a byte[]
    private byte[] blobToBytes(Blob blob) throws SQLException {
        try (InputStream is = blob.getBinaryStream()) {
            return is.readAllBytes(); // Java 9+ (o usa un BufferedInputStream en versiones anteriores)
        } catch (IOException e) {
            throw new SQLException("Error al leer BLOB", e);
        }
    }

    @Override
    public Alumno getAlumno(int idAlumno) {
        String query = GET_ALUMNO_SQL;
        
        try (PreparedStatement ps = conexion.prepareStatement(query)) {
            ps.setInt(1, idAlumno);
            
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    int id = rs.getInt(ID_ALUMNO);
                    String nombre = Objects.toString(rs.getString(NOM_ALUMNO), NOMBRE_POR_DEFECTO);
                    Blob fotoBlob = rs.getBlob(FOTO);
                    
                    Alumno alumno = new Alumno(id, nombre);

                    if (fotoBlob != null) {
                        alumno.setFoto(blobToBytes(fotoBlob));
                    } else {
                        alumno.setFoto(null);
                    }

                    return alumno;
                }
            }
        } catch (SQLException e) {
            throw new DAOException("Error al obtener el alumno con ID: " + idAlumno, e);
        }
        
        return null; // No se encontró el alumno
    }

	@Override
	public int grabarAlumno(Alumno alumno) {
	    String query = ADD_ALUMNO_SQL;
	    int filasAfectadas = 0;

	    try (PreparedStatement ps = conexion.prepareStatement(query)) {
	        ps.setInt(1, alumno.getIdAlumno());
	        ps.setString(2, alumno.getNombreAlumno());

	        // Si hay foto, la convierte en un stream
	        if (alumno.getFoto() != null) {
	            ps.setBinaryStream(3, new ByteArrayInputStream(alumno.getFoto()), alumno.getFoto().length);
	        } else {
	            ps.setNull(3, java.sql.Types.BLOB);
	        }

	        filasAfectadas = ps.executeUpdate();
	    } catch (SQLException e) {
	        throw new DAOException("Error al grabar el alumno: " + alumno.getNombreAlumno(), e);
	    }

	    return filasAfectadas;
	}
	
	@Override
	public int actualizarAlumno(Alumno alumno) {
		String query = UPDATE_ALUMNO_SQL;
	    int filasAfectadas = 0;

	    try (PreparedStatement ps = conexion.prepareStatement(query)) {
	        ps.setString(1, alumno.getNombreAlumno());

	        // Manejo seguro de la foto
	        if (alumno.getFoto() != null) {
	            ps.setBinaryStream(2, new ByteArrayInputStream(alumno.getFoto()), alumno.getFoto().length);
	        } else {
	            ps.setNull(2, java.sql.Types.BLOB);
	        }

	        ps.setInt(3, alumno.getIdAlumno());

	        filasAfectadas = ps.executeUpdate();
	    } catch (SQLException e) {
	        throw new DAOException("Error al actualizar el alumno: " + alumno.getIdAlumno(), e);
	    }

	    return filasAfectadas;
	}

	@Override
	public int borrarAlumno(int idAlumno) {
		String query = DELETE_ALUMNO_SQL;
		int filasAfectadas = 0;
		
		try (PreparedStatement ps = conexion.prepareStatement(query)) {
			ps.setInt(1, idAlumno);			
			filasAfectadas = ps.executeUpdate();		
		} catch (SQLException e) {
			throw new DAOException("Error al eliminar el alumno: " + idAlumno, e);
		}
		return filasAfectadas;
	}
	
	/*
	 * OPERACIONES CURSO *****************************************************************
	 */
	
	@Override
	public Collection<Curso> cargarCursos() {
		String query = FIND_ALL_CURSOS_SQL;
		Collection<Curso> cursos = new ArrayList<Curso>();
		 try (PreparedStatement ps = conexion.prepareStatement(query);
		         ResultSet rs = ps.executeQuery()) 
		 {			
			while (rs.next()) {
				int id = rs.getInt(ID_CURSO);
				String nombre = Objects.toString(rs.getString(NOM_CURSO), NOMBRE_POR_DEFECTO);
	            cursos.add(new Curso(id, nombre));
			}
		} catch (SQLException e) {
            throw new DAOException("Error al cargar los cursos", e);
        }
		return cursos;
	}

	@Override
	public Curso getCurso(int idCurso) {
		String query = GET_CURSO_SQL;
		
		try (PreparedStatement ps = conexion.prepareStatement(query)) {
			ps.setInt(1, idCurso);		
			try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
					int id_Curso = rs.getInt(ID_CURSO);
					String nombre = Objects.toString(rs.getString(NOM_CURSO), NOMBRE_POR_DEFECTO);
					Curso curso = new Curso(id_Curso, nombre);				
					return curso;
                }
			}			
		} catch (SQLException e) {
			throw new DAOException("Error al obtener el curso con ID: " + idCurso, e);
		} 
		return null;
	}
	
	@Override
	public int grabarCurso(Curso curso) {
		String query = ADD_CURSO_SQL;
	    int filasAfectadas = 0;
	    try (PreparedStatement ps = conexion.prepareStatement(query)) {
	        ps.setInt(1, curso.getIdCurso());
	        ps.setString(2, curso.getNombreCurso());
	        
	        filasAfectadas = ps.executeUpdate();
		} catch (SQLException e) {
			 throw new DAOException("Error al grabar el curso: " + curso.getNombreCurso(), e);			
		}
		return filasAfectadas;
	}
	
	
	@Override
	public int actualizarCurso(Curso curso) {
		String query = UPDATE_CURSO_SQL;
	    int filasAfectadas = 0;
	    try (PreparedStatement ps = conexion.prepareStatement(query)) {
	        ps.setString(1, curso.getNombreCurso());
			ps.setInt(2, curso.getIdCurso());			
			
			filasAfectadas = ps.executeUpdate();			
		} catch (SQLException e) {
			throw new DAOException("Error al actualizar el curso: " + curso.getIdCurso(), e);
		}
		return filasAfectadas;
	}
	
	@Override
	public int borrarCurso(int idCurso) {
		String query = DELETE_CURSO_SQL;
		int filasAfectadas = 0;
		try (PreparedStatement ps = conexion.prepareStatement(query)) {
			ps.setInt(1, idCurso);
			
			filasAfectadas = ps.executeUpdate();			
		} catch (SQLException e) {
			throw new DAOException("Error al actualizar el curso: " + idCurso, e);
		}
		return filasAfectadas;
	}
	
	/*
	 * OPERACIONES MATRICULA *************************************************************
	 */
	
	@Override
	public Collection<Matricula> cargarMatriculas() {
		String query = FIND_ALL_MATRICULAS_SQL;
		Collection<Matricula> matriculas = new ArrayList<Matricula>();
		 try (PreparedStatement ps = conexion.prepareStatement(query);
		         ResultSet rs = ps.executeQuery()) 
		 {			
			while (rs.next()) {
				long idMatricula = rs.getLong(ID_MATRICULA);
				int idAlumno = rs.getInt(ID_ALUMNO);
				int idCurso = rs.getInt(ID_CURSO);
				Date fechaInicio = rs.getDate(FECHA_INICIO);
				
				Matricula matricula = new Matricula(idMatricula, idAlumno, idCurso, fechaInicio);
				matriculas.add(matricula);
			}			
		} catch (SQLException e) {
			throw new DAOException("Error al cargar las matrículas para el alumno", e);
		} 
		return matriculas;
	}

	@Override
	public long getIdMatricula(int id_alumno, int id_curso) {
	    String query = GET_IDMATRICULA_SQL;
		try (PreparedStatement ps = conexion.prepareStatement(query)) {
	        ps.setInt(1, id_alumno);
	        ps.setInt(2, id_curso);
	        
	        try (ResultSet rs = ps.executeQuery()) {
	            if (rs.next()) {											
	                return rs.getLong(ID_MATRICULA);
	            }
	        }

	    } catch (SQLException e) {
	        throw new DAOException("Error al obtener la ID de la matrícula para el alumno " + id_alumno + " y el curso " + id_curso, e);
	    }

	    return 0L; // No se encontró la matrícula
	}
	
	@Override
	public Matricula getMatricula(long idMatricula) {
		String query = GET_MATRICULA_SQL;
	    Matricula matricula = null;
	    
		try (PreparedStatement ps = conexion.prepareStatement(query )) {
	        ps.setLong(1, idMatricula);

	        try (ResultSet rs = ps.executeQuery()) {
	            if (rs.next()) {
	                long id_Matricula = rs.getLong(ID_MATRICULA);
	                int idAlumno = rs.getInt(ID_ALUMNO);
	                int idCurso = rs.getInt(ID_CURSO);
	                Date fechaInicio = rs.getDate(FECHA_INICIO);

	                matricula = new Matricula(id_Matricula, idAlumno, idCurso, fechaInicio);
	            }
	        }

	    } catch (SQLException e) {
	        throw new DAOException("Error al obtener la matrícula con ID " + idMatricula, e);
	    }

	    return matricula;
	}
	
	@Override
	public int grabarMatricula(Matricula matricula) {
		String query = ADD_MATRICULA_SQL;
	    int idGenerado = 0; // Para almacenar el ID de la matrícula generada

	    try (PreparedStatement ps = 
	    		conexion.prepareStatement(
	    				query , 
	    				PreparedStatement.RETURN_GENERATED_KEYS)) 
	    {	        
	        ps.setInt(1, matricula.getIdAlumno());
	        ps.setInt(2, matricula.getIdCurso());
	        ps.setDate(3, new java.sql.Date(matricula.getFechaInicio().getTime()));

	        int filasAfectadas = ps.executeUpdate();

	        if (filasAfectadas == 0) {
	            throw new SQLException("La inserción falló, no se insertó ninguna fila.");
	        }

	        // Obtener el valor generado para id_matricula
	        try (ResultSet res = ps.getGeneratedKeys()) {
	            if (res.next()) {
	                idGenerado = res.getInt(1);
	            } else {
	                throw new SQLException("La inserción falló, no se generó ningún ID para la matrícula.");
	            }
	        }

	    } catch (SQLException e) {
	        throw new DAOException("Error al grabar la matrícula del alumno " + matricula.getIdAlumno() + " en el curso " + matricula.getIdCurso(), e);
	    }

	    return idGenerado;
	}

	
	@Override
	public int actualizarMatricula(Matricula matricula) {
		String query = UPDATE_MATRICULA_SQL;
	    int filasAfectadas = 0;

		try (PreparedStatement ps = conexion.prepareStatement(query )) {
	        ps.setDate(1, new java.sql.Date(matricula.getFechaInicio().getTime()));
	        ps.setInt(2, matricula.getIdAlumno());
	        ps.setInt(3, matricula.getIdCurso());

	        filasAfectadas = ps.executeUpdate();

	    } catch (SQLException e) {
	        throw new DAOException("Error al actualizar la matrícula del alumno " + matricula.getIdAlumno() +
	                               " en el curso " + matricula.getIdCurso(), e);
	    }

	    return filasAfectadas;
	}

	
	@Override
	public int borrarMatricula(long idMatricula) {
		String query = DELETE_MATRICULA_SQL;
	    int filasAfectadas = 0;
	    
		try (PreparedStatement ps = conexion.prepareStatement(query )) {
	        ps.setLong(1, idMatricula);
	        filasAfectadas = ps.executeUpdate();
	    } catch (SQLException e) {
	        throw new DAOException("Error al borrar la matrícula con ID " + idMatricula, e);
	    }

	    return filasAfectadas;
	}
	
}
