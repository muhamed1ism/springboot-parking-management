package ba.sum.fsre.parking.controller;

import ba.sum.fsre.parking.model.Parking;
import ba.sum.fsre.parking.model.Price;
import ba.sum.fsre.parking.model.Spot;
import ba.sum.fsre.parking.model.UserDetails;
import ba.sum.fsre.parking.services.ParkingService;
import ba.sum.fsre.parking.services.SpotService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Controller
@RequestMapping("/spots")
public class SpotController {

    @Autowired
    private ParkingService parkingService;

    @Autowired
    private SpotService spotService;



    @GetMapping("/{id}")
    public String listSpots(@PathVariable("id") Long parkingId, Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        model.addAttribute("userDetails", userDetails);

        Parking Parking = parkingService.getParkingById(parkingId);

        if (Parking != null) {
            List<Spot> spots = spotService.findByParking(Parking);

            model.addAttribute("parking", Parking);
            model.addAttribute("spots", spots);
            return "spots";
        } else {

            return "error-page";
        }
    }
    @GetMapping("/add/{id}")
    public String showAddSpotForm(@PathVariable("id") Long parkingId, Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        model.addAttribute("userDetails", userDetails);

        Parking Parking = parkingService.getParkingById(parkingId);

        if (Parking != null) {

            int currentAvailableSpots = Parking.getAvailableSpots();

            if (currentAvailableSpots > 0) {
                Parking.setAvailableSpots(currentAvailableSpots - 1);

                Spot spot = new Spot();
                spot.setParking(Parking);

                model.addAttribute("spot", spot);

                return "add-spot";
            } else {

                return "error-page";
            }

        } else {
            return "redirect:/parking-list";
        }
    }

    @PostMapping("/add/{id}")
    public String addSpot(@PathVariable("id") Long parkingId, @ModelAttribute("spot") @Valid Spot spot, BindingResult result, Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        model.addAttribute("userDetails", userDetails);

        Parking Parking = parkingService.getParkingById(parkingId);

        if (Parking != null) {
            int currentAvailableSpots = Parking.getAvailableSpots();

            if (currentAvailableSpots > 0) {
                // Provjera da li su sva polja ispravno popunjena
                if (result.hasErrors()) {
                    model.addAttribute("spot", spot);
                    return "add-spot";
                }

                LocalDateTime startTime = LocalDateTime.now();
                spot.setStartTime(startTime);

                // Postavljanje kranjeg vremena na osnovu trajanja i jedinice trajanja
                switch (spot.getDurationUnit()) {
                    case "HOURS" -> {
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
                    case "DAYS" -> {
                        spot.setEndTime(spot.getStartTime().plusDays(spot.getDuration()));
                        BigDecimal duration = BigDecimal.valueOf(spot.getDuration());
                        BigDecimal pricePerDay = Price.getPricePerDay();
                        spot.setFinalPrice(duration.multiply(pricePerDay));
                    }
                    case "MONTHS" -> {
                        spot.setEndTime(spot.getStartTime().plusMonths(spot.getDuration()));
                        BigDecimal duration = BigDecimal.valueOf(spot.getDuration());
                        BigDecimal pricePerMonth = Price.getPricePerMonth();
                        spot.setFinalPrice(duration.multiply(pricePerMonth));
                    }
                    default -> { return "error-page";
                    }
                }

                String licensePlate = spot.getLicensePlate();

                // Provjera da li postoji vozilo sa istom registracijom
                if (spotService.getSpotByLicensePlate(licensePlate) != null) {
                    result.rejectValue("licensePlate", "error.spot",
                            "Vozilo sa istom registracijom već postoji!");

                    if (result.hasErrors()) {
                        model.addAttribute("spot", spot);
                        return "add-spot";
                    }
                }


                Parking.setAvailableSpots(currentAvailableSpots - 1);

                spot.setParking(Parking);
                spotService.saveSpot(spot);

                return "redirect:/spots/" + parkingId;
            } else {

                return "error-page";
            }
        } else {
            return "redirect:/parking-list";
        }
    }

    @PostMapping("/delete/{id}")
    public String deleteSpot(@PathVariable("id") Long spotId, HttpServletRequest request, Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        model.addAttribute("userDetails", userDetails);

        Spot spotToDelete = spotService.getSpotById(spotId).orElse(null);

        spotService.deleteSpot(spotId);

        if (spotToDelete != null) {
            Parking parking = spotToDelete.getParking();

            if (parking != null) {
                int currentAvailableSpots = parking.getAvailableSpots();
                parking.setAvailableSpots(currentAvailableSpots + 1);

                parkingService.saveParking(parking);
            }
        }

        String referer = request.getHeader("referer");
        return "redirect:" + referer;
    }

}
