package ba.sum.fsre.parking.services;

import ba.sum.fsre.parking.model.Parking;
import ba.sum.fsre.parking.repositories.ParkingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ParkingService {
    private final ParkingRepository ParkingRepository;

    @Autowired
    public ParkingService(ParkingRepository ParkingRepository) {
        this.ParkingRepository = ParkingRepository;
    }

    public List<Parking> getAllParkings() {
        return ParkingRepository.findAll();
    }

    public Parking saveParking(Parking parking) {
        return ParkingRepository.save(parking);
    }

    public Parking getParkingById(Long id) {
        return ParkingRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid parking Id:" + id));
    }

    public void deleteParking(Long id) {
        ParkingRepository.deleteById(id);
    }
}
