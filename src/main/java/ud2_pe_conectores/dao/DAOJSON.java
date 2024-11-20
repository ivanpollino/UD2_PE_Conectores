package ud2_pe_conectores.dao;

import com.google.gson.Gson;
import ud2_pe_conectores.models.ConnectionConfig;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class DAOJSON {
    private static final String JSON_FILE_PATH = "connection_config.json"; // Ruta del archivo JSON
    private Gson gson = new Gson();  // Instanciamos Gson para convertir a y desde JSON

    // Guardar configuración de conexión en un archivo JSON
    public boolean saveConnectionConfig(ConnectionConfig config) {
        try (FileWriter writer = new FileWriter(JSON_FILE_PATH)) {
            gson.toJson(config, writer);  // Convertimos el objeto a JSON y lo guardamos
            return true;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    // Actualizar configuración de conexión en el archivo JSON
    public boolean updateConnectionConfig(ConnectionConfig config) {
        return saveConnectionConfig(config);  // Para actualizar simplemente reescribimos el archivo
    }

    // Obtener configuración de conexión desde el archivo JSON
    public ConnectionConfig getConnectionConfig() {
        try (FileReader reader = new FileReader(JSON_FILE_PATH)) {
            return gson.fromJson(reader, ConnectionConfig.class);  // Convertimos el JSON a un objeto ConnectionConfig
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
