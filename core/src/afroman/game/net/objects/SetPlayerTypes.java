package afroman.game.net.objects;

import afroman.game.PlayerType;

/**
 * Created by Samson on 2017-04-29.
 */
public class SetPlayerTypes {
    public SetPlayerTypes() {

    }

    public SetPlayerTypes(PlayerType playerType) {
        this.playerType = playerType;
    }

    public PlayerType playerType;
}
