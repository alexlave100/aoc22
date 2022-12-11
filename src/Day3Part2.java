import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class Day3Part2 {
    
    private int total;
    private File inputFile;
    private Map<Character, Integer> itemValues;

    private void buildMap() {
        itemValues = new HashMap<>();
        
        int val = 1;
        for (char i = 'a'; i <= 'z'; i++, val++) {
            itemValues.put(i, val);
        }
        
        for (char j = 'A'; j <= 'Z'; j++, val++) {
            itemValues.put(j, val);
        }
    }

    public Day3Part2() throws IOException {
        buildMap();
        readFile();

        System.out.println(getTotal());
    }

    private void readFile() throws IOException {
        inputFile = new File(getClass().getResource("inputDay3.txt").getPath());
        calcItems();
    }

    private void calcItems() throws IOException {
        FileReader fReader = new FileReader(inputFile);
        BufferedReader bReader = new BufferedReader(fReader);

        String items;
        int groupCount = 1;
        Set<Character> elf1Set = new HashSet<>();
        Set<Character> elf2Set = new HashSet<>();
        while ((items = bReader.readLine()) != null) {
            switch (groupCount) {
                case 1:
                    for (Character item : items.toCharArray()) {
                        elf1Set.add(item);
                    }
                    groupCount += 1;
                    break;
                case 2:
                    for (Character item : items.toCharArray()) {
                        if (elf1Set.contains(item)) {
                            elf2Set.add(item);
                        }
                    }
                    elf1Set.clear();
                    groupCount += 1;
                    break;
                case 3:
                    char commonItem = ' ';
                    for (Character item : items.toCharArray()) {
                        if (elf2Set.contains(item)) {
                            commonItem = item;
                            break;
                        }
                    }
                    elf2Set.clear();
                    total += itemValues.get(commonItem);
                    groupCount = 1;
                    break; 
                default:
                    break;
            }
        }

        fReader.close();
        bReader.close();
    }

    public int getTotal() {
        return total;
    }
}
