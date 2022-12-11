import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Day8Part2 {
    File inputFile;
    int total;

    List<List<Integer>> inputList = new ArrayList<>();
    
    public Day8Part2() throws IOException {
        readFile();
        fillLists();
        calculateVisibleTrees();
        System.out.println(total);
    }

    private void calculateVisibleTrees() {
        for (int i = 0; i < inputList.size(); i++) {
            for (int j = 0; j < inputList.get(i).size(); j++) {
                int north = 0, south = 0, east = 0, west = 0, currTree = inputList.get(i).get(j);
                if (i == 0
                 || i == inputList.size() - 1
                 || j == 0
                 || j == inputList.get(i).size() - 1) {
                    continue;
                } else {
                    north = dfs(i + 1, j, 1, 0, currTree, north);
                    south = dfs(i - 1, j, -1, 0, currTree, south);
                    east  = dfs(i, j + 1, 0, 1, currTree, east);
                    west  = dfs(i, j - 1, 0, -1, currTree, west);
                }
                total = Math.max(total, north * south * east * west);
            }
        }
    }

    private int dfs(int i, int j, int dirI, int dirJ, int treeSize, int currentTotal) {
        if (outOfBounds(i, j)) {
            return currentTotal;
        }

        var currTreeSize = inputList.get(i).get(j);
        if (currTreeSize >= treeSize) {
            return currentTotal + 1;
        }

        return dfs(i + dirI, j + dirJ, dirI, dirJ, treeSize, currentTotal + 1);
    }

    private boolean outOfBounds(int i, int j) {
        return i < 0 
            || i >= inputList.size()
            || j < 0
            || j >= inputList.get(i).size();
    }
    
    private void readFile() {
        inputFile = new File(getClass().getResource("inputDay8.txt").getPath());
    }

    private void fillLists() throws IOException {
        FileReader fReader = new FileReader(inputFile);
        BufferedReader bReader = new BufferedReader(fReader);

        String lines;
        while ((lines = bReader.readLine()) != null) {
            List<Integer> list = new ArrayList<>();
            for (Character num : lines.toCharArray()) {
                list.add(num - 48);
            }
            inputList.add(list);
        }

        fReader.close();
        bReader.close();
    }
    
}
