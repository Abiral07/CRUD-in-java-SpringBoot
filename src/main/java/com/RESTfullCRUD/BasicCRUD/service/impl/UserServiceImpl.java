package com.RESTfullCRUD.BasicCRUD.service.impl;

import com.RESTfullCRUD.BasicCRUD.config.AESCustomCryptoWithIV;
import com.RESTfullCRUD.BasicCRUD.config.RSA;
import com.RESTfullCRUD.BasicCRUD.dto.CustomUserDetails;
import com.RESTfullCRUD.BasicCRUD.dto.requestDTO.RegistrationDto;
import com.RESTfullCRUD.BasicCRUD.entity.Role;
import com.RESTfullCRUD.BasicCRUD.entity.User;
import com.RESTfullCRUD.BasicCRUD.entityToDto.UserConvertor;
import com.RESTfullCRUD.BasicCRUD.exceptions.CustomException;
import com.RESTfullCRUD.BasicCRUD.repository.RoleRepository;
import com.RESTfullCRUD.BasicCRUD.repository.UserRepository;
import com.RESTfullCRUD.BasicCRUD.dto.requestDTO.LoginDto;
import com.RESTfullCRUD.BasicCRUD.service.UserService;
import com.RESTfullCRUD.BasicCRUD.utils.JwtTokenUtil;
import com.RESTfullCRUD.BasicCRUD.utils.JwtUserDetailsService;
import io.jsonwebtoken.Claims;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.*;
import java.util.stream.Collectors;

@Log4j2
@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    UserConvertor userConvertor;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private AESCustomCryptoWithIV aesCustomCryptoWithIV;
    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private JwtUserDetailsService userDetailsService;

    RSA rsa = new RSA();

    public UserServiceImpl() throws IOException, NoSuchAlgorithmException {
    }

    HttpSession sessionObj;
    @Override
    public String userLogin(HttpServletRequest request, LoginDto loginDto) throws Exception {
        authenticate(loginDto.getUserName(), loginDto.getPassword());
//        ----------------SESSION------------------------------------
        sessionObj = request.getSession();      //returns old session if it exists else returns null
        if(sessionObj == null)
            sessionObj = request.getSession(true);      //creating new session if session doesn't exist
        final CustomUserDetails userDetails = userDetailsService.loadUserByUsername(loginDto.getUserName());
        String jwtToken = jwtTokenUtil.generateToken(userDetails);
        sessionObj.setAttribute("token", jwtToken);
        sessionObj.setAttribute("userName", userDetails.getUsername());
        sessionObj.setAttribute("Roles", userDetails.getAuthorities());
        sessionObj.setAttribute("Random", generateRandomPassword(10));
//        ----------------------SESSION-------------------------------------
        return jwtToken;
    }
    private void authenticate(String username, String password) throws Exception {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        } catch (DisabledException e) {
            throw new Exception("USER_DISABLED", e);
        } catch (BadCredentialsException e) {
            throw new Exception("INVALID_CREDENTIALS", e);
        }
    }

    @Override
    public List<User> fetchAllUser() {
        List<User> user = userRepository.findAll();
        return user.stream().map((x)-> {
            try {
                return decryptColumns(x);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }).collect(Collectors.toList());
    }

    @Override
    public User addUser(RegistrationDto regUser) throws CustomException {
        Set<Role> role = new HashSet<>() ;
        role.add(roleRepository.findByRoleName("USER"));
        User user = userConvertor.RegistrationDtoToUser(regUser, role);
        user.setRole(role);
        User user1 = encryptColumns(user);
        return userRepository.save(user1);
    }

    @Override
    public User fetchUserById(Long id) throws Exception {
        User user= userRepository.findById(id).get();
        return decryptColumns(user);
    }

    @Override
    public User fetchUserByName(String name) throws CustomException {
        return userRepository.findByUserName(name);
    }

    @Override
    public User updateUser(Long id, User user) throws NoSuchPaddingException, IllegalBlockSizeException, NoSuchAlgorithmException, IOException, InvalidKeySpecException, BadPaddingException, InvalidKeyException, InvalidAlgorithmParameterException, CustomException {
        User newUser = userRepository.findById(id).get();

        if (Objects.nonNull(user.getUserName()) && !"".equalsIgnoreCase(user.getUserName())) {
            if (Objects.nonNull(userRepository.findByUserName(user.getUserName()))) {
                throw new CustomException("DUBLICATE USER NAME");
            }else
                newUser.setUserName(user.getUserName());
        }
        if (Objects.nonNull(user.getPassword()) && !"".equalsIgnoreCase(user.getPassword())) {
            newUser.setPassword(passwordEncoder.encode(user.getPassword()));
        }
        if (Objects.nonNull(user.getEmail()) && !"".equalsIgnoreCase(user.getEmail())) {
            newUser.setEmail(user.getEmail());
        }
        if (Objects.nonNull(user.getContact()) && !"".equalsIgnoreCase(user.getContact())) {
            newUser.setContact(rsa.rsaEncrypt(user.getContact()));
        }
        if (Objects.nonNull(user.getCitizenNo()) && !"".equalsIgnoreCase(user.getCitizenNo())) {
            newUser.setCitizenNo(aesCustomCryptoWithIV.encrypt("AES/CBC/PKCS5Padding",user.getCitizenNo()));
        }
//        if (Objects.nonNull(user.getRole()) && !ObjectUtils.isEmpty(user.getRole())) {
//            newUser.setRole(user.getRole());
//        }
        if (Objects.nonNull(user.getAddress()) && !ObjectUtils.isEmpty(user.getAddress())) {
            newUser.setAddress(user.getAddress());
        }

        return userRepository.save(newUser);
    }

    @Override
    public List<User> fetchUserOfCountry(String country) {
        return userRepository.getByCountry(country);
    }

    @Override
    public Map<String, Object> getSessionDetails(HttpServletRequest request) throws CustomException {
        HashMap<String,Object> sessionDetails = new HashMap<>();
        sessionObj = request.getSession();      //returns old session if it exists else returns null
        if(sessionObj == null){
            throw new CustomException("no active session found");
        }
        sessionDetails.put("userName", sessionObj.getAttribute("userName"));
        sessionDetails.put("Roles", sessionObj.getAttribute("Roles"));
        sessionDetails.put("token", sessionObj.getAttribute("token"));
        sessionDetails.put("Random", sessionObj.getAttribute("Random"));
        return sessionDetails;
    }

    @Override
    public Map<String, Object> getClaimsDetail(String bearerToken) {
//        ----------------CLAIMS------------------
        HashMap<String,Object> claimsDetails = new HashMap<>();
        if(bearerToken != null){
            String token = bearerToken.substring(7);
            Claims claims = jwtTokenUtil.getAllClaimsFromToken(token);
            claimsDetails.put("expiration", claims.getExpiration());
            claimsDetails.put("email_from_token",claims.get("email"));
            claimsDetails.put("Roles_from_token",claims.get("Roles"));
        }
        return claimsDetails;
    }

    private User encryptColumns(User user) throws CustomException {
        try {
            if (Objects.nonNull(userRepository.findByUserName(user.getUserName()))) {
                throw new CustomException("DUBLICATE USER NAME");
            } else {
                //------------------AES Encryption----------------------
                user.setCitizenNo(aesCustomCryptoWithIV.encrypt("AES/CBC/PKCS5Padding", user.getCitizenNo()));
                //------------------------------------------------------

                //-------------------Bcrypt Encryption-----------------
                user.setPassword(passwordEncoder.encode(user.getPassword()));
                //-----------------------------------------------------
                //-------------------RSA Encryption-----------------

                user.setContact(rsa.rsaEncrypt(user.getContact()));
                //-----------------------------------------------------
                return (user);
            }
        } catch (Exception e) {
            log.error("Registration: " + e.getMessage());
            throw new CustomException(e.getMessage());
        }
    }

    private User decryptColumns(User user) throws Exception {
        user.setContact(rsa.rsaDecrypt(user.getContact()));
        user.setCitizenNo(aesCustomCryptoWithIV.decrypt("AES/CBC/PKCS5Padding", user.getCitizenNo()));
        return user;
    }

    public static String generateRandomPassword(int len) {
        String chars = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz!@#$%&";
        Random rnd = new Random();
        StringBuilder sb = new StringBuilder(len);
        for (int i = 0; i < len; i++)
            sb.append(chars.charAt(rnd.nextInt(chars.length())));
        return sb.toString();
    }
}