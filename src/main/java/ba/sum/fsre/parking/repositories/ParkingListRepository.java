package ba.sum.fsre.parking.repositories;

import ba.sum.fsre.parking.model.ParkingList;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ParkingListRepository extends
        JpaRepository<ParkingList, Long> {
}
