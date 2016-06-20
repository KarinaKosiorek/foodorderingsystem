package foodorderingsystem.model;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "ClientOrder")
public class ClientOrder {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Column(name = "ClientOrderId")
  private int clientOrderID;

  @ManyToOne
  private MainCourse mainCourse;

  @ManyToOne
  private Dessert dessert;

  @ManyToOne
  private Drink drink;

  @Column(name = "Price", precision = 10, scale = 2)
  private BigDecimal price;

  @Column(name = "Lemon")
  private boolean lemon = false;

  @Column(name = "IceCubes")
  private boolean iceCubes = false;

  @Column(name = "Address")
  private String address;

  @Column(name = "Phone")
  private String phone;

  @Column(name = "Done")
  private boolean done = false;

  public ClientOrder() {
  }

  public ClientOrder(MainCourse mainCourse, Dessert dessert, Drink drink, BigDecimal price, boolean lemon, boolean iceCubes,
      String address, String phone) {
    this.mainCourse = mainCourse;
    this.dessert = dessert;
    this.drink = drink;
    this.price = price;
    this.lemon = lemon;
    this.iceCubes = iceCubes;
    this.address = address;
    this.phone = phone;
  }

  public int getOrderID() {
    return clientOrderID;
  }

  public void setOrderID(int orderID) {
    this.clientOrderID = orderID;
  }

  public BigDecimal getPrice() {
    return price;
  }

  public void setPrice(BigDecimal price) {
    this.price = price;
  }

  public boolean isLemon() {
    return lemon;
  }

  public void setLemon(boolean lemon) {
    this.lemon = lemon;
  }

  public boolean isIceCubes() {
    return iceCubes;
  }

  public void setIceCubes(boolean iceCubes) {
    this.iceCubes = iceCubes;
  }

  public Drink getDrink() {
    return drink;
  }

  public void setDrink(Drink drink) {
    this.drink = drink;
  }

  public int getClientOrderID() {
    return clientOrderID;
  }

  public void setClientOrderID(int clientOrderID) {
    this.clientOrderID = clientOrderID;
  }

  public MainCourse getMainCourse() {
    return mainCourse;
  }

  public void setMainCourse(MainCourse mainCourse) {
    this.mainCourse = mainCourse;
  }

  public Dessert getDessert() {
    return dessert;
  }

  public void setDessert(Dessert dessert) {
    this.dessert = dessert;
  }
}
