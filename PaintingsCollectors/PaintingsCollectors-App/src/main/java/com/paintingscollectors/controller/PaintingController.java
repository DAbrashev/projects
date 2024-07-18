package com.paintingscollectors.controller;

import com.paintingscollectors.model.dto.AddPaintingDto;
import com.paintingscollectors.model.entity.StyleName;
import com.paintingscollectors.service.PaintingService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

@Controller
public class PaintingController {

    private final PaintingService paintingService;

    public PaintingController(PaintingService paintingService) {
        this.paintingService = paintingService;
    }

    @ModelAttribute("addPaintingDto")
    public AddPaintingDto createEmptyDto() {
        return new AddPaintingDto();
    }


    @GetMapping("/add-painting")
    public String viewPaintings( Model model) {
        model.addAttribute("allStyleNames", StyleName.values());
        return "add-painting";
    }


    @PostMapping("/add-painting")
    public String addPainting(
            @Valid AddPaintingDto addPaintingDto,
            BindingResult bindingResult,
            RedirectAttributes redirectAttributes
    ) {

        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("addPaintingDto", addPaintingDto);
            redirectAttributes.addFlashAttribute(
                    "org.springframework.validation.BindingResult.addPaintingDto", bindingResult);

            return "redirect:/add-painting";
        }

        paintingService.addPainting(addPaintingDto);

        return "redirect:/home";
    }

    @GetMapping("/add-to-favourite/{id}")
    public String addToFavourite(@PathVariable(name = "id") Long paintingId) {
        paintingService.addToFavourite(paintingId);
        return "redirect:/home";
    }

    @GetMapping("/vote/{id}")
    public String vote(@PathVariable(name = "id") Long paintingId){
        paintingService.vote(paintingId);
        return "redirect:/home";
    }
    @GetMapping("/remove/{id}")
    public String remove(@PathVariable(name ="id")Long paintingId){
        paintingService.remove(paintingId);
        return "redirect:/home";
    }

    @GetMapping("/remove-from-favourite/{id}")
    public String removeFromFavorites(@PathVariable(name ="id")Long paintingId){
        paintingService.removeFromFavorites(paintingId);
        return "redirect:/home";
    }
}
