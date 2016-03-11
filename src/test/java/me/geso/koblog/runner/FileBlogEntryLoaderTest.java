package me.geso.koblog.runner;

import lombok.extern.slf4j.Slf4j;
import me.geso.koblog.domain.BlogEntry;
import me.geso.koblog.domain.BlogEntryRepository;
import me.geso.koblog.formatter.MarkdownFormatter;
import me.geso.koblog.settings.KoblogSettings;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
@Slf4j
public class FileBlogEntryLoaderTest {

    @Mock
    private KoblogSettings koblogSettings;
    @Mock
    private MarkdownFormatter markdownFormatter;
    @Mock
    private BlogEntryRepository blogEntryRepository;
    @InjectMocks
    private FileBlogEntryLoader underTest;

    @Test
    public void parseEntryFile() throws Exception {
        URL resource =
                this.getClass().getClassLoader().getResource("hoge.md");
        Path path = Paths.get(resource.toURI());

        log.info("Path: {}", path);
        when(koblogSettings.getFilePath())
                .thenReturn(path.getParent().toString());

        BlogEntry blogEntry = underTest.parseEntryFile(path);
        assertThat(blogEntry.getPath())
                .isEqualTo("hoge.md");
        assertThat(blogEntry.getTitle())
                .isEqualTo("foo");
        assertThat(blogEntry.getFormatter())
                .isInstanceOf(MarkdownFormatter.class);
        assertThat(blogEntry.getBody())
                .isEqualTo("bar");
    }
}