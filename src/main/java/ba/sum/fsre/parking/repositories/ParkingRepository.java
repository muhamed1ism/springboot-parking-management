package ba.sum.fsre.parking.repositories;

import ba.sum.fsre.parking.model.Parking;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ParkingRepository extends
        JpaRepository<Parking, Long> {
}
