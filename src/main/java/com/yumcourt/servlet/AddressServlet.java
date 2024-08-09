package com.yumcourt.servlet;

import com.yumcourt.model.Address;
import com.yumcourt.service.AddressService;
import com.yumcourt.repository.AddressRepository;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/address")
public class AddressServlet extends HttpServlet {
    private AddressService addressService;
    private ObjectMapper objectMapper;

    @Override
    public void init() throws ServletException {
        AddressRepository addressRepository = new AddressRepository(); // Initialize properly
        addressService = new AddressService(addressRepository);
        objectMapper = new ObjectMapper();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");
        if ("getAddress".equals(action)) {
            long addressId;
            try {
                addressId = Long.parseLong(req.getParameter("id"));
            } catch (NumberFormatException e) {
                resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid Address ID");
                return;
            }

            Address address = addressService.findAddressById(addressId);
            if (address != null) {
                resp.setContentType("application/json");
                resp.getWriter().write(objectMapper.writeValueAsString(address));
            } else {
                resp.sendError(HttpServletResponse.SC_NOT_FOUND, "Address not found");
            }
        } else {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid action");
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");
        if ("createAddress".equals(action)) {
            Address address = parseAddressFromRequest(req);
            addressService.createAddress(address);
            resp.setContentType("application/json");
            resp.getWriter().write("{\"message\":\"Address created successfully\"}");
        } else {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid action");
        }
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");
        if ("updateAddress".equals(action)) {
            Address address = parseAddressFromRequest(req);
            addressService.updateAddress(address);
            resp.setContentType("application/json");
            resp.getWriter().write("{\"message\":\"Address updated successfully\"}");
        } else {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid action");
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");
        if ("deleteAddress".equals(action)) {
            long addressId;
            try {
                addressId = Long.parseLong(req.getParameter("id"));
            } catch (NumberFormatException e) {
                resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid Address ID");
                return;
            }

            addressService.deleteAddress(addressId);
            resp.setContentType("application/json");
            resp.getWriter().write("{\"message\":\"Address deleted successfully\"}");
        } else {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid action");
        }
    }

    private Address parseAddressFromRequest(HttpServletRequest req) throws IOException {
        long id = Long.parseLong(req.getParameter("id"));
        String name = req.getParameter("name");
        long flatNo = Long.parseLong(req.getParameter("flatNo"));
        String buildingName = req.getParameter("buildingName");
        String street = req.getParameter("street");
        String city = req.getParameter("city");
        long pinCode = Long.parseLong(req.getParameter("pinCode"));
        String state = req.getParameter("state");

        return new Address(id, name, flatNo, buildingName, street, city, pinCode, state);
    }
}
