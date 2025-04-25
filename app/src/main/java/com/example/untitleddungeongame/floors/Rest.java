package com.example.untitleddungeongame.floors;

import com.example.untitleddungeongame.entity.Player;
import com.example.untitleddungeongame.floors.Room;
import com.example.untitleddungeongame.handlers.Game;
import com.example.untitleddungeongame.misc.OutputText;

public class Rest extends Room {
    private boolean restUsed;
    public Rest(int roomId) {
        super(roomId, null);
        this.restUsed = false;

    }

    public void useRest(Player player) {
        if (!restUsed) {
            player.heal(player.getMaxHealth() - player.getHealth());
            restUsed = true;
            OutputText.setOutputText("You have rested and healed to full health.");
        }
    }

    public boolean usedRest() {
        return restUsed;
    }
}
