package com.example.untitleddungeongame.floors;

import com.example.untitleddungeongame.characters.Player;

public class Rest extends Room {
    private boolean restUsed;

    public Rest(int roomId) {
        super(roomId);
        this.restUsed = false;
    }

    public void useRest(Player player) {
        if (!restUsed) {
            player.heal(player.getMaxHealth() - player.getHealth());
            restUsed = true;
        }
    }

    public boolean usedRest() {
        return restUsed;
    }
}
