package concordia;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

class StatisticsUtilTest {

  @Test
  void testMean_typical() {
    double[] nums = {1, 2, 3, 4, 5};
    assertEquals(3.0, StatisticsUtil.mean(nums), 1e-9);
  }

  @Test
  void testStdDev_typical() {
    double[] nums = {2, 4, 4, 4, 5, 5, 7, 9};
    assertEquals(2.0, StatisticsUtil.stdDev(nums), 1e-9);
  }

  @Test
  void testParseNumbers_valid() {
    double[] nums = StatisticsUtil.parseNumbers("1, 2, 3.5, 4");
    assertArrayEquals(new double[]{1, 2, 3.5, 4}, nums, 1e-9);
  }

  @Test
  void testParseNumbers_invalid() {
    assertThrows(NumberFormatException.class,
        () -> StatisticsUtil.parseNumbers("1, x, 3"));
  }

  @Test
  void testMean_singleElement() {
    double[] nums = {42};
    assertEquals(42.0, StatisticsUtil.mean(nums), 1e-9);
  }

  @Test
  void testStdDev_singleElement() {
    double[] nums = {42};
    assertEquals(0.0, StatisticsUtil.stdDev(nums), 1e-9);
  }

  @Test
  void testStdDev_identicalElements() {
    double[] nums = {5, 5, 5, 5, 5};
    assertEquals(0.0, StatisticsUtil.stdDev(nums), 1e-9);
  }
}
