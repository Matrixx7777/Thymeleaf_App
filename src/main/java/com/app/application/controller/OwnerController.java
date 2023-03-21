package com.app.application.controller;

import java.util.List;

import com.app.application.domain.Location;
import com.app.application.domain.Owner;
import com.app.application.service.OwnerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class OwnerController {

    @Autowired
    private OwnerService ownerService;

    @GetMapping("/")
    public String viewHomePage(Model model) {
        return findPaginated(1, "name", "asc", model);
    }

    @GetMapping("/getAllFriendsOnLocation")
    public List<Owner> getAllFriendsOnLocation(Location location) {
        List<Owner> getListOfUsersOnLocation = ownerService.getAllOwners();
        for (int i = 0; i < getListOfUsersOnLocation.size(); i++)
            if (getListOfUsersOnLocation.get(i).getName().contains(location.getOwnersShared()))
                return getListOfUsersOnLocation;
        return getListOfUsersOnLocation;
    }

    @GetMapping("/getAllLocationsAvailableForUser")
    public List<Owner> getAllLocationsAvailableForUser (Owner owner) {
        List<Owner> getAllLocationsSharedForUser = ownerService.getAllOwners();
        for (int i = 0; i < getAllLocationsSharedForUser.size(); i++)
            if (getAllLocationsSharedForUser.get(i).getLocation().getOwnersShared().contains(owner.getName()))
                return getAllLocationsSharedForUser;
        return getAllLocationsSharedForUser;
    }

    @GetMapping("/showNewOwnerForm")
    public String showNewOwnerForm(Model model) {
        Owner owner = new Owner();
        model.addAttribute("owner", owner);
        return "new_owner";
    }

    @PostMapping("/saveOwner")
    public String saveOwner(@ModelAttribute("owner") Owner owner) {
        ownerService.saveOwner(owner);
        return "redirect:/";
    }

    @GetMapping("/showOwnerFormForUpdate/{id}")
    public String showOwnerFormForUpdate(@PathVariable ( value = "id") long id, Model model) {
        Owner owner = ownerService.getOwnerById(id);
        model.addAttribute("owner", owner);
        return "update_owner";
    }

    @GetMapping("/shareLocationWith/{id}")
    public String shareLocationWith(@PathVariable ( value = "id") long id, Model model) {
        Owner owner = ownerService.getOwnerById(id);
        model.addAttribute("owner", owner);
        return "share_location_with";
    }

    @GetMapping("/deleteOwner/{id}")
    public String deleteOwner(@PathVariable (value = "id") long id) {
        this.ownerService.deleteOwnerById(id);
        return "redirect:/";
    }

    @GetMapping("/page/{pageNo}")
    public String findPaginated(@PathVariable (value = "pageNo") int pageNo,
                                @RequestParam("sortField") String sortField,
                                @RequestParam("sortDir") String sortDir,
                                Model model) {
        int pageSize = 5;
        Page<Owner> page = ownerService.findPaginated(pageNo, pageSize, sortField, sortDir);
        List<Owner> listOwners = page.getContent();
        model.addAttribute("currentPage", pageNo);
        model.addAttribute("totalPages", page.getTotalPages());
        model.addAttribute("totalItems", page.getTotalElements());
        model.addAttribute("sortField", sortField);
        model.addAttribute("sortDir", sortDir);
        model.addAttribute("reverseSortDir", sortDir.equals("asc") ? "desc" : "asc");
        model.addAttribute("listOwners", listOwners);
        return "index";
    }
}