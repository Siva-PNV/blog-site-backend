package com.fse.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.fse.modals.UserModal;
import com.fse.modals.Users;
import com.fse.repositories.UserModalRepository;
import com.fse.repositories.UsersRepository;

import java.util.List;

@Service
public class UsersService {
    @Autowired
    private UsersRepository usersRepository;

    @Autowired
    private UserModalRepository userModelRepository;

    public Users storeUserDetails(Users user){
        usersRepository.save(user);
        return user;
    }

    public boolean checkExistOrNot(Users user){
        return usersRepository.existsByUserName(user.getUserName());
    }

    public boolean checkUser(String userName,String password){
        Users tempUser = usersRepository.findByUserName(userName);
        return  (tempUser!=null && tempUser.getUserName().equals(userName) && tempUser.getPassword().equals(password));
    }

    public UserModal getUser(String userName, String password){
        return usersRepository.findUserByUsernameAndPassword(userName,password);
    }

    public boolean forgotPassword(String userName, String newPassword){
       Users user= usersRepository.findByUserName(userName);
       if(user !=null){
           user.setPassword(newPassword);
           usersRepository.save(user);
           return true;
       }
       return false;
    }

    public List<UserModal> getAllUsers(){
        return userModelRepository.findAll();
    }

    public Users getByUserName(String userName){
        return usersRepository.findByUserName(userName);
    }


    public UserModal getDetailsOfUser(String userName){
        return userModelRepository.findByUserName(userName);
    }
}
