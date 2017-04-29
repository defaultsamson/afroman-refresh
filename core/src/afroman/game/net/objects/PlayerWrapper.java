package afroman.game.net.objects;

import afroman.game.PlayerType;
import afroman.game.net.PlayerConnection;

/**
 * Created by Samson on 2017-04-29.
 */
public class PlayerWrapper {
    public PlayerWrapper() {

    }

    public PlayerWrapper(PlayerConnection connection) {
        isHost = connection.isHost();
        type = connection.getType();
        username = connection.getUsername();
    }

    public boolean isHost;
    public PlayerType type;
    public String username;
}
