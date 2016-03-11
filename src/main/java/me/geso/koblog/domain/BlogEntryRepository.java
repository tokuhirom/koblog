package me.geso.koblog.domain;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;
import lombok.ToString;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Getter
@ToString
@EqualsAndHashCode
@Scope("singleton")
@Component
public class BlogEntryRepository {
    private final List<BlogEntry> blogEntries;

    public BlogEntryRepository() {
        this.blogEntries = new ArrayList<>();
    }

    public synchronized void addEntry(BlogEntry blogEntry) {
        blogEntries.add(blogEntry);
    }

    public Optional<BlogEntry> findByPath(@NonNull String path) {
        return blogEntries.stream()
                .filter(it -> path.equals(it.getPath()))
                .findFirst();
    }
}
