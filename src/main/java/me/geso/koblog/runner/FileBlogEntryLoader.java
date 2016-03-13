package me.geso.koblog.runner;

import lombok.extern.slf4j.Slf4j;
import me.geso.koblog.domain.BlogEntry;
import me.geso.koblog.repository.BlogEntryRepository;
import me.geso.koblog.exception.UnknownHeaderException;
import me.geso.koblog.formatter.MarkdownFormatter;
import me.geso.koblog.settings.KoblogSettings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;
import java.time.ZoneId;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Service
@Slf4j
public class FileBlogEntryLoader {
    @Autowired
    private KoblogSettings koblogSettings;
    @Autowired
    private MarkdownFormatter markdownFormatter;
    @Autowired
    private BlogEntryRepository blogEntryRepository;
    private static final Pattern headerPattern = Pattern.compile("^(TITLE|FORMAT)\\s*:\\s*(.*)$");

    public void loadAll() throws IOException {
        Files.walk(Paths.get(koblogSettings.getFilePath())).forEach(filePath -> {
            if (Files.isRegularFile(filePath)) {
                String fileName = filePath.getFileName().toString();
                if (fileName.endsWith(".md") && !fileName.startsWith(".")) {
                    try {
                        loadFile(filePath);
                    } catch (IOException | UnknownHeaderException e) {
                        log.info("Cannot load {}", filePath, e);
                    }
                }
            }
        });
    }

    /**
     * Load blog entry file
     *
     * @return true if loaded, false otherwise
     */
    private boolean loadFile(Path filePath) throws IOException, UnknownHeaderException {
        BlogEntry blogEntry = parseEntryFile(filePath);
        if (blogEntry.getTitle() != null) {
            log.info("Loaded {}, {}", filePath, blogEntry.getTitle());
            blogEntryRepository.addEntry(blogEntry);
            return true;
        }
        return false;
    }

    public BlogEntry parseEntryFile(Path filePath) throws IOException, UnknownHeaderException {
        BasicFileAttributes attr = Files.readAttributes(filePath, BasicFileAttributes.class);
        BlogEntry.BlogEntryBuilder blogEntryBuilder = BlogEntry.builder()
                .lastModified(attr.lastModifiedTime().toInstant().atZone(ZoneId.systemDefault()))
                .created(attr.creationTime().toInstant().atZone(ZoneId.systemDefault()));

        blogEntryBuilder.path(
                Paths.get(koblogSettings.getFilePath())
                        .relativize(filePath)
                        .toString()
        );

        List<String> lines = Files.readAllLines(filePath);

        int i;
        for (i = 0; i < lines.size(); ++i) {
            String line = lines.get(i);
            if (line.length() == 0) {
                break;
            }

            Matcher matcher = headerPattern.matcher(line);
            if (matcher.matches()) {
                String headerName = matcher.group(1);
                String headerValue = matcher.group(2);
                switch (headerName) {
                    case "TITLE":
                        blogEntryBuilder.title(headerValue);
                        break;
                    case "FORMAT":
                        switch (headerValue) {
                            case "mkdn":
                                blogEntryBuilder.formatter(markdownFormatter);
                                break;
                            default:
                                throw new IllegalArgumentException("Unknown Format: " + headerValue);
                        }
                        break;
                    default:
                        throw new UnknownHeaderException(filePath, headerName);
                }
            }
        }
        String body = lines.stream()
                .skip(i)
                .collect(Collectors.joining());
        blogEntryBuilder.body(body);
        return blogEntryBuilder.build();
    }
}
