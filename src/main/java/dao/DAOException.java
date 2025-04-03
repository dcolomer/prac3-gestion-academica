package dao;

/**
 * Excepción personalizada para errores en la capa de acceso a datos (DAO).
 */
public class DAOException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    /**
     * Constructor con solo mensaje.
     *
     * @param message Mensaje descriptivo del error.
     */
    public DAOException(String message) {
        super(message);
    }

    /**
     * Constructor con mensaje y causa.
     *
     * @param message Mensaje descriptivo del error.
     * @param cause   Causa original del error (ej. SQLException).
     */
    public DAOException(String message, Throwable cause) {
        super(message, cause);
    }
}

