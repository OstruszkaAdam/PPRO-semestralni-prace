package cz.uhk.ppro.dima.controller;

import cz.uhk.ppro.dima.model.UserDTO;
import cz.uhk.ppro.dima.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.validation.Valid;


@Controller
public class RegistrationController {

    private final UserService userService;

    @Autowired
    RegistrationController(UserService userService) {this.userService = userService;}

    @RequestMapping(value = "/registration", method = RequestMethod.GET)
    public String showRegistrationForm(@ModelAttribute("user") UserDTO user) {
        return "registration";
    }

    @RequestMapping(value = "/registration", method = RequestMethod.POST)
    public String registerNewUser(@ModelAttribute("user") @Valid UserDTO userDTO, BindingResult result) {
        if(result.hasErrors() || !userDTO.getPassword().equals(userDTO.getPasswordRepeat()))
            return "redirect:registration?unsuccesful";

        if(userService.findByUsername(userDTO.getUsername()).isPresent() == false){
            userService.createNewUser(userDTO);
            return "redirect:registrationSuccess";
        }

        return "redirect:registration?unsuccesful";
    }

    @RequestMapping(value = "/registration/registrationSuccess",  method = RequestMethod.GET)
    public String showRegistrationSuccess() {
        return "registrationSuccess";
    }
}
