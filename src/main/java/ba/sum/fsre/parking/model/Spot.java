package ba.sum.fsre.parking.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name="spot")
public class Spot {
    @Id
    @GeneratedValue
    Long id;

    @Column(nullable = false, length = 30)
    @NotBlank(message = "Molimo unesite naziv parking mjesta.")
    @Size(max=30, message = "Naziv parking mjesta ne može biti duži od 30 karaktera.")
    String carName;

    @Column(nullable = false, length = 15, unique = true)
    @NotBlank(message = "Molimo unesite registarsku oznaku vozila.")
    @Size(max=15, message = "Registracijska oznaka ne može biti duža od 15 karaktera.")
    String licensePlate;

    @Column
    @DateTimeFormat(pattern = "dd.MM.yyyy HH:mm")
    LocalDateTime startTime;

    @Column
    @DateTimeFormat(pattern = "dd.MM.yyyy HH:mm")
    LocalDateTime endTime;

    @Column
    @NotNull(message = "Molimo unesite trajanje.")
    @Min(value = 1, message = "Vrijeme trajanja mora biti 1 ili više.")
    Long duration;

    @Column
    String durationUnit;

    @Column
    BigDecimal finalPrice;

    @ManyToOne
    @JoinColumn(name = "parking_id")
    private Parking parking;

    public Spot() {

    }

    public Spot(Long id, String carName, String licensePlate, LocalDateTime startTime, LocalDateTime endTime, Long duration, String durationUnit, BigDecimal finalPrice, Parking parking) {
        this.id = id;
        this.carName = carName;
        this.licensePlate = licensePlate;
        this.startTime = startTime;
        this.endTime = endTime;
        this.duration = duration;
        this.durationUnit = durationUnit;
        this.finalPrice = finalPrice;
        this.parking = parking;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCarName() {
        return carName;
    }

    public void setCarName(String carName) {
        this.carName = carName;
    }

    public String getLicensePlate() {
        return licensePlate;
    }

    public void setLicensePlate(String licensePlate) {
        this.licensePlate = licensePlate;
    }

    public Parking getParking() {
        return parking;
    }

    public void setParking(Parking parking) {
        this.parking = parking;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }

    public Long getDuration() {
        return duration;
    }

    public void setDuration(Long duration) {
        this.duration = duration;
    }

    public String getDurationUnit() {
        return durationUnit;
    }

    public void setDurationUnit(String durationUnit) {
        this.durationUnit = durationUnit;
    }

    public BigDecimal getFinalPrice() {
        return finalPrice;
    }

    public void setFinalPrice(BigDecimal finalPrice) {
        this.finalPrice = finalPrice;
    }
}
