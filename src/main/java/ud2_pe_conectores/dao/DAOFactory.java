package ud2_pe_conectores.dao;

public class DAOFactory {

    public enum DBType {
        MYSQL,
        POSTGRESQL
    }

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
