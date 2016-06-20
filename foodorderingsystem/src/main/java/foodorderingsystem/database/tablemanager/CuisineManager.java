package foodorderingsystem.database.tablemanager;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import foodorderingsystem.model.Cuisine;

public class CuisineManager
{

  private SessionFactory factory;

  public void setFactory(SessionFactory factory)
  {
    this.factory = factory;
  }

  public void initCuisines()
  {
    if (!exists(getCuisine("Polish")))
    {
      addCuisine("Polish");
    }
    if (!exists(getCuisine("Mexican")))
    {
      addCuisine("Mexican");
    }
    if (!exists(getCuisine("Italian")))
    {
      addCuisine("Italian");
    }
  }

  private boolean exists(Cuisine cuisine)
  {
    return cuisine != null;
  }

  public Integer addCuisine(String name)
  {
    Session session = factory.openSession();
    Transaction tx = null;
    Integer cuisineID = null;
    try
    {
      tx = session.beginTransaction();
      Cuisine cuisine = new Cuisine(name);
      cuisineID = (Integer) session.save(cuisine);
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
    return cuisineID;
  }

  @SuppressWarnings({ "unchecked", "deprecation" })
  public List<Cuisine> listCuisines()
  {
    List<Cuisine> cuisines = new ArrayList<Cuisine>();
    Session session = factory.openSession();
    Transaction tx = null;
    try
    {
      tx = session.beginTransaction();
      cuisines = session.createQuery("FROM Cuisine").list();
      // for (Iterator<Cuisine> iterator = cuisines.iterator(); iterator.hasNext();) {
      // Cuisine cuisine = iterator.next();
      // }
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
    return cuisines;
  }

  public void updateCuisine(Integer cuisineID, String name)
  {
    Session session = factory.openSession();
    Transaction tx = null;
    try
    {
      tx = session.beginTransaction();
      Cuisine cuisine = session.get(Cuisine.class, cuisineID);
      cuisine.setName(name);
      session.update(cuisine);
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
  }

  public void deleteCousine(Integer cuisineID)
  {
    Session session = factory.openSession();
    Transaction tx = null;
    try
    {
      tx = session.beginTransaction();
      Cuisine cuisine = session.get(Cuisine.class, cuisineID);
      session.delete(cuisine);
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
  }

  @SuppressWarnings("deprecation")
  public Cuisine getCuisine(String name)
  {
    List<Cuisine> cuisines = new ArrayList<Cuisine>();
    Session session = factory.openSession();
    Transaction tx = null;
    try
    {
      tx = session.beginTransaction();
      Query<Cuisine> query = session.createQuery("FROM Cuisine WHERE Name = :name");
      query.setParameter("name", name);
      cuisines = query.list();
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
    if (cuisines.size() > 0)
    {
      return cuisines.get(0);
    } else
    {
      return null;
    }
  }
}
