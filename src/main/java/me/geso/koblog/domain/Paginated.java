package me.geso.koblog.domain;

import lombok.Value;

import java.util.List;

@Value
public class Paginated<T> {
    private final long page;
    private final long limit;
    private final List<T> entries;
    private final boolean hasNext;
}
