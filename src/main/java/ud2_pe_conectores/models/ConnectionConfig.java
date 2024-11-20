package ud2_pe_conectores.models;

public class ConnectionConfig {
    private String host;
    private int port;
    private String user;
    private String pass;
    private String nickName;

    // Constructor sin parámetros
    public ConnectionConfig() {
    }

    // Constructor con parámetros para inicializar los valores
    public ConnectionConfig(String host, String port, String user, String pass, String nickName) {
        this.host = host;
        this.port = Integer.parseInt(port);
        this.user = user;
        this.pass = pass;
        this.nickName = nickName;
    }

    // Getters y setters
    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }
}
