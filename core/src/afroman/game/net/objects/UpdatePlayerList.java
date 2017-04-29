package afroman.game.net.objects;

import afroman.game.net.PlayerConnection;

import java.util.List;

/**
 * Created by Samson on 2017-04-29.
 */
public class UpdatePlayerList {

    public PlayerWrapper[] connections;

    public UpdatePlayerList() {

    }

    public UpdatePlayerList(List<PlayerConnection> connections) {
        this.connections = new PlayerWrapper[connections.size()];

        for (int i = 0; i < connections.size(); i++) {
            this.connections[i] = new PlayerWrapper(connections.get(i));
        }
    }

    public UpdatePlayerList(PlayerConnection... connections) {
        this.connections = new PlayerWrapper[connections.length];

        for (int i = 0; i < connections.length; i++) {
            this.connections[i] = new PlayerWrapper(connections[i]);
        }
    }
}
