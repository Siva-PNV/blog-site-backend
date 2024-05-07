package com.fse.repositories;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import com.fse.modals.BlogsModal;

@Repository
public interface BlogsRepository extends MongoRepository<BlogsModal,String>{
	
	 @Query("{blogName : ?0}")
	List<BlogsModal> findByBlogName(String blogName);
	 
	 @Query(value="{'blogName' : ?0}", delete = true)
	 BlogsModal deleteByBlogName(String blogName);
	 
	// @Query("{'creationTimeStamp' : { $gte: ?0, $lte: ?1 } }")                 
	 //public List<BlogsModal> getObjectByDate(String fromDateRange, String toDateRange);
	@Query("{'creationTimeStamp' : { $gte: ?0, $lte: ?1 } }")
	List<BlogsModal> getObjectByCreationTimeStamp(String durationFromRang, String durationToRang); 
}
