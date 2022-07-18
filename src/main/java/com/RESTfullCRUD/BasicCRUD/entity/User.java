package com.RESTfullCRUD.BasicCRUD.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Set;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
//@Table(name = "user_Data")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    @NotNull(message = "enter username")
    @Column(unique = true, nullable = false)
    private String userName;

//    @Convert(converter = AESCrypto.class)
    private String password;

//    @Convert(converter = AESCrypto.class)
    private String citizenNo;

//    @Convert(converter = AESCrypto.class)
    @Column(length =  5000)
    private String contact;

    private String email;

    @JsonIgnore
    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
//    @JoinTable(name = "User_Role", joinColumns = {@JoinColumn(name = "userId")}, inverseJoinColumns = {@JoinColumn(name = "roleId")})
    @JoinColumn(name = "Role_id", referencedColumnName = "roleId")
    private Set<Role> role;

//    @JsonIgnore
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "FK_addressID", referencedColumnName = "addID")
    private Address address;

    @JsonIgnore
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name ="FK_vendorID", referencedColumnName = "userID")
    private List<Product> productList ;

    public void addRole(Role role) {
        this.role.add(role);
    }
}
