package ba.sum.fsre.parking.Service;

import ba.sum.fsre.parking.Model.Spot;
import ba.sum.fsre.parking.Model.SpotHistory;
import ba.sum.fsre.parking.Repository.SpotHistoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SpotHistoryService {
    private  final SpotHistoryRepository spotHistoryRepository;

    @Autowired
    public SpotHistoryService(SpotHistoryRepository spotHistoryRepository) {
        this.spotHistoryRepository = spotHistoryRepository;
    }

    //get all spots
    public List<SpotHistory> getAllSpots() {
        return spotHistoryRepository.findAll();
    }

    //get spot by id
    public Optional<SpotHistory> getSpotById(Long id) {
        return spotHistoryRepository.findById(id);
    }

    //get spot by license plate
    public SpotHistory getSpotByLicensePlate(String licensePlate) {
        return spotHistoryRepository.findByLicensePlate(licensePlate);
    }

    //save spot
    public void saveSpot(SpotHistory spotHistory, Spot spotToDelete) {
        spotHistory.setCarName(spotToDelete.getCarName());
        spotHistory.setLicensePlate(spotToDelete.getLicensePlate());
        spotHistory.setStartTime(spotToDelete.getStartTime());
        spotHistory.setEndTime(spotToDelete.getEndTime());
        spotHistory.setDuration(spotToDelete.getDuration());
        spotHistory.setDurationUnit(spotToDelete.getDurationUnit());
        spotHistory.setFinalPrice(spotToDelete.getFinalPrice());
        spotHistory.setParkingId(spotToDelete.getParking().getId());
        spotHistory.setParkingName(spotToDelete.getParking().getParkingName());
        spotHistory.setParkingAddress(spotToDelete.getParking().getParkingAddress());
        spotHistoryRepository.save(spotHistory);
    }

    //delete spot
    public void deleteSpot(Long id) {
        spotHistoryRepository.deleteById(id);
    }

    //get all spots by parking_id
    public List<SpotHistory> findByParkingId(Long parkingId) {
        return spotHistoryRepository.findByParkingId(parkingId);
    }

}
