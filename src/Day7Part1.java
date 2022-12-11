import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Map;
import java.util.HashMap;

public class Day7Part1 {

    private class CustomFile {
        Integer size;
        
        public CustomFile(Integer size) {
            this.size = size;
        }
    }
    
    private class Directory {
        Integer totalSize;
        Directory previousDirectory;
        Map<String, CustomFile> directoryFiles = new HashMap<>();
        Map<String, Directory> subDirectories = new HashMap<>();

        public Directory(Directory previousDirectory) {
            this.previousDirectory = previousDirectory;
        }
    }
    
    File inputFile;

    Directory currentDirectory = null;
    Directory baseDirectory = new Directory(null);
    Directory traverseDirectory = baseDirectory;

    int totalFileSizes;
    
    public Day7Part1() throws IOException {
        readFile();
        buildFileSystem();
        traverseFileSystemAndfindFilesToRemove(traverseDirectory);
        System.out.println(totalFileSizes);
    }

    private void traverseFileSystemAndfindFilesToRemove(Directory currDir) {
        for (var kv : currDir.subDirectories.values()) {
            traverseFileSystemAndfindFilesToRemove(kv);
        }

        int tot = 0;

        for (var kv : currDir.subDirectories.entrySet()) {
            tot += kv.getValue().totalSize;
        }    

        for (var kv : currDir.directoryFiles.entrySet()) {
            tot += kv.getValue().size;
        }

        if (tot <= 100_000) {
            totalFileSizes += tot;
        }

        currDir.totalSize = tot;
    }

    private void readFile() {
        inputFile = new File(getClass().getResource("inputDay7.txt").getPath());
    }

    private void buildFileSystem() throws IOException {
        FileReader fReader = new FileReader(inputFile);
        BufferedReader bReader = new BufferedReader(fReader);

        String lines;
        while ((lines = bReader.readLine()) != null) {
            String[] splittedLine = lines.split(" ");

            if (splittedLine[0].equals("$")) {
                executeCommand(splittedLine);
            } else {
                mapOutDirectoryStruct(splittedLine);
            }
        }

        fReader.close();
        bReader.close();
    }

    private void executeCommand(String[] splittedLine) {
        if (splittedLine[1].equals("cd")) {
            if (splittedLine[2].equals("..")) {
                if (currentDirectory != null) {
                    currentDirectory = currentDirectory.previousDirectory;
                } else {
                    currentDirectory = baseDirectory;
                }
            } else {
                if (currentDirectory != null) {
                    Directory tmpDirectory = currentDirectory;
                    currentDirectory = currentDirectory.subDirectories.get(splittedLine[2]);
                    currentDirectory.previousDirectory = tmpDirectory;
                } else {
                    currentDirectory = baseDirectory;
                }
            }
        }
    }

    private void mapOutDirectoryStruct(String[] splittedLine) {
        if (splittedLine[0].equals("dir")) {
            currentDirectory.subDirectories.putIfAbsent(splittedLine[1], new Directory(null));
            currentDirectory.subDirectories.get(splittedLine[1]).previousDirectory = currentDirectory;
        } else {
            var fileExists = currentDirectory.directoryFiles.containsKey(splittedLine[1]);
            if (!fileExists) {
                currentDirectory.directoryFiles.put(splittedLine[1],new CustomFile(Integer.parseInt(splittedLine[0])));
            }
        }
    }

}
