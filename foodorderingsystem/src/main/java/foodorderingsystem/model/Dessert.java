package foodorderingsystem.model;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = " Dessert")
public class Dessert {

  @Id
  @Column(name = "DessertID")
  @GeneratedValue(strategy = GenerationType.AUTO)
  private int dessertID;

  @Column(name = "Name")
  private String name;

  @Column(name = "Price", precision = 10, scale = 2)
  private BigDecimal price;

  public Dessert() {
  }

  public Dessert(String name, BigDecimal price) {
    this.name = name;
    this.price = price;
  }

  public int getDessertID() {
    return dessertID;
  }

  public void setDessertID(int dessertID) {
    this.dessertID = dessertID;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public BigDecimal getPrice() {
    return price;
  }

  public void setPrice(BigDecimal price) {
    this.price = price;
  }
}
