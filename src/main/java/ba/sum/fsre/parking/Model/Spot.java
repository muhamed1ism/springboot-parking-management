package ba.sum.fsre.parking.Model;

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
    @NotBlank(message = "Please enter car name.")
    @Size(max=30, message = "Car name can't be longer than 30 characters.")
    String carName;

    @Column(nullable = false, length = 15, unique = true)
    @NotBlank(message = "Please enter license plate.")
    @Size(max=15, message = "License plate can't be longer than 15 characters.")
    String licensePlate;

    @Column
    @DateTimeFormat(pattern = "dd.MM.yyyy HH:mm")
    LocalDateTime startTime;

    @Column
    @DateTimeFormat(pattern = "dd.MM.yyyy HH:mm")
    LocalDateTime endTime;

    @Column
    @NotNull(message = "Please enter duration.")
    @Min(value = 1, message = "Duration must be at least 1.")
    Long duration;

    @Column
    String durationUnit;

    @Column
    BigDecimal finalPrice;

    @ManyToOne
    @JoinColumn(name = "parking_id")
    private Parking parking;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    public Spot() {

    }

    public Spot(Long id, String carName, String licensePlate, LocalDateTime startTime, LocalDateTime endTime, Long duration, String durationUnit, BigDecimal finalPrice, Parking parking, User user) {
        this.id = id;
        this.carName = carName;
        this.licensePlate = licensePlate;
        this.startTime = startTime;
        this.endTime = endTime;
        this.duration = duration;
        this.durationUnit = durationUnit;
        this.finalPrice = finalPrice;
        this.parking = parking;
        this.user = user;
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

    public Parking getParking() {
        return parking;
    }

    public void setParking(Parking parking) {
        this.parking = parking;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
