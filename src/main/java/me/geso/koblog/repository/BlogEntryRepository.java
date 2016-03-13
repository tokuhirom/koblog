package me.geso.koblog.repository;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;
import lombok.ToString;
import me.geso.koblog.domain.BlogEntry;
import me.geso.koblog.domain.Paginated;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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

    public Paginated<BlogEntry> getBlogEntries(long page, long limit) {
        List<BlogEntry> entries = blogEntries.stream()
                .skip((page - 1) * limit)
                .limit(limit + 1)
                .collect(Collectors.toList());

        boolean hasNext = false;
        if (entries.size() == limit + 1) {
            entries.remove(entries.size() - 1);
            hasNext = true;
        }
        return new Paginated<>(page, limit, entries, hasNext);
    }

    public Paginated<BlogEntry> search(String keyword, long page, int limit) {
        List<BlogEntry> entries = blogEntries.stream()
                .filter(entry -> entry.getTitle().contains(keyword)
                        || entry.getBody().contains(keyword))
                .skip((page - 1) * limit)
                .limit(limit + 1)
                .collect(Collectors.toList());

        boolean hasNext = false;
        if (entries.size() == limit + 1) {
            entries.remove(entries.size() - 1);
            hasNext = true;
        }
        return new Paginated<>(page, limit, entries, hasNext);
    }
}
