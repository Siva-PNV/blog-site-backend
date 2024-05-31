package com.fse.controller;

import java.util.Collection;

import com.fse.services.Producer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.CrossOrigin;
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
@CrossOrigin(origins = "https://blogs-site.azurewebsites.net/")
public class UserController {
	private static final String SOMETHING_WENT_WRONG = "Something went wrong";
	private static final String UNAUTHORIZED = "Unauthorized";
	public static final String INVALID_CREDENTIALS = "Invalid credentials";
	public static final String USER_NAME_ALREADY_EXIST_PLEASE_LOGIN = "User name already exist, please login";
	public static final String BLOG_NAME_NOT_FOUND = "Blog name not found";
	public static final String BLOGS_COULD_NOT_SAVED = "Blogs could not saved";

		@Autowired
	    private JwtTokenUtil jwtTokenUtil;

	    @Autowired
	    private JwtUserDetailsService userDetailsService;

	    @Autowired
	    private UsersService usersService;
	    
	    @Autowired
	  	private BlogsService blogsService;

		@Autowired
		private Producer kafkaProducer;

	    JwtResponse jwtResponse=new JwtResponse();
	    
	    UserDetails loginCredentials;
			    	
		@PostMapping("/blogs/add/{blogName}")
		public ResponseEntity<?> addNewBlog(@RequestHeader String authorization, @RequestBody BlogsModal blogsModal,
												 @PathVariable String blogName,@RequestHeader String userName) {
			 getUserDetails(userName);
			 if(authorization !=null && jwtTokenUtil.validateToken(authorization, loginCredentials)) {
				 try {
					 //kafkaProducer.sendMessage(blogsModal);
					 return new ResponseEntity<>(blogsService.createNewBlog(blogsModal,blogName,userName), HttpStatus.CREATED);
				 }
				 catch(Exception e) {
					 return new ResponseEntity<>(BLOGS_COULD_NOT_SAVED, HttpStatus.INTERNAL_SERVER_ERROR);
				 }
			 }
			 return new ResponseEntity<>(UNAUTHORIZED, HttpStatus.UNAUTHORIZED);
		}
		
		@DeleteMapping("/delete/{blogName}")
		public ResponseEntity<?> deleteBlogName(@PathVariable String blogName,@RequestHeader String authorization,
												@RequestHeader String userName) throws Exception {
			 getUserDetails(userName);
			 if(authorization !=null && jwtTokenUtil.validateToken(authorization, loginCredentials)) {
				 try {
						if(!blogsService.isBlogAvailable(blogName)) {
							return new ResponseEntity<>(blogsService.deleteBlog(blogName), HttpStatus.CREATED);
						}else {
							return new ResponseEntity<>(BLOG_NAME_NOT_FOUND, HttpStatus.NOT_FOUND);
						}
						}
						catch(Exception e) {
							return new ResponseEntity<>(SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
						}
			 }
				return new ResponseEntity<>(UNAUTHORIZED, HttpStatus.UNAUTHORIZED);
			
		}
		
		@GetMapping("/getall")
		public ResponseEntity<?> getAllBlogs(){
			try {
				return new ResponseEntity<>( blogsService.getAllBlogs(), HttpStatus.OK);
			}
			catch(Exception e) {
				return new ResponseEntity<>(SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
			}
		}

	@GetMapping("/get/{userName}")
	public ResponseEntity<?> getMyBlogs(@PathVariable String userName){
		try {
			return new ResponseEntity<>( blogsService.getMyBlogs(userName), HttpStatus.OK);
		}
		catch(Exception e) {
			return new ResponseEntity<>(SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	    @PostMapping("/register")
	    public ResponseEntity<?> registerNewUser(@RequestBody Users users) {
			try{
				if (!usersService.checkExistOrNot(users)) {
					return new ResponseEntity<>(usersService.storeUserDetails(users), HttpStatus.CREATED);
				}
				return new ResponseEntity<>(USER_NAME_ALREADY_EXIST_PLEASE_LOGIN,
					HttpStatus.CONFLICT);
			} catch (Exception e) {
				return new ResponseEntity<>(SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
			}
	    }

	    @PostMapping("/login")
	    public ResponseEntity<?> loginUser(@RequestBody LoginCredentials user) throws UsernameNotFoundException {
			try {
				if (usersService.checkUser(user.getUserName(), user.getPassword())) {
					final UserDetails userDetails = userDetailsService
							.loadUserByUsername(user.getUserName());
					final String token = jwtTokenUtil.generateToken(userDetails);
					jwtResponse.setJwttoken(token);
					return new ResponseEntity<>(usersService.getUser(user.getUserName(), user.getPassword()), HttpStatus.OK);
				}
				return new ResponseEntity<>(INVALID_CREDENTIALS, HttpStatus.BAD_REQUEST);
			} catch (Exception e) {
				return new ResponseEntity<>(SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
			}
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
