package music.dao;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import music.entities.*;
@Repository(value="customerDAO")
@Transactional
public class CustomerDAO {
	@Autowired
	private SessionFactory sessionFactory;
	public void Update(final User user) {
		Session session=this.sessionFactory.openSession();
		Transaction t=session.beginTransaction();
		try {
			session.update(user);
			t.commit();
		}
		catch(Exception e) {
			t.rollback();
		}
		finally {
			session.close();
		}
	}
	public void Insert(final User user) {
		Session session=this.sessionFactory.openSession();
		Transaction t=session.beginTransaction();
		try {
			session.save(user);
			t.commit();
		}
		catch(Exception e) {
			t.rollback();
		}
		finally {
			session.close();
		}
	}
	public Product selectProduct(final String productCode) {
		Session session=this.sessionFactory.getCurrentSession();
		String hql="FROM Product WHERE code = '"+productCode+"'";
		Query query=session.createQuery(hql);
		Product product=(Product) query.uniqueResult();
		return product;
	}
	public User selectUser(final String emailAddress) {
		Session session=this.sessionFactory.getCurrentSession();
		String hql="FROM User WHERE email like '"+emailAddress+"'";
		Query query=session.createQuery(hql);
		User user=(User) query.uniqueResult();
		return user;
	}
	public long selectMaxID() {
		Session session=this.sessionFactory.getCurrentSession();
		String hql="SELECT MAX(userID) FROM User";
		Query query=session.createQuery(hql);
		long t=(long) query.uniqueResult();
		return t;
	}
	public boolean emailExists(String email) {
		Session session=this.sessionFactory.getCurrentSession();
		String hql1="FROM User WHERE email like '"+email+"'";
		Query query=session.createQuery(hql1);
		User user=(User) query.uniqueResult();
		if(user==null) {
			return false;		
		}
		return true;
	}
}
