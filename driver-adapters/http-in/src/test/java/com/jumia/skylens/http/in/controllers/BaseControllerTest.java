package com.jumia.skylens.http.in.controllers;

import com.jumia.skylens.http.in.fakers.RestFaker;
import jakarta.annotation.Resource;
import org.springframework.test.web.servlet.MockMvc;

abstract class BaseControllerTest {

    @Resource
    protected MockMvc mvc;

    protected final RestFaker restFaker = new RestFaker();
}
