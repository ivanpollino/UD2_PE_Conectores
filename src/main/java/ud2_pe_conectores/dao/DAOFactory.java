package ud2_pe_conectores.dao;

/**
 * Clase de fábrica para obtener instancias de DAO según el tipo de base de datos.
 */
public class DAOFactory {

    /**
     * Enumeración que representa los tipos de bases de datos soportados.
     */
    public enum DBType {
        /**
         * Representa una base de datos MySQL.
         */
        MYSQL,

        /**
         * Representa una base de datos PostgreSQL.
         */
        POSTGRESQL
    }

    /**
     * Devuelve una implementación de {@link IDAOEmpresa} basada en el tipo de base de datos especificado.
     *
     * @param dbType tipo de base de datos deseado.
     * @return una instancia de {@link IDAOEmpresa} adecuada para el tipo de base de datos.
     * @throws IllegalArgumentException si el tipo de base de datos no está soportado.
     */
    public static IDAOEmpresa getDAO(DBType dbType) {
        switch (dbType) {
            case MYSQL:
                return new DAOMySQL();
            case POSTGRESQL:
                return new DAOPostgreSQL();
            default:
                throw new IllegalArgumentException("Tipo de base de datos no soportado.");
        }
    }
}
