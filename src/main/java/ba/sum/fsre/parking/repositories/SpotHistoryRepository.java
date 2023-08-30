package ba.sum.fsre.parking.repositories;

import ba.sum.fsre.parking.model.Parking;
import ba.sum.fsre.parking.model.SpotHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SpotHistoryRepository extends JpaRepository<SpotHistory, Long> {
    
    SpotHistory findByLicensePlate(String licensePlate);

    List<SpotHistory> findByParkingId(Long parkingId);
}
