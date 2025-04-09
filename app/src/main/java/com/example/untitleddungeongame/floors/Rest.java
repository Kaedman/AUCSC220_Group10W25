package com.example.untitleddungeongame.floors;


public class Rest extends Room {
    private boolean restUsed;

    public Rest(int roomId) {
        super(roomId);
        this.restUsed = false;
    }

    public boolean useRest() {
        if (!restUsed) {
            //player.setHp(player.getMaxHp);
            restUsed = true;
        }

        return restUsed;
    }
}
