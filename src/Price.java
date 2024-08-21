
public class Price {
    private String hours;
    private int[] costs;

    public Price(String hours, int[] costs) {

        this.hours = hours;
        this.costs = costs;
    }

    // Getter methods for hours and costs
    public String getHours() {
        return hours;
    }

    public int[] getCosts() {
        return costs;
    }
}