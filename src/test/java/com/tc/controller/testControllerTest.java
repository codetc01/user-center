package com.tc.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @PROJECT_NAME: user-center
 * @DESCRIPTION:
 * @USER: Administrator
 * @DATE: 2023/11/14 23:42
 */
@SpringBootTest
public class testControllerTest {

    @Autowired
    private testController testController;

    @Test
    public void test(){
        System.out.println(testController.test("tian", "", ""));
    }
}
