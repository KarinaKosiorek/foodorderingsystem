package foodorderingsystem.database.tablemanager;

import java.math.BigDecimal;
import java.util.List;

import org.hibernate.SessionFactory;

import foodorderingsystem.model.Cuisine;
import foodorderingsystem.model.Dessert;
import foodorderingsystem.model.Drink;
import foodorderingsystem.model.MainCourse;

public class QueryManager {

  private ClientOrderManager clientOrderManager = new ClientOrderManager();
  private CuisineManager cuisineManager = new CuisineManager();
  private MainCourseManager mainCourseManager = new MainCourseManager();
  private DessertManager dessertManager = new DessertManager();
  private DrinkManager drinkManager = new DrinkManager();

  public QueryManager(SessionFactory factory) {
    clientOrderManager.setFactory(factory);
    cuisineManager.setFactory(factory);
    mainCourseManager.setFactory(factory);
    dessertManager.setFactory(factory);
    drinkManager.setFactory(factory);
  }

  public void addMainCourses() {
    Cuisine polishCuisine = cuisineManager.getCuisine("Polish");
    if (polishCuisine != null) {
      mainCourseManager.addMainCourse(polishCuisine, "Dumplings", new BigDecimal(15));
      mainCourseManager.addMainCourse(polishCuisine, "Pork Chop", new BigDecimal(17));
    }

    Cuisine italianCuisine = cuisineManager.getCuisine("Italian");
    if (italianCuisine != null) {
      mainCourseManager.addMainCourse(italianCuisine, "Spaghetti Bolognese", new BigDecimal(16));
      mainCourseManager.addMainCourse(italianCuisine, "Pizza", new BigDecimal(20));
    }

    Cuisine mexicanCuisine = cuisineManager.getCuisine("Mexican");
    if (mexicanCuisine != null) {
      mainCourseManager.addMainCourse(mexicanCuisine, "Burrito", new BigDecimal(16));
      mainCourseManager.addMainCourse(mexicanCuisine, "Nachos", new BigDecimal(18));
    }
  }

  public String getMenu() {
    StringBuilder result = new StringBuilder();
    List<Cuisine> listCuisines = cuisineManager.listCuisines();
    for (Cuisine cuisine : listCuisines) {
      result.append(getMainCourses(cuisine));
    }
    result.append(getDesserts());
    result.append(getDrinks());
    return result.toString();
  }

  public String getCuisines() {
    StringBuilder result = new StringBuilder();
    result.append("Cuisines:");
    List<Cuisine> listCuisines = cuisineManager.listCuisines();
    for (Cuisine cuisine : listCuisines) {
      result.append("\t" + String.format("%4s", cuisine.getCuisineID()) + ":" + cuisine.getName());
      result.append("\n");
    }
    result.append("\n");
    return result.toString();
  }

  public String getCuisine(String name) {
    Cuisine cuisine = cuisineManager.getCuisine(name);
    return getMainCourses(cuisine);
  }

  public String getDrinks() {
    StringBuilder result = new StringBuilder();
    result.append("Drinks:");
    result.append("\n");
    for (Drink drink : drinkManager.listDrinks()) {
      result.append("\t" + String.format("%4s", drink.getDrinkID()) + ":" + drink.getName());
      result.append("\n");
    }
    result.append("\n");
    return result.toString();
  }

  public String getDesserts() {
    StringBuilder result = new StringBuilder();
    result.append("Desserts:");
    result.append("\n");
    for (Dessert dessert : dessertManager.listDesserts()) {
      result.append("\t" + String.format("%4s", dessert.getDessertID()) + ":" + dessert.getName());
      result.append("\n");
    }
    result.append("\n");
    return result.toString();
  }

  public Integer makeOrder(String lunch, String drinkID, boolean lemon, boolean icecubes, String address, String phone)
      throws Exception {
    String[] split = lunch.split("+");
    String mainCourseID = split[0];
    String dessertID = split[1];

    MainCourse mainCourse = mainCourseManager.getMainCourse(mainCourseID);
    Dessert dessert = dessertManager.getDessert(dessertID);
    Drink drink = drinkManager.getDrink(drinkID);

    BigDecimal price = new BigDecimal(0);
    price.add(mainCourse.getPrice()).add(dessert.getPrice()).add(drink.getPrice());

    return clientOrderManager.addClientOrder(mainCourse, dessert, drink, price, lemon, icecubes, address, phone);
  }

  public String getMainCourses(Cuisine cuisine) {
    StringBuilder result = new StringBuilder();
    List<MainCourse> mainCourses = mainCourseManager.getMainCourses(cuisine);
    result.append("Main courses for cuisine:" + cuisine.toString());
    result.append("\n");

    for (MainCourse mainCourse : mainCourses) {
      result.append("\t" + String.format("%4s", mainCourse.getMainCourseID()) + ":" + mainCourse.getName());
      result.append("\n");
    }
    result.append("\n");
    return result.toString();
  }

  public void generateExampleRecords() {
    cuisineManager.initCuisines();
    dessertManager.initDesserts();
    drinkManager.initDrinks();
    addMainCourses();
  }
}
