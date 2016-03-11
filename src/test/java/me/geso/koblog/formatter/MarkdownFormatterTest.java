package me.geso.koblog.formatter;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;


public class MarkdownFormatterTest {

    @Test
    public void format() throws Exception {
        MarkdownFormatter markdownFormatter = new MarkdownFormatter();
        assertThat(markdownFormatter.format("# hoge"))
                .isEqualTo("<h1 id=\"hoge\">hoge</h1>\n");
    }
}