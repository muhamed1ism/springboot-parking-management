package ba.sum.fsre.parking.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name="parking_list")
public class ParkingList {
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
    Integer numberOfSlots;

    public ParkingList() {

    }

    public ParkingList(Long id, String parkingName, String parkingAddress, Integer numberOfSlots) {
        this.id = id;
        this.parkingName = parkingName;
        this.parkingAddress = parkingAddress;
        this.numberOfSlots = numberOfSlots;
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

    public Integer getNumberOfSlots() {
        return numberOfSlots;
    }

    public void setNumberOfSlots(Integer numberOfSlots) {
        this.numberOfSlots = numberOfSlots;
    }
}