package foodorderingsystem.database.tablemanager;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import foodorderingsystem.model.Drink;

public class DrinkManager
{

  private SessionFactory factory;

  public void setFactory(SessionFactory factory)
  {
    this.factory = factory;
  }

  public void initDrinks()
  {
    if (!exists(getDrinkByName("Coffee")))
    {
      addDrink("Coffee", new BigDecimal(7));
    }
    if (!exists(getDrinkByName("Tea")))
    {
      addDrink("Tea", new BigDecimal(7));
    }
    if (!exists(getDrinkByName("Coca Cola")))
    {
      addDrink("Coca Cola", new BigDecimal(7));
    }
    if (!exists(getDrinkByName("House beer")))
    {
      addDrink("House beer", new BigDecimal(7));
    }
  }

  @SuppressWarnings("unchecked")
  private Drink getDrinkByName(String name)
  {
    List<Drink> drinks = new ArrayList<Drink>();
    Session session = factory.openSession();
    Transaction tx = null;
    try
    {
      tx = session.beginTransaction();
      Query query = session.createQuery("FROM Drink WHERE Name = :name");
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
  private Integer addDrink(int id, String name, BigDecimal price) throws Exception
  {
    Session session = factory.openSession();
    Transaction tx = null;
    Integer drinkID = null;
    try
    {
      tx = session.beginTransaction();
      Drink drink = new Drink(id, name, price);
      drinkID = (Integer) session.save(drink);
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
    return drinkID;
  }

  private boolean exists(Drink drink)
  {
    return drink != null;
  }

  public Integer addDrink(String name, BigDecimal price)
  {
    Session session = factory.openSession();
    Transaction tx = null;
    Integer drinkID = null;
    try
    {
      tx = session.beginTransaction();
      Drink drink = new Drink(name, price);
      drinkID = (Integer) session.save(drink);
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
    return drinkID;
  }

  @SuppressWarnings({ "unchecked" })
  public List<Drink> listDrinks()
  {
    List<Drink> drinks = new ArrayList<Drink>();
    Session session = factory.openSession();
    Transaction tx = null;
    try
    {
      tx = session.beginTransaction();
      drinks = session.createQuery("FROM Drink").list();
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
    return drinks;
  }

  public void updateDrink(Integer drinkID, String name, BigDecimal price)
  {
    Session session = factory.openSession();
    Transaction tx = null;
    try
    {
      tx = session.beginTransaction();
      Drink drink = session.get(Drink.class, drinkID);
      drink.setName(name);
      drink.setPrice(price);
      session.update(drink);
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

  public void deleteDrink(Integer drinkID)
  {
    Session session = factory.openSession();
    Transaction tx = null;
    try
    {
      tx = session.beginTransaction();
      Drink drink = session.get(Drink.class, drinkID);
      session.delete(drink);
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
  public Drink getDrink(String drinkID)
  {
    List<Drink> drinks = new ArrayList<Drink>();
    Session session = factory.openSession();
    Transaction tx = null;
    try
    {
      tx = session.beginTransaction();
      Query query = session.createQuery("FROM Drink WHERE DrinkID = :drinkID");
      query.setParameter("drinkID", drinkID);
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
