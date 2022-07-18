package com.RESTfullCRUD.BasicCRUD.repository;

import com.RESTfullCRUD.BasicCRUD.entity.Product;
import com.RESTfullCRUD.BasicCRUD.dto.responseDTO.ProductAndVendorDTO;
import com.RESTfullCRUD.BasicCRUD.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    List<Product> findByProdName(String prodName);

    @Query("select new com.RESTfullCRUD.BasicCRUD.dto.responseDTO.ProductAndVendorDTO(p.prodID,p.prodName,p.prodType,p.price,u.userName,u.contact,a.country,a.city,a.province) from Product p join p.vendor u join u.address a where u.userName= :vendorName and a.city= :city")
    List<ProductAndVendorDTO> findWithVendorAndCity(String vendorName, String city);

//    @Query("select v.userName from Product p join  p.vendor v where p.prodID = :productId ")
    @Query(value = "select u.user_name from crud.user as u join crud.product_table as p on u.user_id = p.fk_vendorid where p.prodID = :productId", nativeQuery = true)
    String getVendorName(Long productId);


//    @Query("SELECT new com.RESTfullCRUD.BasicCRUD.dto.joinDto(p.prodName, v,vendorName, a.country) FROM Product p join Vendor v on p.FK_vendorID = v.vendorID join Address a on v.vendorID=a.FK_vendorId WHERE v.vendorName = :vendorName and a.city = :city")
//    List<joinDto> fetchProductQueryToDto();
}
