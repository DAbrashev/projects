package com.paintingscollectors.controller;

import com.paintingscollectors.service.CurrentUser;
import com.paintingscollectors.service.PaintingService;
import com.paintingscollectors.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    private final CurrentUser currentUser;
    private final PaintingService paintingService;

    private final UserService userService;

    public HomeController(CurrentUser currentUser, PaintingService paintingService, UserService userService) {
        this.currentUser = currentUser;
        this.paintingService = paintingService;
        this.userService = userService;
    }

    @GetMapping("/")
    public String viewIndex(){
        if(currentUser.loggedIn()){
            return "redirect:/home";
        }
        return "index";
    }

    @GetMapping("/home")
    public String homeView(Model model) {
        if (!currentUser.loggedIn()){
            return "redirect:/";
        }
        addDataToModel(model);
        return "home";
    }

    public void addDataToModel(Model model){
        model.addAttribute("currentUserPaintings",
                userService.getUserPaintings(currentUser.getUser().getId()));

        model.addAttribute("otherPaintings",
                paintingService.getOtherPaintings((currentUser.getUser().getId())));

        model.addAttribute("currentUserFavoritePaintings",
                userService.getUserFavoritePaintings(currentUser.getUser().getId()));

        model.addAttribute("twoMostRatedPaintings",
                paintingService.getTwoMostRatedPaintings());
    }

}
