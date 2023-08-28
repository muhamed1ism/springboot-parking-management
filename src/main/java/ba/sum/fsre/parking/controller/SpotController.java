package ba.sum.fsre.parking.controller;

import ba.sum.fsre.parking.model.Parking;
import ba.sum.fsre.parking.model.Spot;
import ba.sum.fsre.parking.model.UserDetails;
import ba.sum.fsre.parking.services.ParkingService;
import ba.sum.fsre.parking.services.SpotService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

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
            return "spots"; // Return the name of your Thymeleaf template
        } else {
            // Handle the case where the parking with the given ID does not exist
            return "error-page"; // Create an error page in your templates
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
                // Handle the case where no available spots are left
                // You can redirect to an error page or display an error message
                return "error-page"; // Create an error page in your templates
            }

        } else {
            return "redirect:/parking-list";
        }
    }

    @PostMapping("/add/{id}")
    public String addSpot(@PathVariable("id") Long parkingId,Model model, @ModelAttribute("spot") Spot spot) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        model.addAttribute("userDetails", userDetails);

        Parking Parking = parkingService.getParkingById(parkingId);

        if (Parking != null) {
            int currentAvailableSpots = Parking.getAvailableSpots();

            if (currentAvailableSpots > 0) {
                Parking.setAvailableSpots(currentAvailableSpots - 1);

                spot.setParking(Parking);
                spotService.saveSpot(spot);

                return "redirect:/spots/" + parkingId;
            } else {
                // Handle the case where no available spots are left
                // You can redirect to an error page or display an error message
                return "error-page"; // Create an error page in your templates
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

        // Redirect to the previous page or a specific URL
        String referer = request.getHeader("referer");

        return "redirect:" + referer;
    }

}
