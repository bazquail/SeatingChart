import javax.swing.*;
import java.awt.*;

public class Main {
    static SeatingChart seatingChart;
    static int columns;
    static int rows;
    static JFrame frame;
    static JButton createChartButton = new JButton("Set Chart Dimensions");
    static JButton resStringButton = new JButton("Reserve seats");
    static JButton resNumButton = new JButton("Reserve seats");
    static JLabel columnsLabel = new JLabel("Type number of columns");
    static JLabel rowsLabel =  new JLabel("Type number of rows");
    static JTextField columnsField = new JTextField(10);
    static JTextField rowsField = new JTextField(10);
    static JLabel resStringLabel = new JLabel("Type seat you want to reserve");
    static JLabel resNumLabel = new JLabel("Type number of seats to reserve");
    static JTextField resStringField = new JTextField(10);
    static JTextField resNumField = new JTextField(10);

    public static void main(String[] args) {
        buildGui();
    }
    public static void buildGui() {
        frame = new JFrame("Seating Chart");
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setSize(300,200);

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel,BoxLayout.Y_AXIS));

        createChartButton.addActionListener(l -> setChartDimensions());
        resStringButton.addActionListener(l -> resSpecificSeats(resStringField.getText().toUpperCase()));
        resNumButton.addActionListener(l -> resNumSeats());

        panel.add(columnsLabel);
        panel.add(columnsField);
        panel.add(rowsLabel);
        panel.add(rowsField);
        panel.add(createChartButton);
        setVisibility(true);

        panel.add(resStringLabel);
        panel.add(resStringField);
        panel.add(resStringButton);
        panel.add(resNumLabel);
        panel.add(resNumField);
        panel.add(resNumButton);

        frame.getContentPane().add(panel,BorderLayout.CENTER);
        frame.setVisible(true);
    }
    public static void setChartDimensions() {
        if (columnsField.getText().matches("[0-9]+") && rowsField.getText().matches("[0-9]+")) {
            columns = Integer.parseInt(columnsField.getText());
            rows = Integer.parseInt(rowsField.getText());
            if (columns == 0 || rows == 0) {
                System.out.println("Rows and columns must be greater than 0!");
            } else {
                seatingChart = new SeatingChart(columns, rows);
                setVisibility(false);
            }
        } else {
            System.out.println("Enter valid integers for both rows and columns!");
        }
    }
    public static void resSpecificSeats(String request) {
        if (request.matches("R[0-9]+C[0-9]+")) {
            String[] result = request.split("[A-Z]");
            int requestedRows = Integer.parseInt(result[1]);
            int requestedColumns = Integer.parseInt(result[2]);

            if (requestedRows > 0 && requestedColumns > 0 && requestedRows <= rows && requestedColumns <= columns) {
                seatingChart.setReservedSeat(resStringField.getText());
            }
            if (requestedRows > rows || requestedRows <= 0) {
                System.out.println("Row out of bounds in request!");
            }
            if (requestedColumns > columns || requestedColumns <= 0) {
                System.out.println("Column out of bounds in request!");
            }
        } else {
            System.out.println("Invalid 'specific seat' request. Request in form of 'R#C#'");
        }
    }
    public static void resNumSeats() {
        if (resNumField.getText().matches("[0-9]+")) {
            seatingChart.setReservedSeat(Integer.parseInt(resNumField.getText()));
        } else {
            System.out.println("Invalid 'number of seats' request. Request in form of '#'");
        }
        System.out.println("Num remaining seats: " + seatingChart.getReservedSeatCount());
    }
    public static void setVisibility(Boolean bool) {
        columnsField.setVisible(bool);
        columnsLabel.setVisible(bool);
        rowsField.setVisible(bool);
        rowsLabel.setVisible(bool);
        createChartButton.setVisible(bool);

        resNumField.setVisible(!bool);
        resNumLabel.setVisible(!bool);
        resNumButton.setVisible(!bool);
        resStringField.setVisible(!bool);
        resStringLabel.setVisible(!bool);
        resStringButton.setVisible(!bool);
    }
}