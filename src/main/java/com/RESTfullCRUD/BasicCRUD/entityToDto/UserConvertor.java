package com.RESTfullCRUD.BasicCRUD.entityToDto;

import com.RESTfullCRUD.BasicCRUD.dto.requestDTO.RegistrationDto;
import com.RESTfullCRUD.BasicCRUD.dto.responseDTO.UserResponseDto;
import com.RESTfullCRUD.BasicCRUD.entity.Role;
import com.RESTfullCRUD.BasicCRUD.entity.User;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
@Component
public class UserConvertor {
    public UserResponseDto entityToDto(User user){
        UserResponseDto dto = new UserResponseDto();
        dto.setUserName(user.getUserName());
        dto.setEmail(user.getEmail());
        dto.setContact(user.getContact());
        dto.setAddress(user.getAddress());
        return dto;
    }

    public List<UserResponseDto> entityToDto(List<User> user){
        return user.stream().map(x -> entityToDto(x)).collect(Collectors.toList());
    }

    public User DtoToEntity(UserResponseDto dto){
        User user = new User();
        user.setUserName(dto.getUserName());
        user.setEmail(dto.getEmail());
        user.setContact(dto.getContact());
        user.setAddress(dto.getAddress());
        return user;
    }
    public User RegistrationDtoToUser(RegistrationDto dto, Set<Role> role){
        User user = new User();
        user.setUserName(dto.getUserName());
        user.setPassword(dto.getPassword());
        user.setCitizenNo(dto.getCitizenNo());
        user.setContact(dto.getContact());
        user.setEmail(dto.getEmail());
        user.setAddress(dto.getAddress());
        user.setRole(role);
        return user;
    }

    public List<User> DtoToEntity(List<UserResponseDto> dto){
        return dto.stream().map(x -> DtoToEntity(x)).collect(Collectors.toList());
    }
}
