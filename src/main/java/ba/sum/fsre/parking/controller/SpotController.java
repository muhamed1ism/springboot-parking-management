package ba.sum.fsre.parking.controller;

import ba.sum.fsre.parking.model.*;
import ba.sum.fsre.parking.services.ParkingService;
import ba.sum.fsre.parking.services.SpotHistoryService;
import ba.sum.fsre.parking.services.SpotService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@Controller
@RequestMapping("/spots")
public class SpotController {

    @Autowired
    private ParkingService parkingService;

    @Autowired
    private SpotService spotService;

    @Autowired
    private SpotHistoryService spotHistoryService;

    @ModelAttribute("userDetails")
    public UserDetails getUserDetails(Authentication authentication) {
        return (UserDetails) authentication.getPrincipal();
    }

    @GetMapping("/{id}")
    public String listSpots(@PathVariable("id") Long parkingId, Model model) {
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

    @GetMapping("/user-spots")
    public String listSpots( Model model, @ModelAttribute("userDetails") UserDetails userDetails) {
        User user = userDetails.getUser();
        if (user != null) {
            List<Spot> spots = spotService.findByUser(user);

            model.addAttribute("user", user);
            model.addAttribute("spots", spots);
            model.addAttribute("activeLink", "Moje karte");
            return "user-spots";
        } else {

            return "error-page";
        }
    }

    @GetMapping("/add/{id}")
    public String showAddSpotForm(@PathVariable("id") Long parkingId, Model model) {
        Parking Parking = parkingService.getParkingById(parkingId);

        if (Parking != null) {

            long currentAvailableSpots = Parking.getAvailableSpots();

            if (currentAvailableSpots > 0) {
                Parking.setAvailableSpots(currentAvailableSpots - 1);

                Spot spot = new Spot();
                spot.setParking(Parking);

                model.addAttribute("spot", spot);

                return "add-spot";
            } else {
                return "redirect:/spots/{id}?errorMessage=Nema slobodnih mjesta!";
            }

        } else {
            return "redirect:/parking-list";
        }
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @PostMapping("/user-add/{id}")
    public String addUserSpot(@PathVariable("id") Long parkingId, @ModelAttribute("spot") @Valid Spot spot, BindingResult result, Model model, @ModelAttribute("userDetails") UserDetails userDetails) {
        Parking Parking = parkingService.getParkingById(parkingId);
        User user = userDetails.getUser();
        if (Parking != null) {
            long currentAvailableSpots = Parking.getAvailableSpots();

            if (currentAvailableSpots > 0) {
                if (result.hasErrors()) {
                    model.addAttribute("spot", spot);
                    return "add-spot";
                }

                LocalDateTime startTime = LocalDateTime.now();
                spot.setStartTime(startTime);

                spotService.calculatePriceForSpot(spot);

                String licensePlate = spot.getLicensePlate();
                if (spotService.getSpotByLicensePlate(licensePlate) != null) {
                    result.rejectValue("licensePlate", "error.spot",
                            "Vozilo sa istom registracijom već postoji!");

                    if (result.hasErrors()) {
                        model.addAttribute("spot", spot);
                        return "add-spot";
                    }
                }
                Parking.setAvailableSpots(currentAvailableSpots - 1);
                spot.setUser(user);
                spot.setParking(Parking);
                spotService.saveSpot(spot);

                return "redirect:/select-parking";


            } else {
                return "error-page";
            }
        } else {
            return "redirect:/parking-list";
        }
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping("/add/{id}")
    public String addSpot(@PathVariable("id") Long parkingId, @ModelAttribute("spot") @Valid Spot spot, BindingResult result, Model model, @ModelAttribute("userDetails") UserDetails userDetails) {
        Parking Parking = parkingService.getParkingById(parkingId);
        User user = userDetails.getUser();
        if (Parking != null) {
            long currentAvailableSpots = Parking.getAvailableSpots();

            if (currentAvailableSpots > 0) {
                if (result.hasErrors()) {
                    model.addAttribute("spot", spot);
                    return "add-spot";
                }

                LocalDateTime startTime = LocalDateTime.now();
                spot.setStartTime(startTime);

                spotService.calculatePriceForSpot(spot);

                String licensePlate = spot.getLicensePlate();
                if (spotService.getSpotByLicensePlate(licensePlate) != null) {
                    result.rejectValue("licensePlate", "error.spot",
                            "Vozilo sa istom registracijom već postoji!");

                    if (result.hasErrors()) {
                        model.addAttribute("spot", spot);
                        return "add-spot";
                    }
                }
                Parking.setAvailableSpots(currentAvailableSpots - 1);
                spot.setUser(user);
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


    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping("/delete/{id}")
    public String deleteSpot(@PathVariable("id") Long spotId, HttpServletRequest request, Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        model.addAttribute("userDetails", userDetails);

        Spot spotToDelete = spotService.getSpotById(spotId).orElse(null);

        // save spot to history
        if (spotToDelete != null) {
            SpotHistory spotHistory = new SpotHistory();
            spotHistoryService.saveSpot(spotHistory, spotToDelete);

            Parking parking = spotToDelete.getParking();

            if (parking != null) {
                long currentAvailableSpots = parking.getAvailableSpots();
                parking.setAvailableSpots(currentAvailableSpots + 1);

                parkingService.saveParking(parking);
            }
        }

        spotService.deleteSpot(spotId);

        String referer = request.getHeader("referer");
        return "redirect:" + referer;
    }

}
