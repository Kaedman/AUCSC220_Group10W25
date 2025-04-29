package com.example.untitleddungeongame;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import com.example.untitleddungeongame.entity.Player;
import com.example.untitleddungeongame.floors.RoomMaster;

import java.util.ArrayList;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class RoomMasterTest {
    @Test
    public void testGetEmptyPathsNoEmpty() {
        RoomMaster roomMaster = new RoomMaster(new Player(60));
        roomMaster.setFloorMap(new int[][] {{0, 0, 0, 0},
                                            {0, 0, 0, 0},
                                            {0, 3, 0, 0},
                                            {3, 1, 3, 0}});

        assertEquals(new ArrayList<int[]>(), roomMaster.getEmptyPaths(new int[] {3, 1}));
    }

    @Test
    public void testGetEmptyPathLDEmpty() {
        RoomMaster roomMaster = new RoomMaster(new Player(60));
        roomMaster.setFloorMap(new int[][] {{0, 0, 0, 0},
                                            {0, 0, 3, 0},
                                            {0, 0, 1, 5},
                                            {0, 0, 0, 0}});
        ArrayList<int[]> expected = new ArrayList<>();
        expected.add(new int[] {0, -1});
        expected.add(new int[] {1, 0});

        ArrayList<int[]> actual = roomMaster.getEmptyPaths(new int[] {2, 2});

        assertArrayEquals(expected.toArray(), actual.toArray());
    }

    @Test
    public void testFindEmptyPathLastEmpty() {
        RoomMaster roomMaster = new RoomMaster(new Player(60));
        roomMaster.setFloorMap(new int[][] {{0, 6, , 5},
                                            {0, 5, 3, 4},
                                            {0, 0, 1, 5},
                                            {0, 0, 0, 2}});

        ArrayList<int[]> path = new ArrayList<>();
        path.add(0, new int[] {2, 2});
        path.add(0, new int[] {2, 3});
        path.add(0, new int[] {1, 3});
        path.add(0, new int[] {1, 2});
        path.add(0, new int[] {0, 2});
        path.add(0, new int[] {0, 3});

        ArrayList<int[]> expected = new ArrayList<>();
        expected.add(new int[] {2, 2});

        ArrayList<int[]> actual = roomMaster.findEmptyPath(path);

        assertArrayEquals(expected.toArray(), actual.toArray());
    }

    @Test
    public void testViewFloorMap() {
        RoomMaster roomMaster = new RoomMaster(new Player(60));
        roomMaster.generateRoomArray(6, 6, 30);
        assertTrue(roomMaster.getRoomCount() >= 15);
    }

    @Test
    public void TestRoomGenOrigin(){
        RoomMaster roomMaster = new RoomMaster(new Player(60));
        roomMaster.setFloorMap(new int[][] {{4, 3, 0, 1},
                                            {0, 3, 0, 4},
                                            {2, 4, 1, 2},
                                            {4, 0, 0, 2}});
        roomMaster.setHead(roomMaster.createFloor());
        int actualId = roomMaster.getHead().getRight().getUp().getUp().getRoomId();
        int expectedId = 3;

        assertEquals(actualId, expectedId);
    }

}
