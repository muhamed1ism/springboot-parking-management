package ba.sum.fsre.parking.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="parking_list")
public class Parking {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(nullable = false, length = 50)
    @NotBlank(message = "Molimo unesite naziv parkinga.")
    String parkingName;

    @Column(nullable = false, length = 50)
    @NotBlank(message = "Molimo unesite adresu parkinga.")
    String parkingAddress;

    @Column(nullable = false, length = 5)
    @NotNull(message = "Molimo unesite broj parking mjesta.")
    Long totalSpots;

    @Column
    Long availableSpots;

    @OneToMany(mappedBy = "parking", cascade = CascadeType.ALL)
    private List<Spot> spots = new ArrayList<>();

    public Parking() {

    }

    public Parking(Long id, String parkingName, String parkingAddress, Long totalSpots, Long availableSpots, List<Spot> spots) {
        this.id = id;
        this.parkingName = parkingName;
        this.parkingAddress = parkingAddress;
        this.totalSpots = totalSpots;
        this.availableSpots = availableSpots;
        this.spots = spots;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public Long getTotalSpots() {
        return totalSpots;
    }

    public void setTotalSpots(Long totalSpots) {
        this.totalSpots = totalSpots;
    }

    public Long getAvailableSpots() {
        return availableSpots;
    }

    public void setAvailableSpots(Long availableSpots) {
        this.availableSpots = availableSpots;
    }

    public List<Spot> getSpots() {
        return spots;
    }

    public void setSpots(List<Spot> spots) {
        this.spots = spots;
    }
}