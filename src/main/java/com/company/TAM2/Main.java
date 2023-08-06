package com.company.TAM2;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class Main {

    public static final int COLUMNS_COUNT = 2;

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
                String[] parts = line.split(",");
                if(parts.length != COLUMNS_COUNT){
                    throw new IllegalArgumentException("Not correct data");
                }

                String action = parts[0].trim();
                int count = Integer.parseInt(parts[1].trim());

                actionCount.put(action, count);
            }

            writer.write("supply = " + actionCount.get("supply"));
            writer.newLine();
            writer.write("buy = " + actionCount.get("buy"));
            writer.newLine();
            writer.write("result = " + ( actionCount.get("supply") - actionCount.get("buy")));
        }
        catch (Exception e) {
            throw new RuntimeException("Can't read data from the file " + fromFileName, e);
        }
    }
}
