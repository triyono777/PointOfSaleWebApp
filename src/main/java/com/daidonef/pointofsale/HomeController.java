package com.daidonef.pointofsale;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.jasypt.util.password.StrongPasswordEncryptor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class HomeController {
	
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String home(Model model, HttpServletRequest request) {
		
		request.getSession(true).invalidate();
		
		return "home";
	}
	
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public String login(Model model, HttpServletRequest request) {
		
		request.getSession(true).invalidate();
		
		return "login";
	}
	
	@RequestMapping(value = "/searchcustomer", method = RequestMethod.POST)
	public String searchCustomer(Model model, HttpServletRequest request) {
		
		HttpSession session = request.getSession(true);
		session.setAttribute("account", null);
		
		if (session.getAttribute("employee") == null) {
			
			if(request.getParameter("userName") == null) {
				return CheckingInformation.login(model);
			} else {
				return GettingInformation.employee(request.getParameter("userName"), 
						request.getParameter("password"), model, session);
			}
		}
		
		Employee employee = (Employee)session.getAttribute("employee");
		if (employee.getUserName().equals("Admin")) {
			model.addAttribute("owner", GettingInformation.toOwnerPage());
		}
		
		return "searchcustomer";
		
	}
	
	@RequestMapping(value = "/addcustomer", method = RequestMethod.POST)
	public String addCustomer(Model model, HttpServletRequest request) {
		
		HttpSession session = request.getSession(true);
		if (session.getAttribute("employee") ==  null) {
			return "login";
		}
		
		return "addcustomer";
	}
	
	@RequestMapping(value = "/inputproduct", method = RequestMethod.POST)
	public String inputProduct(Model model, HttpServletRequest request) {
		
		HttpSession session = request.getSession(true);
		if (session.getAttribute("employee") ==  null) {
			return "login";
		}
		
		Account account;
		
		if (session.getAttribute("account") == null) {
			if (request.getParameter("search") == null) {
				if (CheckingInformation.noCustomerAccount(request.getParameter("userName"))) {
					return "addcustomer";
				}
				account = SavingInformation.newAccount(request);
				DAOAccount.addAccount(account);
				session.setAttribute("account", account);
			} else {
				if (CheckingInformation.oneCustomerAccount(request.getParameter("search"))) {
					model.addAttribute("notFound", "Customer not found!<br>Please try again!");
					return "searchcustomer";
				}
				account = GettingInformation.account(request.getParameter("search"), request);
				session.setAttribute("account", account);
			}
		}
		
		if (request.getParameter("productNumber") != null) {
			if (CheckingInformation.oneProduct(Integer.parseInt(request.getParameter(
					"productNumber")))){
				model.addAttribute("wrongProduct", "You entered the wrong product.");
				return "inputproduct";
			}
			SavingInformation.addProduct(model, request, session);
		}
		
		return "inputproduct";
	}
	
	@RequestMapping(value = "/payment", method = RequestMethod.POST)
	public String payment(Model model, HttpServletRequest request) {
		
		HttpSession session = request.getSession(true);
		if (session.getAttribute("employee") ==  null) {
			return "login";
		}
		
		if(session.getAttribute("total") == null) {
			return "inputproduct";
		}
		
		if (request.getParameter("paymentType") != null) {
			
			session.setAttribute("paymentType", request.getParameter("paymentType"));
			
			if (request.getParameter("paymentType").equals("cash")) {
				DisplayingInformation.cashForm(model, request, session);
			}
		
			if (request.getParameter("paymentType").equals("creditCard")) {
				DisplayingInformation.CreditCardForm(model, request, session);
			}
		
			if (request.getParameter("paymentType").equals("check")) {
				DisplayingInformation.CheckForm(model, request, session);
			}
		}
		model.addAttribute("subTotal", session.getAttribute("total"));
		
		return "payment";
	}
	
	@RequestMapping(value = "/receipt", method = RequestMethod.POST)
	public String receipt(Model model, HttpServletRequest request) {
		
		HttpSession session = request.getSession(true);
		if (session.getAttribute("employee") ==  null) {
			return "login";
		}
		//Need to add to receipt.jsp
		//Need to test for cash, credit card, and check.
		if (request.getParameter("customerCash") != null) {
			model.addAttribute("cash", SavingInformation.paymentCash(request, session));
		}
		
		if (request.getParameter("creditCardNumber") != null) {
			model.addAttribute("creditCard", SavingInformation.paymentCreditCard(
					request, session));
		}
		
		if (request.getParameter("checkNumber") != null) {
			model.addAttribute("check", SavingInformation.paymentCheck(request, session));
		}
		
		return "receipt";
	}
	
	@RequestMapping(value = "/ownerpage", method = RequestMethod.POST)
	public String ownerPage(Model model, HttpServletRequest request) {
		
		HttpSession session = request.getSession(true);
		Employee owner = (Employee) session.getAttribute("employee");
		
		if (owner.getUserName().equals("Admin")) {
			
		} else {
			return "home";
		}
		
		if (request.getParameter("productName") != null) {
			if (CheckingInformation.oneProduct(request.getParameter("productName"))) {
				model.addAttribute("productExist", "Product is already in the system!");
				return "ownerpage";
			}
			model.addAttribute("addedProduct", SavingInformation.addProductToData(request)
					.getProductName());
			return "ownerpage";
		}
		
		if (request.getParameter("searchProduct") != null) {
			model.addAttribute("products", DAOProduct.getProduct(Query.searchingProduct(
					request.getParameter("searchProduct"))));
		}
		
		if (request.getParameter("productID") != null) {
			model.addAttribute("productUpdated", SavingInformation.updateProduct(request));
			model.addAttribute("productUpdated2", " Product Updated");
		}
		
		if (request.getParameter("deleteProduct") != null) {
			model.addAttribute("productDeleted", DAOProduct.deleteProduct(Integer.parseInt(
					request.getParameter("deleteProduct"))));
			model.addAttribute("productDeleted2", " Product Deleted");
		}
		
		if (request.getParameter("searchEmployee") != null) {
			model.addAttribute("employees", DAOEmployee.getEmployee(Query.searchingEmployee(
					request.getParameter("searchEmployee"))));
		}
		
		if (request.getParameter("employeeID") != null) {
			model.addAttribute("employeeUpdated", SavingInformation.updateEmployee(request));
			model.addAttribute("employeeUpdated2", " Employee Updated");
		}
		
		if (request.getParameter("deleteEmployee") != null) {
			model.addAttribute("employeeDeleted", DAOEmployee.deleteEmployee(Integer.parseInt(
					request.getParameter("deleteEmployee"))));
			model.addAttribute("employeeDeleted2", " Employee Deleted");
		}
		
		if (request.getParameter("userName") != null) {
			if (CheckingInformation.noEmployee(request.getParameter("userName"))) {
				model.addAttribute("employed", "User Name for New Employee already exist!<br>"
						+ "Please try another User Name!");
				return "ownerpage";
			}
			Employee employee = SavingInformation.newEmployee(request);
			DAOEmployee.addEmployee(employee);
			
			model.addAttribute("employee", employee);
		}
		
		return "ownerpage";
	}
	
	@RequestMapping(value = "/updateproduct", method = RequestMethod.POST)
	public String updateProduct(Model model, HttpServletRequest request) {
		
		model.addAttribute("product", GettingInformation.product(Integer.parseInt(
				request.getParameter("updateProduct"))));
		
		return "updateproduct";
	}
	
	@RequestMapping(value = "/updateemployee", method = RequestMethod.POST)
	public String updateEmployee(Model model, HttpServletRequest request) {
		
		model.addAttribute("employee", GettingInformation.employee(request));
		
		return "updateemployee";
	}
	
}