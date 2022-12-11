import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.PriorityQueue;

public class Day1Part2 {
    
    private File inputFile;
    private int largestAmountOfCalories = 0;
    PriorityQueue<Integer> topThreeElfCalories;

    public Day1Part2() throws IOException {
        topThreeElfCalories = new PriorityQueue<>();
        createFile();
    }
    
    private void createFile() throws IOException {
        inputFile = new File(getClass().getResource("inputday1.txt").getPath());
        readFile();
    }

    private void readFile() throws IOException {
        FileReader fReader = new FileReader(inputFile);
        BufferedReader bReader = new BufferedReader(fReader);
        
        String calories;
        int currentCalories = 0;
        while ((calories = bReader.readLine()) != null) {
            if (calories.trim().isEmpty()) {
                calculateCalories(currentCalories);
                currentCalories = 0;
                continue;
            }
            currentCalories += Integer.parseInt(calories);
        }
        fReader.close();
        bReader.close();
    }

    private void calculateCalories(int convertedCalories) {
        if(topThreeElfCalories.size() == 3) {
            if(convertedCalories > topThreeElfCalories.peek()) {
                largestAmountOfCalories -= topThreeElfCalories.poll();
                largestAmountOfCalories += convertedCalories;
                topThreeElfCalories.add(convertedCalories);
            }
        } else {
            largestAmountOfCalories += convertedCalories;
            topThreeElfCalories.add(convertedCalories);
        }
    }

    public int getAnswer() {
        return largestAmountOfCalories;
    }
}