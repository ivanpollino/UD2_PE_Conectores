package empresa.dao;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Map;
import empresa.models.PlayerConfig;

public class DAOJson {

    private static final String CONFIG_FILE = "player_config.json"; // Ruta del archivo JSON
    private Gson gson;

    public DAOJson() {
        gson = new Gson();
    }

    // Método para cargar la configuración del jugador desde el archivo JSON
    public PlayerConfig getPlayerConfig() {
        try (FileReader reader = new FileReader(CONFIG_FILE)) {
            JsonObject jsonObject = gson.fromJson(reader, JsonObject.class);

            PlayerConfig config = new PlayerConfig();

            // Obtener los ajustes de control y convertirlo a un Map
            String controlSettingsJson = jsonObject.get("control_settings").getAsString();
            Map<String, String> controlSettings = gson.fromJson(controlSettingsJson, Map.class);
            config.setControlSettings(controlSettings);

            // Obtener el resto de los datos
            config.setSoundEnabled(jsonObject.get("sound_enabled").getAsBoolean());
            config.setResolution(jsonObject.get("resolution").getAsString());
            config.setLanguage(jsonObject.get("language").getAsString());

            return config;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    // Método para guardar la configuración del jugador en el archivo JSON
    public boolean savePlayerConfig(PlayerConfig config) {
        JsonObject jsonObject = new JsonObject();

        // Convertir los ajustes de control a JSON
        String controlSettingsJson = gson.toJson(config.getControlSettings());
        jsonObject.addProperty("control_settings", controlSettingsJson);

        // Guardar los demás parámetros
        jsonObject.addProperty("sound_enabled", config.isSoundEnabled());
        jsonObject.addProperty("resolution", config.getResolution());
        jsonObject.addProperty("language", config.getLanguage());

        try (FileWriter writer = new FileWriter(CONFIG_FILE)) {
            gson.toJson(jsonObject, writer);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }
}
