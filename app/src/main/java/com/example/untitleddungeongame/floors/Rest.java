package com.example.untitleddungeongame.floors;

import com.example.untitleddungeongame.entity.Player;
import com.example.untitleddungeongame.floors.Room;

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
        }
    }

    @Override
    public boolean getRoomCleared(){
        return true;
    }

    public boolean usedRest() {
        return restUsed;
    }
}
