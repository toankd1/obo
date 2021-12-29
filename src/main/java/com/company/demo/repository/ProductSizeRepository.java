package com.company.demo.repository;

import com.company.demo.entity.ProductSize;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface ProductSizeRepository extends JpaRepository<ProductSize, Long> {

  @Query(nativeQuery = true, value = "SELECT size FROM product_size WHERE product_id = ?1 AND quantity > 0")
  List<Integer> findAllSizeOfProduct(String id);

  @Query(nativeQuery = true, value = "SELECT * FROM product_size WHERE product_id = ?1 AND size = ?2 AND quantity > 0")
  ProductSize checkProductSizeAvailable(String productId, int size);

  @Transactional
  @Modifying
  @Query(nativeQuery = true, value = "Delete from product_size where product_id = ?1")
  void deleteByProductId(String id);

  List<ProductSize> findByProductId(String id);

  @Transactional
  @Modifying
  @Query(nativeQuery = true, value = "Update product_size set quantity = quantity - 1 where product_id = ?1 and size = ?2")
  void minusOneProductBySize(String id, int size);

  @Transactional
  @Modifying
  @Query(nativeQuery = true, value = "Update product_size set quantity = quantity + 1 where product_id = ?1 and size = ?2")
  void plusOneProductBySize(String id, int size);
}
