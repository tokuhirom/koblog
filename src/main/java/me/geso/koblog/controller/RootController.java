package me.geso.koblog.controller;

import lombok.extern.slf4j.Slf4j;
import me.geso.koblog.domain.BlogEntry;
import me.geso.koblog.domain.Paginated;
import me.geso.koblog.exception.NotFoundException;
import me.geso.koblog.repository.BlogEntryRepository;
import me.geso.koblog.settings.KoblogSettings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@Controller
public class RootController {
    @Autowired
    private KoblogSettings koblogSettings;
    @Autowired
    private BlogEntryRepository blogEntryRepository;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String index(
            @RequestParam(value = "page", defaultValue = "1") long page,
            Model model
    ) {
        log.info(koblogSettings.getFilePath());
        long limit = 20;
        Paginated<BlogEntry> paginated = blogEntryRepository.getBlogEntries(page, limit);
        model.addAttribute("entries", paginated.getEntries());
        model.addAttribute("pager", paginated);
        return "index";
    }

    // FIXME: better path handling is required.
    @RequestMapping(value = "/entry/**", method = RequestMethod.GET)
    public String detail(HttpServletRequest request, Model model) throws NotFoundException {
        String path = request.getServletPath().substring("/entry/".length());
        log.debug("accessing {}", path);
        BlogEntry entry = blogEntryRepository.findByPath(path)
                .orElseThrow(() -> new NotFoundException("There's no entry."));

        model.addAttribute("entry", entry);

        return "detail";
    }

    @RequestMapping(value = "/search/{keyword}", method = RequestMethod.GET)
    public String search(
            @PathVariable("keyword") String keyword,
            @RequestParam(value = "page", defaultValue = "1") long page,
            Model model) {
        int limit = 20;
        model.addAttribute("keyword", keyword);
        Paginated<BlogEntry> paginated = blogEntryRepository.search(keyword, page, limit);
        model.addAttribute("entries", paginated.getEntries());
        model.addAttribute("pager", paginated);
        return "search";
    }
}