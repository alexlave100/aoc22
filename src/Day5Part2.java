import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.Stack;

public class Day5Part2 {
    File inputFile;

    List<Character[]> listOfStartingStacks = List.of(
        new Character[] {'H','C','R'},
        new Character[] {'B','J','H','L','S','F'},
        new Character[] {'R','M','D','H','J','T','Q'},
        new Character[] {'S','G','R','H','Z','B','J'},
        new Character[] {'R','P','F','Z','T','D','C','B'}, 
        new Character[] {'T','H','C','G'},
        new Character[] {'S','N','V','Z','B','P','W','L'},
        new Character[] {'R','J','Q','G','C'},
        new Character[] {'L','D','T','R','H','P','F','S'});

    List<Stack<Character>> stackList = List.of(
        new Stack<>(),
        new Stack<>(),
        new Stack<>(),
        new Stack<>(),
        new Stack<>(),
        new Stack<>(),
        new Stack<>(),
        new Stack<>(),
        new Stack<>());
    
    private void fillStacks() {
        for (int i = 0; i < 9; i++) {
            for (Character c : listOfStartingStacks.get(i)) {
                stackList.get(i).add(c);
            }
        }
    }

    public Day5Part2() throws IOException {
        fillStacks();
        readFile();
    }

    private void readFile() throws IOException {
        inputFile = new File(getClass().getResource("inputDay5.txt").getPath());
        calculateMovedCrates();
    }
    
    private void calculateMovedCrates() throws IOException {
        FileReader fReader = new FileReader(inputFile);
        BufferedReader bReader = new BufferedReader(fReader);

        String items;
        while ((items = bReader.readLine()) != null) {
            String[] splittedLine = items.split(" ");
            
            int cratesToMove = Integer.parseInt(splittedLine[1]);
            int fromIdx = Integer.parseInt(splittedLine[3]) - 1;
            int toIdx = Integer.parseInt(splittedLine[5]) - 1;
            
            var fromStack = stackList.get(fromIdx);
            var toStack = stackList.get(toIdx);

            Stack<Character> tempStack = new Stack<>();
            for (int i = 0; i < cratesToMove; i++) {
                if (!fromStack.empty()) {
                    tempStack.add(fromStack.pop());
                } else {
                    break;
                }
            }
            
            while (!tempStack.empty()) {
                toStack.add(tempStack.pop());
            }
        }

        for (Stack<Character> stack : stackList) {
            System.out.print(stack.peek());
        }
        
        System.out.println();
        
        fReader.close();
        bReader.close();
    }
}
