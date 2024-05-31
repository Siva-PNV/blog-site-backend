package com.fse.controller;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fse.auth.JwtTokenUtil;
import com.fse.modals.Users;
import com.fse.services.BlogsService;
import com.fse.services.UsersService;

@RestController
@RequestMapping("/api/v1.0/blogsite/blogs/")
@CrossOrigin(origins = "https://blogs-site.azurewebsites.net/login")
public class BlogsController {

    public static final String SOMETHING_WENT_WRONG = "Something went wrong";
    @Autowired
	BlogsService blogsService;
	
	@Autowired
	UsersService userService;
	
	UserDetails loginCredentials;
	
	private final JwtTokenUtil jwtTokenUtil=new JwtTokenUtil();
	
	@GetMapping("info/{category}")
	public ResponseEntity<?> getAllBlogsByCategory(@PathVariable String category) throws Exception{
        try {
            return new ResponseEntity<>(blogsService.getBlogsByCategory(category), HttpStatus.OK);
        }catch (Exception e) {
            return new ResponseEntity<>(SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
        }

	}
	
	@GetMapping("get/{category}/{durationFromRang}/{durationToRang}")
	public ResponseEntity<?> getCategoriesRange(@PathVariable String category, @PathVariable String durationFromRang,
                                                @PathVariable String durationToRang) throws Exception{
		try {
            return new ResponseEntity<>(blogsService.getAllBlogsCategoriesByRange(category, durationFromRang, durationToRang), HttpStatus.OK);
        }catch (Exception e) {
            return new ResponseEntity<>(SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
        }
	}
}
