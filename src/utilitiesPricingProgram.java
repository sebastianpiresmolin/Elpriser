import java.util.Scanner;

public class utilitiesPricingProgram {

    public static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        // Menu
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
    }

}
