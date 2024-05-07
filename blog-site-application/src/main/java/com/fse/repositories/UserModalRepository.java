package com.fse.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.fse.modals.UserModal;

@Repository
public interface UserModalRepository extends MongoRepository<UserModal,String> {
    UserModal findByUserName(String userName);
}
