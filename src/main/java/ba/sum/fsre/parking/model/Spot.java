package ba.sum.fsre.parking.model;

import jakarta.persistence.*;

@Entity
@Table(name="spot")
public class Spot {
    @Id
    @GeneratedValue
    Long id;

    @Column(nullable = false, length = 50)
    String carName;

    @Column(nullable = false, length = 30)
    String licensePlate;

    @ManyToOne
    @JoinColumn(name = "parking_id")
    private Parking parking;

    public Spot() {

    }

    public Spot(Long id, String spotName, Boolean isAvailable, Parking parking) {
        this.id = id;
        this.carName = spotName;
        this.licensePlate = licensePlate;
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
}
