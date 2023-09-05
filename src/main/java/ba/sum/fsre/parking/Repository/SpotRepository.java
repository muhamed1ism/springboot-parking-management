package ba.sum.fsre.parking.Repository;

import ba.sum.fsre.parking.Model.Parking;
import ba.sum.fsre.parking.Model.Spot;
import ba.sum.fsre.parking.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface SpotRepository extends JpaRepository<Spot, Long> {
    List<Spot> findByParking(Parking parking);

    Spot findByLicensePlate(String licensePlate);

    List<Spot> findByUser(User user);
}
