package music.controller;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import music.entities.*;
import music.service.CustomerService;



@Controller
public class CatalogController {
	@Autowired
	private CustomerService customerService;
	
	@RequestMapping(value= {"/","home"})
	public String index() {
		return "home";
	}
	
	@RequestMapping("catalog")
	public String pageCatalog(){
		return "catalog";
	}
	@RequestMapping("login")
	public String login() {
		return "register";
	}
	@RequestMapping("logout")
	public String logOut(HttpServletResponse response, HttpServletRequest request) {
		Cookie cookie=new Cookie("emailCookie", "");
		cookie.setMaxAge(0);
		response.addCookie(cookie);
		HttpSession session=request.getSession();
		session.removeAttribute("user");
		session.removeAttribute("product");
		return "home";
	}
	@RequestMapping(value="product", params="productCode")
	public String showProduct(@RequestParam("productCode") String productCode, HttpServletRequest request, Model model) {
		Product product=customerService.selectProduct(productCode);
		HttpSession session=request.getSession();
		session.setAttribute("product", product);
		model.addAttribute("product", product);
		return "product";
	}
	
	@RequestMapping("listen")
	public String listen(HttpServletRequest request, Model model) {
		HttpSession session=request.getSession();
		Product product=(Product) session.getAttribute("product");
		model.addAttribute("product",product);
		User user=(User) session.getAttribute("user");
		String emailAddress= null;
		if(user==null) {
			Cookie[] cookies=request.getCookies();
			for(Cookie cookie : cookies) {
				if(cookie.getName().equals("emailCookie")) {
					emailAddress=cookie.getValue();
				}
			}
			if(emailAddress==null || emailAddress.equals("")) {
				return "register";
			}
			else {
				user=customerService.selectUser(emailAddress);
				if(user==null) {
					return "register";
				}
				session.setAttribute("user",user);
				
			}

		}
		model.addAttribute("user", user);
		return "sound";
	}

	@RequestMapping(value="register", method=RequestMethod.POST)
	public String register(HttpServletRequest request, HttpServletResponse response, Model model) {
		String firstName=request.getParameter("firstName");
		String lastName=request.getParameter("lastName");
		String email=request.getParameter("email");
		if(email.trim().length()==0) {
			model.addAttribute("errorEmail", "Email is not null!");			
		}
		if(firstName.trim().length()==0) {
			model.addAttribute("errorFirstName","First Name is not null!");
		}
		if(lastName.trim().length()==0) {
			model.addAttribute("errorLastName", "Last Name is not null!");
		}
		if(email.trim().length()==0 || firstName.trim().length()==0 || lastName.trim().length()==0) {
			return "register";
		}
		HttpSession session=request.getSession();
		Product product=(Product) session.getAttribute("product");
		model.addAttribute("product",product);
		
		User user;
		if(customerService.emailExists(email)) {
			user=customerService.selectUser(email);
			user.setFirstName(firstName);
			user.setLastName(lastName);
			user.setEmail(email);
			customerService.Update(user);
		}
		else {
			user=new User();
			user.setFirstName(firstName);
			user.setLastName(lastName);
			user.setEmail(email);
			long t=1+customerService.selectMaxID();
			user.setUserID(t);
			customerService.Insert(user);
			
		}
		session.setAttribute("user", user);
		model.addAttribute("user",user);
		Cookie emailCookie=new Cookie("emailCookie", email);
		emailCookie.setMaxAge(60*60);
		response.addCookie(emailCookie);
		return "sound";
	}
}
