package com.gmail.kostia_borozdyh.controller;

import com.gmail.kostia_borozdyh.dto.UserDTO;
import com.gmail.kostia_borozdyh.dto.ValidationDTO;
import com.gmail.kostia_borozdyh.entity.User;
import com.gmail.kostia_borozdyh.service.impl.UserService;
import com.gmail.kostia_borozdyh.util.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.Objects;

@Controller
public class EditUserController {

    private UserService userService;


    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/editUser")
    public String getEditUserPage(Model model, HttpSession session) {
        model.addAttribute("invalid", session.getAttribute("invalid"));
        model.addAttribute("userDTO", session.getAttribute("userDTO"));
        model.addAttribute("validation", session.getAttribute("validation"));
        session.removeAttribute("invalid");
        session.removeAttribute("userDTO");
        session.removeAttribute("validation");
        return "editUser";
    }

    @PostMapping("/editUser")
    public String registrationUser(@ModelAttribute @Valid UserDTO userDTO, BindingResult bindingResult, HttpSession session, HttpServletRequest request) {
        User user = (User) session.getAttribute("user");
        session.setAttribute("userDTO", userDTO);
        if (userService.findByEmail(userDTO.getEmail()) != null && (!Objects.equals(userDTO.getEmail(), user.getEmail()))) {
            session.setAttribute("invalid", "email");
            return "redirect:/editUser";
        }
        if (!userDTO.getPassword().equals(userDTO.getSecondPassword())) {
            session.setAttribute("invalid", "password");
            return "redirect:/editUser";
        }
        if (bindingResult.hasErrors() && (bindingResult.getFieldErrors().size() != 1 || (!userDTO.getPassword().equals("")))) {
            ValidationDTO validationDTO = Validator.getValidationInfo(bindingResult);

            if (userDTO.getPassword().equals("")) {
                validationDTO.setPassword("");
            }

            session.setAttribute("validation", validationDTO);

            return "redirect:/editUser";
        }

        if (user.getRole().getRole().equals("USER")) {
            String[] notify = request.getParameterValues("notify");
            String ntf = (notify == null) ? "no" : "yes";
            user.setNotify(ntf);
        }

        session.setAttribute("user", userService.saveUser(user, userDTO));

        session.removeAttribute("invalid");
        session.removeAttribute("userDTO");
        session.removeAttribute("validation");

        return "redirect:/editUser";
    }
}
