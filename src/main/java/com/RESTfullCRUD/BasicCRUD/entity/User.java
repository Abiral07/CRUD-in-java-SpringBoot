package com.RESTfullCRUD.BasicCRUD.entity;

import com.RESTfullCRUD.BasicCRUD.config.AESCrypto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

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
    private String userName;

//    @Convert(converter = AESCrypto.class)
    private String password;

//    @Convert(converter = AESCrypto.class)
    private String citizenNo;

//    @Convert(converter = AESCrypto.class)
    @Column(length =  5000)
    private String contact;

    private String email;
    private Role role;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "FK_addressID", referencedColumnName = "addID")
    private Address address;

//    @OneToMany(cascade = CascadeType.MERGE)
//    @JoinColumn(name = "FK_vendorId" , referencedColumnName = "vendorId")
//    private List<Address> address;
//

}

enum Role {
    user,
    vendor,
    admin
}
