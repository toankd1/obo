package com.company.demo.repository;

import com.company.demo.entity.Order;
import com.company.demo.model.dto.OrderDetailDto;
import com.company.demo.model.dto.OrderInfoDto;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

  @Query(nativeQuery = true, name = "getListOrderOfPersonByStatus")
  List<OrderInfoDto> getListOrderOfPersonByStatus(int status, long userId);

  @Query(nativeQuery = true, name = "userGetDetailById")
  OrderDetailDto userGetDetailById(long id, long userId);

  int countByProductId(String id);

  @Query(nativeQuery = true, value = "SELECT * FROM orders " +
      "WHERE id LIKE ?1 AND receiver_name LIKE CONCAT('%',?2,'%') " +
      "AND receiver_phone LIKE CONCAT('%',?3,'%') AND status LIKE ?4 " +
      "AND product_id LIKE ?5")
  Page<Order> adminGetListOrder(String id, String name, String phone, String status,
      String product, Pageable page);
}
