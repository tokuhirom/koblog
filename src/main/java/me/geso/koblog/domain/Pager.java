package me.geso.koblog.domain;

import lombok.AllArgsConstructor;
import lombok.Value;

import java.util.List;
import java.util.stream.Collectors;

@Value
public class Pager {
    private final long page;
    private final long limit;
    private final long totalEntries;

    public Pager(long page, long limit, long totalEntries) {
        this.page = page;
        this.limit = limit;
        this.totalEntries = totalEntries;
    }

    public <T> List<T> slice(List<T> entries) {
        return entries.stream()
                .skip((page - 1) * limit)
                .limit(limit)
                .collect(Collectors.toList());
    }

    public boolean hasNext() {
        return totalEntries > page * limit;
    }
}
