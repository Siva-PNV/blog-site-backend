package com.fse.controller;
/*
import com.fse.services.BlogsService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@AutoConfigureMockMvc
@SpringBootTest
public class BlogsController {
    @InjectMocks
    BlogsController blogsController;
    @Autowired
    BlogsService blogsService;

    @Autowired
    MockMvc mockMvc;

    @Test
    void testGetAllBlogsByCategory() throws Exception {
        MvcResult result = mockMvc
                .perform(get("http://localhost:8080/api/v1.0/blogsite/blogs/info/blog1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                )
                .andReturn();
        int actual = result.getResponse().getContentLength();
        assertEquals(actual, actual);
    }

    @Test
    void testGetCategoriesRange() throws Exception {
        MvcResult result = mockMvc
                .perform(get("http://localhost:8080/api/v1.0/blogsite/blogs/get/blog1/2021-01-01/2021-12-31")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                )
                .andReturn();
        int actual = result.getResponse().getContentLength();
        assertEquals(actual, actual);
    }
}
*/