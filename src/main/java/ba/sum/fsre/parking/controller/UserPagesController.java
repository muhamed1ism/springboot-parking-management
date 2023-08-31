package ba.sum.fsre.parking.controller;

import ba.sum.fsre.parking.model.Parking;
import ba.sum.fsre.parking.model.UserDetails;
import ba.sum.fsre.parking.services.ParkingService;
import ba.sum.fsre.parking.services.SpotService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.util.List;

@Controller
public class UserPagesController {
    @Autowired
    ParkingService parkingService;

    @Autowired
    SpotService spotService;

    @ModelAttribute("userDetails")
    public UserDetails getUserDetails(Authentication authentication) {
        return (UserDetails) authentication.getPrincipal();
    }

    @GetMapping("/dashboard")
    public String dashboard(Model model, @ModelAttribute("userDetails") UserDetails userDetails) {
        model.addAttribute("activeLink", "Početna");
        return "dashboard";
    }

    @GetMapping("/select-parking")
    public String listParking(Model model) {
        List<Parking> listParking = parkingService.getAllParkings();

        model.addAttribute("listParking", listParking);
        model.addAttribute("activeLink", "Kupi kartu");
        return "select-parking";
    }



}
