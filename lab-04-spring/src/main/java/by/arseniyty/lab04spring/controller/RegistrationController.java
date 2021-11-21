package by.arseniyty.lab04spring.controller;

import by.arseniyty.lab04spring.entity.User;
import by.arseniyty.lab04spring.service.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

@RequestMapping("/registration")
@Controller
public class RegistrationController {

    @Autowired
    private UsersService usersService;

    @GetMapping
    public String registration(Model model) {
        model.addAttribute("userForm", new User());
        return "registration";
    }

    @PostMapping
    public String addUser(@ModelAttribute("userForm") @Valid User userForm,
                          BindingResult bindingResult,
                          Model model) {
        if (bindingResult.hasErrors()) {  // @Valid checks for limitations, like that provided from @Size, and puts to bindingResult
            return "registration";
        }
        if (!userForm.getPassword().equals(userForm.getPasswordConfirm())){
            model.addAttribute("passwordError", "Incorrect password");
            return "registration";
        }
        if (!usersService.saveUser(userForm)){
            model.addAttribute("usernameError", "User with such a name already exists");
            return "registration";
        }
        return "redirect:/";
    }
}
