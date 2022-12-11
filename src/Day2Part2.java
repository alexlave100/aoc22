import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class Day2Part2 {
    
    private File inputFile;
    private int result = 0;
    Map<Character, Integer> rockPaperScissors;

    public Day2Part2() throws IOException {
        rockPaperScissors = new HashMap<>();
        rockPaperScissors.put('A', 1);
        rockPaperScissors.put('B', 2);
        rockPaperScissors.put('C', 3);
        rockPaperScissors.put('X', 1);
        rockPaperScissors.put('Y', 2);
        rockPaperScissors.put('Z', 3);

        createFile();

        System.out.println(getResult());
    }
    
    private void createFile() throws IOException {
        inputFile = new File(getClass().getResource("inputDay2.txt").getPath());
        readFile();
    }

    private void readFile() throws IOException {
        FileReader fReader = new FileReader(inputFile);
        BufferedReader bReader = new BufferedReader(fReader);

        String chosenPlay;
        while ((chosenPlay = bReader.readLine()) != null) {
            var opponentPlay = chosenPlay.charAt(0);
            var outcome = chosenPlay.charAt(2);

            if (outcome == 'X') {
                if (opponentPlay == 'A') {
                    result += 3;
                } else if (opponentPlay == 'B') {
                    result += 1;
                } else {
                    result += 2;
                }
            } else if (outcome == 'Y') {
                if (opponentPlay == 'A') {
                    result += 1 + 3;
                } else if (opponentPlay == 'B') {
                    result += 2 + 3;
                } else {
                    result += 3 + 3;
                }
            } else {
                if (opponentPlay == 'A') {
                    result += 2 + 6;
                } else if (opponentPlay == 'B') {
                    result += 3 + 6;
                } else {
                    result += 1 + 6;
                }
            }
        }
        
        fReader.close();
        bReader.close();
    }
    
    public int getResult() {
        return result;
    }
}
