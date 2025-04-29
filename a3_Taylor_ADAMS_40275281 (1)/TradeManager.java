import java.io.*;
import java.util.*;

public class TradeManager {
    public static void main(String[] args) {
        // Part 1: ArrayList & File I/O
        ArrayList<Product> products = new ArrayList<>();

        // Read TradeData.txt
        try (BufferedReader reader = new BufferedReader(new FileReader("TradeData.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 4) {
                    String productName = parts[0];
                    String country = parts[1];
                    String category = parts[2];
                    double price = Double.parseDouble(parts[3]);

                    products.add(new Product(productName, country, category, price));
                }
            }
        } catch (IOException e) {
            System.out.println("Error reading TradeData.txt: " + e.getMessage());
            return;
        }

        // Apply tariffs
        for (Product product : products) {
            applyTariff(product);
        }

        // Sort products by name
        Collections.sort(products);

        // Write to UpdatedTradeData.txt
        try (PrintWriter writer = new PrintWriter(new FileWriter("UpdatedTradeData.txt"))) {
            for (Product product : products) {
                writer.println(product.toString());
            }
        } catch (IOException e) {
            System.out.println("Error writing to UpdatedTradeData.txt: " + e.getMessage());
        }

        // Part 2: Linked Lists

        // (a) Create at least two empty lists from the TariffList class
        TariffList tariffList1 = new TariffList();
        TariffList tariffList2 = new TariffList();

        // File paths
        String tariffsFilePath = "Tariffs.txt";
        String tradeRequestsFilePath = "TradeRequests.txt";

        // Check if files exist, and if they are text files
        // assingment description asks to account foir many types of input files
        if (!isTextFile(tariffsFilePath) || !isTextFile(tradeRequestsFilePath)) {
            System.out.println("Oops! One or both files are not valid text files.");
            return;
        }

        // (b) Open the Tariffs.txt file and read its contents line by line
        try (BufferedReader reader = new BufferedReader(new FileReader(tariffsFilePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("\\s+");
                if (parts.length >= 4) {
                    String destinationCountry = parts[0];
                    String originCountry = parts[1];
                    String productCategory = parts[2];
                    double minimumTariff = Double.parseDouble(parts[3]);

                    // Create Tariff object with minimumTariff
                    Tariff tariff = new Tariff(destinationCountry, originCountry, productCategory);
                    // Set the minimum tariff using Show inner class
                    Tariff.Show show = tariff.new Show(destinationCountry, originCountry, productCategory,
                            minimumTariff);

                    // Check for duplicates before adding
                    if (!tariffList1.contains(originCountry, destinationCountry, productCategory)) {
                        tariffList1.addToStart(tariff);
                    }
                }
            }
        } catch (IOException e) {
            System.out.println("Error reading Tariffs.txt: " + e.getMessage());
            return;
        } catch (NumberFormatException e) {
            System.out.println("Error parsing minimum tariff value: " + e.getMessage());
            return;
        }

        // (c) Open TradeRequests.txt and create an ArrayList from the contents
        ArrayList<String[]> tradeRequests = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(tradeRequestsFilePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("\\s+");
                if (parts.length >= 6) {
                    tradeRequests.add(parts);
                }
            }
        } catch (IOException e) {
            System.out.println("Error reading TradeRequests.txt: " + e.getMessage());
            return;
        }

        // Process each trade request
        for (String[] request : tradeRequests) {
            String requestId = request[0];
            String originCountry = request[1];
            String destCountry = request[2];
            String productCategory = request[3];
            double tradeValue = Double.parseDouble(request[4]);
            double proposedTariff = Double.parseDouble(request[5]);

            // Find the tariff rule
            TariffList.TariffNode node = tariffList1.find(originCountry, destCountry, productCategory);

            if (node != null) {
                Tariff tariff = node.getTariff();
                double minimumTariff = tariff.getMinimumTariff();

                // Use the EvaluateTrade method from TariffPolicy interface
                String outcome = tariffList1.EvaluateTrade(proposedTariff, minimumTariff);

                // Display results according to the sample output format
                if (outcome.equals("Accepted")) {
                    System.out.println(requestId + " - Accepted.");
                    System.out.println("Proposed tariff meets or exceeds the minimum requirement.");
                    System.out.println();
                } else if (outcome.equals("Conditionally Accepted")) {
                    // Within 20% of minimum
                    double surcharge = tradeValue * ((minimumTariff - proposedTariff) / 100);
                    System.out.println(requestId + " - Conditionally Accepted.");
                    System.out.println("Proposed tariff " + proposedTariff
                            + "% is within 20% of the required minimum tariff " + minimumTariff + "%.");
                    System.out.println("A surcharge of $" + (int) surcharge + " is applied.");
                    System.out.println();
                } else {
                    System.out.println(requestId + " - Rejected");
                    System.out.println("Proposed tariff " + proposedTariff
                            + "% is more than 20% below the required minimum tariff " + minimumTariff + "%.");
                    System.out.println();
                }
            } else {
                System.out.println(requestId + " - Error: No tariff rule found for this trade relation.");
                System.out.println();
            }
        }

        // (d) Prompt the user to enter a few Tariffs and search the list
        Scanner scanner = new Scanner(System.in);
        System.out.println("\nSearch for Tariffs:");

        for (int i = 0; i < 2; i++) {
            System.out.print("Enter origin country: ");
            String origin = scanner.nextLine();

            System.out.print("Enter destination country: ");
            String destination = scanner.nextLine();

            System.out.print("Enter product category: ");
            String category = scanner.nextLine();

            // Use the find method which already tracks iterations
            TariffList.TariffNode result = tariffList1.find(origin, destination, category);

            if (result != null) {
                // The find method already counts iterations
                System.out.println("Tariff found!");
                System.out.println("Minimum tariff: " + result.getTariff().getMinimumTariff() + "%");
            } else {
                System.out.println("Tariff not found.");
            }
        }

        scanner.close();
    }

    private static boolean isTextFile(String filePath) {
        return filePath != null && filePath.toLowerCase().endsWith(".txt");
    }

    private static void applyTariff(Product product) {
        String country = product.getCountry();
        String category = product.getCategory();
        double originalPrice = product.getPrice();
        double newPrice = originalPrice;

        // Apply tariffs based on country and category
        if (country.equals("China")) {
            newPrice = originalPrice * 1.25;
        } else if (country.equals("USA") && category.equals("Electronics")) {
            newPrice = originalPrice * 1.10;
        } else if (country.equals("Japan") && category.equals("Automobiles")) {
            newPrice = originalPrice * 1.15;
        } else if (country.equals("India") && category.equals("Agriculture")) {
            newPrice = originalPrice * 1.05;
        } else if (country.equals("South Korea") && category.equals("Electronics")) {
            newPrice = originalPrice * 1.08;
        } else if (country.equals("Saudi Arabia") && category.equals("Energy")) {
            newPrice = originalPrice * 1.12;
        } else if (country.equals("Germany") && category.equals("Manufacturing")) {
            newPrice = originalPrice * 1.06;
        } else if (country.equals("Bangladesh") && category.equals("Textile")) {
            newPrice = originalPrice * 1.04;
        } else if (country.equals("Brazil") && category.equals("Agriculture")) {
            newPrice = originalPrice * 1.09;
        }

        product.setPrice(newPrice);
    }
}