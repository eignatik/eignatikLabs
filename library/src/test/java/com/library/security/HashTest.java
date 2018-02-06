package com.library.security;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;


public class HashTest {
    @Test
    public void hash() throws Exception {
        String toHash = "password";
        assertThat(Hash.hash(toHash)).isEqualTo(Hash.hash(toHash));
    }

}