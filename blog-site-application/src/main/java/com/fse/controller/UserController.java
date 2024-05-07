package com.fse.controller;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fse.auth.JwtResponse;
import com.fse.auth.JwtTokenUtil;
import com.fse.modals.BlogsModal;
import com.fse.modals.LoginCredentials;
import com.fse.modals.Users;
import com.fse.services.BlogsService;
import com.fse.services.JwtUserDetailsService;
import com.fse.services.UsersService;

@RestController
@RequestMapping("/api/v1.0/blogsite/users")
public class UserController {

	    @Autowired
	    private JwtTokenUtil jwtTokenUtil;

	    @Autowired
	    private JwtUserDetailsService userDetailsService;

	    @Autowired
	    private UsersService usersService;
	    
	    @Autowired
	  	private BlogsService blogsService;

	    JwtResponse jwtResponse=new JwtResponse();
	    
	    UserDetails loginCredentials;
			    	
		@PostMapping("/blogs/add/{blogName}")
		public ResponseEntity<?> registerNewBlog(@RequestHeader String Authorization, @RequestBody BlogsModal blogsModal,@PathVariable String blogName,@RequestHeader String userName) {
			 getUserDetails(userName);
			 if(Authorization !=null && jwtTokenUtil.validateToken(Authorization, loginCredentials)) {
				 try {
						return new ResponseEntity<>(blogsService.createNewBlog(blogsModal,blogName), HttpStatus.CREATED);
						}
						catch(Exception e) {
							return new ResponseEntity<>("Blogs could not saved", HttpStatus.INTERNAL_SERVER_ERROR);
						}	 
			 }
			return new ResponseEntity<>("un authorized", HttpStatus.UNAUTHORIZED);
		}
		
		@DeleteMapping("/delete/{blogName}")
		public ResponseEntity<?> deleteBlogName(@PathVariable String blogName,@RequestHeader String Authorization,@RequestHeader String userName) throws Exception {
			 getUserDetails(userName);
			 if(Authorization !=null && jwtTokenUtil.validateToken(Authorization, loginCredentials)) {
				 try {
						if(!blogsService.isBlogAvailable(blogName)) {
							return new ResponseEntity<>(blogsService.deleteBlog(blogName), HttpStatus.CREATED);
						}else {
							return new ResponseEntity<>("Blog name not found", HttpStatus.NOT_FOUND);
						}
						}
						catch(Exception e) {
							return new ResponseEntity<>("Something went wrong", HttpStatus.INTERNAL_SERVER_ERROR);
						}
			 }
				return new ResponseEntity<>("un authorized", HttpStatus.UNAUTHORIZED);
			
		}
		
		@GetMapping("/getall")
		public ResponseEntity<?> getAllBlogs(){
			try {
				return new ResponseEntity<>( blogsService.getAllBlogs(), HttpStatus.OK);
			}
			catch(Exception e) {
				return new ResponseEntity<>("Something went wrong", HttpStatus.INTERNAL_SERVER_ERROR);
			}
		}
		

	    @PostMapping("/register")
	    public ResponseEntity<?> registerNewUser(@RequestBody Users users) {
			 
	        if(!usersService.checkExistOrNot(users)){

	            return new ResponseEntity<>(usersService.storeUserDetails(users), HttpStatus.CREATED);
	        }
	        return new ResponseEntity<>("User name already exist, please login",
	                HttpStatus.CONFLICT);
	    }

	    @PostMapping("/login")
	    public ResponseEntity<?> loginUser(@RequestBody LoginCredentials user) throws Exception {
	        if (usersService.checkUser(user.getUserName(), user.getPassword())) {
	            final UserDetails userDetails = userDetailsService
	                    .loadUserByUsername(user.getUserName());
	            final String token = jwtTokenUtil.generateToken(userDetails);
	            jwtResponse.setJwttoken(token);
	            return new ResponseEntity<>(usersService.getUser(user.getUserName(), user.getPassword()), HttpStatus.OK);
	        }
	        return new ResponseEntity<>("Invalid credentials", HttpStatus.BAD_REQUEST);
	    }
	    
	    @GetMapping("/jwt/authentication")
	    public ResponseEntity<?> getToken(){
	        return new ResponseEntity<>(jwtResponse,HttpStatus.OK);
	    }
	    
	    public Users getUserDetails(String userName) {
	    	 Users user=usersService.getByUserName(userName);
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
