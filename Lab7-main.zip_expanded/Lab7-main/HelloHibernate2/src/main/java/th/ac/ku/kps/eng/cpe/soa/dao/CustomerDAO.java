package th.ac.ku.kps.eng.cpe.soa.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.TransactionException;
import org.hibernate.criterion.Restrictions;

import th.ac.ku.kps.eng.cpe.soa.model.Customer;

public class CustomerDAO {
	public ArrayList<Customer> getAllCustomers() {
		Session session = SessionUtil.getSession();
		Query query = session.createQuery("from Customer");
		ArrayList<Customer> customers = (ArrayList<Customer>) query.list();
		session.close();
		return customers;
	}

	public boolean addCustomer(Customer c) {
		try {
			Session session = SessionUtil.getSession();
			Transaction tx = session.beginTransaction();
			session.save(c);
			tx.commit();
			session.close();
		} catch (TransactionException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	public Customer findCustomer(String userName) {
		Session session = SessionUtil.getSession();
		String hql = "FROM Customer WHERE username = :value";
		Query<Customer> query = session.createQuery(hql, Customer.class);
		query.setParameter("value", userName);
		List<Customer> result = query.getResultList();
		return result.get(0);
	}

	public Customer findCus(Customer c) {
		Session session = SessionUtil.getSession();
		Criteria cr = session.createCriteria(Customer.class);
		cr.add(Restrictions.eq("username", c.getUsername()));
		cr.add(Restrictions.eq("pwd", c.getPwd()));
		if (cr.list().size() == 0)
			return null;
		return (Customer) cr.list().get(0);
	}

}