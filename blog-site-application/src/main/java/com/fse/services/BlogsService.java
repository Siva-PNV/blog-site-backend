package com.fse.services;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fse.modals.BlogsModal;
import com.fse.repositories.BlogsRepository;

@Service
public class BlogsService {
	@Autowired
	private BlogsRepository blogsRepository;
	
	public BlogsModal createNewBlog(BlogsModal blogsModal,String blogName) {
		BlogsModal blogsModalNew=new BlogsModal();
			LocalDateTime myDateObj = LocalDateTime.now();
	        DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern("dd-MM-yyyy");
	        String formattedDate = myDateObj.format(myFormatObj);
			blogsModalNew.setBlogName(blogName);
			blogsModalNew.setArticle(blogsModal.getArticle());
			blogsModalNew.setAuthorName(blogsModal.getAuthorName());
			blogsModalNew.setCategory(blogsModal.getCategory());
			blogsModalNew.setCreationTimeStamp(formattedDate);
			return blogsRepository.save(blogsModalNew);
	}
	
	public BlogsModal deleteBlog(String blogName) throws Exception {
		try {
		return blogsRepository.deleteByBlogName(blogName);
		}
		catch(Exception e) {
			throw new Exception();
		}
	}
	
	public boolean isBlogAvailable(String blogName) {
		System.out.println("isBlogAvailable");
		List<BlogsModal> blogModal=blogsRepository.findByBlogName(blogName);
		System.out.println("blogModal.isEmpty() "+blogModal.isEmpty());
		return blogModal.isEmpty();
	}
	
	public List<BlogsModal> getAllBlogs() throws Exception{
		try {
			return blogsRepository.findAll();
		}
		catch(Exception e) {
			throw new Exception();
		}
	}

	public List<BlogsModal> getBlogsByCategory(String category) throws Exception {
		List<BlogsModal> modals=blogsRepository.findAll();
		System.out.print("all "+modals);
		List<BlogsModal> filteredModals=new ArrayList<BlogsModal>();
		if(!modals.isEmpty()) {
			for (BlogsModal blogsModal : modals) {
				if(Objects.equals(blogsModal.getCategory(), category)) {
					filteredModals.add(blogsModal);
				}
			}
			return filteredModals;
		}
		else {
			throw new Exception();
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
		return filteredBlogs;
		
	}
	

}
