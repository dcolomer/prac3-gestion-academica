package dao;

public enum TiposDAO {
	
	JDBC_DRIVER_MANAGER("no-pool"), JDBC_DATASOURCE("pool"), SPRING("spring");
	
	private final String nombreAmigable;
	
	TiposDAO(String nombreAmigable) {
        this.nombreAmigable = nombreAmigable;
    }
	
	public String getNombreAmigable() {
		return nombreAmigable;
	}
	
	 // Método para buscar un enum por su nombre amigable
    public static TiposDAO aPartirDeNombreAmigable(String nombre) {
        for (TiposDAO tiposDAO : TiposDAO.values()) {
            if (tiposDAO.getNombreAmigable().equalsIgnoreCase(nombre)) {
                return tiposDAO;
            }
        }
        throw new IllegalArgumentException("No existe un TiposDAO con el nombre: " + 
        		nombre + " informado en dao.properties");
    }
}
