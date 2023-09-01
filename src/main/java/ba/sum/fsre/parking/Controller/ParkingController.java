package ba.sum.fsre.parking.Controller;

import ba.sum.fsre.parking.Model.Parking;
import ba.sum.fsre.parking.Model.UserDetails;
import ba.sum.fsre.parking.Service.ParkingService;
import ba.sum.fsre.parking.Service.SpotService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
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

    @Autowired
    SpotService spotService;

    @ModelAttribute("userDetails")
    public UserDetails getUserDetails(Authentication authentication) {
        return (UserDetails) authentication.getPrincipal();
    }

    @GetMapping
    public String listParking(Model model) {
        List<Parking> listParking = parkingService.getAllParkings();

        model.addAttribute("listParking", listParking);
        model.addAttribute("activeLink", "Parking Lista");
        return "parking-list";
    }

    @GetMapping("add")
    public String showAddParkingForm(Model model) {
        model.addAttribute("parking", new Parking());
        model.addAttribute("activeLink", "Parking Lista");
        return "add-parking";
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping("add")
    public String addParking(@Valid Parking parking, BindingResult result,
                             @ModelAttribute("userDetails") UserDetails userDetails) {
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
        model.addAttribute("parking", parking);
        model.addAttribute("activeLink", "Parking Lista");
        return "edit-parking";
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping("update/{id}")
    public String updateParking(@PathVariable("id") long parkingId, @Valid Parking parking,
                                BindingResult result, @ModelAttribute("userDetails") UserDetails userDetails) {
        if (result.hasErrors()) {
            return "edit-parking";
        }
        parking.setAvailableSpots(parking.getTotalSpots() - spotService.countSpotByParking(parking));

        parkingService.saveParking(parking);
        return "redirect:/parking-list";
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping("delete/{id}")
    public String delete(@PathVariable("id") long parkingId, @ModelAttribute("userDetails") UserDetails userDetails) {
        parkingService.deleteParking(parkingId);
        return "redirect:/parking-list";
    }

}