package com.dslab.video_management_service.services;

import com.dslab.video_management_service.entity.User;
import com.dslab.video_management_service.entity.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
public class UserService {
    @Autowired
    UserRepository repository;

    public User addUser(User user){
        return repository.save(user);
    }
    public Iterable<User> getUsers() {return repository.findAll();}
}
