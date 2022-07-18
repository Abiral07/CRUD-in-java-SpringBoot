package com.RESTfullCRUD.BasicCRUD.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

//@NamedNativeQuery(name = "Product.findWithVendorAndCity",
//        query = "select p.prodid as pId,p.prod_name as pName,p.prod_type as pType,p.price as price,u.user_name as vendor,u.contact as contact,a.country as country,a.city as city,a.province as province from crud.product_table as p join crud.user as u on p.fk_vendorid = u.user_id  join crud.address as a on u.fk_addressid = a.addid where u.user_name = :vendorName and a.city = :city",
//        resultSetMapping = "Mapping.ProductAndVendorDto")
//@SqlResultSetMapping(name = "Mapping.ProductAndVendorDto",
//        classes = @ConstructorResult(targetClass = ProductAndVendorDTO.class,
//                columns = {@ColumnResult(name = "pId"),
//                        @ColumnResult(name = "pName"),
//                        @ColumnResult(name = "pType"),
//                        @ColumnResult(name = "price"),
//                        @ColumnResult(name = "vendor"),
//                        @ColumnResult(name = "contact"),
//                        @ColumnResult(name = "country"),
//                        @ColumnResult(name = "city"),
//                        @ColumnResult(name = "province"),}))
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "Product_table")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long prodID;

    @NotBlank(message = "Please product name")
    private String prodName;
    private String prodType;
    private Double price;

//    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    @JsonIgnore           //to ignore vendor and only fetch products
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name ="FK_vendorID", referencedColumnName = "userID")
    private User vendor;

}
