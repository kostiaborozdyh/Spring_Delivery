package com.gmail.kostia_borozdyh.controller;

import com.gmail.kostia_borozdyh.entity.Review;
import com.gmail.kostia_borozdyh.entity.User;

import com.gmail.kostia_borozdyh.service.impl.ReviewService;
import com.gmail.kostia_borozdyh.service.impl.UserService;
import com.gmail.kostia_borozdyh.util.ConvertToDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpSession;
import java.security.Principal;
import java.sql.Date;
import java.time.LocalDate;

@Controller
public class GuestController {
    private UserService userService;
    private ReviewService reviewService;


    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @Autowired
    public void setReviewService(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    @GetMapping("/")
    public String getIndexPage(Principal principal, HttpSession session) {
        User user = (User) session.getAttribute("user");

        if (principal != null && user == null) {
            user = userService.findByLogin(principal.getName());
            user.setPassword("");
            session.setAttribute("user", user);
        }

        if (user != null) {
            switch (user.getRole().getRole()) {
                case "MANAGER":
                    return "redirect:/order";
                case "ADMIN":
                    return "redirect:/adm/usersTable";
                case "EMPLOYEE":
                    return "redirect:/employee/ordersTable";
            }
        }

        return "index";
    }

    @PostMapping("/")
    public String getPostIndexPage() {
        return "redirect:/";
    }

    @GetMapping("/login")
    public String getLoginPage(Model model, HttpSession session) {
        model.addAttribute("registration", session.getAttribute("registration"));
        session.removeAttribute("registration");
        model.addAttribute("changePassword", session.getAttribute("changePassword"));
        session.removeAttribute("changePassword");
        return "login";
    }

    @GetMapping("/lout")
    public String getLogoutPage() {
        return "logout";
    }

    @GetMapping("/aboutUs")
    public String getAboutUsPage() {
        return "aboutUs";
    }

    @GetMapping("/reviews")
    public String getReviewPage(Model model) {
        model.addAttribute("reviews", ConvertToDTO.fromListReview(reviewService.getAllReviews()));
        return "reviews";
    }

    @PostMapping("/addReview")
    public String addReview(@ModelAttribute Review review, HttpSession session) {
        User user = (User) session.getAttribute("user");
        review.setUser(user);
        review.setDate(Date.valueOf(LocalDate.now()));
        reviewService.addReview(review);
        return "redirect:/reviews";
    }

    @GetMapping("/registration")
    public String getRegistrationPage() {
        return "redirect:/register";
    }


}
