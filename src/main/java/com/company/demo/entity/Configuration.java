package com.company.demo.entity;

import com.vladmihalcea.hibernate.type.json.JsonStringType;
import java.util.ArrayList;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "configuration")
@Table(name = "configuration")
@TypeDef(
    name = "json",
    typeClass = JsonStringType.class
)
public class Configuration {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Column(name = "id")
  private long id;

  @Type(type = "json")
  @Column(name = "obo_choices", columnDefinition = "json")
  private ArrayList<String> oboChoices;
}
