package ba.sum.fsre.parking.services;

import ba.sum.fsre.parking.model.Parking;
import ba.sum.fsre.parking.model.Spot;
import ba.sum.fsre.parking.repositories.SpotRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SpotService {

    private final SpotRepository spotRepository;

    @Autowired
    public SpotService(SpotRepository spotRepository) {
        this.spotRepository = spotRepository;
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



}
