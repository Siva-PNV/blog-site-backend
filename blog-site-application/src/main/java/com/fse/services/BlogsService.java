package com.fse.services;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;

import com.fse.exceptions.BlogsNotFoundException;
import com.fse.exceptions.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fse.modals.BlogsModal;
import com.fse.repositories.BlogsRepository;

@Service
public class BlogsService {
	@Autowired
	private BlogsRepository blogsRepository;
	
	public BlogsModal createNewBlog(BlogsModal blogsModal,String blogName,String userName) throws Exception {
		try {
			BlogsModal blogsModalNew = new BlogsModal();
			LocalDateTime myDateObj = LocalDateTime.now();
			DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern("yyyy-MM-dd");
			String formattedDate = myDateObj.format(myFormatObj);
			blogsModalNew.setBlogName(blogName);
			blogsModalNew.setArticle(blogsModal.getArticle());
			blogsModalNew.setAuthorName(blogsModal.getAuthorName());
			blogsModalNew.setCategory(blogsModal.getCategory());
			blogsModalNew.setCreationTimeStamp(formattedDate);
			blogsModalNew.setUserName(userName);

			return blogsRepository.save(blogsModalNew);
		}catch (Exception e){
			throw  new UserNotFoundException("User not found");
		}
	}
	
	public BlogsModal deleteBlog(String blogName) throws Exception {
		try {
		return blogsRepository.deleteByBlogName(blogName);
		}
		catch(Exception e) {
			throw new BlogsNotFoundException("Blog not found");
		}
	}
	
	public boolean isBlogAvailable(String blogName) {
		List<BlogsModal> blogModal=blogsRepository.findByBlogName(blogName);
		return blogModal.isEmpty();
	}
	
	public List<BlogsModal> getAllBlogs() throws Exception{
		try {
			List<BlogsModal> sortedBlogs=blogsRepository.findAll();
			sortedBlogs.sort(Comparator.comparing(BlogsModal::getCreationTimeStamp).reversed());
			return sortedBlogs;
		}
		catch(Exception e) {
			throw new BlogsNotFoundException("Blogs not found");
		}
	}

	public List<BlogsModal> getBlogsByCategory(String category) throws Exception {
		List<BlogsModal> modals=blogsRepository.findAll();
		List<BlogsModal> filteredModals=new ArrayList<>();
		if(!modals.isEmpty()) {
			for (BlogsModal blogsModal : modals) {
				if(Objects.equals(blogsModal.getCategory(), category)) {
					filteredModals.add(blogsModal);
				}
			}
			filteredModals.sort(Comparator.comparing(BlogsModal::getCreationTimeStamp).reversed());
			return filteredModals;
		}
		else {
			throw new BlogsNotFoundException("Blogs not found for category "+category);
		}
	}

	public List<BlogsModal> getAllBlogsCategoriesByRange(String category, String durationFromRang, String durationToRang) throws Exception {
		List<BlogsModal> filteredBlogs=new ArrayList<BlogsModal>();
		List<BlogsModal> dateRangeBlogs=blogsRepository.getObjectByCreationTimeStamp(durationFromRang, durationToRang);
		System.out.print("dateRangeBlogs"+dateRangeBlogs);
		for (BlogsModal blogsModal : dateRangeBlogs) {
			if(Objects.equals(blogsModal.getCategory(), category)) {
				filteredBlogs.add(blogsModal);
			}
		}
		filteredBlogs.sort(Comparator.comparing(BlogsModal::getCreationTimeStamp).reversed());
		return filteredBlogs;
		
	}


	public List<BlogsModal> getMyBlogs(String userName) throws Exception {
		List<BlogsModal> modals=blogsRepository.findAll();
		List<BlogsModal> filteredModals=new ArrayList<BlogsModal>();
		if(!modals.isEmpty()) {
			for (BlogsModal blogsModal : modals) {
				if(Objects.equals(blogsModal.getUserName(), userName)) {
					filteredModals.add(blogsModal);
				}
			}
			filteredModals.sort(Comparator.comparing(BlogsModal::getCreationTimeStamp).reversed());
			return filteredModals;
		}
		else {
			throw new BlogsNotFoundException("Blogs not found for user "+userName);
		}
	}
}
