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
public class CreateCategoryReq {

  @NotNull(message = "Tên category trống")
  @NotEmpty(message = "Tên category trống")
  @Size(min = 1, max = 300, message = "Độ dài tên category từ 1 - 300 ký tự")
  private String name;
}
