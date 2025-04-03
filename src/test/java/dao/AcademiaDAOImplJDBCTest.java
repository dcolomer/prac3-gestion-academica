package dao;

import static org.easymock.EasyMock.*;
import static org.junit.jupiter.api.Assertions.*;

import java.sql.*;
import java.util.Collection;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import entidades.Alumno;

public class AcademiaDAOImplJDBCTest {
    private Connection mockConnection;
    private PreparedStatement mockPreparedStatement;
    private ResultSet mockResultSet;
    private AcademiaDAO academiaDAO;
    
    @BeforeEach
    void setUp() throws Exception {
        // Crear mocks de JDBC
        mockConnection = createMock(Connection.class);
        mockPreparedStatement = createMock(PreparedStatement.class);
        mockResultSet = createMock(ResultSet.class);

        // Inicializar el DAO con la conexión mockeada
        academiaDAO = new AcademiaDAOImplJDBC(mockConnection);       
    }
    
    @Test
    void testCargarAlumnos() throws Exception {
        // Definir comportamiento esperado
        expect(mockConnection.prepareStatement(AcademiaDAO.FIND_ALL_ALUMNOS_SQL)).andReturn(mockPreparedStatement);
        expect(mockPreparedStatement.executeQuery()).andReturn(mockResultSet);

        // Simular dos alumnos en la BD
        expect(mockResultSet.next()).andReturn(true).times(2); // Dos filas
        expect(mockResultSet.getInt(AcademiaDAO.ID_ALUMNO)).andReturn(1).andReturn(2);
        expect(mockResultSet.getString(AcademiaDAO.NOM_ALUMNO)).andReturn("Juan").andReturn("María");
        expect(mockResultSet.getBlob(AcademiaDAO.FOTO)).andReturn(null).times(2); // Sin foto

        // Indicar que no hay más filas
        expect(mockResultSet.next()).andReturn(false);

        // Cerrar recursos
        mockResultSet.close();
        expectLastCall();
        mockPreparedStatement.close();
        expectLastCall();

        // Activar los mocks
        replay(mockConnection, mockPreparedStatement, mockResultSet);

        // Ejecutar el método a probar
        Collection<Alumno> alumnos = academiaDAO.cargarAlumnos();

        // Verificar resultados
        assertNotNull(alumnos);
        assertEquals(2, alumnos.size());

        // Verificar que los mocks se usaron correctamente
        verify(mockConnection, mockPreparedStatement, mockResultSet);
    }
}
