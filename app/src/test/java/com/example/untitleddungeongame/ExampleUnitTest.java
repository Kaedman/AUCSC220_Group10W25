package com.example.untitleddungeongame;

import org.junit.Test;

import static org.junit.Assert.*;

import com.example.untitleddungeongame.entity.Player;
import com.example.untitleddungeongame.floors.Encounter;
import com.example.untitleddungeongame.floors.Rest;
import com.example.untitleddungeongame.floors.Room;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() {
        assertEquals(4, 2 + 2);
    }

    //Just testing room joining.
    @Test
    public void TestRoomJoining() {
        Player player = new Player(13);
        Enemy enemy = new Enemy("Test", 5, 5, 2);
        Encounter enemyRoom = new Encounter(0010, enemy, player);
        Rest restRoom = new Rest(0020);
        enemyRoom.setLeftRoom(restRoom);
        restRoom.setRightRoom(enemyRoom);
        Room currentRoom = restRoom;
        currentRoom.setUpRoom(enemyRoom);
    }

}