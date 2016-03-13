package me.geso.koblog.repository;

import me.geso.koblog.domain.BlogEntry;
import me.geso.koblog.domain.Paginated;
import org.junit.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.assertThat;

public class BlogEntryRepositoryTest {
    @Test
    public void getBlogEntries() throws Exception {
        BlogEntryRepository blogEntryRepository = new BlogEntryRepository();

        IntStream.rangeClosed(1, 3)
                .forEach(
                        i -> blogEntryRepository.addEntry(BlogEntry.builder()
                                .title("TITLE: " + i)
                                .build())
                );

        {
            Paginated<BlogEntry> got = blogEntryRepository.getBlogEntries(1, 2);
            assertThat(got.getPage()).isEqualTo(1);
            assertThat(got.getLimit()).isEqualTo(2);
            assertThat(got.getEntries()).isEqualTo(Arrays.asList(
                    BlogEntry.builder().title("TITLE: 1").build(),
                    BlogEntry.builder().title("TITLE: 2").build()
            ));
            assertThat(got.isHasNext()).isTrue();
        }
        {
            Paginated<BlogEntry> got = blogEntryRepository.getBlogEntries(2, 2);
            assertThat(got.getPage()).isEqualTo(2);
            assertThat(got.getLimit()).isEqualTo(2);
            assertThat(got.getEntries()).isEqualTo(Collections.singletonList(
                    BlogEntry.builder().title("TITLE: 3").build()
            ));
            assertThat(got.isHasNext()).isFalse();
        }
    }
}