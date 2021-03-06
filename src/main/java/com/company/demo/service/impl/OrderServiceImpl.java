package com.company.demo.service.impl;

import static com.company.demo.config.Constant.CANCELED_STATUS;
import static com.company.demo.config.Constant.COMPLETE_STATUS;
import static com.company.demo.config.Constant.DELIVERY_STATUS;
import static com.company.demo.config.Constant.LIST_ORDER_STATUS;
import static com.company.demo.config.Constant.ORDER_STATUS;
import static com.company.demo.config.Constant.RETURNED_STATUS;
import static com.company.demo.config.Constant.SIZE_CM;
import static com.company.demo.config.Constant.SIZE_US;
import static com.company.demo.config.Constant.SIZE_VN;

import com.company.demo.entity.Finance;
import com.company.demo.entity.Order;
import com.company.demo.entity.Product;
import com.company.demo.entity.ProductSize;
import com.company.demo.entity.Promotion;
import com.company.demo.entity.User;
import com.company.demo.exception.BadRequestException;
import com.company.demo.exception.InternalServerException;
import com.company.demo.exception.NotFoundException;
import com.company.demo.model.dto.OrderDetailDto;
import com.company.demo.model.dto.OrderInfoDto;
import com.company.demo.model.request.CreateOrderReq;
import com.company.demo.model.request.UpdateDetailOrderReq;
import com.company.demo.model.request.UpdateStatusOrderReq;
import com.company.demo.repository.FinanceRepository;
import com.company.demo.repository.OrderRepository;
import com.company.demo.repository.ProductRepository;
import com.company.demo.repository.ProductSizeRepository;
import com.company.demo.service.OrderService;
import com.company.demo.service.PromotionService;
import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class OrderServiceImpl implements OrderService {

  @Autowired
  private OrderRepository orderRepository;

  @Autowired
  private ProductRepository productRepository;

  @Autowired
  private ProductSizeRepository productSizeRepository;

  @Autowired
  private PromotionService promotionService;

  @Autowired
  private FinanceRepository financeRepository;

  @Override
  public Order createOrder(CreateOrderReq req, long userId) {
    Order order = new Order();

    // Check product and size
    Optional<Product> product = productRepository.findById(req.getProductId());
    if (product.isEmpty()) {
      throw new NotFoundException("S???n ph???m kh??ng t???n t???i");
    }
    ProductSize productSize = productSizeRepository
        .checkProductSizeAvailable(req.getProductId(), req.getSize());
    if (productSize == null) {
      throw new BadRequestException(
          "Size gi??y cho s???n ph???m n??y t???m h???t. Vui l??ng ch???n s???n ph???m kh??c");
    }
    if (product.get().getPrice() != req.getProductPrice()) {
      throw new BadRequestException(
          "Gi?? s???n ph???m ???? thay ?????i. Vui l??ng ki???m tra v?? ?????t l???i ????n h??ng");
    }

    // Check promotion
    if (req.getCouponCode() != "") {
      Promotion promotion = promotionService.checkPromotion(req.getCouponCode());
      if (promotion == null) {
        throw new NotFoundException("M?? khuy???n m??i kh??ng t???n t???i ho???c ch??a ???????c k??ch ho???t");
      }
      Timestamp now = new Timestamp(System.currentTimeMillis());
      if (promotion.getExpiredAt().before(now)) {
        throw new BadRequestException("M?? khuy???n m??i h???t h???n");
      }
      long promotionPrice = promotionService
          .calculatePromotionPrice(req.getProductPrice(), promotion);
      if (promotionPrice != req.getTotalPrice()) {
        throw new BadRequestException(
            "T???ng gi?? tr??? ????n h??ng thay ?????i. Vui l??ng ki???m tra v?? ?????t l???i ????n h??ng");
      }
      Order.UsedPromotion usedPromotion = new Order.UsedPromotion(req.getCouponCode(),
          promotion.getDiscountType(), promotion.getDiscountValue(),
          promotion.getMaximumDiscountValue());
      order.setPromotion(usedPromotion);
    }

    // Create order
    order.setCreatedAt(new Timestamp(System.currentTimeMillis()));

    User createdBy = new User(userId);
    order.setCreatedBy(createdBy);

    order.setReceiverAddress(req.getReceiverAddress());
    order.setReceiverName(req.getReceiverName());
    order.setReceiverPhone(req.getReceiverPhone());
    order.setNote(req.getNote());
    order.setBuyer(createdBy);
    order.setProduct(product.get());
    order.setSize(req.getSize());
    order.setProductPrice(req.getProductPrice());
    order.setTotalPrice(req.getTotalPrice());
    order.setStatus(ORDER_STATUS);

    orderRepository.save(order);

    return order;
  }

  @Override
  public List<OrderInfoDto> getListOrderOfPersonByStatus(int status, long userId) {
    List<OrderInfoDto> list = orderRepository.getListOrderOfPersonByStatus(status, userId);

    for (OrderInfoDto order : list) {
      for (int i = 0; i < SIZE_VN.size(); i++) {
        if (SIZE_VN.get(i) == order.getSizeVn()) {
          order.setSizeUs(SIZE_US[i]);
          order.setSizeCm(SIZE_CM[i]);
        }
      }
    }

    return list;
  }

  @Override
  public OrderDetailDto userGetDetailById(long id, long userId) {
    OrderDetailDto order = orderRepository.userGetDetailById(id, userId);
    if (order == null) {
      return null;
    }

    if (order.getStatus() == ORDER_STATUS) {
      order.setStatusText("Ch??? l???y h??ng");
    } else if (order.getStatus() == DELIVERY_STATUS) {
      order.setStatusText("??ang giao h??ng");
    } else if (order.getStatus() == COMPLETE_STATUS) {
      order.setStatusText("???? giao h??ng");
    } else if (order.getStatus() == CANCELED_STATUS) {
      order.setStatusText("???? h???y");
    } else if (order.getStatus() == RETURNED_STATUS) {
      order.setStatusText("???? tr??? h??ng");
    }

    for (int i = 0; i < SIZE_VN.size(); i++) {
      if (SIZE_VN.get(i) == order.getSizeVn()) {
        order.setSizeUs(SIZE_US[i]);
        order.setSizeCm(SIZE_CM[i]);
      }
    }

    return order;
  }

  @Override
  public void userCancelOrder(long id, long userId) {
    Optional<Order> rs = orderRepository.findById(id);
    if (rs.isEmpty()) {
      throw new NotFoundException("????n h??ng kh??ng t???n t???i");
    }
    Order order = rs.get();
    if (order.getBuyer().getId() != userId) {
      throw new BadRequestException("B???n kh??ng ph???i ch??? nh??n ????n h??ng");
    }
    if (order.getStatus() != ORDER_STATUS) {
      throw new BadRequestException(
          "Tr???ng th??i ????n h??ng kh??ng ph?? h???p ????? h???y. Vui l??ng li??n h??? v???i shop ????? ???????c h??? tr???");
    }

    order.setStatus(CANCELED_STATUS);
    orderRepository.save(order);
  }

  @Override
  public Page<Order> adminGetListOrder(String id, String name, String phone, String status,
      String product, int page) {
    page--;
    if (page < 0) {
      page = 0;
    }

    if (id.isEmpty()) {
      id = "%%";
    }
    if (status.isEmpty()) {
      status = "%%";
    }

    if (product.isEmpty()) {
      product = "%%";
    }

    Page<Order> rs = orderRepository.adminGetListOrder(id, name, phone, status, product,
        PageRequest.of(page, 10, Sort.by("created_at").descending()));

    return rs;
  }

  @Override
  public Order getOrderById(long id) {
    // Check order exist
    Optional<Order> result = orderRepository.findById(id);
    if (result.isEmpty()) {
      throw new NotFoundException("????n h??ng kh??ng t???n t???i");
    }

    return result.get();
  }

  @Override
  public void updateDetailOrder(UpdateDetailOrderReq req, long id, long userId) {
    // Check order exist
    Optional<Order> rs = orderRepository.findById(id);
    if (rs.isEmpty()) {
      throw new NotFoundException("????n h??ng kh??ng t???n t???i");
    }
    Order order = rs.get();

    // Check order status
    if (order.getStatus() != ORDER_STATUS) {
      throw new BadRequestException("Ch??? c?? th??? c???p nh???t chi ti???t ????n h??ng ??? tr???ng ch??? l???y h??ng");
    }

    // Check product and size
    Optional<Product> product = productRepository.findById(req.getProductId());
    if (product.isEmpty()) {
      throw new NotFoundException("S???n ph???m kh??ng t???n t???i");
    }
    ProductSize productSize = productSizeRepository
        .checkProductSizeAvailable(req.getProductId(), req.getSize());
    if (productSize == null) {
      throw new BadRequestException(
          "Size gi??y cho s???n ph???m n??y t???m h???t. Vui l??ng ch???n s???n ph???m kh??c");
    }
    if (product.get().getPrice() != req.getProductPrice()) {
      throw new BadRequestException(
          "Gi?? s???n ph???m ???? thay ?????i. Vui l??ng ki???m tra v?? ?????t l???i ????n h??ng");
    }

    // Check promotion
    if (req.getCouponCode() != "") {
      Promotion promotion = promotionService.checkPromotion(req.getCouponCode());
      if (promotion == null) {
        throw new NotFoundException("M?? khuy???n m??i kh??ng t???n t???i ho???c ch??a ???????c k??ch ho???t");
      }
      long promotionPrice = promotionService
          .calculatePromotionPrice(req.getProductPrice(), promotion);
      if (promotionPrice != req.getTotalPrice()) {
        throw new BadRequestException(
            "T???ng gi?? tr??? ????n h??ng thay ?????i. Vui l??ng ki???m tra v?? ?????t l???i ????n h??ng");
      }
      Order.UsedPromotion usedPromotion = new Order.UsedPromotion(req.getCouponCode(),
          promotion.getDiscountType(), promotion.getDiscountValue(),
          promotion.getMaximumDiscountValue());
      order.setPromotion(usedPromotion);
    }

    // Update detail order
    order.setModifiedAt(new Timestamp(System.currentTimeMillis()));

    User modifiedBy = new User();
    modifiedBy.setId(userId);
    order.setModifiedBy(modifiedBy);

    order.setProduct(product.get());
    order.setSize(req.getSize());
    order.setProductPrice(req.getProductPrice());
    order.setTotalPrice(req.getTotalPrice());

    try {
      orderRepository.save(order);
    } catch (Exception ex) {
      throw new InternalServerException("L???i khi c???p nh???t chi ti???t ????n h??ng");
    }
  }

  @Override
  @Transactional
  public void updateStatusOrder(UpdateStatusOrderReq req, long id, long userId) {
    // Check order exist
    Optional<Order> rs = orderRepository.findById(id);
    if (rs.isEmpty()) {
      throw new NotFoundException("????n h??ng kh??ng t???n t???i");
    }
    Order order = rs.get();

    // Validate status of order
    boolean statusIsValid = false;
    for (Integer status : LIST_ORDER_STATUS) {
      if (status == req.getStatus()) {
        statusIsValid = true;
        break;
      }
    }
    if (!statusIsValid) {
      throw new BadRequestException("Tr???ng th??i ????n h??ng kh??ng h???p l???");
    }

    User modifiedBy = new User();
    modifiedBy.setId(userId);

    System.out.println(order.getStatus());
    System.out.println(req.getStatus());

    if (order.getStatus() == ORDER_STATUS) {
      // ????n h??ng c?? ??? tr???ng th??i ch??? giao h??ng
      if (req.getStatus() == ORDER_STATUS) {
        order.setReceiverPhone(req.getReceiverPhone());
        order.setReceiverName(req.getReceiverName());
        order.setReceiverAddress(req.getReceiverAddress());
      } else if (req.getStatus() == DELIVERY_STATUS) {
        // TODO: Minus 1 product
        productSizeRepository.minusOneProductBySize(order.getProduct().getId(), order.getSize());
      } else if (req.getStatus() == COMPLETE_STATUS) {
        // TODO: Minus 1 product, Plus money
        productSizeRepository.minusOneProductBySize(order.getProduct().getId(), order.getSize());
        updateRevenue(modifiedBy, order.getTotalPrice(), order);
      } else if (req.getStatus() != CANCELED_STATUS) {
        throw new BadRequestException("Kh??ng th??? chuy???n ????n h??ng sang tr???ng th??i n??y");
      }
    } else if (order.getStatus() == DELIVERY_STATUS) {
      // ????n h??ng c?? ??? tr???ng th??i ??ang giao h??ng
      if (req.getStatus() == COMPLETE_STATUS) {
        // TODO: Plus money
        updateRevenue(modifiedBy, order.getTotalPrice(), order);
      } else if (req.getStatus() == RETURNED_STATUS) {
        // TODO: Plus 1 product
        productSizeRepository.plusOneProductBySize(order.getProduct().getId(), order.getSize());
      } else if (req.getStatus() == CANCELED_STATUS) {

      } else if (req.getStatus() != DELIVERY_STATUS) {
        throw new BadRequestException("Kh??ng th??? chuy???n ????n h??ng sang tr???ng th??i n??y");
      }
    } else if (order.getStatus() == COMPLETE_STATUS) {
      // ????n h??ng c?? ??? tr???ng th??i ???? giao h??ng
      if (req.getStatus() == RETURNED_STATUS) {
        // TODO: Plus 1 product, Minus money
        productSizeRepository.plusOneProductBySize(order.getProduct().getId(), order.getSize());
        updateRevenue(modifiedBy, -order.getTotalPrice(), order);
      } else if (req.getStatus() != COMPLETE_STATUS) {
        throw new BadRequestException("Kh??ng th??? chuy???n ????n h??ng sang tr???ng th??i n??y");
      }
    } else {
      // ????n h??ng c?? ??? tr???ng th??i ???? h???y ho???c ???? tr??? h??ng
      if (order.getStatus() != req.getStatus()) {
        throw new BadRequestException("Kh??ng th??? chuy???n ????n h??ng sang tr???ng th??i n??y");
      }
    }

    order.setModifiedAt(new Timestamp(System.currentTimeMillis()));
    order.setModifiedBy(modifiedBy);
    order.setNote(req.getNote());
    order.setStatus(req.getStatus());

    try {
      orderRepository.save(order);
    } catch (Exception ex) {
      throw new InternalServerException("L???i khi c???p nh???t ????n h??ng");
    }
  }

  public void updateRevenue(User createdBy, long amount, Order order) {
    Finance finance = new Finance();
    finance.setAmount(amount);
    finance.setOrder(order);
    finance.setCreatedBy(createdBy);
    finance.setCreatedAt(new Timestamp(System.currentTimeMillis()));

    financeRepository.save(finance);
  }
}