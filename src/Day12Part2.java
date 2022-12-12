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
    File inputFile;
    final char Start = 'S';
    final char End = 'E';

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
            var heightToVisit = unvisited.poll();
            int k = heightToVisit[0];
            int l = heightToVisit[1];

            if (!outOfReach(k + 1, l) && canTravel(k + 1, l, k, l)) {
                if (pathIsShorter(k + 1, l, k, l)) {
                    addHeight(unvisited, k + 1, l, k, l);
                }
            }
            if (!outOfReach(k - 1, l) && canTravel(k - 1, l, k, l)) {
                if (pathIsShorter(k - 1, l, k, l)) {
                    addHeight(unvisited, k - 1, l, k, l);
                }
            }
            if (!outOfReach(k, l + 1) && canTravel(k, l + 1, k, l)) {
                if (pathIsShorter(k, l + 1, k, l)) {
                    addHeight(unvisited, k, l + 1, k, l);
                }
            }
            if (!outOfReach(k, l - 1) && canTravel(k, l - 1, k, l)) {
                if (pathIsShorter(k, l - 1, k, l)) {
                    addHeight(unvisited, k, l - 1, k, l);
                }
            }
        }
    }
    
    private boolean canTravel(int k, int l, int oldK, int oldL) {
        return heightMap.get(oldK).get(oldL) <= heightMap.get(k).get(l) + 1;
    }

    private boolean pathIsShorter(int k, int l, int oldK, int oldL) {
        return minDistanceToTravel[k][l] > minDistanceToTravel[oldK][oldL] + 1;
    }

    private void addHeight(Queue<int[]> unvisited, int k, int l, int oldK, int oldL) {
        unvisited.add(new int[] { k, l });
        minDistanceToTravel[k][l] = minDistanceToTravel[oldK][oldL] + 1;
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
                } else if (heightMap.get(i).get(j).equals('a')) {
                    shortestA = Math.min(shortestA, minDistanceToTravel[i][j]);
                } else if (heightMap.get(i).get(j).equals(Start)) {
                    heightMap.get(i).set(j, 'a');
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
