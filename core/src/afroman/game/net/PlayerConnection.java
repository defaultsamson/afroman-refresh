package afroman.game.net;

import afroman.game.PlayerType;

/**
 * Created by Samson on 2017-04-28.
 */
public class PlayerConnection {
    private boolean isHost;
    private PlayerType type;
    private String username;

    public PlayerConnection(String username, boolean isHost, PlayerType type) {
        this.username = username;
        this.isHost = isHost;
        this.type = type;
    }

    public String getUsername() {
        return username;
    }

    public PlayerType getType() {
        return type;
    }

    public boolean isHost() {
        return isHost;
    }

    public void setHost(boolean host) {
        this.isHost = host;
    }
}
