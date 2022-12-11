import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class Day3Part1 {
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

    public Day3Part1() throws IOException {
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
        while ((items = bReader.readLine()) != null) {
            Set<Character> dups1 = new HashSet<>();

            for (int i = 0; i < items.length() / 2; i++) {
                dups1.add(items.charAt(i));
            }

            for (int i = items.length() / 2; i < items.length(); i++) {
                if (dups1.contains(items.charAt(i))) {
                    total += itemValues.get(items.charAt(i));
                    break;
                }
            }
        }

        fReader.close();
        bReader.close();
    }

    public int getTotal() {
        return total;
    }
}
