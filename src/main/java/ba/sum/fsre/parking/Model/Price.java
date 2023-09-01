package ba.sum.fsre.parking.Model;

import jakarta.persistence.Column;
import jakarta.persistence.Id;

import java.math.BigDecimal;

public class Price {
    @Id
    private static final Long id = 1L;

    @Column
    private static BigDecimal priceForHour = BigDecimal.valueOf(1.0);

    @Column
    private static BigDecimal pricePerHour = BigDecimal.valueOf(0.5);

    @Column
    private static BigDecimal pricePerDay = BigDecimal.valueOf(10.0);

    @Column
    private static BigDecimal pricePerMonth = BigDecimal.valueOf(50.0);

    public Price() {
    }

    public static BigDecimal getPriceForHour() {
        return priceForHour;
    }

    public static void setPriceForHour(BigDecimal priceForHour) {
        Price.priceForHour = priceForHour;
    }

    public static BigDecimal getPricePerHour() {
        return pricePerHour;
    }

    public static void setPricePerHour(BigDecimal pricePerHour) {
        Price.pricePerHour = pricePerHour;
    }

    public static BigDecimal getPricePerDay() {
        return pricePerDay;
    }

    public static void setPricePerDay(BigDecimal pricePerDay) {
        Price.pricePerDay = pricePerDay;
    }

    public static BigDecimal getPricePerMonth() {
        return pricePerMonth;
    }

    public static void setPricePerMonth(BigDecimal pricePerMonth) {
        Price.pricePerMonth = pricePerMonth;
    }
}
