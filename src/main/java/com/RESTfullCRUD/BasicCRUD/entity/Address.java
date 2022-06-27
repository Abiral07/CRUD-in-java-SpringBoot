package com.RESTfullCRUD.BasicCRUD.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
@Table(name = "address")
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long addID;
    private String country;
    private String province;
    private String city;
    private Short ward;
    private String type;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name="vendorId")
    private Vendor vendor;
}
