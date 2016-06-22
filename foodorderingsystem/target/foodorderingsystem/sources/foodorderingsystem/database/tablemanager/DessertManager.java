package foodorderingsystem.database.tablemanager;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import foodorderingsystem.model.Dessert;

public class DessertManager
{

  private SessionFactory factory;

  public void setFactory(SessionFactory factory)
  {
    this.factory = factory;
  }

  public void initDesserts()
  {
    if (!exists(getDessertByName("Ice cream")))
    {
      addDessert("Ice cream", new BigDecimal(8));
    }
    if (!exists(getDessertByName("Apple cake")))
    {
      addDessert("Apple cake", new BigDecimal(8));
    }
    if (!exists(getDessertByName("Fruit salad")))
    {
      addDessert("Fruit salad", new BigDecimal(8));
    }
  }

  @SuppressWarnings("unchecked")
  private Dessert getDessertByName(String name)
  {
    List<Dessert> drinks = new ArrayList<Dessert>();
    Session session = factory.openSession();
    Transaction tx = null;
    try
    {
      tx = session.beginTransaction();
      Query query = session.createQuery("FROM Dessert WHERE Name = :name");
      query.setParameter("name", name);
      drinks = query.list();
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
    if (drinks.size() > 0)
    {
      return drinks.get(0);
    } else
    {
      return null;
    }
  }

  @SuppressWarnings("unused")
  private Integer addDessert(int id, String name, BigDecimal price)
  {
    Session session = factory.openSession();
    Transaction tx = null;
    Integer dessertId = null;
    try
    {
      tx = session.beginTransaction();
      Dessert dessert = new Dessert(id, name, price);
      dessertId = (Integer) session.save(dessert);
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
    return dessertId;
  }

  private boolean exists(Dessert dessert)
  {
    return dessert != null;
  }

  public Integer addDessert(String name, BigDecimal price)
  {
    Session session = factory.openSession();
    Transaction tx = null;
    Integer dessertId = null;
    try
    {
      tx = session.beginTransaction();
      Dessert dessert = new Dessert(name, price);
      dessertId = (Integer) session.save(dessert);
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
    return dessertId;
  }

  @SuppressWarnings({ "unchecked" })
  public List<Dessert> listDesserts()
  {
    List<Dessert> desserts = new ArrayList<Dessert>();
    Session session = factory.openSession();
    Transaction tx = null;
    try
    {
      tx = session.beginTransaction();
      desserts = session.createQuery("FROM Dessert").list();
      // for (Iterator<Cuisine> iterator = cuisines.iterator(); iterator.hasNext();) {
      // Cuisine cuisine = iterator.next();
      // }
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
    return desserts;
  }

  public void updateDessert(Integer dessertId, String name, BigDecimal price)
  {
    Session session = factory.openSession();
    Transaction tx = null;
    try
    {
      tx = session.beginTransaction();
      Dessert dessert = session.get(Dessert.class, dessertId);
      dessert.setName(name);
      dessert.setPrice(price);
      session.update(dessert);
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

  public void deleteDessert(Integer dessertId)
  {
    Session session = factory.openSession();
    Transaction tx = null;
    try
    {
      tx = session.beginTransaction();
      Dessert dessert = session.get(Dessert.class, dessertId);
      session.delete(dessert);
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

  @SuppressWarnings({ "unchecked" })
  public Dessert getDessert(String dessertID)
  {
    List<Dessert> drinks = new ArrayList<Dessert>();
    Session session = factory.openSession();
    Transaction tx = null;
    try
    {
      tx = session.beginTransaction();
      Query query = session.createQuery("FROM Dessert WHERE DessertID = :dessertID");
      query.setParameter("dessertID", dessertID);
      drinks = query.list();
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
    if (drinks.size() > 0)
    {
      return drinks.get(0);
    } else
    {
      return null;
    }
  }
}
