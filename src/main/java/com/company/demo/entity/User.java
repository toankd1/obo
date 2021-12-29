package com.company.demo.entity;

import com.vladmihalcea.hibernate.type.json.JsonStringType;
import java.sql.Timestamp;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
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
@Entity(name = "users")
@TypeDef(
    name = "json",
    typeClass = JsonStringType.class
)
public class User {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Column(name = "id")
  private long id;

  @Column(name = "full_name", nullable = false, length = 200)
  private String fullName;

  @Column(name = "email", nullable = false, unique = true, length = 200)
  private String email;

  @Column(name = "password", nullable = false)
  private String password;

  @Type(type = "json")
  @Column(name = "roles", nullable = false, columnDefinition = "json")
  private List<String> roles;

  @Column(name = "address")
  private String address;

  @Column(name = "phone")
  private String phone;

  @Column(name = "status", columnDefinition = "BOOLEAN")
  private boolean status;

  @Column(name = "created_at")
  private Timestamp createdAt;

  public User(long id) {
    this.id = id;
  }
}
