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
    static JLabel columnsLabel = new JLabel("Number of Columns: ");
    static JLabel rowsLabel =  new JLabel("Number of Rows: ");
    static JTextField columnsField = new JTextField(5);
    static JTextField rowsField = new JTextField(5);
    static JLabel resStringLabel = new JLabel("Seat to Reserve: ");
    static JLabel resNumLabel = new JLabel("Number of Seats to Reserve:");
    static JTextField resStringField = new JTextField(5);
    static JTextField resNumField = new JTextField(5);
    static JTextArea resultArea = new JTextArea(6,30);

    public static void main(String[] args) {
        buildGui();
    }
    public static void buildGui() {
        frame = new JFrame("Seating Chart");
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        JPanel panel = new JPanel();

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

        JScrollPane scroller = new JScrollPane(resultArea);
        scroller.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        scroller.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        panel.add(scroller);

        frame.getContentPane().add(panel,BorderLayout.CENTER);
        frame.setVisible(true);
    }
    public static void setChartDimensions() {
        if (columnsField.getText().matches("[0-9]+") && rowsField.getText().matches("[0-9]+")) {
            columns = Integer.parseInt(columnsField.getText());
            rows = Integer.parseInt(rowsField.getText());
            if (columns == 0 || rows == 0) {
                resultArea.append("Rows and columns must be greater than 0!\n");
            } else {
                seatingChart = new SeatingChart(columns, rows);
                setVisibility(false);
            }
        } else {
            resultArea.append("Enter valid integers for both rows and columns!\n");
        }
    }
    public static void resSpecificSeats(String request) {
        if (request.matches("R[0-9]+C[0-9]+")) {
            String[] result = request.split("[A-Z]");
            int requestedRows = Integer.parseInt(result[1]);
            int requestedColumns = Integer.parseInt(result[2]);

            if (requestedRows > 0 && requestedColumns > 0 && requestedRows <= rows && requestedColumns <= columns) {
                resultArea.append(seatingChart.setReservedSeat(resStringField.getText())+"\n");
            }
            if (requestedRows > rows || requestedRows <= 0) {
                resultArea.append("Row out of bounds in request!\n");
            }
            if (requestedColumns > columns || requestedColumns <= 0) {
                resultArea.append("Column out of bounds in request!\n");
            }
        } else {
            resultArea.append("Invalid 'specific seat' request. Request in form of 'R#C#'\n");
        }
        resStringField.setText("");
    }
    public static void resNumSeats() {
        if (resNumField.getText().matches("[0-9]+")) {
            resultArea.append(seatingChart.setReservedSeat(Integer.parseInt(resNumField.getText()))+"\n");
        } else {
            resultArea.append("Invalid 'number of seats' request. Request in form of '#'\n");
        }
        resultArea.append("Number of remaining seats: " + seatingChart.getReservedSeatCount()+ '\n');
        resNumField.setText("");
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
        resultArea.setVisible(!bool);

        if (bool) {
            frame.setSize(200,130);
        } else {
            frame.setSize(400, 220);
        }
    }
}