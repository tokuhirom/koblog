package me.geso.koblog.controller;

import lombok.extern.slf4j.Slf4j;
import me.geso.koblog.domain.BlogEntry;
import me.geso.koblog.domain.BlogEntryRepository;
import me.geso.koblog.domain.Pager;
import me.geso.koblog.exception.NotFoundException;
import me.geso.koblog.settings.KoblogSettings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

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
        List<BlogEntry> entries = blogEntryRepository.getBlogEntries();
        Pager pager = new Pager(page, limit, entries.size());
        model.addAttribute("entries", pager.slice(entries));
        model.addAttribute("pager", pager);
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
    public String search(@PathVariable("keyword") String keyword, Model model) {
        model.addAttribute("keyword", keyword);
        return "search";
    }
}