import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Day8Part1 {
    File inputFile;
    int treeCount;

    List<List<Integer>> inputList = new ArrayList<>();
    
    public Day8Part1() throws IOException {
        readFile();
        fillLists();
        calculateVisibleTrees();
        System.out.println(treeCount);
    }

    private void calculateVisibleTrees() {
        for (int i = 0; i < inputList.size(); i++) {
            for (int j = 0; j < inputList.get(i).size(); j++) {
                int currTreeSize = inputList.get(i).get(j);
                if (i == 0
                 || i == inputList.size() - 1
                 || j == 0
                 || j == inputList.get(i).size() - 1) {
                    treeCount += 1;
                } else if (dfs(i + 1, j, 1, 0, currTreeSize)) {
                    treeCount += 1;
                } else if (dfs(i - 1, j, -1, 0, currTreeSize)) {
                    treeCount += 1;
                } else if (dfs(i, j + 1, 0, 1, currTreeSize)) {
                    treeCount += 1;
                } else if (dfs(i, j - 1, 0, -1, currTreeSize)) {
                    treeCount += 1;
                }
            }
        }
    }

    private boolean dfs(int i, int j, int dirI, int dirJ, int treeSize) {
        if (outOfBounds(i, j)) {
            return true;
        }

        var currTreeSize = inputList.get(i).get(j);
        if (currTreeSize >= treeSize) {
            return false;
        }

        return dfs(i + dirI, j + dirJ, dirI, dirJ, treeSize);
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
