package foodorderingsystem.database.tablemanager;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import foodorderingsystem.model.Cuisine;
import foodorderingsystem.model.MainCourse;

public class MainCourseManager {

  private SessionFactory factory;

  public void setFactory(SessionFactory factory) {
    this.factory = factory;
  }

  public Integer addMainCourse(Cuisine cuisine, String name, BigDecimal price) {
    Session session = factory.openSession();
    Transaction tx = null;
    Integer drinkID = null;
    try {
      tx = session.beginTransaction();
      MainCourse mainCourse = new MainCourse(cuisine, name, price);
      drinkID = (Integer) session.save(mainCourse);
      tx.commit();
    } catch (HibernateException e) {
      if (tx != null)
        tx.rollback();
      e.printStackTrace();
    } finally {
      session.close();
    }
    return drinkID;
  }

  @SuppressWarnings({ "unchecked", "deprecation" })
  public List<MainCourse> listMainCourses() {
    List<MainCourse> mainCourses = new ArrayList<MainCourse>();
    Session session = factory.openSession();
    Transaction tx = null;
    try {
      tx = session.beginTransaction();
      mainCourses = session.createQuery("FROM Drink").list();
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
    return mainCourses;
  }

  public void updateMainCourse(Integer mainCourseID, Cuisine cuisine, String name, BigDecimal price) {
    Session session = factory.openSession();
    Transaction tx = null;
    try {
      tx = session.beginTransaction();
      MainCourse mainCourse = session.get(MainCourse.class, mainCourseID);
      mainCourse.setCuisine(cuisine);
      mainCourse.setName(name);
      mainCourse.setPrice(price);
      session.update(mainCourse);
      tx.commit();
    } catch (HibernateException e) {
      if (tx != null)
        tx.rollback();
      e.printStackTrace();
    } finally {
      session.close();
    }
  }

  public void deleteMainCourse(Integer mainCourseID) {
    Session session = factory.openSession();
    Transaction tx = null;
    try {
      tx = session.beginTransaction();
      MainCourse mainCourse = session.get(MainCourse.class, mainCourseID);
      session.delete(mainCourse);
      tx.commit();
    } catch (HibernateException e) {
      if (tx != null)
        tx.rollback();
      e.printStackTrace();
    } finally {
      session.close();
    }
  }

  @SuppressWarnings("deprecation")
  public MainCourse getMainCourse(String mainCourseID) {
    List<MainCourse> mainCourses = new ArrayList<MainCourse>();
    Session session = factory.openSession();
    Transaction tx = null;
    try {
      tx = session.beginTransaction();
      Query<MainCourse> query = session.createQuery("FROM MainCourse WHERE MainCourseID = :mainCourseID");
      query.setParameter("mainCourseID", mainCourseID);
      mainCourses = query.list();
      tx.commit();
    } catch (HibernateException e) {
      if (tx != null)
        tx.rollback();
      e.printStackTrace();
    } finally {
      session.close();
    }
    if (mainCourses.size() > 0) {
      return mainCourses.get(0);
    } else {
      return null;
    }
  }

  public List<MainCourse> getMainCourses(Cuisine cuisine) {
    List<MainCourse> mainCourses = new ArrayList<MainCourse>();
    Session session = factory.openSession();
    Transaction tx = null;
    try {
      tx = session.beginTransaction();
      Query<MainCourse> query = session.createQuery("FROM MainCourse WHERE CuisineID = :cuisineID");
      query.setParameter("cuisineID", cuisine.getCuisineID());
      mainCourses = query.list();
      tx.commit();
    } catch (HibernateException e) {
      if (tx != null)
        tx.rollback();
      e.printStackTrace();
    } finally {
      session.close();
    }
    return mainCourses;
  }
}
