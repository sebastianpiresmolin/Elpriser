import java.util.Scanner;

public class UtilitiesPricingProgram {

    private static int[] prices = new int[24];

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        while (true) {
            displayMenu();
            int choice = menuSelection(sc);
            if (choice == 1) {
                manualInput(sc);
            } else if (choice == 2) {
                System.out.println("Inmatade elpriser:");
                for (int i = 0; i < prices.length; i++) {
                    System.out.printf("Kl %02d:00 - %02d:00: %d%n", i, (i + 1) % 24, prices[i]);
                }
            } else if (choice == 'e') {
                break;
            }
        }
    }

    public static void displayMenu() {
        System.out.println("====================================");
        System.out.println("Elpriskalkylatorn");
        System.out.println(
                "Om du ej anger egna inmatningsvärden så kommer programmet att använda standardvärden. (CSV - filen för VG)");
        System.out.println("====================================");
        System.out.println("Var god välj ett alternativ:");
        System.out.println("1. Manuell inmatning av elpriser");
        System.out.println("2. Beräkna min, max och medelvärde av elpriser");
        System.out.println("3. Sortera elpriser");
        System.out.println("4. Beräkna bästa laddnigstid (4t)");
        System.out.println("e. Avsluta programmet");
        System.out.println("====================================");
    }

    public static int menuSelection(Scanner scanner) {
        System.out.print("Ange ditt val: ");
        return scanner.nextInt();
    }

    public static void manualInput(Scanner scanner) {
        System.out.println("====================================");
        System.out.println("Manuell inmatning av elpriser");
        System.out.println("Ange elpris för varje timme (00-23)");
        System.out.println("Ange priset i hela ören (1.50kr anges således som 150)");
        System.out.println("====================================");

        //this is needed to solve unwanted behavior of input.isEmpty() in the while loop
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
                        prices[i] = price;
                        break;
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Ogiltig inmatning. Ange ett heltal. Försök igen.");
                }
            }
        }

        System.out.println("Inmatade elpriser:");
        for (int i = 0; i < prices.length; i++) {
            System.out.printf("Kl %02d:00 - %02d:00: %d%n", i, (i + 1) % 24, prices[i]);
        }

        System.out.println("Tack! Tryck på Enter för att återgå till menyn.");
        scanner.nextLine(); // Wait for the user to press Enter before returning to the menu
    }
}