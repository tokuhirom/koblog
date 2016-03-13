package me.geso.koblog.controller;

import lombok.extern.slf4j.Slf4j;
import me.geso.koblog.repository.BlogEntryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Slf4j
@Controller
public class AdminController {
    @Autowired
    private BlogEntryRepository blogEntryRepository;

    @RequestMapping(value = "/admin/", method = RequestMethod.GET)
    public String index(
            Model model
    ) {
        return "admin/index";
    }
}
