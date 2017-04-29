package afroman.game.net;

import afroman.game.PlayerType;
import com.esotericsoftware.kryonet.Connection;

/**
 * Created by Samson on 2017-04-28.
 */
public class ServerPlayerConnection extends PlayerConnection {
    private Connection connection;
    private int connectionID;
    private boolean hasAuthenticated;
    private int passwordAttempts;

    public ServerPlayerConnection(Connection connection, String username, boolean isHost, PlayerType type) {
        super(username, isHost, type);
        this.connection = connection;
        this.connectionID = connection.getID();
        hasAuthenticated = false;
        passwordAttempts = 0;
    }

    public boolean hasAuthenticated() {
        return hasAuthenticated;
    }

    public int passwordAttempts() {
        return passwordAttempts;
    }

    public void incrementPasswordAttempts() {
        passwordAttempts++;
    }

    public void confirmPlayerAsAuthenticated() {
        hasAuthenticated = true;
    }

    public Connection getConnection() {
        return connection;
    }

    public int getConnectionID() {
        return connectionID;
    }
}
