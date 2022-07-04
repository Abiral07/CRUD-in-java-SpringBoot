package com.RESTfullCRUD.BasicCRUD.repository;

import com.RESTfullCRUD.BasicCRUD.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    public List<Product> findByProdName(String prodName);

    @Query(value = "select * from product_table p join vendor v on p.fk_vendorid = v.vendorid join address a on v.vendorid=a.fk_vendor_id where v.vendor_name = :vendorName and a.city = :city"
    , nativeQuery = true)
    List<Object> findWithVendorAndCity(String vendorName, String city);

//    @Query(value = "select * from product_table p join vendor v on p.fk_vendorid = v.vendorid join address a on v.vendorid=a.fk_vendor_id where v.vendor_name = :vendorName and a.city = :city"
//            , nativeQuery = true)
//    Object findWithVendorAndCity(String vendorName, String city);


//    @Query("SELECT new com.RESTfullCRUD.BasicCRUD.dto.joinDto(p.prodName, v,vendorName, a.country) FROM Product p join Vendor v on p.FK_vendorID = v.vendorID join Address a on v.vendorID=a.FK_vendorId WHERE v.vendorName = :vendorName and a.city = :city")
//    List<joinDto> fetchProductQueryToDto();
}
