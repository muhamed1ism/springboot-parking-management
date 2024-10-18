package ba.sum.fsre.parking.Controller;

import ba.sum.fsre.parking.Model.Spot;
import ba.sum.fsre.parking.Model.SpotHistory;
import ba.sum.fsre.parking.Service.SpotHistoryService;
import ba.sum.fsre.parking.Service.SpotService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/spot-history")
public class SpotHistoryController {

    @Autowired
    private SpotHistoryService spotHistoryService;

    @Autowired
    private SpotService spotService;

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping
    public String listSpots(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        model.addAttribute("userDetails", userDetails);
        List<SpotHistory> spotHistoryList = spotHistoryService.getAllSpots();

        model.addAttribute("spotHistory", spotHistoryList);
        model.addAttribute("activeLink", "Ticket history");

        return "spot-history";
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping("/restore/{id}")
    public String restoreSpot(@PathVariable("id") Long id, Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        model.addAttribute("userDetails", userDetails);

        SpotHistory spotHistoryToRestore = spotHistoryService.getSpotById(id).orElse(null);

        if (spotHistoryToRestore != null) {
            Spot spot = new Spot();

            spotService.restoreSpot(spot, spotHistoryToRestore);

            spotHistoryService.deleteSpot(id);
        }
        return "redirect:/spot-history";
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping("/delete/{id}")
    public String deleteSpot(@PathVariable("id") Long id, Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        model.addAttribute("userDetails", userDetails);

        if (!spotHistoryService.getSpotById(id).isPresent()) {
            model.addAttribute("message", "Spot with id " + id + " does not exist.");
            return "spot-history";
        }

        spotHistoryService.deleteSpot(id);
        return "redirect:/spot-history";
    }
}
