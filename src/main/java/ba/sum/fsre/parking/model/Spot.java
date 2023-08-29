package ba.sum.fsre.parking.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

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

    @Column(nullable = false, length = 15)
    @NotBlank(message = "Molimo unesite registarsku oznaku vozila.")
    @Size(max=15, message = "Registracijska oznaka ne može biti duža od 15 karaktera.")
    String licensePlate;

    @Column(nullable = false)
    @NotNull(message = "Molimo unesite početno vrijeme.")
    LocalDateTime startTime;

    @Column(nullable = false)
    @NotNull(message = "Molimo unesite krajnje vrijeme.")
    LocalDateTime endTime;

    @ManyToOne
    @JoinColumn(name = "parking_id")
    private Parking parking;

    public Spot() {

    }

    public Spot(Long id, String spotName, Boolean isAvailable, Parking parking, LocalDateTime startTime, LocalDateTime endTime) {
        this.id = id;
        this.carName = spotName;
        this.licensePlate = licensePlate;
        this.parking = parking;
        this.startTime = startTime;
        this.endTime = endTime;
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
}
