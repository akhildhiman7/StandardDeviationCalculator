import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;

/**
 * StandardDeviationGui is a graphical user interface for calculating
 * the standard deviation of a set of numbers provided by the user.
 */
public class StandardDeviationGui extends JFrame {

  private JTextField inputField;
  private JTextArea resultArea;
  private JLabel formulaLabel;

  /**
   * Constructor to set up the GUI components and layout.
   */
  public StandardDeviationGui() {
    setTitle("Standard Deviation Calculator");
    setSize(600, 400);
    setDefaultCloseOperation(EXIT_ON_CLOSE);
    setLayout(new BorderLayout(10, 10));

    formulaLabel = new JLabel("<html><body style='text-align:center;'>"
            + "<h3>Standard Deviation Formula</h3>"
            + "<p style='font-size:14px;'>σ = √(1/N × Σ(xᵢ - μ)²)</p>"
            + "</body></html>", SwingConstants.CENTER);
    formulaLabel.setFont(new Font("Serif", Font.PLAIN, 16));
    formulaLabel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

    inputField = new JTextField();

    JButton calculateButton = new JButton("Calculate");
    JButton fileButton = new JButton("Load from File");

    calculateButton.addActionListener(new CalculateListener());
    fileButton.addActionListener(new FileLoadListener());

    resultArea = new JTextArea();
    resultArea.setEditable(false);
    resultArea.setFont(new Font("Monospaced", Font.PLAIN, 14));
    resultArea.setBorder(BorderFactory.createTitledBorder("Result"));

    JLabel promptLabel = new JLabel("Enter numbers separated by commas:");
    JPanel inputPanel = new JPanel(new BorderLayout(5, 5));
    inputPanel.add(promptLabel, BorderLayout.NORTH);
    inputPanel.add(inputField, BorderLayout.CENTER);

    JPanel buttonPanel = new JPanel();
    buttonPanel.add(calculateButton);
    buttonPanel.add(fileButton);

    JPanel centerPanel = new JPanel(new BorderLayout(5, 5));
    centerPanel.add(inputPanel, BorderLayout.NORTH);
    centerPanel.add(buttonPanel, BorderLayout.SOUTH);

    add(formulaLabel, BorderLayout.NORTH);
    add(centerPanel, BorderLayout.CENTER);
    JScrollPane scrollPane = new JScrollPane(resultArea);
    scrollPane.setPreferredSize(new Dimension(580, 120));
    add(scrollPane, BorderLayout.SOUTH);
  }

  private class CalculateListener implements ActionListener {
    public void actionPerformed(ActionEvent e) {
      processInput(inputField.getText());
    }
  }

  private class FileLoadListener implements ActionListener {
    public void actionPerformed(ActionEvent e) {
      JFileChooser fileChooser = new JFileChooser();
      int result = fileChooser.showOpenDialog(StandardDeviationGui.this);
      if (result == JFileChooser.APPROVE_OPTION) {
        File file = fileChooser.getSelectedFile();
        try (Scanner scanner = new Scanner(file)) {
          StringBuilder inputBuilder = new StringBuilder();
          while (scanner.hasNextLine()) {
            inputBuilder.append(scanner.nextLine()).append(",");
          }
          inputField.setText(inputBuilder.toString().replaceAll(",+$", ""));
        } catch (FileNotFoundException ex) {
          resultArea.setText("Error: File not found.");
        } catch (Exception ex) {
          resultArea.setText("Error reading file: " + ex.getMessage());
        }
      }
    }
  }

  private void processInput(String input) {
    try {
      String[] tokens = input.split(",");
      if (tokens.length == 0 || input.trim().isEmpty()) {
        throw new IllegalArgumentException("No input data provided.");
      }

      double[] numbers = new double[tokens.length];
      double sum = 0;

      for (int i = 0; i < tokens.length; i++) {
        numbers[i] = Double.parseDouble(tokens[i].trim());
        sum += numbers[i];
      }

      double mean = sum / numbers.length;
      double squaredDiffSum = 0;

      for (double num : numbers) {
        squaredDiffSum += (num - mean) * (num - mean);
      }

      double stdDev = Math.sqrt(squaredDiffSum / numbers.length);
      resultArea.setText("Mean: " + String.format("%.4f", mean)
              + "\nStandard Deviation (σ): " + String.format("%.4f", stdDev));
    } catch (NumberFormatException ex) {
      resultArea.setText("Error: Invalid number format. Please check your input.");
    } catch (IllegalArgumentException ex) {
      resultArea.setText("Error: " + ex.getMessage());
    } catch (Exception ex) {
      resultArea.setText("Unexpected error: " + ex.getMessage());
    }
  }

  /**
   * Main method to run the GUI application.
   */
  public static void main(String[] args) {
    SwingUtilities.invokeLater(() -> {
      StandardDeviationGui app = new StandardDeviationGui();
      app.setVisible(true);
    });
  }
}
