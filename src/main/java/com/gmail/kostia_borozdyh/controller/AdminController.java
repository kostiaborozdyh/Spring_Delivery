package com.gmail.kostia_borozdyh.controller;

import com.gmail.kostia_borozdyh.service.impl.UserService;
import com.gmail.kostia_borozdyh.util.Calculate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;


@Controller
@RequestMapping(value = "/adm")
public class AdminController {
    private UserService userService;

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }


    @GetMapping("/usersTable")
    public String getUsersTablePage(HttpSession session, Model model) {
        Integer id = 1;

        if (session.getAttribute("pageNumberUser") == null) {
            int count = Math.toIntExact(userService.countUser());
            List<Integer> list = Calculate.getPaginationList(count);
            session.setAttribute("listNumberUser", list);
            session.setAttribute("previousPageUser", 0);
            session.setAttribute("pageNumberUser", 1);
        } else {
            id = (Integer) session.getAttribute("pageNumberUser");
        }

        model.addAttribute("userList", userService.getUserLimit((id - 1) * 5));

        return "adm/usersTable";
    }

    @PostMapping("/blockUser")
    public String blockUser(HttpServletRequest request) {
        Integer id = Integer.valueOf(request.getParameter("id"));

        userService.blockUser(id);

        return "redirect:/adm/usersTable";
    }

    @PostMapping("/unblockUser")
    public String unblockUser(HttpServletRequest request) {
        Integer id = Integer.valueOf(request.getParameter("id"));

        userService.unblockUser(id);

        return "redirect:/adm/usersTable";
    }

    @PostMapping("/deleteUser")
    public String deleteUser(HttpServletRequest request) {
        Integer id = Integer.valueOf(request.getParameter("id"));

        userService.deleteUser(id);

        return "redirect:/adm/usersTable";
    }

    @GetMapping("/createEmployeeAccount")
    public String getAddEmployeePage() {
        return "redirect:/register";
    }
}
