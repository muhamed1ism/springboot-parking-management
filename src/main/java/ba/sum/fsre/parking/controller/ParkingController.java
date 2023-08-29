package ba.sum.fsre.parking.controller;

import ba.sum.fsre.parking.model.Parking;
import ba.sum.fsre.parking.model.UserDetails;
import ba.sum.fsre.parking.services.ParkingService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@Controller
@RequestMapping("/parking-list")
public class ParkingController {

    @Autowired
    ParkingService parkingService;

    @GetMapping
    public String listParking(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        model.addAttribute("userDetails", userDetails);
        List<Parking> listParking = parkingService.getAllParkings();

        model.addAttribute("listParking", listParking);
        model.addAttribute("activeLink", "Parking Lista");
        return "parking-list";
    }


    @GetMapping("add")
    public String showAddParkingForm(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        model.addAttribute("userDetails", userDetails);

        model.addAttribute("parking", new Parking());
        model.addAttribute("activeLink", "Parking Lista");
        return "add-parking";
    }

    @PostMapping("add")
    public String addParking(@Valid Parking parking, BindingResult result, Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        model.addAttribute("userDetails", userDetails);
        model.addAttribute("activeLink", "Parking Lista");
        if (result.hasErrors()) {
            return "add-parking";
        }

        parking.setAvailableSpots(parking.getTotalSpots());
        parkingService.saveParking(parking);
        return "redirect:/parking-list";
    }

    @GetMapping("edit/{id}")
    public String showUpdateForm(@PathVariable("id") long parkingId, Model model) {
        Parking parking = parkingService.getParkingById(parkingId);
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        model.addAttribute("userDetails", userDetails);

        model.addAttribute("parking", parking);
        model.addAttribute("activeLink", "Parking Lista");
        return "edit-parking";
    }

    @PostMapping("update/{id}")
    public String updateParking(@PathVariable("id") long parkingId, @Valid Parking parking,
                                BindingResult result, Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        model.addAttribute("userDetails", userDetails);
        model.addAttribute("activeLink", "Parking Lista");
        if (result.hasErrors()) {
            return "edit-parking";
        }
        Parking existingParking = parkingService.getParkingById(parkingId);

        parking.setAvailableSpots(existingParking.getAvailableSpots());
        parkingService.saveParking(parking);
        return "redirect:/parking-list";
    }

    @GetMapping("delete/{id}")
    public String delete(@PathVariable("id") long parkingId, Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        model.addAttribute("userDetails", userDetails);

        Parking parking = parkingService.getParkingById(parkingId);
        parkingService.deleteParking(parking.getId());
        return "redirect:/parking-list";
    }

}