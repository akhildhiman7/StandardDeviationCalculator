package concordia;

/**
 * Utility class for statistics calculations.
 * Version: 1.0.0
 */
public class StatisticsUtil {

  private StatisticsUtil() {
    // Prevent instantiation
  }

  /**
   * Calculates the mean of a double array.
   */
  public static double mean(double[] numbers) {
    double sum = 0;
    for (double n : numbers) {
      sum += n;
    }
    return sum / numbers.length;
  }

  /**
   * Calculates the population standard deviation of a double array.
   */
  public static double stdDev(double[] numbers) {
    double mean = mean(numbers);
    double squaredDiffSum = 0;
    for (double n : numbers) {
      squaredDiffSum += (n - mean) * (n - mean);
    }
    return Math.sqrt(squaredDiffSum / numbers.length);
  }

  /**
   * Parses a comma-separated string of numbers into a double array.
   *
   * @throws NumberFormatException if any value is not a valid double.
   */
  public static double[] parseNumbers(String input) {
    String[] tokens = input.split(",");
    double[] numbers = new double[tokens.length];
    for (int i = 0; i < tokens.length; i++) {
      numbers[i] = Double.parseDouble(tokens[i].trim());
    }
    return numbers;
  }
}
