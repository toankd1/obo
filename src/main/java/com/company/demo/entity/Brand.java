package com.company.demo.entity;

import com.company.demo.model.dto.BrandInfo;
import javax.persistence.Column;
import javax.persistence.ColumnResult;
import javax.persistence.ConstructorResult;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedNativeQuery;
import javax.persistence.SqlResultSetMapping;
import javax.persistence.SqlResultSetMappings;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@SqlResultSetMappings(
    value = {
        @SqlResultSetMapping(
            name = "brandInfo",
            classes = @ConstructorResult(
                targetClass = BrandInfo.class,
                columns = {
                    @ColumnResult(name = "id", type = Integer.class),
                    @ColumnResult(name = "name", type = String.class),
                    @ColumnResult(name = "thumbnail", type = String.class),
                    @ColumnResult(name = "product_count", type = Integer.class)
                }
            )
        )
    }
)
@NamedNativeQuery(
    name = "getListBrandAndProductCount",
    resultSetMapping = "brandInfo",
    query = "SELECT brand.id, brand.name, brand.thumbnail,\n" +
        "(\n" +
        "    SELECT count(product.id)\n" +
        "    FROM product\n" +
        "    WHERE product.brand_id = brand.id\n" +
        ") product_count \n" +
        "FROM brand "
)
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "brand")
@Table(name = "brand")
public class Brand {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Column(name = "id")
  private int id;

  @Column(name = "name", nullable = false, unique = true)
  private String name;

  @Column(name = "thumbnail")
  private String thumbnail;
}
