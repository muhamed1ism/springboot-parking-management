package ba.sum.fsre.parking.repositories;

import ba.sum.fsre.parking.model.Parking;
import ba.sum.fsre.parking.model.Spot;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SpotRepository extends JpaRepository<Spot, Long> {
    List<Spot> findByParking(Parking parking);

    Spot findByLicensePlate(String licensePlate);
}
