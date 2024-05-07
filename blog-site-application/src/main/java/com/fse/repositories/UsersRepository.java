package com.fse.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import com.fse.modals.UserModal;
import com.fse.modals.Users;

@Repository
public interface UsersRepository extends MongoRepository<Users,String> {
    Users findByUserName(String userName);
    Boolean existsByUserName(String userName);

    @Query("{userName : ?0, password : ?1}")
    UserModal findUserByUsernameAndPassword(String userName, String password);

}