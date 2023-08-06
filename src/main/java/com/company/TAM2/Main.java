package com.company.TAM2;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class Main{

    public static final int COLUMNS_COUNT = 2;
    public static final String COMA = ",";
    public static final int ACTION_INDEX = 0;
    public static final int COUNT_INDEX = 1;

    public static void main(String[] args) {
        String fromFileName =  "orange.csv";
        String toFileName = "result.txt";
        getStatistic(fromFileName, toFileName);
    }

    public static void getStatistic(String fromFileName, String toFileName) {
        try (
            BufferedReader reader = new BufferedReader(new FileReader(fromFileName));
            BufferedWriter writer = new BufferedWriter(new FileWriter(toFileName));
        ) {
            Map<String, Integer> actionCount = new HashMap<>();
            String line;

            while ((line = reader.readLine()) != null){
                putAction(actionCount, line);
            }
            writeToFile(writer, actionCount);
        }
        catch (Exception e) {
            throw new RuntimeException("Can't read data from the file " + fromFileName, e);
        }
    }

    private static void putAction(Map<String, Integer> actionCount, String line) {
        String[] parts = line.split(COMA);
        if(parts.length != COLUMNS_COUNT){
            throw new IllegalArgumentException("Not correct data");
        }

        String action = parts[ACTION_INDEX].trim();
        int count = Integer.parseInt(parts[COUNT_INDEX].trim());

        int currentCount = actionCount.getOrDefault(action, 0);
        actionCount.put(action, currentCount + count);
    }

    private static void writeToFile(BufferedWriter writer, Map<String, Integer> actionCount) throws IOException {
        String finalReport = "supply = " + actionCount.get("supply")
                + "\s" + "buy = " + actionCount.get("buy")
                + "\s" + "result = " + (actionCount.get("supply") - actionCount.get("buy"));
        writer.write(String.valueOf(finalReport));
    }
}
