package com.example.untitleddungeongame;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import com.example.untitleddungeongame.RoomMaster;

import android.util.Log;

import org.junit.Test;

import java.util.ArrayList;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class RoomMasterTest {
    @Test
    public void testGetEmptyPathsNoEmpty() {
        RoomMaster roomMaster = new RoomMaster();
        roomMaster.setFloorMap(new int[][] {{0, 0, 0, 0},
                                            {0, 0, 0, 0},
                                            {0, 3, 0, 0},
                                            {3, 1, 3, 0}});

        assertEquals(new ArrayList<int[]>(), roomMaster.getEmptyPaths(new int[] {3, 1}));
    }

    @Test
    public void testGetEmptyPathLDEmpty() {
        RoomMaster roomMaster = new RoomMaster();
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
    public void findEmptyPathLastEmpty() {
        RoomMaster roomMaster = new RoomMaster();
        roomMaster.setFloorMap(new int[][] {{0, 6, 3, 5},
                                            {0, 5, 3, 4},
                                            {0, 0, 1, 5},
                                            {0, 0, 0, 2}});

        ArrayList<int[]> path = new ArrayList<>();
        path.add(new int[] {2, 2});
        path.add(new int[] {2, 3});
        path.add(new int[] {1, 3});
        path.add(new int[] {1, 2});
        path.add(new int[] {0, 2});
        path.add(new int[] {0, 3});

        ArrayList<int[]> expected = new ArrayList<>();
        expected.add(new int[] {0, 3});

        ArrayList<int[]> actual = roomMaster.findEmptyPath(path);

        assertArrayEquals(expected.toArray(), actual.toArray());
    }
}
