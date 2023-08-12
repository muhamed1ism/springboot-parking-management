package ba.sum.fsre.parking.controller;

import ba.sum.fsre.parking.model.ParkingList;
import ba.sum.fsre.parking.model.UserDetails;
import ba.sum.fsre.parking.repositories.ParkingListRepository;
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
@RequestMapping("/parking_list")
public class ParkingListController {
    @Autowired
    ParkingListRepository parkingRepo;

    @GetMapping
    public String listParking(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        model.addAttribute("userDetails", userDetails);
        List<ParkingList> listParking = parkingRepo.findAll();
        model.addAttribute("listParking", listParking);
        model.addAttribute("activeLink", "Parking Lista");
        return "parking_list";
    }


    @GetMapping("add")
    public String showAddParkingForm(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        model.addAttribute("userDetails", userDetails);
        model.addAttribute("parkingList", new ParkingList());
        model.addAttribute("activeLink", "Parking Lista");
        return "add_parking";
    }

    @PostMapping("add")
    public String addParking(@Valid ParkingList parking, BindingResult result, Model model) {
        if (result.hasErrors()) {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            model.addAttribute("userDetails", userDetails);
            model.addAttribute("parking", parking);
            model.addAttribute("activeLink", "Parking Lista");
            return "add_parking";
        }
        parkingRepo.save(parking);
        return "redirect:/parking_list";
    }

    @GetMapping("edit/{id}")
    public String showUpdateForm(@PathVariable("id") long id, Model model) {
        ParkingList parking = parkingRepo.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid parking Id:" + id));
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        model.addAttribute("userDetails", userDetails);
        model.addAttribute("parking", parking);
        model.addAttribute("activeLink", "Parking Lista");
        return "edit_parking";
    }

    @PostMapping("update/{id}")
    public String updateParking(@PathVariable("id") long id, @Valid ParkingList parking,
                                BindingResult result, Model model) {
        if (result.hasErrors()) {
            parking.setId(id);
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            model.addAttribute("userDetails", userDetails);
            model.addAttribute("parking", parking);
            model.addAttribute("activeLink", "Parking Lista");
            return "edit_parking";
        }
        parkingRepo.save(parking);
        return "redirect:/parking_list";
    }

    @GetMapping("delete/{id}")
    public String delete(@PathVariable("id") long id, Model model) {
        ParkingList parking = parkingRepo.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid parking Id:" + id));
        parkingRepo.delete(parking);
        return "redirect:/parking_list";
    }

}