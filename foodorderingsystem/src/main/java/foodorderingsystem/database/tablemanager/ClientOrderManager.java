package foodorderingsystem.database.tablemanager;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import foodorderingsystem.model.ClientOrder;
import foodorderingsystem.model.Dessert;
import foodorderingsystem.model.Drink;
import foodorderingsystem.model.MainCourse;

public class ClientOrderManager {

  private SessionFactory factory;

  public void setFactory(SessionFactory factory) {
    this.factory = factory;
  }

  public Integer addClientOrder(MainCourse mainCourse, Dessert dessert, Drink drink, BigDecimal price, boolean lemon,
      boolean iceCubes, String address, String phone) {
    Session session = factory.openSession();
    Transaction tx = null;
    Integer clientOrderID = null;
    try {
      tx = session.beginTransaction();
      ClientOrder clientOrder = new ClientOrder(mainCourse, dessert, drink, price, lemon, iceCubes, address, phone);
      clientOrderID = (Integer) session.save(clientOrder);
      tx.commit();
    } catch (HibernateException e) {
      if (tx != null)
        tx.rollback();
      e.printStackTrace();
    } finally {
      session.close();
    }
    return clientOrderID;
  }

  @SuppressWarnings({ "unchecked", "deprecation" })
  public List<ClientOrder> listClientOrders() {
    List<ClientOrder> clientOrders = new ArrayList<ClientOrder>();
    Session session = factory.openSession();
    Transaction tx = null;
    try {
      tx = session.beginTransaction();
      clientOrders = session.createQuery("FROM ClientOrder").list();
      // for (Iterator<Cuisine> iterator = cuisines.iterator(); iterator.hasNext();) {
      // Cuisine cuisine = iterator.next();
      // }
      tx.commit();
    } catch (HibernateException e) {
      if (tx != null)
        tx.rollback();
      e.printStackTrace();
    } finally {
      session.close();
    }
    return clientOrders;
  }
}
