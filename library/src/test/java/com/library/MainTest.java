package com.library;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class MainTest {

    @Test
    public void shouldFindMainScene() {
        assertThat(Main.class.getClassLoader().getResource(
                Main.getLoginWindowName())).isNotNull();
    }

}