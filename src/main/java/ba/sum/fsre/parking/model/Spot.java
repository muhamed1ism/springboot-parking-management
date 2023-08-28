package ba.sum.fsre.parking.model;

import jakarta.persistence.*;

@Entity
@Table(name="spot")
public class Spot {
    @Id
    @GeneratedValue
    Long id;

    @Column(nullable = false, length = 50)
    String spotName;

    @Column(columnDefinition = "boolean default true")
    Boolean isAvailable;

    @ManyToOne
    @JoinColumn(name = "parking_id")
    private Parking parking;

    public Spot() {

    }

    public Spot(Long id, String spotName, Boolean isAvailable, Parking parking) {
        this.id = id;
        this.spotName = spotName;
        this.isAvailable = isAvailable;
        this.parking = parking;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSpotName() {
        return spotName;
    }

    public void setSpotName(String spotName) {
        this.spotName = spotName;
    }

    public Boolean getAvailable() {
        return isAvailable;
    }

    public void setAvailable(Boolean available) {
        isAvailable = available;
    }

    public Parking getParking() {
        return parking;
    }

    public void setParking(Parking parking) {
        this.parking = parking;
    }
}
