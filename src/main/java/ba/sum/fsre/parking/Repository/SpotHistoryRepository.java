package ba.sum.fsre.parking.Repository;

import ba.sum.fsre.parking.Model.SpotHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SpotHistoryRepository extends JpaRepository<SpotHistory, Long> {
    
    SpotHistory findByLicensePlate(String licensePlate);

    List<SpotHistory> findByParkingId(Long parkingId);
}
