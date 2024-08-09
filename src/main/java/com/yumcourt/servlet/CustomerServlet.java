package com.yumcourt.servlet;

import com.yumcourt.model.Contact;
import com.yumcourt.model.Customer;
import com.yumcourt.service.CustomerService;
import com.yumcourt.repository.CustomerRepository;
import com.yumcourt.repository.ContactRepository;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/customer")
public class CustomerServlet extends HttpServlet {
    private CustomerService customerService;
    private ContactRepository contactRepository;

    @Override
    public void init() throws ServletException {
        contactRepository = new ContactRepository(); // Ensure this repository exists
        CustomerRepository customerRepository = new CustomerRepository(contactRepository);
        customerService = new CustomerService(customerRepository, contactRepository);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");
        if ("getCustomer".equals(action)) {
            long customerId = Long.parseLong(req.getParameter("id"));
            Customer customer = customerService.findCustomerById(customerId);
            if (customer != null) {
                resp.setContentType("application/json");
                resp.getWriter().write(customer.toJson());
            } else {
                resp.sendError(HttpServletResponse.SC_NOT_FOUND, "Customer not found");
            }
        } else {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid action");
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");
        if ("createCustomer".equals(action)) {
            // Extract Customer details from request
            long id = Long.parseLong(req.getParameter("id"));
            String name = req.getParameter("name");
            String email = req.getParameter("email");
            String password = req.getParameter("password");
            long contactId = Long.parseLong(req.getParameter("contact_id"));

            Contact contact = contactRepository.findById(contactId);
            Customer customer = new Customer(id, name, email, password, contact);
            customerService.createCustomer();
            resp.setContentType("application/json");
            resp.getWriter().write("{\"message\":\"Customer created successfully\"}");
        } else {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid action");
        }
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");
        if ("updateCustomer".equals(action)) {
            long customerId = Long.parseLong(req.getParameter("id"));
            String name = req.getParameter("name");
            String email = req.getParameter("email");
            String password = req.getParameter("password");
            long contactId = Long.parseLong(req.getParameter("contact_id"));

            Contact contact = contactRepository.findById(contactId);
            Customer customer = new Customer(customerId, name, email, password, contact);
            customerService.updateCustomer();
            resp.setContentType("application/json");
            resp.getWriter().write("{\"message\":\"Customer updated successfully\"}");
        } else {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid action");
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");
        if ("deleteCustomer".equals(action)) {
            long customerId = Long.parseLong(req.getParameter("id"));
            customerService.deleteCustomer();
            resp.setContentType("application/json");
            resp.getWriter().write("{\"message\":\"Customer deleted successfully\"}");
        } else {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid action");
        }
    }
}
