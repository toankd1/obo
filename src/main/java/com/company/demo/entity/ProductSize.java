package com.company.demo.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "product_size")
@Table(name = "product_size")
@IdClass(ProductSizeId.class)
public class ProductSize {

  @Id
  @Column(name = "product_id")
  private String productId;

  @Id
  @Column(name = "size")
  private int size;

  @Column(name = "quantity", nullable = false)
  private int quantity;
}
