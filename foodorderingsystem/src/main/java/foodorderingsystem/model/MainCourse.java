package foodorderingsystem.model;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "MainCourse")
public class MainCourse {

  @Id
  @Column(name = "MainCourseID")
  @GeneratedValue(strategy = GenerationType.AUTO)
  private int mainCourseID;

  @ManyToOne
  @JoinColumn(name = "CuisineID")
  private Cuisine cuisine;

  @Column(name = "Name")
  private String name;

  @Column(name = "Price", precision = 10, scale = 2)
  private BigDecimal price;

  public MainCourse() {
  }

  public MainCourse(Cuisine cuisine, String name, BigDecimal price) {
    this.cuisine = cuisine;
    this.name = name;
    this.price = price;
  }

  public int getMainCourseID() {
    return mainCourseID;
  }

  public void setMainCourseID(int mainCourseID) {
    this.mainCourseID = mainCourseID;
  }

  public Cuisine getCuisine() {
    return cuisine;
  }

  public void setCuisine(Cuisine cuisine) {
    this.cuisine = cuisine;
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
