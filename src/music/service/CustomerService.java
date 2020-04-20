package music.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import music.dao.CustomerDAO;
import music.entities.*;
@Service
@Transactional

public class CustomerService {
	@Autowired
	private CustomerDAO customerDAO;
	
	public Product selectProduct(String productCode) {
		return customerDAO.selectProduct(productCode);
	}
	public User selectUser(String emailAddress) {
		return customerDAO.selectUser(emailAddress);
	}
	public long selectMaxID() {
		return customerDAO.selectMaxID();
	}
	public boolean emailExists(String email) {
		return customerDAO.emailExists(email);
	}
	public void Update(User user) {
		customerDAO.Update(user);
	}
	public void Insert(User user) {
		customerDAO.Insert(user);
	}
}
