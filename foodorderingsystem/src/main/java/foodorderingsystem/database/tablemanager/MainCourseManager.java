package foodorderingsystem.database.tablemanager;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import foodorderingsystem.model.Cuisine;
import foodorderingsystem.model.MainCourse;

public class MainCourseManager
{

  private SessionFactory factory;

  public void setFactory(SessionFactory factory)
  {
    this.factory = factory;
  }

  public Integer addMainCourse(Cuisine cuisine, String name, BigDecimal price)
  {
    Session session = factory.openSession();
    Transaction tx = null;
    Integer drinkID = null;
    try
    {
      tx = session.beginTransaction();
      MainCourse mainCourse = new MainCourse(cuisine, name, price);
      drinkID = (Integer) session.save(mainCourse);
      tx.commit();
    } catch (HibernateException e)
    {
      if (tx != null)
        tx.rollback();
      e.printStackTrace();
    } finally
    {
      session.close();
    }
    return drinkID;
  }

  @SuppressWarnings({ "unchecked" })
  public List<MainCourse> listMainCourses()
  {
    List<MainCourse> mainCourses = new ArrayList<MainCourse>();
    Session session = factory.openSession();
    Transaction tx = null;
    try
    {
      tx = session.beginTransaction();
      mainCourses = session.createQuery("FROM Drink").list();
      tx.commit();
    } catch (HibernateException e)
    {
      if (tx != null)
      {
        tx.rollback();
      }
      throw e;
    } finally
    {
      session.close();
    }
    return mainCourses;
  }

  public void updateMainCourse(Integer mainCourseID, Cuisine cuisine, String name, BigDecimal price)
  {
    Session session = factory.openSession();
    Transaction tx = null;
    try
    {
      tx = session.beginTransaction();
      MainCourse mainCourse = session.get(MainCourse.class, mainCourseID);
      mainCourse.setCuisine(cuisine);
      mainCourse.setName(name);
      mainCourse.setPrice(price);
      session.update(mainCourse);
      tx.commit();
    } catch (HibernateException e)
    {
      if (tx != null)
      {
        tx.rollback();
      }
      throw e;
    } finally
    {
      session.close();
    }
  }

  public void deleteMainCourse(Integer mainCourseID)
  {
    Session session = factory.openSession();
    Transaction tx = null;
    try
    {
      tx = session.beginTransaction();
      MainCourse mainCourse = session.get(MainCourse.class, mainCourseID);
      session.delete(mainCourse);
      tx.commit();
    } catch (HibernateException e)
    {
      if (tx != null)
      {
        tx.rollback();
      }
      throw e;
    } finally
    {
      session.close();
    }
  }

  @SuppressWarnings("unchecked")
  public MainCourse getMainCourse(String mainCourseID)
  {
    List<MainCourse> mainCourses = new ArrayList<MainCourse>();
    Session session = factory.openSession();
    Transaction tx = null;
    try
    {
      tx = session.beginTransaction();
      Query query = session.createQuery("FROM MainCourse WHERE MainCourseID = :mainCourseID");
      query.setParameter("mainCourseID", mainCourseID);
      mainCourses = query.list();
      tx.commit();
    } catch (HibernateException e)
    {
      if (tx != null)
      {
        tx.rollback();
      }
      throw e;
    } finally
    {
      session.close();
    }
    if (mainCourses.size() > 0)
    {
      return mainCourses.get(0);
    } else
    {
      return null;
    }
  }

  @SuppressWarnings("unchecked")
  public List<MainCourse> getMainCourses(Cuisine cuisine)
  {
    List<MainCourse> mainCourses = new ArrayList<MainCourse>();
    Session session = factory.openSession();
    Transaction tx = null;
    try
    {
      tx = session.beginTransaction();
      Query query = session.createQuery("FROM MainCourse WHERE CuisineID = :cuisineID");
      query.setParameter("cuisineID", cuisine.getCuisineID());
      mainCourses = query.list();
      tx.commit();
    } catch (HibernateException e)
    {
      if (tx != null)
      {
        tx.rollback();
      }
      throw e;
    } finally
    {
      session.close();
    }
    return mainCourses;
  }

  @SuppressWarnings("unchecked")
  public Object getMainCourseByName(String name)
  {
    List<MainCourse> mainCourses = new ArrayList<MainCourse>();
    Session session = factory.openSession();
    Transaction tx = null;
    try
    {
      tx = session.beginTransaction();
      Query query = session.createQuery("FROM MainCourse WHERE Name = :name");
      query.setParameter("name", name);
      mainCourses = query.list();
      tx.commit();
    } catch (HibernateException e)
    {
      if (tx != null)
      {
        tx.rollback();
      }
      throw e;
    } finally
    {
      session.close();
    }
    if (mainCourses.size() > 0)
    {
      return mainCourses.get(0);
    } else
    {
      return null;
    }
  }
}
