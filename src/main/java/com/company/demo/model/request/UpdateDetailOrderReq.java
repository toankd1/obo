package com.company.demo.model.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UpdateDetailOrderReq {

  @NotNull(message = "Sản phẩm trống")
  @NotEmpty(message = "Sản phẩm trống")
  @JsonProperty("product_id")
  private String productId;

  @Min(value = 35)
  @Max(value = 42)
  private int size;

  @JsonProperty("coupon_code")
  private String couponCode;

  @JsonProperty("total_price")
  private long totalPrice;

  @JsonProperty("product_price")
  private long productPrice;
}
