import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayDeque;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;

public class Day11Part2 {
    private class Monkey {
        long inspectedCount, divisibleBy, operationNumber, operation, trueMonkey, falseMonkey;
        Queue<Long> items = new ArrayDeque<>();
    }

    File inputFile;
    long product = 1;
    Map<Long, Monkey> monkeys = new HashMap<>();

    public Day11Part2() throws IOException {
        readFile();
        parseFile();
        calculateMonkeyBusiness();
        System.out.println(getTop2Monkeys());
    }
    
    private long getTop2Monkeys() {
        Queue<Long> topMonkeys = new PriorityQueue<>(Collections.reverseOrder());
        for (Monkey monkey : monkeys.values()) {
            topMonkeys.add(monkey.inspectedCount);
        }
        return topMonkeys.poll() * topMonkeys.poll();
    }

    private void calculateMonkeyBusiness() {
        calculateProduct();
        for (int i = 0; i < 10_000; i++) {
            for (Monkey monkey : monkeys.values()) {
                inspect(monkey);
            }
        }
    }

    private void inspect(Monkey monkey) {
        while (!monkey.items.isEmpty()) {
            Long worryLevel = monkey.items.poll();
            if (monkey.operationNumber == -1) {
                worryLevel = monkey.operation == 0 ? worryLevel + worryLevel : worryLevel * worryLevel;
            } else if (monkey.operation == 0) {
                worryLevel += monkey.operationNumber;
            } else {
                worryLevel *= monkey.operationNumber;
            }

            worryLevel %= product;

            if (worryLevel % monkey.divisibleBy == 0) {
                monkeys.get(monkey.trueMonkey).items.add(worryLevel);
            } else {
                monkeys.get(monkey.falseMonkey).items.add(worryLevel);
            }

            monkey.inspectedCount += 1;
        }
    }

    private void parseFile() throws IOException {
        FileReader fReader = new FileReader(inputFile);
        BufferedReader bReader = new BufferedReader(fReader);

        String lines;
        Monkey monkey = new Monkey();
        while ((lines = bReader.readLine()) != null) {
            lines = lines.replaceAll("[^a-zA-Z0-9*+]", " ");
            String[] splittedLine = lines.trim().split("\\s+");

            if (splittedLine[0].equals("Monkey")) {
                var num = Long.parseLong(splittedLine[1]);
                monkeys.putIfAbsent(num, new Monkey());
                monkey = monkeys.get(num);
            } else if (splittedLine[0].equals("Starting")) {
                for (int i = 2; i < splittedLine.length; i++) {
                    monkey.items.add(Long.parseLong(splittedLine[i]));
                }
            } else if (splittedLine[0].equals("Operation")) {
                monkey.operation = splittedLine[3].equals("*") ? 1 : 0;
                try {
                    monkey.operationNumber = Long.parseLong(splittedLine[4]);
                } catch (Exception e) {
                    monkey.operationNumber = -1;
                }
            } else if (splittedLine[0].equals("Test")) {
                monkey.divisibleBy = Long.parseLong(splittedLine[3]);
            } else if (splittedLine[0].equals("If")) {
                if (splittedLine[1].equals("true")) {
                    monkey.trueMonkey = Long.parseLong(splittedLine[5]);
                } else {
                    monkey.falseMonkey = Long.parseLong(splittedLine[5]);
                }
            }
        }
        
        fReader.close();
        bReader.close();
    }

    private void calculateProduct() {
        for (Monkey monkey : monkeys.values()) {
            product *= monkey.divisibleBy;
        }
    }

    private void readFile() {
        inputFile = new File(getClass().getResource("inputDay11.txt").getPath());
    }
}
