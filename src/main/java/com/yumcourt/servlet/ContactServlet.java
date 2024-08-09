package com.yumcourt.servlet;

import com.yumcourt.model.Contact;
import com.yumcourt.model.Address;
import com.yumcourt.service.ContactService;
import com.yumcourt.service.AddressService;
import com.yumcourt.repository.ContactRepository;
import com.yumcourt.repository.AddressRepository;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/contact")
public class ContactServlet extends HttpServlet {
    private ContactService contactService;
    private AddressService addressService;
    private ObjectMapper objectMapper;

    @Override
    public void init() throws ServletException {
        ContactRepository contactRepository = new ContactRepository(); // Initialize properly
        AddressRepository addressRepository = new AddressRepository(); // Initialize properly
        contactService = new ContactService(contactRepository);
        addressService = new AddressService(addressRepository);
        objectMapper = new ObjectMapper();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");
        if ("getContact".equals(action)) {
            long contactId = Long.parseLong(req.getParameter("id"));
            Contact contact = contactService.findContactById(contactId);
            if (contact != null) {
                resp.setContentType("application/json");
                resp.getWriter().write(objectMapper.writeValueAsString(contact));
            } else {
                resp.sendError(HttpServletResponse.SC_NOT_FOUND, "Contact not found");
            }
        } else {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid action");
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");
        if ("createContact".equals(action)) {
            long id = Long.parseLong(req.getParameter("id"));
            long phone = Long.parseLong(req.getParameter("phone"));
            long addressId = Long.parseLong(req.getParameter("addressId"));
            Address address = addressService.findAddressById(addressId);

            if (address != null) {
                Contact contact = new Contact(id, phone, address);
                contactService.createContact(contact);
                resp.setContentType("application/json");
                resp.getWriter().write("{\"message\":\"Contact created successfully\"}");
            } else {
                resp.sendError(HttpServletResponse.SC_NOT_FOUND, "Address not found");
            }
        } else {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid action");
        }
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");
        if ("updateContact".equals(action)) {
            long contactId = Long.parseLong(req.getParameter("id"));
            long phone = Long.parseLong(req.getParameter("phone"));
            long addressId = Long.parseLong(req.getParameter("addressId"));
            Address address = addressService.findAddressById(addressId);

            if (address != null) {
                Contact contact = new Contact(contactId, phone, address);
                contactService.updateContact(contact);
                resp.setContentType("application/json");
                resp.getWriter().write("{\"message\":\"Contact updated successfully\"}");
            } else {
                resp.sendError(HttpServletResponse.SC_NOT_FOUND, "Address not found");
            }
        } else {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid action");
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");
        if ("deleteContact".equals(action)) {
            long contactId = Long.parseLong(req.getParameter("id"));
            contactService.deleteContact(contactId);
            resp.setContentType("application/json");
            resp.getWriter().write("{\"message\":\"Contact deleted successfully\"}");
        } else {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid action");
        }
    }
}
