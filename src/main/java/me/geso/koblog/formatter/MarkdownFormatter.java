package me.geso.koblog.formatter;

import io.github.gitbucket.markedj.Marked;
import org.springframework.stereotype.Component;

@Component
public class MarkdownFormatter implements Formatter {
    @Override
    public String format(String src) {
        return Marked.marked(src);
    }
}
