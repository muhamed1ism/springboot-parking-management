package ba.sum.fsre.parking.Repository;

import ba.sum.fsre.parking.Model.Parking;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ParkingRepository extends
        JpaRepository<Parking, Long> {
}
