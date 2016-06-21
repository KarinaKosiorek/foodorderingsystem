package foodorderingsystem.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import foodorderingsystem.database.tablemanager.QueryManager;
import foodorderingsystem.main.FOSClientConfiguration;

public class DatabaseService {

  static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
  static final String DB_URL = "jdbc:mysql://localhost/?useSSL=false";
  static final String USER = "root";
  static final String PASS = "root";

  private static SessionFactory factory;
  private QueryManager queryManager;

  public void initDatabase() throws Exception {
    createDatabaseIfNotExists();
    initDatabaseManagement();
    queryManager.generateExampleRecords();
  }

  private void initDatabaseManagement() throws Exception {
    factory = new Configuration().configure().buildSessionFactory();
    if (factory == null) {
      throw new IllegalStateException("Cannot instantiate hibernate factory.");
    }
    this.queryManager = new QueryManager(factory);
    if (queryManager == null) {
      throw new Exception("Error with database management system.");
    }
  }

  private void createDatabaseIfNotExists() throws Exception {
    Connection conn = null;
    Statement stmt = null;
    try {
      Class.forName(JDBC_DRIVER).newInstance();
      System.out.println(DB_URL);
      conn = DriverManager.getConnection(DB_URL, USER, PASS);
      stmt = conn.createStatement();
      String sql = "CREATE DATABASE IF NOT EXISTS FoodOrderingSystemDB";
      stmt.executeUpdate(sql);
    } catch (SQLException se) {
    } catch (Exception e) {
      throw e;
    } finally {
      try {
        if (stmt != null) {
          stmt.close();
        }
      } catch (SQLException se2) {
      }
      try {
        if (conn != null) {
          conn.close();
        }
      } catch (SQLException se) {
      }
    }
  }

  public Integer makeOrder(String lunch, String drinkID, String lemon, String icecubes, String address, String phone)
      throws Exception {
    boolean lemonOption = false;
    boolean iceCubesOption = false;
    if (lemon != null && lemon.equals(FOSClientConfiguration.LEMON_OPTION)) {
      lemonOption = true;
    }
    if (icecubes != null && icecubes.equals(FOSClientConfiguration.ICECUBES_OPTION)) {
      iceCubesOption = true;
    }
    if (address == null || address.isEmpty()) {
      throw new Exception("Address must be provided!");
    }
    return queryManager.makeOrder(lunch, drinkID, lemonOption, iceCubesOption, address, phone);
  }

  public void closeDatabaseService() {
    if (factory != null) {
      factory.close();
    }
  }

  public String getMenu() {
    return queryManager.getMenu();
  }

  public String getCuisines() {
    return queryManager.getCuisines();
  }

  public String getCuisine(String cuisine) {
    return queryManager.getCuisine(cuisine);
  }

  public String getDrinks() {
    return queryManager.getDrinks();
  }

  public String getDesserts() {
    return queryManager.getDesserts();
  }
}
