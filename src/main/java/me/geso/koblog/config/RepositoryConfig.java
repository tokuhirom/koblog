package me.geso.koblog.config;

import lombok.extern.slf4j.Slf4j;
import me.geso.koblog.runner.FileBlogEntryLoader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.IOException;

@Component
@Slf4j
public class RepositoryConfig {
    @Autowired
    private FileBlogEntryLoader fileBlogEntryLoader;

    @PostConstruct
    public void init() throws IOException {
        try {
            fileBlogEntryLoader.loadAll();
        } catch (IOException e) {
            log.error("Got IOException while loading entries", e);
            throw e;
        }
    }
}
