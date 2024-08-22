import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class UtilitiesPricingProgram {

    private static int[] manualSource = new int[24];
    private static int[] csvSource;
    private static String dataSourceChoice = "CSV";
    private static int[] dataSet;

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        // Use an absolute path to the CSV file
        String csvFilePath = System.getProperty("user.dir") + File.separator + "elpriser.csv";
        csvSource = loadCsvData(csvFilePath);
        dataSet = csvSource; // Update dataSet after loading CSV data

        OUTER: while (true) {
            displayMenu();
            int choice = menuSelection(sc);
            if (choice == -1) {
                System.out.println("Programmet avslutas.");
                break; // Exit the program
            }
            switch (choice) {
                case 1:
                    manualInput(sc);
                    break;
                case 2:
                    minMaxAverage(sc);
                    break;
                case 3:
                    sortDataSet(sc);
                    break;
                case 5:
                    dataSourceSelector();
                    break;
                case 6:
                    printDataSet();
                    break;
                default:
                    break;
            }
        }
    }

    public static void displayMenu() {
        String csvIndicator = dataSourceChoice.equals("CSV") ? "[X]" : "[]";
        String manualIndicator = dataSourceChoice.equals("Manuell inmatning") ? "[X]" : "[]";

        System.out.println("====================================");
        System.out.println("Elpriskalkylatorn");
        System.out.println(
                "Om du ej anger egna inmatningsvärden så kommer programmet att använda standardvärden. (CSV-fil)");
        System.out.println(
                "Efter du matat in egna värden så måste du ändra vilken datakälla som skall användas (menyalternativ 5).");
        System.out.println("====================================");
        System.out.println("Var god välj ett alternativ:");
        System.out.println("1. Manuell inmatning av elpriser");
        System.out.println("2. Beräkna min, max och medelvärde av elpriser");
        System.out.println("3. Sortera elpriser");
        System.out.println("4. Beräkna bästa laddnigstid (4t)");
        System.out.println("5. Ändra datakälla | Nu används: CSV-fil " + csvIndicator + " | Manuell inmatning "
                + manualIndicator + " |");
        System.out.println("6. Skriv ut dataunderlag");
        System.out.println("e. Avsluta programmet");
        System.out.println("====================================");
    }

    public static int menuSelection(Scanner scanner) {
        System.out.print("Ange ditt val: ");
        String input = scanner.next();
        if (input.equalsIgnoreCase("e")) {
            return -1; // Return -1 if 'e' or 'E' is entered
        }
        return Integer.parseInt(input); // Parse the input as an integer
    }

    private static int[] loadCsvData(String filePath) {
        File file = new File(filePath);
        if (!file.exists()) {
            System.err.println("File not found: " + filePath);
            return new int[0];
        }

        List<Integer> prices = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(","); // i split the line by comma
                if (parts.length == 2) {
                    try {
                        int price = Integer.parseInt(parts[1]);
                        prices.add(price);
                    } catch (NumberFormatException e) {
                        System.err.println("Error parsing price: " + parts[1]);
                    }
                } else {
                    System.err.println("Invalid line format: " + line);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        int[] result = new int[prices.size()];
        for (int i = 0; i < prices.size(); i++) {
            result[i] = prices.get(i);
        }
        return result;
    }

    public static void printDataSet() {
        if (dataSet == null || dataSet.length == 0) {
            System.out.println("CSV data is not loaded or empty.");
            return;
        }
        System.out.println("Dataunderlag:");
        for (int i = 0; i < dataSet.length; i++) {
            System.out.println("Klockan " + String.format("%02d", i) + "-" + String.format("%02d", (i + 1) % 24) + ": "
                    + dataSet[i]);
        }
    }

    public static void dataSourceSelector() {
        if (dataSourceChoice.equals("CSV")) {
            dataSourceChoice = "Manuell inmatning";
            dataSet = manualSource;
        } else {
            dataSourceChoice = "CSV";
            dataSet = csvSource;
        }
        System.out.println("Dataunderlagg ändrades till: " + dataSourceChoice);
    }

    // RUN ON MENU CHOICE 1
    public static void manualInput(Scanner scanner) {
        System.out.println("====================================");
        System.out.println("Manuell inmatning av elpriser");
        System.out.println("Ange elpris för varje timme (00-23)");
        System.out.println("Ange priset i hela ören (1.50kr anges således som 150)");
        System.out.println("====================================");

        // this is needed to solve unwanted behavior of input.isEmpty() in the while
        // loop

        if (scanner.hasNextLine()) {
            scanner.nextLine();
        }

        for (int i = 0; i < 24; i++) {
            while (true) {
                System.out.printf("Ange elpris för kl %02d:00 - %02d:00: ", i, (i + 1) % 24);
                String input = scanner.nextLine().trim();
                if (input.isEmpty()) {
                    System.out.println("Inmatningen kan inte vara tom. Försök igen.");
                    continue;
                }
                try {
                    int price = Integer.parseInt(input);
                    if (price < 0) {
                        System.out.println("Kostnaden kan inte vara negativ. Försök igen.");
                    } else {
                        manualSource[i] = price;
                        break;
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Ogiltig inmatning. Ange ett heltal. Försök igen.");
                }
            }
        }
        System.out.println("Tack! Tryck på Enter för att återgå till menyn.");
        scanner.nextLine(); // Wait for the user to press Enter before returning to the menu
    }

    // RUN ON MENU CHOICE 2
    public static void minMaxAverage(Scanner scanner) {
        int min = Integer.MAX_VALUE;
        int max = Integer.MIN_VALUE;
        int sum = 0;

        for (int price : dataSet) {
            if (price < min) {
                min = price;
            }
            if (price > max) {
                max = price;
            }
            sum += price;
        }

        double average = (double) sum / dataSet.length;

        System.out.println("====================================");
        System.out.println("Beräkning av min, max och medelvärde av elpriser");
        System.out.println("Minsta elpris: " + min);
        System.out.println("Högsta elpris: " + max);
        System.out.println("Medelvärde: " + average);
        System.out.println("====================================");
        System.out.println("Tryck på Enter för att återgå till menyn.");
        scanner.nextLine();
        scanner.nextLine(); // Wait for the user to press Enter before returning to the menu
    }

    public static void sortDataSet(Scanner scanner) {
        if (dataSet == null || dataSet.length == 0) {
            System.out.println("Data set is not loaded or empty.");
            return;
        }
        int[] sortedDataSet = Arrays.copyOf(dataSet, dataSet.length);
        Arrays.sort(sortedDataSet);
        System.out.println("====================================");
        System.out.println("Elpriser i stigande ordning:");
        System.out.println("====================================");
        for (int i = 0; i < sortedDataSet.length; i++) {
            System.out.println("Klockan " + String.format("%02d", i) + "-" + String.format("%02d", (i + 1) % 24) + ": "
                    + sortedDataSet[i]);
        }
        System.out.println("====================================");
        System.out.println("Tryck på Enter för att återgå till menyn.");
        scanner.nextLine();
        scanner.nextLine(); // Wait for the user to press Enter before returning to the menu
    }
}