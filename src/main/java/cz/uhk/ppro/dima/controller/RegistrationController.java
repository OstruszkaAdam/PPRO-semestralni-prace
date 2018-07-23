package cz.uhk.ppro.dima.controller;

import cz.uhk.ppro.dima.dto.UserDto;
import cz.uhk.ppro.dima.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.config.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;


@Controller
public class RegistrationController {

    private final UserService userService;

    @Autowired
    RegistrationController(UserService userService) {this.userService = userService;}

    @RequestMapping(value = "/registration", method = RequestMethod.GET)
    public String showRegistrationForm(@ModelAttribute("user") UserDto user) {
        return "userRegistration";
    }

    @RequestMapping(value = "/registration", method = RequestMethod.POST)
    public String registerNewUser(@ModelAttribute("user") @Valid UserDto userDto, BindingResult result) {
        if(result.hasErrors() || !userDto.getPassword().equals(userDto.getPasswordRepeat()))
            return "redirect:registration?unsuccesful";

        if(userService.findByUsername(userDto.getUsername()).isPresent() == false){
            userService.createNewUser(userDto);
            return "redirect:registration/success";
        }

        return "redirect:registration?unsuccesful";
    }

    @RequestMapping(value = "/registration/success",  method = RequestMethod.GET)
    public String showRegistrationSuccess() {
        return "userRegistrationSuccess";
    }

}
