import java.util.Scanner;

public class UtilitiesPricingProgram {

    private static int[] manualSource = new int[24];
    private static int[] csvSource = manualSource;
    private static String dataSourceChoice = "CSV";
    private static int[] dataSet = csvSource;

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        OUTER: while (true) {
            displayMenu();
            int choice = menuSelection(sc); // row 57
            if (choice == -1) {
                System.out.println("Programmet avslutas.");
                break; // Exit the loop
            }
            switch (choice) {
                case 1:
                    manualInput(sc); // row 73
                    break;
                case 2:
                    minMaxAverage(sc); // row 112
                    break;
                case 5:
                    dataSourceSelector(); // row 62
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

    public static void dataSourceSelector() {
        if (dataSourceChoice.equals("CSV")) {
            dataSourceChoice = "Manuell inmatning";
            dataSet = manualSource;
        } else {
            dataSourceChoice = "CSV";
            dataSet = csvSource;
        }
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
}