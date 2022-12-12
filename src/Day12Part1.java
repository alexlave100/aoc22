import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Queue;

public class Day12Part1 {
    File inputFile;
    final char Start = 'S';
    final char End = 'E';

    int[] startCoordinates = new int[2];
    int[][] minDistanceToTravel;

    List<List<Character>> heightMap = new ArrayList<>();

    public Day12Part1() throws IOException {
        readFile();
        buildHeightMap();
        calculateShortestPath();
        System.out.println(minDistanceToTravel[startCoordinates[0]][startCoordinates[1]]);
    }

    private void findShortestPathToGoal(int i, int j) {
        Queue<int[]> unvisited = new ArrayDeque<>();
        unvisited.add(new int[] { i , j });

        minDistanceToTravel[i][j] = 0;
        
        while (!unvisited.isEmpty()) {
            var heightToVisit = unvisited.poll();
            int k = heightToVisit[0];
            int l = heightToVisit[1];

            if (!outOfReach(k + 1, l) && heightMap.get(k).get(l) <= heightMap.get(k + 1).get(l) + 1) {
                if (minDistanceToTravel[k + 1][l] > minDistanceToTravel[k][l] + 1) {
                    unvisited.add(new int[] { k + 1, l });
                    minDistanceToTravel[k + 1][l] = minDistanceToTravel[k][l] + 1;
                }
            }
            if (!outOfReach(k - 1, l) && heightMap.get(k).get(l) <= heightMap.get(k - 1).get(l) + 1) {
                if (minDistanceToTravel[k - 1][l] > minDistanceToTravel[k][l] + 1) {
                    unvisited.add(new int[] { k - 1, l });
                    minDistanceToTravel[k - 1][l] = minDistanceToTravel[k][l] + 1;
                }
            }
            if (!outOfReach(k, l + 1) && heightMap.get(k).get(l) <= heightMap.get(k).get(l + 1) + 1) {
                if (minDistanceToTravel[k][l + 1] > minDistanceToTravel[k][l] + 1) {
                    unvisited.add(new int[] { k, l + 1});
                    minDistanceToTravel[k][l + 1] = minDistanceToTravel[k][l] + 1;
                }
            }
            if (!outOfReach(k, l - 1) && heightMap.get(k).get(l) <= heightMap.get(k).get(l - 1) + 1) {
                if (minDistanceToTravel[k][l - 1] > minDistanceToTravel[k][l] + 1) {
                    unvisited.add(new int[] { k, l - 1});
                    minDistanceToTravel[k][l - 1] = minDistanceToTravel[k][l] + 1;
                }
            }
        }
    }

    private boolean outOfReach(int i, int j) {
        return i < 0
                || i >= heightMap.size()
                || j < 0
                || j >= heightMap.get(i).size();
    }
    
    private void calculateShortestPath() {
        boolean foundStart = false, foundEnd = false;
        for (int i = 0; i < heightMap.size(); i++) {
            for (int j = 0; j < heightMap.get(i).size(); j++) {
                if (heightMap.get(i).get(j).equals(End)) {
                    heightMap.get(i).set(j, 'z');
                    findShortestPathToGoal(i, j);
                    foundEnd = true;
                } else if (heightMap.get(i).get(j).equals(Start)) {
                    heightMap.get(i).set(j, 'a');
                    startCoordinates[0] = i;
                    startCoordinates[1] = j;
                    foundStart = true;
                }
                if (foundStart && foundEnd) {
                    break;
                }
            }
            if (foundStart && foundEnd) {
                break;
            }
        }
    }

    private void buildHeightMap() throws IOException {
        FileReader fReader = new FileReader(inputFile);
        BufferedReader bReader = new BufferedReader(fReader);

        String lines;
        while ((lines = bReader.readLine()) != null) {
            List<Character> list = new ArrayList<>();
            for (Character height : lines.toCharArray()) {
                list.add(height);
            }
            heightMap.add(list);
        }

        minDistanceToTravel = new int[heightMap.size()][heightMap.get(0).size()];

        for (var arr : minDistanceToTravel) {
            Arrays.fill(arr, Integer.MAX_VALUE);
        }

        fReader.close();
        bReader.close();
    }

    private void readFile() {
        inputFile = new File(getClass().getResource("inputDay12.txt").getPath());
    }
}
