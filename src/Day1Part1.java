import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class Day1Part1 {
    private File inputFile;
    private int largestAmountOfCalories = 0;

    public Day1Part1() throws IOException {
        createFile();
    }
    
    private void createFile() throws IOException {
        inputFile = new File(getClass().getResource("inputDay1.txt").getPath());
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
        largestAmountOfCalories = convertedCalories > largestAmountOfCalories ? convertedCalories : largestAmountOfCalories;
    }

    public int getAnswer() {
        return largestAmountOfCalories;
    }
}