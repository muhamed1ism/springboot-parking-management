package ba.sum.fsre.parking.services;

import ba.sum.fsre.parking.model.*;
import ba.sum.fsre.parking.repositories.ParkingRepository;
import ba.sum.fsre.parking.repositories.SpotRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
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

    public long countSpotByParking(Parking parking) {
        return spotRepository.findByParking(parking).size();
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
        long currentAvailableSpots = parking.getAvailableSpots();
        parking.setAvailableSpots(currentAvailableSpots - 1);
        spot.setParking(parking);

        spotRepository.save(spot);
    }

    public void calculatePriceForSpot(Spot spot) {
        switch (spot.getDurationUnit()) {
            case "HOURS" -> calculatePriceForHours(spot);
            case "DAYS" -> calculatePriceForDays(spot);
            case "MONTHS" -> calculatePriceForMonths(spot);
            default -> { throw new RuntimeException("Unsupported duration unit"); }
        }
    }

    public void calculatePriceForHours(Spot spot) {
        spot.setEndTime(spot.getStartTime().plusHours(spot.getDuration()));
        if (spot.getDuration() > 1) {
            BigDecimal duration = BigDecimal.valueOf(spot.getDuration());
            BigDecimal pricePerHour = Price.getPricePerHour();
            BigDecimal priceForHour = Price.getPriceForHour();
            duration = duration.subtract(BigDecimal.valueOf(1));
            spot.setFinalPrice(priceForHour.add(duration.multiply(pricePerHour)));
        } else {
            BigDecimal priceForHour = Price.getPriceForHour();
            spot.setFinalPrice(priceForHour);
        }
    }

    public void calculatePriceForDays(Spot spot) {
        spot.setEndTime(spot.getStartTime().plusDays(spot.getDuration()));
        BigDecimal duration = BigDecimal.valueOf(spot.getDuration());
        BigDecimal pricePerDay = Price.getPricePerDay();
        spot.setFinalPrice(duration.multiply(pricePerDay));
    }

    public void calculatePriceForMonths(Spot spot) {
        spot.setEndTime(spot.getStartTime().plusMonths(spot.getDuration()));
        BigDecimal duration = BigDecimal.valueOf(spot.getDuration());
        BigDecimal pricePerMonth = Price.getPricePerMonth();
        spot.setFinalPrice(duration.multiply(pricePerMonth));
    }


    public List<Spot> findByUser(User user) {
        return spotRepository.findByUser(user);
    }
}
