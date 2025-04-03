package dao;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

public class AcademiaDAOFactoria {

    private static ApplicationContext spring = null;
    private static TiposDAO tipoSeleccionado = null;

    public static AcademiaDAO getAcademiaDAO() throws SQLException, IOException {
        // Si ya se ha determinado el tipo de DAO, reutilizarlo
        if (tipoSeleccionado == null) {
            tipoSeleccionado = leerTipoDAO();
        }
        return getDaoImplementation(tipoSeleccionado);
    }

    private static TiposDAO leerTipoDAO() {
        Properties properties = new Properties();
        try (InputStream input = AcademiaDAOFactoria.class.getClassLoader().getResourceAsStream("dao.properties")) {
            if (input == null) { // En caso de no existir el fichero, usar JDBC puro.
                System.out.println("No se encontró dao.properties, se usará JDBC sin pool por defecto.");
                return TiposDAO.JDBC_DRIVER_MANAGER;
            }
            properties.load(input);
            String nombreDeProperties = properties.getProperty("dao.impl");
            
            TiposDAO tiposDAO = TiposDAO.aPartirDeNombreAmigable(nombreDeProperties); 
            
            System.out.println("Tipo de implementación informada en dao.properties: " + 
            		tiposDAO + "(" + tiposDAO.getNombreAmigable() + ")");
            return tiposDAO;
        } catch (IOException | IllegalArgumentException e) {
            e.printStackTrace();
            return TiposDAO.JDBC_DRIVER_MANAGER; // En caso de error, usar JDBC puro.
        }
    }

    private static AcademiaDAO getDaoImplementation(TiposDAO tipo) throws SQLException, IOException {
       switch(tipo) {
	    	case JDBC_DRIVER_MANAGER:
	    		System.out.println("Utilizando implementación JDBC sin pool de conexiones");
	            return new AcademiaDAOImplJDBC(obtenerConexionSinPool());            
	    	case JDBC_DATASOURCE:
	    		System.out.println("Utilizando implementación JDBC con pool de conexiones");
	            return new AcademiaDAOImplJDBC(obtenerConexionConPool());            
	    	default: // Soporte de SPRING framework para JDBC
	    		// Inicializar Spring una sola vez
	            if (spring == null) {
	                System.out.println("Cargando contexto de Spring...");
	                spring = new ClassPathXmlApplicationContext("applicationContext.xml");
	            }
	            System.out.println("Utilizando implementación Spring-JDBC");
	            return (AcademiaDAO) spring.getBean("academiaDAO");
    	}
    }

    private static Connection obtenerConexionSinPool() throws SQLException, IOException {  
    	DatosConexion dc = obtenerDatosConexion();
        return DriverManager.getConnection(dc.url(), dc.user(), dc.pwd());
    }
    
    @SuppressWarnings("resource")
	private static Connection obtenerConexionConPool() throws SQLException, IOException {  
    	DatosConexion dc = obtenerDatosConexion();
    	
    	HikariConfig config = new HikariConfig();
        config.setJdbcUrl(dc.url());
        config.setUsername(dc.user());
        config.setPassword(dc.pwd());
    	
        // Configuraciones opcionales (ajusta según necesidades)
        config.setMaximumPoolSize(10);
        config.setMinimumIdle(5);
        config.setIdleTimeout(600000);
        config.setConnectionTimeout(30000);
        config.setMaxLifetime(1800000);
        config.setPoolName("MySQLPoolParaGestionAcademica");
        //config.setLeakDetectionThreshold(5000);  // comentado pq lanza excepcion una vez finalizado el programa
        
        // Para MySQL es recomendable agregar estas configuraciones
        config.addDataSourceProperty("cachePrepStmts", "true");
        config.addDataSourceProperty("prepStmtCacheSize", "250");
        config.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");
        config.addDataSourceProperty("useServerPrepStmts", "true");
        
        return new HikariDataSource(config).getConnection();
    }
    
    private static DatosConexion obtenerDatosConexion() throws IOException {
        Properties properties = new Properties();
        try (InputStream input = AcademiaDAOFactoria.class.getClassLoader().getResourceAsStream("db.properties")) {
            if (input == null) {
                throw new RuntimeException("No se encontró el archivo db.properties!");
            }
            properties.load(input);
            String url = properties.getProperty("db.url");
            String user = properties.getProperty("db.username");
            String pwd = properties.getProperty("db.password");

            return new DatosConexion(url, user, pwd);
        }
    }
}
