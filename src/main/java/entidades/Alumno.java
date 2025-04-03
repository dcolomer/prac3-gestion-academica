package entidades;

import java.io.Serializable;

public class Alumno implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private int idAlumno;
	private String nombreAlumno;
	private byte[] foto;

	public Alumno() {}
	
	public Alumno(int idAlumno, String nombreAlumno) {
		this.idAlumno = idAlumno;
		this.nombreAlumno = nombreAlumno;
	}

	public int getIdAlumno() {
		return this.idAlumno;
	}

	public void setIdAlumno(int idAlumno) {
		this.idAlumno = idAlumno;
	}

	public String getNombreAlumno() {
		return this.nombreAlumno;
	}

	public void setNombreAlumno(String nombreAlumno) {
		this.nombreAlumno = nombreAlumno;
	}
	
	public byte[] getFoto() {
		return foto;
	}

	public void setFoto(byte[] foto) {
		this.foto = foto;
	}
	
	public String toString() {
		return this.idAlumno+" - "+this.nombreAlumno;
	}

}
