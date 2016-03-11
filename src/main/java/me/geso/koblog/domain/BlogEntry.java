package me.geso.koblog.domain;

import lombok.Builder;
import lombok.Value;
import me.geso.koblog.formatter.Formatter;

import java.time.ZonedDateTime;

@Value
@Builder
public class BlogEntry {
    private final String path;
    private final String title;
    private final String body;
    private final Formatter formatter;
    private final ZonedDateTime created;
    private final ZonedDateTime lastModified;
}
