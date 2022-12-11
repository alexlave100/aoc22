import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class Day2Part1 {
    
    private File inputFile;
    private int result = 0;
    Map<Character, Integer> rockPaperScissors;

    public Day2Part1() throws IOException {
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
            var myPlay = chosenPlay.charAt(2);
            var localResult = rockPaperScissors.get(opponentPlay) - rockPaperScissors.get(myPlay);

            if (localResult < 0) {
                if (Math.abs(localResult) == 1) {
                    result += rockPaperScissors.get(myPlay) + 6;
                } else {
                    result += rockPaperScissors.get(myPlay);
                }
            } else if (localResult == 0) {
                result += rockPaperScissors.get(myPlay) + 3;
            } else {
                if (Math.abs(localResult) == 1) {
                    result += rockPaperScissors.get(myPlay);
                } else {
                    result += rockPaperScissors.get(myPlay) + 6;
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
