package ba.sum.fsre.parking.services;

import ba.sum.fsre.parking.model.Parking;
import ba.sum.fsre.parking.repositories.ParkingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ParkingService {
    private final ParkingRepository parkingRepository;

    @Autowired
    public ParkingService(ParkingRepository parkingRepository) {
        this.parkingRepository = parkingRepository;
    }

    public List<Parking> getAllParkings() {
        return parkingRepository.findAll();
    }

    public Parking saveParking(Parking parking) {
        return parkingRepository.save(parking);
    }

    public Parking getParkingById(Long id) {
        return parkingRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid parking Id:" + id));
    }

    public void deleteParking(Long id) {
        parkingRepository.deleteById(id);
    }

    public boolean hasAvailableSpots(Parking parking) {
        return parking.getAvailableSpots() > 0;
    }
}
