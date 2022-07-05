package com.RESTfullCRUD.BasicCRUD.entityToDto;

import com.RESTfullCRUD.BasicCRUD.dto.UpdateUserDto;
import com.RESTfullCRUD.BasicCRUD.entity.User;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;
@Component
public class UserConvertor {
    public UpdateUserDto entityToDto(User user){
        UpdateUserDto dto = new UpdateUserDto();
        dto.setUserName(user.getUserName());
        dto.setEmail(user.getEmail());
        dto.setContact(user.getContact());
        dto.setAddress(user.getAddress());
        return dto;
    }

    public List<UpdateUserDto> entityToDto(List<User> user){
        return user.stream().map(x -> entityToDto(x)).collect(Collectors.toList());
    }

    public User DtoToEntity(UpdateUserDto dto){
        User user = new User();
        user.setUserName(dto.getUserName());
        user.setEmail(dto.getEmail());
        user.setContact(dto.getContact());
        user.setAddress(dto.getAddress());
        return user;
    }

    public List<User> DtoToEntity(List<UpdateUserDto> dto){
        return dto.stream().map(x -> DtoToEntity(x)).collect(Collectors.toList());
    }
}
