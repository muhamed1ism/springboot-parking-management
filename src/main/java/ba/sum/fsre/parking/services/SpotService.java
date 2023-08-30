package ba.sum.fsre.parking.services;

import ba.sum.fsre.parking.model.Parking;
import ba.sum.fsre.parking.model.Spot;
import ba.sum.fsre.parking.model.SpotHistory;
import ba.sum.fsre.parking.repositories.ParkingRepository;
import ba.sum.fsre.parking.repositories.SpotRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SpotService {
    @Autowired
    private final SpotRepository spotRepository;

    @Autowired
    private final ParkingRepository parkingRepository;

    @Autowired
    public SpotService(SpotRepository spotRepository, ParkingRepository parkingRepository) {
        this.spotRepository = spotRepository;
        this.parkingRepository = parkingRepository;
    }

    public List<Spot> getAllSpots() {
        return spotRepository.findAll();
    }

    public Spot saveSpot(Spot spot) {
        return spotRepository.save(spot);
    }

    public Optional<Spot> getSpotById(Long id) {
        return spotRepository.findById(id);
    }

    public Spot getSpotByLicensePlate(String licensePlate) {
        return spotRepository.findByLicensePlate(licensePlate);
    }

    public void deleteSpot(Long id) {
        spotRepository.deleteById(id);
    }

    public List<Spot> findByParking(Parking parking) {
        return spotRepository.findByParking(parking);
    }

    public void restoreSpot(Spot spot, SpotHistory spotToRestore) {
        spot.setCarName(spotToRestore.getCarName());
        spot.setLicensePlate(spotToRestore.getLicensePlate());
        spot.setStartTime(spotToRestore.getStartTime());
        spot.setEndTime(spotToRestore.getEndTime());
        spot.setDuration(spotToRestore.getDuration());
        spot.setDurationUnit(spotToRestore.getDurationUnit());
        spot.setFinalPrice(spotToRestore.getFinalPrice());

        Parking parking = parkingRepository.findById(spotToRestore.getParkingId()).orElse(null);
        assert parking != null;
        int currentAvailableSpots = parking.getAvailableSpots();
        parking.setAvailableSpots(currentAvailableSpots - 1);
        spot.setParking(parking);

        spotRepository.save(spot);
    }



}
