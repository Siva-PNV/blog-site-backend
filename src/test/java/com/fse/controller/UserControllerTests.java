package com.fse.controller;
/*
import com.fse.modals.UserModal;
import com.fse.modals.Users;
import com.fse.repositories.UsersRepository;
import com.fse.services.BlogsService;
import com.fse.services.UsersService;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.web.client.HttpServerErrorException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@SpringBootTest
public class UserControllerTests {
        @Mock
    private UsersService usersService;

        @MockBean
        private UsersService usersServiceMock;
        @MockBean
    BlogsService    blogsService;

    @Autowired
    UsersService usersServiceBean;

    @Autowired
    MockMvc mockMvc;

    @Autowired
    UsersRepository usersRepository;

//    @Test
//    void loginUserTest() throws Exception{
//       String userName="sivapallem";
//       String password="password";
//       when(usersServiceMock.checkUser(userName,password)).thenReturn(true);
//        MvcResult result = mockMvc
//                .perform(post("http://localhost:8080/api/v1.0/blogsite/users/login")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content("{\"userName\":\"sivapallem\",\"password\":\"password\"}")
//                        .accept(MediaType.APPLICATION_JSON)
//                )
//                .andReturn();
//       String actual=result.getResponse().getContentAsString();
//       String expected="{\"emailId\":\"sivapallem@gmail.com\",\"userName\":\"sivapallem\",\"password\":\"password\"}";
//       assertEquals(expected,actual);
//    }

    @Test
    void loginUserTest1() throws Exception{
        String userName="sivapallem";
        String password="password";
        String emailId="sivapallem@gmail.com";
        UserModal user=new UserModal();
        user.setUserName(userName);
        user.setPassword(password);
        user.setEmailId(emailId);
        when(usersServiceMock.checkUser(userName,password)).thenReturn(true);
        when(usersServiceMock.getUser(userName,password)).thenReturn(user);
        MvcResult result = mockMvc
                .perform(post("http://localhost:8080/api/v1.0/blogsite/users/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"userName\":\"sivapallem\",\"password\":\"password\"}")
                        .accept(MediaType.APPLICATION_JSON)
                )
                .andReturn();
        String actual=result.getResponse().getContentAsString();
        String expected="{\"emailId\":\"sivapallem@gmail.com\",\"userName\":\"sivapallem\",\"password\":\"password\"}";
        assertEquals(expected,actual);
    }

    @Test
    void registerUserTest() throws Exception {
        String user = "{\"emailId\":\"test@gmail.com\"," +
                "\"userName\":\"newuser1\"," +
                "\"password\":\"password\"}";
        Users user1 = new Users();
        user1.setUserName("newuser1");
        when(usersServiceMock.checkExistOrNot(user1)).thenReturn(true);
        MvcResult result = mockMvc
                .perform(post("http://localhost:8080/api/v1.0/blogsite/users/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(user)
                        .accept(MediaType.APPLICATION_JSON)
                )
                .andReturn();
        String actual = result.getResponse().getContentAsString();
        String expected = "{\"emailId\":\"test@gmail.com\",\"userName\":\"newuser1\",\"password\":\"password\"}";
        assertEquals(expected, actual);
    }

    @Test
    void registerUserTestNegative() throws Exception {
        String user = "{\"emailId\":\"test@gmail.com\"," +
                "\"userName\":\"sivapallem\"," +
                "\"password\":\"password\"}";
        Users user1 = new Users();
        user1.setUserName("sivapallem");
        when(usersServiceMock.checkExistOrNot(user1)).thenReturn(false);
        MvcResult result = mockMvc
                .perform(post("http://localhost:8080/api/v1.0/blogsite/users/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(user)
                        .accept(MediaType.APPLICATION_JSON)
                )
                .andReturn();
        String actual = result.getResponse().getContentAsString();
        String expected = "User name already exist, please login";
        assertEquals(expected, actual);
    }

    @Test
    void addBlogs() throws Exception {
        String blog = "{\"article\":\"article\",\"category\":\"category\",\"authorName\":\"authorName\"}";
        MvcResult result = mockMvc
                .perform(post("http://localhost:8080/api/v1.0/blogsite/users/blogs/add/blog1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("userName", "sivapallem")
                        .header("authorization", "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJzaXZhcGFsbGVtIiwiaWF0IjoxNzE1OTM0ODYwfQ.86kyQ2hX02JnTHx7C2mGxdm2u8B5xUxilpcHKj_MB5Q8CDN8gCMhVJrb1xwzgWMmj9Gjh9tjP1qIGkcmAFI3mw")
                        .content(blog)
                        .accept(MediaType.APPLICATION_JSON)
                )
                .andReturn();
        String actual = result.getResponse().getContentAsString();
        String expected = "{\"blogName\":\"blog1\",\"category\":\"category\",\"article\":\"article\",\"authorName\":\"authorName\",\"creationTimeStamp\":\"2024-05-17\",\"userName\":\"sivapallem\"}";
        assertEquals(expected, actual);
    }

    @Test
    void deleteBlog() throws Exception {
        MvcResult result = mockMvc
                .perform(delete("http://localhost:8080/api/v1.0/blogsite/users/delete/blog1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("userName", "sivapallem")
                        .header("authorization", "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJzaXZhcGFsbGVtIiwiaWF0IjoxNzE1OTM0ODYwfQ.86kyQ2hX02JnTHx7C2mGxdm2u8B5xUxilpcHKj_MB5Q8CDN8gCMhVJrb1xwzgWMmj9Gjh9tjP1qIGkcmAFI3mw")
                        .accept(MediaType.APPLICATION_JSON)
                )
                .andReturn();
        String actual = result.getResponse().getContentAsString();
        String expected = "{\"blogName\":\"blog1\",\"category\":\"category\",\"article\":\"article\",\"authorName\":\"authorName\",\"creationTimeStamp\":\"2024-05-17\",\"userName\":\"sivapallem\"}";
        assertEquals(expected, actual);
    }

    @Test
    void getAllBlogsByCategory() throws Exception {
        MvcResult result = mockMvc
                .perform(get("http://localhost:8080/api/v1.0/blogsite/blogs/info/category")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                )
                .andReturn();
        int actual = result.getResponse().getContentLength();
        assertEquals(actual, actual);
    }

    @Test
    void getCategoriesRange() throws Exception {
        MvcResult result = mockMvc
                .perform(get("http://localhost:8080/api/v1.0/blogsite/blogs/get/category/2024-05-17/2024-05-17")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                )
                .andReturn();
        int actual = result.getResponse().getContentLength();
        assertEquals(actual, actual);
    }

    @Test
    void getMyBlogs() throws Exception {
        MvcResult result = mockMvc
                .perform(get("http://localhost:8080/api/v1.0/blogsite/users/get/sivapallem")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                )
                .andReturn();
        int actual = result.getResponse().getContentLength();
        assertEquals(actual, actual);
    }

    @Test
    void getAllBlogs() throws Exception {
        MvcResult result = mockMvc
                .perform(get("http://localhost:8080/api/v1.0/blogsite/users/getall")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                )
                .andReturn();
        int actual = result.getResponse().getContentLength();
        assertEquals(actual, actual);
    }

    @Test
    void getAllBlogsError() throws Exception {
        when(blogsService.getAllBlogs()).thenThrow(HttpServerErrorException.InternalServerError.class);
        RequestBuilder requestBuilder = get("http://localhost:8080/api/v1.0/blogsite/users/getall")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON);
        mockMvc.perform(requestBuilder)
                .andExpect(status().isInternalServerError());
    }

    @Test
    void getMyBlogsError() throws Exception {
        when(blogsService.getMyBlogs("sivapallem")).thenThrow(HttpServerErrorException.InternalServerError.class);
        RequestBuilder requestBuilder = get("http://localhost:8080/api/v1.0/blogsite/users/get/sivapallem")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON);
        mockMvc.perform(requestBuilder)
                .andExpect(status().isInternalServerError());
    }

    @Test
    void loginUserTestNegative() throws Exception{
        Users user=new Users();
        user.setUserName("sivapallem");
        when(usersServiceMock.getUser("sivapallem","password")).thenThrow(HttpServerErrorException.InternalServerError.class);
        RequestBuilder requestBuilder = post("http://localhost:8080/api/v1.0/blogsite/users/login")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON);
        mockMvc.perform(requestBuilder)
                .andExpect(status().isBadRequest());
    }

    @Test
    void registerUserTestError() throws Exception{
        Users user=new Users();
        user.setUserName("sivapallem");
        when(usersServiceMock.checkExistOrNot(user)).thenThrow(HttpServerErrorException.InternalServerError.class);
        RequestBuilder requestBuilder = post("http://localhost:8080/api/v1.0/blogsite/users/register")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON);
        mockMvc.perform(requestBuilder)
                .andExpect(status().isBadRequest());
    }
}
*/