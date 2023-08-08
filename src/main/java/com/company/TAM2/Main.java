package com.company.TAM2;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Main {

    public static final int COLUMNS_COUNT = 2;
    public static final String COMA = ",";
    public static final int ACTION_INDEX = 0;
    public static final int COUNT_INDEX = 1;

    public static void main(String[] args) {
        String fromFileName = "src/main/resources/orange.csv";
        String toFileName = "src/main/resources/result.txt";
        getStatistic(fromFileName, toFileName);
    }

    public static void getStatistic(String fromFileName, String toFileName) {

        List<String> dataFromFile = readFromFile(fromFileName);
        Map<String, Integer> actionCount = new HashMap<>();

        for (String line : dataFromFile) {
            String[] parts = line.split(COMA);
            if (parts.length != COLUMNS_COUNT) {
                throw new IllegalArgumentException("Not correct data");
            }

            String action = parts[ACTION_INDEX].trim();
            int count = Integer.parseInt(parts[COUNT_INDEX].trim());

            int currentCount = actionCount.getOrDefault(action, 0);
            actionCount.put(action, currentCount + count);
        }

        String finalReport = createReport(actionCount);
        writeToFile(toFileName, finalReport);
    }

    private static String createReport(Map<String, Integer> actionCount) {
        return "supply = " + actionCount.get("supply") + System.lineSeparator() +
                "buy = " + actionCount.get("buy") + System.lineSeparator() +
                "result = " + (actionCount.get("supply") - actionCount.get("buy"));
    }

    private static List<String> readFromFile(String fromFileName) {
        List<String> resultList = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(fromFileName))) {
            String line;
            while ((line = reader.readLine()) != null) {
                resultList.add(line);
            }
        } catch (IOException e) {
            throw new RuntimeException("Can't read data from the file " + fromFileName, e);
        }
        return resultList;
    }

    private static void writeToFile(String toFileName, String dataToWrite) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(toFileName))) {
            writer.write(dataToWrite);
        } catch (Exception e) {
            throw new RuntimeException("Can't write data to the file " + toFileName, e);
        }
    }
}
