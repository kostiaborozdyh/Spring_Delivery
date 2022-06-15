package com.gmail.kostia_borozdyh.controller;

import com.gmail.kostia_borozdyh.service.impl.UserService;
import com.gmail.kostia_borozdyh.util.GenerateCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
@RequestMapping(value = "/restore")
public class RestorePasswordController {
    private UserService userService;

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/restore")
    public String getRestorePage(HttpSession session, Model model) {
        model.addAttribute("invalidLogin", session.getAttribute("invalidLogin"));
        session.removeAttribute("invalidLogin");
        return "restore/restore";
    }

    @PostMapping("/restore")
    public String Restore(@RequestParam(name = "login") String login, HttpSession session) {
        String email = userService.getUserEmailForRestore(login);
        if (email == null) {
            session.setAttribute("invalidLogin", "invalidLogin");
            return "redirect:/restore/restore";
        }
        session.setAttribute("email", email);
        session.setAttribute("code", GenerateCode.restorePassword(email));
        return "redirect:/restore/restorePassword";

    }

    @GetMapping("/restorePassword")
    public String getRestorePasswordPage(HttpSession session, Model model) {
        model.addAttribute("invalidCode", session.getAttribute("invalidCode"));
        session.removeAttribute("invalidCode");
        return "restore/restorePassword";
    }

    @PostMapping("/restorePassword")
    public String RestorePassword(@RequestParam(name = "code") String code, HttpSession session) {
        String trueCode = (String) session.getAttribute("code");

        if (code.equals(trueCode)) {
            return "redirect:/restore/enterPassword";
        }

        session.setAttribute("invalidCode", "invalidCode");
        return "redirect:/restore/restorePassword";
    }

    @GetMapping("/enterPassword")
    public String getEnterPasswordPage(HttpSession session, Model model) {
        model.addAttribute("passwordNameInvalid", session.getAttribute("passwordNameInvalid"));
        session.removeAttribute("passwordNameInvalid");
        model.addAttribute("passwordInvalid", session.getAttribute("passwordInvalid"));
        session.removeAttribute("passwordInvalid");
        return "restore/enterPassword";
    }

    @PostMapping("/enterPassword")
    public String enterPasswordPage(@RequestParam(name = "password") String password,
                                    @RequestParam(name = "secondPassword") String secondPassword,
                                    HttpSession session) {

        if (!password.equals(secondPassword)) {
            session.setAttribute("passwordInvalid", "invalid");
            return "redirect:/restore/enterPassword";
        }
        if (invalidNamePassword(password)) {
            session.setAttribute("passwordNameInvalid", "invalid");
            return "redirect:/restore/enterPassword";
        }

        userService.updateUserPassword((String) session.getAttribute("email"), password);

        session.setAttribute("changePassword", "changePassword");
        session.removeAttribute("email");
        return "redirect:/login";
    }

    private boolean invalidNamePassword(String password) {
        return !password.matches("^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?!.*\\s).*$");
    }

}
