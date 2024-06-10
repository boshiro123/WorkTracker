package com.example.demo.dto;

import com.example.demo.entity.User;
import com.example.demo.entity.User_info;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class User_infoDto {
    private Long id;
    private Double experience;
    private String city;
    private String position;
    private String male;
    private String message;
    private Double bet;

    public static User_infoDto valueOf(User_info user_info) {
        return new User_infoDto(
                user_info.getId(),
                user_info.getExperience(),
                user_info.getCity(),
                user_info.getPosition(),
                user_info.getMale(),
                user_info.getMessage(),
                user_info.getBet()
        );
    }
    public User_info mapToUser() {
        return new User_info(id,experience,city,position,male,message,bet);
    }

}
