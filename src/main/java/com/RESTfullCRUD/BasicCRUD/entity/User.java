package com.RESTfullCRUD.BasicCRUD.entity;

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
public class User{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long userId;
    private String userName;
    private String password;
    private Integer contact;
    private String email;
    @OneToOne(cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    @JoinColumn(name ="FK_addressID", referencedColumnName = "addID")
    private Address address;

//    @OneToMany(cascade = CascadeType.MERGE)
//    @JoinColumn(name = "FK_vendorId" , referencedColumnName = "vendorId")
//    private List<Address> address;
//

}
