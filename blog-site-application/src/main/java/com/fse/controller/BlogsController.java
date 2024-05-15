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
@CrossOrigin(origins = "http://localhost:4200")
public class BlogsController {

	@Autowired
	BlogsService blogsService;
	
	@Autowired
	UsersService userService;
	
	UserDetails loginCredentials;
	
	private final JwtTokenUtil jwtTokenUtil=new JwtTokenUtil();
	
	@GetMapping("info/{category}")
	public ResponseEntity<?> getAllBlogsByCategory(@PathVariable String category) throws Exception{
//		 getUserDetails(userName);
//		  if(Authorization!=null && jwtTokenUtil.validateToken(Authorization, loginCredentials)  ){
				return new ResponseEntity<>(blogsService.getBlogsByCategory(category),HttpStatus.OK);
		  //}
		//return new ResponseEntity<>("Unauthorized",HttpStatus.UNAUTHORIZED);
	}
	
	@GetMapping("get/{category}/{durationFromRang}/{durationToRang}")
	public ResponseEntity<?> getCategoriesRange(@PathVariable String category, @PathVariable String durationFromRang,@PathVariable String durationToRang) throws Exception{
		return new ResponseEntity<>(blogsService.getAllBlogsCategoriesByRange(category,durationFromRang,durationToRang),HttpStatus.OK);
	}
	
	public Users getUserDetails(String userName) {
        Users user=userService.getByUserName(userName);
        loginCredentials = new UserDetails() {
            /**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
            public Collection<? extends GrantedAuthority> getAuthorities() {
                return null;
            }

            @Override
            public String getPassword() {
                return user.getPassword();
            }

            @Override
            public String getUsername() {
                return user.getUserName();
            }

            @Override
            public boolean isAccountNonExpired() {
                return false;
            }

            @Override
            public boolean isAccountNonLocked() {
                return false;
            }

            @Override
            public boolean isCredentialsNonExpired() {
                return false;
            }

            @Override
            public boolean isEnabled() {
                return false;
            }
        };
        return user;
    }
	
	
}
