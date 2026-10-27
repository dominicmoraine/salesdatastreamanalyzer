package org.example;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.OptionalDouble;

public class Main {
    public static void main(String[] args) {
        List<Sale> sales = new ArrayList<>();

        try (BufferedReader reader = Files.newBufferedReader(Paths.get("sales.csv"))) {
            String line;
            boolean header = true;

            while ((line = reader.readLine()) != null) {
                if (header) {
                    header = false;
                    continue;
                }

                if (line.trim().isEmpty()) {
                    continue;
                }

                String[] fields = line.split(",", -1);

                if (fields.length != 4) {
                    System.out.println("Skipping invalid row: " + line);
                    continue;
                }

                try {
                    int saleId = Integer.parseInt(fields[0].trim());
                    String employeeName = fields[1].trim();
                    String region = fields[2].trim();
                    double amount = Double.parseDouble(
                            fields[3].trim().replace("$", ""));

                    sales.add(new Sale(saleId, employeeName, region, amount));
                } catch (NumberFormatException e) {
                    System.out.println("Skipping invalid row: " + line);
                }
            }
        } catch (IOException e) {
            System.out.println("Error reading sales.csv: " + e.getMessage());
            return;
        }

        if (sales.isEmpty()) {
            System.out.println("No sales records found.");
            return;
        }

        System.out.println("Sales data stream analyzer");
        System.out.println("------------------");

        System.out.println("\nAll sales:");
        sales.stream().forEach(System.out::println);

        System.out.println("\nSales over 500:");
        sales.stream()
                .filter(sale -> sale.getAmount() > 500)
                .forEach(System.out::println);

        long countOver1000 = sales.stream()
                .filter(sale -> sale.getAmount() > 1000)
                .count();

        System.out.println("\nNumber of sales over 1,000: " + countOver1000);

        double totalSales = sales.stream()
                .mapToDouble(Sale::getAmount)
                .sum();

        System.out.printf("Total sales: $%.2f%n", totalSales);

        OptionalDouble average = sales.stream()
                .mapToDouble(Sale::getAmount)
                .average();

        if (average.isPresent()) {
            System.out.printf("Average sale: $%.2f%n", average.getAsDouble());
        }

        Optional<Sale> highestSale = sales.stream()
                .max((s1, s2) -> Double.compare(s1.getAmount(), s2.getAmount()));

        System.out.println("\nHighest sale:");
        highestSale.ifPresent(System.out::println);

        Optional<Sale> lowestSale = sales.stream()
                .min((s1, s2) -> Double.compare(s1.getAmount(), s2.getAmount()));

        System.out.println("\nLowest sale:");
        lowestSale.ifPresent(System.out::println);

        String selectedRegion = "North";

        System.out.println("\nSales from " + selectedRegion + " region:");
        sales.stream()
                .filter(sale -> sale.getRegion().equalsIgnoreCase(selectedRegion))
                .forEach(System.out::println);

        System.out.println("\nEmployee names:");
        sales.stream()
                .map(Sale::getEmployeeName)
                .distinct()
                .forEach(System.out::println);
    }
}
