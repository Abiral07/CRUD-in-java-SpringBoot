package com.RESTfullCRUD.BasicCRUD.Controller;

import ch.qos.logback.core.util.ContentTypeUtil;
import com.RESTfullCRUD.BasicCRUD.constant.PathConstant;
import com.RESTfullCRUD.BasicCRUD.dto.requestDTO.RegistrationDto;
import com.RESTfullCRUD.BasicCRUD.dto.responseDTO.UserResponseDto;
import com.RESTfullCRUD.BasicCRUD.dto.responseDTO.LoginResponseJWT;
import com.RESTfullCRUD.BasicCRUD.entity.Role;
import com.RESTfullCRUD.BasicCRUD.entity.User;
import com.RESTfullCRUD.BasicCRUD.entityToDto.UserConvertor;
import com.RESTfullCRUD.BasicCRUD.exceptions.CustomException;
import com.RESTfullCRUD.BasicCRUD.repository.UserRepository;
import com.RESTfullCRUD.BasicCRUD.dto.requestDTO.LoginDto;
import com.RESTfullCRUD.BasicCRUD.service.UserService;
import jdk.jfr.ContentType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.io.IOException;
import java.net.http.HttpRequest;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.List;
import java.util.Map;
import java.util.Set;

@RestController
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private UserConvertor userConvertor;

    @Autowired private UserRepository userRepository;

    @GetMapping(PathConstant.GET_USER)
    public ResponseEntity<List<UserResponseDto>> fetchAllUser() throws CustomException {
        return new ResponseEntity<>(userConvertor.entityToDto(userService.fetchAllUser()), HttpStatus.ACCEPTED);
    }

    @GetMapping(PathConstant.GET_USERDTO)
    public ResponseEntity<List<UserResponseDto>> fetchAllUserDto() throws CustomException {
        return new ResponseEntity<>(userConvertor.entityToDto(userService.fetchAllUser()),HttpStatus.ACCEPTED);
    }

    @PostMapping(value = PathConstant.REGISTRATION)
    public  ResponseEntity<User> addUser(@RequestBody @Valid RegistrationDto user) throws Exception {
        return new ResponseEntity<>(userService.addUser(user),HttpStatus.ACCEPTED);

    }

    @GetMapping(PathConstant.GET_USER_ID)
    public ResponseEntity<UserResponseDto> fetchUserById(@PathVariable("id") Long id) throws Exception {
        return new ResponseEntity<>(userConvertor.entityToDto(userService.fetchUserById(id)),HttpStatus.ACCEPTED);
    }

    @Secured("ROLE_ADMIN")
    @GetMapping(PathConstant.GET_USER+"/name/{name}")
    public ResponseEntity<UserResponseDto> fetchUserByName(@PathVariable("name") String name) throws CustomException {
        return new ResponseEntity<>(userConvertor.entityToDto(userService.fetchUserByName(name)),HttpStatus.ACCEPTED);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PutMapping(PathConstant.UPDATE_USER)
    public ResponseEntity<UserResponseDto> updateUser(@PathVariable("id") Long id, @RequestBody UserResponseDto userResponseDto) throws InvalidAlgorithmParameterException, NoSuchPaddingException, IllegalBlockSizeException, NoSuchAlgorithmException, IOException, InvalidKeySpecException, BadPaddingException, InvalidKeyException, CustomException {
        User user = userConvertor.DtoToEntity(userResponseDto);
        return new ResponseEntity<>(userConvertor.entityToDto(userService.updateUser(id,user)),HttpStatus.ACCEPTED);
    }

    @GetMapping(PathConstant.GET_USER_BY_COUNTRY)
    public ResponseEntity<List<UserResponseDto>> fetchUserOfCountry(@PathVariable("country") String country){
        return new ResponseEntity<>(userConvertor.entityToDto(userService.fetchUserOfCountry(country)),HttpStatus.ACCEPTED);

    }

    @PostMapping(PathConstant.LOGIN)
    public ResponseEntity<LoginResponseJWT> userLogin(@RequestBody LoginDto loginDto, HttpServletRequest request) throws Exception {
        return ResponseEntity.ok(new LoginResponseJWT(userService.userLogin(request,loginDto)));
    }

    @GetMapping(PathConstant.GET_SESSION_DETAILS)
    public ResponseEntity<Map<String,Object>> getSessionDetails(HttpServletRequest request) throws CustomException {
        return ResponseEntity.ok(userService.getSessionDetails(request));
    }
    @GetMapping(PathConstant.GET_CLAIMS_DETAILS)
    public ResponseEntity<Map<String,Object>> getClaimsDetail(@RequestHeader("Authorization") String bearerToken){
        return ResponseEntity.ok(userService.getClaimsDetail(bearerToken));
    }

}

