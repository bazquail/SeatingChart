import java.util.ArrayList;

public class SeatingChart {
    private double columns;
    private double rows;
    private final ArrayList<Seat> seats = new ArrayList<>();
    private ArrayList<Seat> reservedSeats = new ArrayList<>();
    public SeatingChart(int c, int r) {
        this.columns = c;
        this.rows = r;

        for (int row = 1; row <= r; row++) {
            for (int col = 1; col <= c; col++) {
                seats.add(new Seat(row,col)); //creates a new seat object for every row and column combination upon initialization
            }
        }
    }
    public String setReservedSeat(String seatRes) {
        String result = "";
        for (int i = 0; i < rows*columns; i++) {
            if (seats.get(i).getName().equals(seatRes)) { //checks every seat for a match
                if (!seats.get(i).isReserved()) { //if not reserved, reserve it and add to reserved seats array
                    seats.get(i).setReserved();
                    reservedSeats.add(seats.get(i));
                    result = "Seat " + seatRes + " is now reserved.";
                } else {
                    result = "Seat " + seatRes + " is not available! Select another seat.";
                }
                break; //if it's a match, no need to keep checking. Break out of loop
            }
        }
        return result;
    }
    public String setReservedSeat(int numSeatsToRes) {
        ArrayList<Seat> tempReservedSeats = new ArrayList<>(); //tracks current attempt at seat combination
        ArrayList<Seat> bestSeats = new ArrayList<>(); //tracks current best combination of seats
        int currentRow = 1;
        double idealDistance = 1; //the ideal average manhattan distance for a set of seats
        double bestDistance = 0.0; //tracks the current closest distance to ideal distance
        String result;

        //loop variable tracks how many seats have been evaluated and establishes position of seat currently being checked
        for (int numOfCurrentSeat = 0; numOfCurrentSeat < rows*columns; numOfCurrentSeat++) {
            if (numOfCurrentSeat >= columns*currentRow) {
                currentRow++;
            }
            //check if the first seat is free and there is theoretically room in the row for the rest of the seats
            if (!seats.get(numOfCurrentSeat).isReserved() && (numOfCurrentSeat+numSeatsToRes <= columns*currentRow)) {
                tempReservedSeats.add(seats.get(numOfCurrentSeat)); //begin to build the array
                for (int i = 1; i < numSeatsToRes; i++) {
                    if (!seats.get(numOfCurrentSeat+i).isReserved()) { //check res status of subsequent seats and add if free
                        tempReservedSeats.add(seats.get(numOfCurrentSeat+i));
                    } else {
                        numOfCurrentSeat = numOfCurrentSeat+i; //set to current seat, no need to check any previous seats again when loop restarts
                        tempReservedSeats.clear();
                        break;
                    }
                }
            }
            if (tempReservedSeats.size() == numSeatsToRes) { //if a successful attempt, these will match
                if (bestSeats.isEmpty()) { //defaults to best seat combo if none is established
                    bestSeats = (ArrayList<Seat>) tempReservedSeats.clone();
                    bestDistance = manhattanDistance(bestSeats, numSeatsToRes);
                } else { //if there is a current best seat combo, compare manhattan distances
                    double currentDistance = manhattanDistance(tempReservedSeats, numSeatsToRes);

                    double currentDifference = Math.abs((currentDistance - idealDistance));
                    double bestDifference = Math.abs(bestDistance - idealDistance);

                    if (currentDifference < bestDifference) {
                        bestDistance = currentDistance;
                        bestSeats = (ArrayList<Seat>) tempReservedSeats.clone();
                    }
                }
                tempReservedSeats.clear();
            }
        }
        if (!bestSeats.isEmpty()) { //check if one viable set is found to then update those seats' res status
            for (Seat seat : seats) {
                for (Seat bestSeat : bestSeats) {
                    if (seat.equals(bestSeat)) {
                        seat.setReserved();
                    }
                }
            }
            if (numSeatsToRes == 1) {
                result = "Seat " + bestSeats.get(0).getName() + " is now reserved.";
            } else {
                result = "Seats " + bestSeats.get(0).getName() + " - " + bestSeats.get(numSeatsToRes - 1).getName() + " are now reserved.";
            }
            reservedSeats.addAll(bestSeats);
            System.out.println(reservedSeats);
        } else { //inform user if request cannot be accommodated
            result = "Insufficient space for request. Try another number.";
        }
        return result;
    }
    public double manhattanDistance(ArrayList<Seat> seatSet,int numSeatsToRes) {
        double distances = 0.0;
        for (Seat seat : seatSet) {
            double columnDistance = Math.abs(seat.getColumn() - ((columns+1)/2)); //absolute horizontal distance from middle of columns
            double rowDistance = seat.getRow(); //vertical distance from first row
            double hypotenuse = Math.sqrt(columnDistance*columnDistance + rowDistance*rowDistance); //hypotenuse of triangle created is seat's manhattan distance
            distances+=hypotenuse;
        }
        return distances/numSeatsToRes; //average manhattan distance for the given group of seats
    }
    public int getReservedSeatCount() {
        return ((int)rows*(int)columns) - reservedSeats.size();
    }
}
