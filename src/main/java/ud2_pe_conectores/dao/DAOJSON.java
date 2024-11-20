package ud2_pe_conectores.dao;

import com.google.gson.Gson;
import ud2_pe_conectores.models.ConnectionConfig;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Clase para gestionar la configuración de conexión a través de un archivo JSON.
 */
public class DAOJSON {

    /**
     * Ruta del archivo JSON donde se guarda la configuración.
     */
    private static final String JSON_FILE_PATH = "connection_config.json";

    /**
     * Instancia de Gson para convertir objetos a JSON y viceversa.
     */
    private Gson gson = new Gson();

    /**
     * Guarda la configuración de conexión en un archivo JSON.
     *
     * @param config objeto {@link ConnectionConfig} con los datos de configuración a guardar.
     * @return {@code true} si la operación fue exitosa, {@code false} en caso de error.
     */
    public boolean saveConnectionConfig(ConnectionConfig config) {
        try (FileWriter writer = new FileWriter(JSON_FILE_PATH)) {
            gson.toJson(config, writer);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Actualiza la configuración de conexión en el archivo JSON.
     * Este método sobrescribe el archivo existente con los nuevos datos.
     *
     * @param config objeto {@link ConnectionConfig} con los datos actualizados.
     * @return {@code true} si la operación fue exitosa, {@code false} en caso de error.
     */
    public boolean updateConnectionConfig(ConnectionConfig config) {
        return saveConnectionConfig(config);
    }

    /**
     * Obtiene la configuración de conexión desde el archivo JSON.
     *
     * @return un objeto {@link ConnectionConfig} con los datos de configuración leídos,
     *         o {@code null} si ocurre un error al leer el archivo.
     */
    public ConnectionConfig getConnectionConfig() {
        try (FileReader reader = new FileReader(JSON_FILE_PATH)) {
            return gson.fromJson(reader, ConnectionConfig.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
