package com.example.demo.service.impl;

import com.example.demo.Hash.SHA256;
import com.example.demo.dto.UserDto;
import com.example.demo.entity.Status;
import com.example.demo.entity.User;
import com.example.demo.exception.StatusNotFoundException;
import com.example.demo.exception.UserNotFoundException;
import com.example.demo.repository.StatusRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.repository.User_infoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.NoSuchAlgorithmException;
import java.util.List;

@Service
public class UserService extends SHA256 {
    private final UserRepository userRepository;
    private final StatusRepository statusRepository;
    private final User_infoRepository userInfoRepository;
    @Autowired
    public UserService(UserRepository userRepository, StatusRepository statusRepository, User_infoRepository userInfoRepository) {
        this.userRepository = userRepository;
        this.statusRepository = statusRepository;
        this.userInfoRepository = userInfoRepository;
    }

    public boolean save(UserDto userdto) throws NoSuchAlgorithmException {
        try {
            UserDto userDto = UserDto.valueOf(userRepository.findByEmail(userdto.getEmail()));
        }catch (NullPointerException e){
            System.out.println("Пользователь не найдем");
            Status status = statusRepository.findById(3L).orElseThrow(() -> new StatusNotFoundException(3L));
            status.setName("common");
            status.setId(3L);
            User user = userdto.mapToUser();
            user.setPassword(toHexString(getSHA(user.getPassword())));
            user.setStatus(status);
            userRepository.save(user);
            return true;
        }
        return false;
    }

    public UserDto logIn(String email, String password) throws NoSuchAlgorithmException {
        UserDto userDto = new UserDto();
        try {
            userDto = UserDto.valueOf(userRepository.findByEmail(email));
        }catch (NullPointerException e){
            System.out.println("User is not Found");
            return null;
        }
        String checkPassHash = toHexString(getSHA(password));
        if (userDto.getPassword().equals(checkPassHash)){
            userDto.setPassword(password);
//            System.out.println(userDto);
            return userDto;
        }
        return null;
    }

    public UserDto fingById(Long id){
        UserDto userDto = userRepository.findById(id)
                .map(it -> UserDto.valueOf(it))
                .orElseThrow(() -> new UserNotFoundException(id));
        return userDto;
    }

    public List<UserDto> getUsers(){
        List<UserDto> list = UserDto.ListValueOf(userRepository.findAll());
        return list;
    }

    public void updateUser(UserDto user) throws NoSuchAlgorithmException {
        Long id = user.getId();
         User currentUser = userRepository.findById(id).orElseThrow(() -> new UserNotFoundException(id));
       User update = user.mapToUser();
        System.out.println(update);
       update.setPassword(toHexString(getSHA(user.getPassword())));
       userRepository.save(update);

    }
    public void updateMessage(UserDto user) throws NoSuchAlgorithmException {
        Long id = user.getId();
        User currentUser = userRepository.findById(id).orElseThrow(() -> new UserNotFoundException(id));
        User update = user.mapToUser();
        System.out.println(update);
        userRepository.save(update);

    }
    public void Block(UserDto user){
        Long id = user.getId();
        User currentUser = userRepository.findById(id).orElseThrow(() -> new UserNotFoundException(id));
        System.out.println("id: "+id);
        if(!user.getStatus().getName().equals("block")){
            user.setStatus(new Status(2L,"block"));

        } else if (user.getStatus().getName().equals("block")) {
            user.setStatus(new Status(3L,"common"));
        }
        User update = user.mapToUser();
        userRepository.save(update);
    }
}
