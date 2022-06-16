package com.gmail.kostia_borozdyh.controller;

import com.gmail.kostia_borozdyh.dto.UserDTO;
import com.gmail.kostia_borozdyh.entity.User;
import com.gmail.kostia_borozdyh.service.impl.RoleService;
import com.gmail.kostia_borozdyh.service.impl.UserService;
import com.gmail.kostia_borozdyh.util.Validator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

@Controller
@Slf4j
public class RegistrationController {

    private PasswordEncoder passwordEncoder;
    private UserService userService;
    private RoleService roleService;

    @Autowired
    public void setPasswordEncoder(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Autowired
    public void setRoleService(RoleService roleService) {
        this.roleService = roleService;
    }

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/register")
    public String getRegisterPage(Model model, HttpSession session) {
        model.addAttribute("invalid", session.getAttribute("invalid"));
        model.addAttribute("userDTO", session.getAttribute("userDTO"));
        model.addAttribute("validation", session.getAttribute("validation"));
        session.removeAttribute("invalid");
        session.removeAttribute("userDTO");
        session.removeAttribute("validation");
        if (session.getAttribute("user") == null) {
            return "/registration";
        }
        return "/adm/createEmployeeAccount";
    }

    @PostMapping("/registration")
    public String registrationUser(@ModelAttribute @Valid UserDTO userDTO, BindingResult bindingResult,
                                   HttpSession session, HttpServletRequest request) {
        session.setAttribute("userDTO", userDTO);

        if (userService.findByLogin(userDTO.getLogin()) != null) {
            session.setAttribute("invalid", "login");
            return "redirect:/register";
        }
        if (userService.findByEmail(userDTO.getEmail()) != null) {
            session.setAttribute("invalid", "email");
            return "redirect:/register";
        }
        if (!userDTO.getPassword().equals(userDTO.getSecondPassword())) {
            session.setAttribute("invalid", "password");
            return "redirect:/register";
        }
        if (bindingResult.hasErrors()) {
            session.setAttribute("validation", Validator.getValidationInfo(bindingResult));
            return "redirect:/register";
        }

        if (session.getAttribute("user") == null) {
            String[] notify = request.getParameterValues("notify");
            String ntf = (notify == null) ? "no" : "yes";
            userDTO.setNotify(ntf);
            userDTO.setRole(roleService.findRoleById(1));
        } else {
            String employee = request.getParameter("employee");
            userDTO.setNotify("no");
            Integer roleId = (employee.equals("employee")) ? 4 : 2;
            userDTO.setRole(roleService.findRoleById(roleId));
        }

        userDTO.setBan("no");
        userDTO.setMoney(0);
        userDTO.setPassword(passwordEncoder.encode(userDTO.getPassword()));

        userService.saveUser(User.fromDTO(userDTO));

        session.removeAttribute("invalid");
        session.removeAttribute("userDTO");
        session.removeAttribute("validation");

        log.info("register user with login - "+userDTO.getLogin());

        if (session.getAttribute("user") == null) {
            session.setAttribute("registration", "yes");
            return "redirect:/login";
        }
        return "redirect:/adm/usersTable";
    }


}
