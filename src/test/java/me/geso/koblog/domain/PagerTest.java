package me.geso.koblog.domain;

import org.junit.Test;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.assertThat;


public class PagerTest {

    @Test
    public void slice() throws Exception {
        Pager pager = new Pager(1, 3, 100);
        List<String> got = pager.slice(IntStream.rangeClosed(1, 100).mapToObj(String::valueOf).collect(Collectors.toList()));
        assertThat(got)
                .isEqualTo(Arrays.asList("1", "2", "3"));
    }

    @Test
    public void hasNext() throws Exception {

    }
}