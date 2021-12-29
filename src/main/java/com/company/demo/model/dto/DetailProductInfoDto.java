package com.company.demo.model.dto;

import com.company.demo.entity.Brand;
import java.util.ArrayList;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class DetailProductInfoDto {

  private String id;

  private String name;

  private String slug;

  private long price;

  private int totalSold;

  private ArrayList<String> productImages;

  private ArrayList<String> onfeetImages;

  private long promotionPrice;

  private String couponCode;

  private String description;

  private Brand brand;
}
