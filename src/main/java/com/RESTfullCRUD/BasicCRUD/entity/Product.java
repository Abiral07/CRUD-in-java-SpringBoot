package com.RESTfullCRUD.BasicCRUD.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "Product_table")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long prodID;

    @NotBlank(message = "Please product name")
    private String prodName;
    private String prodType;
    private double price;

    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name ="FK_vendorID", referencedColumnName = "vendorID")
    private Vendor vendor;

}
