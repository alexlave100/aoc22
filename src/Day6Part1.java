import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class Day6Part1 {
    File inputFile;
    int markerIdx;

    public Day6Part1() throws IOException {
        readFile();
        System.out.println(getMarkerIdx());
    }

    private void readFile() throws IOException {
        inputFile = new File(getClass().getResource("inputDay6.txt").getPath());
        calculateMarker();
    }
    
    private void calculateMarker() throws IOException {
        FileReader fReader = new FileReader(inputFile);
        BufferedReader bReader = new BufferedReader(fReader);
        
        String markerSeq;
        while ((markerSeq = bReader.readLine()) != null) {

            Map<Character, Integer> markers = new HashMap<>();
            for (int start = 0, end = 0; end < markerSeq.length(); end++) {
                var endChar = markerSeq.charAt(end);

                if (markers.get(endChar) != null) {
                    start = Math.max(start, markers.get(endChar) + 1);
                }

                markers.put(endChar, end);

                if (end - start + 1 == 4) {
                    markerIdx = end + 1;
                    break;
                }
            }
        }

        fReader.close();
        bReader.close();
    }

    public int getMarkerIdx() {
        return markerIdx;
    }

}
