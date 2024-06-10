package com.example.demo.service.impl;

import com.example.demo.dto.User_infoDto;
import com.example.demo.exception.UserNotFoundException;
import com.example.demo.repository.User_infoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class User_infoService {
    private final User_infoRepository userInfoRepository;

    @Autowired
    public User_infoService(User_infoRepository userInfoRepository) {
        this.userInfoRepository = userInfoRepository;
    }

//    public User_infoDto getInfo(Long id){
//        return User_infoDto.valueOf(userInfoRepository.findByUser_id(id);
//    }
}
