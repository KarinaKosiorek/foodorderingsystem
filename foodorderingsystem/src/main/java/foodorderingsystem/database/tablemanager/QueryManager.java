package foodorderingsystem.database.tablemanager;

import java.math.BigDecimal;
import java.util.List;

import org.hibernate.SessionFactory;

import foodorderingsystem.model.Cuisine;
import foodorderingsystem.model.Dessert;
import foodorderingsystem.model.Drink;
import foodorderingsystem.model.MainCourse;

public class QueryManager
{

  private ClientOrderManager clientOrderManager = new ClientOrderManager();
  private CuisineManager cuisineManager = new CuisineManager();
  private MainCourseManager mainCourseManager = new MainCourseManager();
  private DessertManager dessertManager = new DessertManager();
  private DrinkManager drinkManager = new DrinkManager();

  public QueryManager(SessionFactory factory)
  {
    clientOrderManager.setFactory(factory);
    cuisineManager.setFactory(factory);
    mainCourseManager.setFactory(factory);
    dessertManager.setFactory(factory);
    drinkManager.setFactory(factory);
  }

  public void addMainCourses()
  {
    Cuisine polishCuisine = cuisineManager.getCuisine("Polish");
    if (polishCuisine != null)
    {
      if (mainCourseManager.getMainCourseByName("Dumplings") == null)
      {
        mainCourseManager.addMainCourse(polishCuisine, "Dumplings", new BigDecimal(15));
      }
      if (mainCourseManager.getMainCourseByName("Pork Chop") == null)
      {
        mainCourseManager.addMainCourse(polishCuisine, "Pork Chop", new BigDecimal(15));
      }
    }

    Cuisine italianCuisine = cuisineManager.getCuisine("Italian");
    if (italianCuisine != null)
    {
      if (mainCourseManager.getMainCourseByName("Spaghetti Bolognese") == null)
      {
        mainCourseManager.addMainCourse(italianCuisine, "Spaghetti Bolognese", new BigDecimal(15));
      }
      if (mainCourseManager.getMainCourseByName("Pizza") == null)
      {
        mainCourseManager.addMainCourse(italianCuisine, "Pizza", new BigDecimal(15));
      }
    }

    Cuisine mexicanCuisine = cuisineManager.getCuisine("Mexican");
    if (mexicanCuisine != null)
    {
      if (mainCourseManager.getMainCourseByName("Burrito") == null)
      {
        mainCourseManager.addMainCourse(mexicanCuisine, "Burrito", new BigDecimal(15));
      }
      if (mainCourseManager.getMainCourseByName("Nachos") == null)
      {
        mainCourseManager.addMainCourse(mexicanCuisine, "Nachos", new BigDecimal(15));
      }
    }
  }

  public String getMenu()
  {
    StringBuilder result = new StringBuilder();
    List<Cuisine> listCuisines = cuisineManager.listCuisines();
    for (Cuisine cuisine : listCuisines)
    {
      result.append(getMainCourses(cuisine));
    }
    result.append(getDesserts());
    result.append(getDrinks());
    return result.toString();
  }

  public String getCuisines()
  {
    StringBuilder result = new StringBuilder();
    result.append("Cuisines:");
    List<Cuisine> listCuisines = cuisineManager.listCuisines();
    for (Cuisine cuisine : listCuisines)
    {
      result.append("\t" + String.format("%4s", cuisine.getCuisineID()) + ":" + cuisine.getName());
      result.append("\n");
    }
    result.append("\n");
    return result.toString();
  }

  public String getCuisine(String name)
  {
    Cuisine cuisine = cuisineManager.getCuisine(name);
    return getMainCourses(cuisine);
  }

  public String getDrinks()
  {
    StringBuilder result = new StringBuilder();
    result.append("Drinks:");
    result.append("\n");
    for (Drink drink : drinkManager.listDrinks())
    {
      result.append("\t" + String.format("%4s", drink.getDrinkID()) + ":" + drink.getName());
      result.append("\n");
    }
    result.append("\n");
    return result.toString();
  }

  public String getDesserts()
  {
    StringBuilder result = new StringBuilder();
    result.append("Desserts:");
    result.append("\n");
    for (Dessert dessert : dessertManager.listDesserts())
    {
      result.append("\t" + String.format("%4s", dessert.getDessertID()) + ":" + dessert.getName());
      result.append("\n");
    }
    result.append("\n");
    return result.toString();
  }

  public Integer makeOrder(String lunch, String drinkID, boolean lemon, boolean icecubes, String address, String phone)
      throws Exception
  {
    String mainCourseID = "";
    String dessertID = "";
    if (lunch != null && !lunch.isEmpty())
    {
      String[] split = lunch.split("#");
      if (split != null && split.length == 2)
      {
        mainCourseID = split[0];
        dessertID = split[1];
      } else
      {
        throw new Exception("Order error: lunch parameter is not specified correctly!");
      }
    }

    MainCourse mainCourse = mainCourseManager.getMainCourse(mainCourseID);
    Dessert dessert = dessertManager.getDessert(dessertID);
    Drink drink = drinkManager.getDrink(drinkID);

    if (lunch != null && !lunch.isEmpty())
    {
      if (mainCourse == null)
      {
        throw new Exception("Order error: wrong main course ID.");
      }
      if (dessert == null)
      {
        throw new Exception("Order error: wrong dessert ID.");
      }
    }
    if (drinkID != null && !drinkID.isEmpty())
    {
      if (drink == null)
      {
        throw new Exception("Order error: wrong drink ID.");
      }
    }

    if (drink == null && dessert == null && mainCourse == null)
    {
      throw new Exception("Orer error: you need to specify drink or lunch (lunch = main course and dessert)");
    }

    BigDecimal price = new BigDecimal(0);
    if (mainCourse != null)
    {
      price = price.add(mainCourse.getPrice());
    }
    if (dessert != null)
    {
      price = price.add(dessert.getPrice());
    }
    if (drink != null)
    {
      price = price.add(drink.getPrice());
    }
    return clientOrderManager.addClientOrder(mainCourse, dessert, drink, price, lemon, icecubes, address, phone);
  }

  public String getMainCourses(Cuisine cuisine)
  {
    StringBuilder result = new StringBuilder();
    List<MainCourse> mainCourses = mainCourseManager.getMainCourses(cuisine);
    result.append(cuisine.getName() + " cuisine.");
    result.append("\n");
    result.append("\t Main courses:");
    result.append("\n");

    for (MainCourse mainCourse : mainCourses)
    {
      result.append("\t\t" + String.format("%4s", mainCourse.getMainCourseID()) + ":" + mainCourse.getName());
      result.append("\n");
    }
    result.append("\n");
    return result.toString();
  }

  public void generateExampleRecords()
  {
    cuisineManager.initCuisines();
    dessertManager.initDesserts();
    drinkManager.initDrinks();
    addMainCourses();
  }
}
