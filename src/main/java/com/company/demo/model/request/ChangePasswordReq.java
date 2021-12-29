package com.company.demo.model.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ChangePasswordReq {

  @NotNull(message = "Mật khẩu cũ trống")
  @NotEmpty(message = "Mật khẩu cũ trống")
  @Size(min = 4, max = 20, message = "Mật khẩu phải chứa từ 4 - 20 ký tự")
  @ApiModelProperty(
      example = "verysecretpassword",
      notes = "Mật khẩu trống",
      required = true
  )
  @JsonProperty("old_password")
  private String oldPassword;

  @NotNull(message = "Mật khẩu mới trống")
  @NotEmpty(message = "Mật khẩu mới trống")
  @Size(min = 4, max = 20, message = "Mật khẩu phải chứa từ 4 - 20 ký tự")
  @ApiModelProperty(
      example = "verysecretpassword",
      notes = "Mật khẩu trống",
      required = true
  )
  @JsonProperty("new_password")
  private String newPassword;
}
