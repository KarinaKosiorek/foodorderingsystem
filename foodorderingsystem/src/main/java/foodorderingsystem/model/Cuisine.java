package foodorderingsystem.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "Cuisine")
public class Cuisine {

  @Id
  @Column(name = "CuisineID")
  @GeneratedValue(strategy = GenerationType.AUTO)
  private int cuisineID;

  @Column(name = "Name")
  private String name;

  public Cuisine() {
  }

  public Cuisine(String name) {
    this.name = name;
  }

  public int getCuisineID() {
    return cuisineID;
  }

  public void setCuisineID(int cuisineID) {
    this.cuisineID = cuisineID;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }
}
