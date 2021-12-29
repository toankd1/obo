package com.company.demo.model.request;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CreateBrandReq {

  @NotNull(message = "Tên nhãn hiệu trống")
  @NotEmpty(message = "Tên nhãn hiệu trống")
  @Size(min = 1, max = 255, message = "Độ dài tên nhãn hiệu từ 1 - 255 ký tự")
  private String name;

  @NotNull(message = "Logo trống")
  @NotEmpty(message = "Logo trống")
  private String thumbnail;
}
