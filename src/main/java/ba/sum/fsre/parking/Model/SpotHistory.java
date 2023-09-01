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
@Table(name="spot_history")
public class SpotHistory {
    @Id
    @GeneratedValue
    Long id;

    @Column(nullable = false, length = 30)
    @NotBlank(message = "Molimo unesite naziv parking mjesta.")
    @Size(max = 30, message = "Naziv parking mjesta ne može biti duži od 30 karaktera.")
    String carName;

    @Column(nullable = false, length = 15, unique = true)
    @NotBlank(message = "Molimo unesite registarsku oznaku vozila.")
    @Size(max = 15, message = "Registracijska oznaka ne može biti duža od 15 karaktera.")
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

    @Column(name = "parking_id")
    Long parkingId;

    @Column(name = "parking_name")
    String parkingName;

    @Column(name = "parking_address")
    String parkingAddress;

    public SpotHistory() {

    }

    public SpotHistory(Long id, String carName, String licensePlate, LocalDateTime startTime, LocalDateTime endTime, Long duration, String durationUnit, BigDecimal finalPrice, Long parkingId, String parkingName, String parkingAddress) {
        this.id = id;
        this.carName = carName;
        this.licensePlate = licensePlate;
        this.startTime = startTime;
        this.endTime = endTime;
        this.duration = duration;
        this.durationUnit = durationUnit;
        this.finalPrice = finalPrice;
        this.parkingId = parkingId;
        this.parkingName = parkingName;
        this.parkingAddress = parkingAddress;
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

    public Long getParkingId() {
        return parkingId;
    }

    public void setParkingId(Long parkingId) {
        this.parkingId = parkingId;
    }

    public String getParkingName() {
        return parkingName;
    }

    public void setParkingName(String parkingName) {
        this.parkingName = parkingName;
    }

    public String getParkingAddress() {
        return parkingAddress;
    }

    public void setParkingAddress(String parkingAddress) {
        this.parkingAddress = parkingAddress;
    }
}
