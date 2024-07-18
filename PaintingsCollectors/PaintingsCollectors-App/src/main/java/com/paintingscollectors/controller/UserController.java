package com.paintingscollectors.controller;

import com.paintingscollectors.model.dto.UserLoginDto;
import com.paintingscollectors.model.dto.UserRegisterDto;
import com.paintingscollectors.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

@Controller
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @ModelAttribute("registerDto")
    public UserRegisterDto createEmptyDto(){
        return new UserRegisterDto();
    }

    @ModelAttribute("loginDto")
    public UserLoginDto createEmptyLoginDto(){
        return new UserLoginDto();
    }

    @GetMapping("/register")
    public String viewRegister(){
        return "register";
    }

    @PostMapping("/register")
    public String doRegister(
            @Valid UserRegisterDto userRegisterDto,
            BindingResult bindingResult,
            RedirectAttributes redirectAttributes
            ){

        if (bindingResult.hasErrors() || !userService.saveUser(userRegisterDto)){
            redirectAttributes.addFlashAttribute("registerDto",userRegisterDto);
            redirectAttributes.addFlashAttribute(
                    "org.springframework.validation.BindingResult.registerDto",bindingResult);

            return "redirect:/register";
        }
        return "redirect:/login";
    }

    @GetMapping("/login")
    public String viewLogin(){
        return "login";
    }

    @PostMapping("/login")
    public String doLogin(
            @Valid UserLoginDto userLoginDto,
            BindingResult bindingResult,
            RedirectAttributes redirectAttributes
    ){

        if (bindingResult.hasErrors()){
            redirectAttributes.addFlashAttribute("loginDto",userLoginDto);
            redirectAttributes.addFlashAttribute(
                    "org.springframework.validation.BindingResult.loginDto",bindingResult);

            return "redirect:/login";
        }

        boolean success = userService.loginUser(userLoginDto);

        if (!success){
            redirectAttributes.addFlashAttribute("loginDto",userLoginDto);
            redirectAttributes.addFlashAttribute("userPassMismatch",true);

            return "redirect:/login";
        }

        return "redirect:/home";
    }

    @PostMapping("/logout")
    public String logout(){
        userService.logout();
        return "redirect:/";
    }
}
