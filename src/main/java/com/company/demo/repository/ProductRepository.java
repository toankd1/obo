package com.company.demo.repository;

import com.company.demo.entity.Product;
import com.company.demo.model.dto.ProductInfoDto;
import com.company.demo.model.dto.ShortProductInfoDto;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, String> {

  @Query(nativeQuery = true, name = "getListBestSellerProduct")
  List<ProductInfoDto> getListBestSellerProduct(int limit);

  @Query(nativeQuery = true, name = "getListNewProduct")
  List<ProductInfoDto> getListNewProduct(int limit);

  @Query(nativeQuery = true, name = "getListSuggestProduct")
  List<ProductInfoDto> getListSuggestProduct(List<String> products, int limit);

  @Query(nativeQuery = true, name = "getRelatedProducts")
  List<ProductInfoDto> getRelatedProducts(String id, int limit);

  @Query(nativeQuery = true, name = "searchProductByKeyword")
  List<ProductInfoDto> searchProductByKeyword(@Param("keyword") String keyword,
      @Param("limit") int limit, @Param("offset") int offset);

  @Query(nativeQuery = true, value = "SELECT count(DISTINCT product.id)\n" +
      "FROM product \n" +
      "INNER JOIN product_category \n" +
      "ON product.id = product_category.product_id \n" +
      "INNER JOIN category\n" +
      "ON category.id = product_category.category_id\n" +
      "WHERE product.is_available = true AND (product.name LIKE CONCAT('%',:keyword,'%') OR category.name LIKE CONCAT('%',:keyword,'%'))\n")
  int countProductByKeyword(@Param("keyword") String keyword);

  @Query(nativeQuery = true, name = "searchProductBySize")
  List<ProductInfoDto> searchProductBySize(List<Integer> brands, List<Integer> categories,
      long minPrice, long maxPrice, List<Integer> sizes, int limit, int offset);

  @Query(nativeQuery = true, value = "SELECT COUNT(DISTINCT d.id)\n" +
      "FROM (\n" +
      "SELECT DISTINCT product.id\n" +
      "FROM product \n" +
      "INNER JOIN product_category \n" +
      "ON product.id = product_category.product_id \n" +
      "WHERE product.is_available = true AND product.brand_id IN (?1) AND product_category.category_id IN (?2)\n"
      +
      "AND product.price > ?3 AND product.price < ?4) as d\n" +
      "INNER JOIN product_size \n" +
      "ON product_size.product_id = d.id\n" +
      "WHERE product_size.size IN (?5)")
  int countProductBySize(List<Integer> brands, List<Integer> categories, long minPrice,
      long maxPrice, List<Integer> sizes);

  @Query(nativeQuery = true, name = "searchProductAllSize")
  List<ProductInfoDto> searchProductAllSize(List<Integer> brands, List<Integer> categories,
      long minPrice, long maxPrice, int limit, int offset);

  @Query(nativeQuery = true, value = "SELECT COUNT(DISTINCT product.id)\n" +
      "FROM product \n" +
      "INNER JOIN product_category \n" +
      "ON product.id = product_category.product_id \n" +
      "WHERE product.is_available = true AND product.brand_id IN (?1) AND product_category.category_id IN (?2)\n"
      +
      "AND product.price > ?3 AND product.price < ?4\n")
  int countProductAllSize(List<Integer> brands, List<Integer> categories, long minPrice,
      long maxPrice);

  @Query(nativeQuery = true, value = "SELECT DISTINCT product.*\n" +
      "FROM product\n" +
      "INNER JOIN product_category\n" +
      "ON product.id = product_category.product_id\n" +
      "INNER JOIN category\n" +
      "ON category.id = product_category.category_id\n" +
      "WHERE product.id LIKE CONCAT('%',?1,'%') AND product.name LIKE CONCAT('%',?2,'%') AND category.id LIKE ?3 AND product.brand_id LIKE ?4 \n"
      +
      "ORDER BY ?5 ?6 \n" +
      "LIMIT ?7\n" +
      "OFFSET ?8")
  List<Product> adminGetListProduct(String id, String name, String category, String brand,
      String order, String direction, int limit, int offset);

  @Query(nativeQuery = true, value = "SELECT count(distinct product.id)\n" +
      "FROM product\n" +
      "INNER JOIN product_category\n" +
      "ON product.id = product_category.product_id\n" +
      "INNER JOIN category\n" +
      "ON category.id = product_category.category_id\n" +
      "WHERE product.id LIKE CONCAT('%',:id,'%') AND product.name LIKE CONCAT('%',:name,'%') AND category.id LIKE :category AND product.brand_id LIKE :brand \n")
  int countAdminGetListProduct(@Param("id") String id, @Param("name") String name,
      @Param("category") String category, @Param("brand") String brand);

  int countByBrandId(int id);

  @Query(nativeQuery = true, name = "getAllProduct")
  List<ShortProductInfoDto> getAllProduct();

  @Query(nativeQuery = true, name = "getAvailableProducts")
  List<ShortProductInfoDto> getAvailableProducts();
}
