package com.example.untitleddungeongame;

public class Rest extends Room{
    private boolean restUsed;

    public Rest(Room left, Room right, Room up, Room down, Enemy enemy) {
        super(left, right, up, down);
        this.restUsed = false;
    }

    public boolean useRest() {
        if (!restUsed) {
            player.setHp(player.getMaxHp);
            restUsed = true;
        }

        return restUsed;
    }
}
