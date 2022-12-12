import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Queue;

public class Day12Part2 {
    final char End = 'E';
    final char Start = 'S';
    final int[][] directions = new int[][] {
        new int[] {1, 0},
        new int[] {-1,0},
        new int[] {0, 1},
        new int[] {0,-1}
    };
    
    File inputFile;
    int[][] minDistanceToTravel;
    int shortestA = Integer.MAX_VALUE;
    List<List<Character>> heightMap = new ArrayList<>();

    public Day12Part2() throws IOException {
        readFile();
        buildHeightMap();
        calculateShortestPath();
        System.out.println(shortestA);
    }

    private void findShortestPathToGoal(int i, int j) {
        Queue<int[]> unvisited = new ArrayDeque<>();
        unvisited.add(new int[] { i, j });

        minDistanceToTravel[i][j] = 0;

        while (!unvisited.isEmpty()) {
            var currentHeight = unvisited.poll();
            int currentNorthSouth = currentHeight[0];
            int currentEastWest = currentHeight[1];

            for (int d = 0; d < 4; d++) {
                int northSouth = directions[d][0] + currentNorthSouth;
                int eastWest = directions[d][1] + currentEastWest;
                if (!outOfReach(northSouth, eastWest) && canTravel(northSouth, eastWest, currentNorthSouth, currentEastWest)) {
                    if (pathIsShorter(northSouth, eastWest, currentNorthSouth, currentEastWest)) {
                        addHeight(unvisited, northSouth, eastWest, currentNorthSouth, currentEastWest);
                    }
                }
            }
        }
    }
    
    private boolean canTravel(int newNorthSouth, int newEastWest, int currentNorthSouth, int currentEastWest) {
        return heightMap.get(currentNorthSouth).get(currentEastWest) <= heightMap.get(newNorthSouth).get(newEastWest) + 1;
    }

    private boolean pathIsShorter(int newNorthSouth, int newEastWest, int currentNorthSouth, int currentEastWest) {
        return minDistanceToTravel[newNorthSouth][newEastWest] > minDistanceToTravel[currentNorthSouth][currentEastWest] + 1;
    }

    private void addHeight(Queue<int[]> unvisited, int newNorthSouth, int newEastWest, int currentNorthSouth, int currentEastWest) {
        unvisited.add(new int[] { newNorthSouth, newEastWest });
        minDistanceToTravel[newNorthSouth][newEastWest] = minDistanceToTravel[currentNorthSouth][currentEastWest] + 1;
    }

    private boolean outOfReach(int i, int j) {
        return i < 0
            || i >= heightMap.size()
            || j < 0
            || j >= heightMap.get(i).size();
    }
    
    private void calculateShortestPath() {
        for (int i = 0; i < heightMap.size(); i++) {
            for (int j = 0; j < heightMap.get(i).size(); j++) {
                if (heightMap.get(i).get(j).equals(End)) {
                    heightMap.get(i).set(j, 'z');
                    findShortestPathToGoal(i, j);
                } else if (heightMap.get(i).get(j).equals(Start)) {
                    heightMap.get(i).set(j, 'a');
                }
            }
        }

        for (int i = 0; i < heightMap.size(); i++) {
            for (int j = 0; j < heightMap.get(i).size(); j++) {
                if (heightMap.get(i).get(j).equals('a')) {
                    shortestA = Math.min(shortestA, minDistanceToTravel[i][j]);
                }
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
