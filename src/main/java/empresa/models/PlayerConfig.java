package empresa.models;

import java.util.Map;

public class PlayerConfig {
    private Map<String, String> controlSettings; // Ajustes de control (JSON/XML)
    private boolean soundEnabled;
    private String resolution;
    private String language;

    // Getters and Setters
    public Map<String, String> getControlSettings() {
        return controlSettings;
    }

    public void setControlSettings(Map<String, String> controlSettings) {
        this.controlSettings = controlSettings;
    }

    public boolean isSoundEnabled() {
        return soundEnabled;
    }

    public void setSoundEnabled(boolean soundEnabled) {
        this.soundEnabled = soundEnabled;
    }

    public String getResolution() {
        return resolution;
    }

    public void setResolution(String resolution) {
        this.resolution = resolution;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }
}
