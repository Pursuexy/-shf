package com.atguigu;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * @description:
 * @author: xyf
 * @date: 2023/1/5 0:52
 */
public class Test {
    public static void main(String[] args) {
        System.out.println(new BCryptPasswordEncoder().encode("12345"));
    }
}
