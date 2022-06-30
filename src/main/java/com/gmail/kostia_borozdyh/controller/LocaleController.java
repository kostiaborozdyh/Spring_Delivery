package com.gmail.kostia_borozdyh.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.logging.Logger;

@Controller
@Slf4j
public class LocaleController {
    @GetMapping("/international")
    public String changeLocale(@RequestParam(name = "lang") String lang, HttpSession session, HttpServletRequest request) {
        log.info("change language for " + lang);
        session.setAttribute("local", lang);

        String referer = request.getHeader("Referer");
        return "redirect:" + referer;
    }


}
