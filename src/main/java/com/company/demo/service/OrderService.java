package com.company.demo.service;

import com.company.demo.entity.Order;
import com.company.demo.model.dto.OrderDetailDto;
import com.company.demo.model.dto.OrderInfoDto;
import com.company.demo.model.request.CreateOrderReq;
import com.company.demo.model.request.UpdateDetailOrderReq;
import com.company.demo.model.request.UpdateStatusOrderReq;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Service
public interface OrderService {

  Order createOrder(CreateOrderReq req, long userId);

  List<OrderInfoDto> getListOrderOfPersonByStatus(int status, long userId);

  OrderDetailDto userGetDetailById(long id, long userId);

  void userCancelOrder(long id, long userId);

  Page<Order> adminGetListOrder(String id, String name, String phone, String status,
      String product, int page);

  Order getOrderById(long id);

  void updateDetailOrder(UpdateDetailOrderReq req, long id, long userId);

  void updateStatusOrder(UpdateStatusOrderReq req, long id, long userId);
}
