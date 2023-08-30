package ba.sum.fsre.parking.controller;

import ba.sum.fsre.parking.model.SpotHistory;
import ba.sum.fsre.parking.services.SpotHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/spot-history")
public class SpotHistoryController {

    @Autowired
    private SpotHistoryService spotHistoryService;

    @GetMapping
    public String listSpots(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        model.addAttribute("userDetails", userDetails);
        List<SpotHistory> spotHistoryList = spotHistoryService.getAllSpots();

        model.addAttribute("spotHistory", spotHistoryList);
        model.addAttribute("activeLink", "Povjest karti");

        return "spot-history";
    }

    @PostMapping("/delete/{id}")
    public String deleteSpot(Long id, Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        model.addAttribute("userDetails", userDetails);

        spotHistoryService.deleteSpot(id);
        return "redirect:/spot-history/all";
    }
}
