public class Seat {
    private double row;
    private double column;
    private String name;
    private boolean reserved;
    public Seat(int r, int c) {
        this.row = r;
        this.column = c;
        this.reserved = false;
        this.name = "R" + r + "C" + c;
    }
    public double getRow() {
        return this.row;
    }
    public double getColumn() {
        return this.column;
    }
    public String getName() {
        return this.name;
    }
    public boolean isReserved() {
        return reserved;
    }
    public void setReserved() {
        this.reserved = true;
    }
    public String toString() {
        return this.name;
    }
}
