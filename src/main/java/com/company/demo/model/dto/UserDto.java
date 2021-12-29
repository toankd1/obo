package com.company.demo.model.dto;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {

  private long id;

  private String fullName;

  private String email;

  private String phone;

  private String address;

  private List<String> roles;
}
