package xyz.tcbuildmc.common.config.test;

import org.junit.jupiter.api.Test;

import java.util.Arrays;

public class StringTest {
    @Test
    public void splitTest() {
        String s = "114514";

        String[] splited = s.split("\\.");
        System.out.println(Arrays.toString(splited));
        System.out.println(splited.length);
    }
}
