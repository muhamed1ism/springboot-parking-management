package ba.sum.fsre.parking.controller;

import ba.sum.fsre.parking.model.Parking;
import ba.sum.fsre.parking.model.Spot;
import ba.sum.fsre.parking.model.UserDetails;
import ba.sum.fsre.parking.repositories.ParkingRepository;
import ba.sum.fsre.parking.repositories.SpotRepository;
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
    private ParkingRepository parkingRepo;

    @Autowired
    private SpotRepository spotRepo;

    @GetMapping("/{id}")
    public String listSpots(@PathVariable("id") Long parkingId, Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        model.addAttribute("userDetails", userDetails);

        Parking Parking = parkingRepo.findById(parkingId).orElse(null);

        if (Parking != null) {
            List<Spot> spots = spotRepo.findByParking(Parking);

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

        Parking Parking = parkingRepo.findById(parkingId).orElse(null);

        if (Parking != null) {


            Spot spot = new Spot();
            spot.setParking(Parking);

            model.addAttribute("spot", spot);

            return "add-spot";
        } else {
            return "redirect:/parking-list";
        }
    }

    @PostMapping("/add/{id}")
    public String addSpot(@PathVariable("id") Long parkingId,Model model, @ModelAttribute("spot") Spot spot) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        model.addAttribute("userDetails", userDetails);

        Parking Parking = parkingRepo.findById(parkingId).orElse(null);

        if (Parking != null) {
            spot.setParking(Parking);
            spotRepo.save(spot);

            return "redirect:/spots/" + parkingId;
        } else {
            return "redirect:/parking-list";
        }
    }

    @PostMapping("/delete/{id}")
    public String deleteSpot(@PathVariable("id") Long spotId, HttpServletRequest request) {

        spotRepo.deleteById(spotId);

        // Redirect to the previous page or a specific URL
        String referer = request.getHeader("referer");

        return "redirect:" + referer;
    }

}
