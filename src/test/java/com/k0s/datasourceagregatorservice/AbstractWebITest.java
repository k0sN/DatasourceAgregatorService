package com.k0s.datasourceagregatorservice;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.test.web.servlet.MockMvc;


@AutoConfigureMockMvc
public class AbstractWebITest extends AbstractBaseITest {

    @Autowired
    protected MockMvc mockMvc;

}
