import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class Day4Part2 {
    private int total;
    private File inputFile;

    public Day4Part2() throws IOException {
        readFile();
        System.out.println(getTotal());
    }

    private void readFile() throws IOException {
        inputFile = new File(getClass().getResource("inputDay4.txt").getPath());
        calcItems();
    }

    private void calcItems() throws IOException {
        FileReader fReader = new FileReader(inputFile);
        BufferedReader bReader = new BufferedReader(fReader);

        String items;
        while ((items = bReader.readLine()) != null) {
            StringBuilder sb = new StringBuilder();

            int[] elf1 = new int[2], elf2 = new int[2];
            int i = 0;

            while (items.charAt(i) != '-') {
                sb.append(items.charAt(i++));
            }

            elf1[0] = Integer.parseInt(sb.toString());
            i++;
            sb = new StringBuilder();

            while (items.charAt(i) != ',') {
                sb.append(items.charAt(i++));
            }

            elf1[1] = Integer.parseInt(sb.toString());
            i++;
            sb = new StringBuilder();

            while (items.charAt(i) != '-') {
                sb.append(items.charAt(i++));
            }

            elf2[0] = Integer.parseInt(sb.toString());
            i++;
            sb = new StringBuilder();

            while (i < items.length()) {
                sb.append(items.charAt(i++));
            }

            elf2[1] = Integer.parseInt(sb.toString());

            if (elf1[0] < elf2[0]) {
                if (elf1[1] >= elf2[0]) {
                    total += 1;
                }
            } else if (elf1[0] > elf2[0]) {
                if (elf2[1] >= elf1[0]) {
                    total += 1;
                }
            } else if (elf1[0] == elf2[0]) {
                total += 1;
            } else if (elf1[1] == elf2[1]) {
                total += 1;
            }
        }
        
        fReader.close();
        bReader.close();
    }

    public int getTotal() {
        return total;
    }
}
