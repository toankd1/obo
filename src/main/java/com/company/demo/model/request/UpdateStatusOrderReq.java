package com.company.demo.model.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class UpdateStatusOrderReq {

  @NotNull(message = "Họ tên trống")
  @NotEmpty(message = "Họ tên trống")
  @ApiModelProperty(
      example = "Sam Smith",
      notes = "Họ tên trống",
      required = true
  )
  @JsonProperty("receiver_name")
  private String receiverName;

  @Pattern(regexp = "(09|01[2|6|8|9])+([0-9]{8})\\b", message = "Điện thoại không hợp lệ")
  @ApiModelProperty(
      example = "0916016972",
      notes = "Điện thoại trống",
      required = true
  )
  @JsonProperty("receiver_phone")
  private String receiverPhone;

  @NotNull(message = "Địa chỉ trống")
  @NotEmpty(message = "Địa chỉ trống")
  @ApiModelProperty(
      example = "Ha Noi",
      notes = "Địa chỉ trống",
      required = true
  )
  @JsonProperty("receiver_address")
  private String receiverAddress;

  private String note;

  private int status;
}
