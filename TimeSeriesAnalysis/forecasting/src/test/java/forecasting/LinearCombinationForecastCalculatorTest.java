package forecasting;

import forecasting.model.Chromosome;
import forecasting.model.SlidingTimeWindow;
import org.jfree.data.time.Day;
import org.jfree.data.time.TimeSeries;
import org.junit.Test;

import static org.junit.Assert.*;

public class LinearCombinationForecastCalculatorTest {
    @Test
    public void testCalculateForecast() throws Exception {

        TimeSeries timeSeries = new TimeSeries("My Series");
        timeSeries.add(new Day(1, 1, 2013), 20);
        timeSeries.add(new Day(2, 1, 2013), 40);
        timeSeries.add(new Day(3, 1, 2013), 30);
        timeSeries.add(new Day(4, 1, 2013), 20);
        timeSeries.add(new Day(5, 1, 2013), 30);

        SlidingTimeWindow slidingTimeWindow = new SlidingTimeWindow(new int[]{1, 2, 3});

        AbstractForecastCalculator calculator = new LinearCombinationForecastCalculator();

        Chromosome chromosome = new Chromosome(new double[]{-0.5, 1, 0.5, 0.2});

        assertEquals(53.5, calculator.calculateForecast(timeSeries,
                slidingTimeWindow,
                chromosome,
                3),
                0.00001);

        assertEquals(42.5, calculator.calculateForecast(timeSeries,
                slidingTimeWindow,
                chromosome,
                4),
                0.00001);

        assertNull(calculator.calculateForecast(timeSeries,
                slidingTimeWindow,
                chromosome,
                2));
    }
}
