package com.yumcourt.servlet;

import com.yumcourt.model.Address;
import com.yumcourt.model.Contact;
import com.yumcourt.model.Restaurant;
import com.yumcourt.repository.ContactRepository;
import com.yumcourt.repository.RestaurantRepository;
import com.yumcourt.service.RestaurantService;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;

@WebServlet("/restaurant")
public class RestaurantServlet extends HttpServlet {
    private RestaurantService restaurantService;

    @Override
    public void init() throws ServletException {
        // Initialize your dependencies here, e.g., via dependency injection or manual setup
        // Assuming repositories are provided in the actual implementation
        restaurantService = new RestaurantService(new RestaurantRepository(new ContactRepository()), new ContactRepository());
        ObjectMapper objectMapper = new ObjectMapper();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");
        if ("getRestaurant".equals(action)) {
            long restaurantId = Long.parseLong(req.getParameter("id"));
            Restaurant restaurant = restaurantService.getRestaurantById(restaurantId);
            if (restaurant != null) {
                resp.setContentType("application/json");
                resp.getWriter().write(restaurant.toJson());
            } else {
                resp.sendError(HttpServletResponse.SC_NOT_FOUND, "Restaurant not found");
            }
        } else {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid action");
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");
        if ("createRestaurant".equals(action)) {
            long id = Long.parseLong(req.getParameter("id"));
            String name = req.getParameter("name");
            // Simulate address and contact creation
            // These should be extracted from the request parameters
            // Example: Address and Contact details can be obtained similarly as in the service

            Address address = new Address(0, req.getParameter("addressName"), Long.parseLong(req.getParameter("flatNo")),
                    req.getParameter("buildingName"), req.getParameter("street"), req.getParameter("city"),
                    Long.parseLong(req.getParameter("pinCode")),req.getParameter("state"));
            Contact contact = new Contact(Long.parseLong(req.getParameter("contactId")), Long.parseLong(req.getParameter("phone")), address);

            Restaurant restaurant = new Restaurant(id, name, contact, new ArrayList<>());
            restaurantService.createRestaurant();
            resp.setContentType("application/json");
            resp.getWriter().write("{\"message\":\"Restaurant created successfully\"}");
        } else {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid action");
        }
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");
        if ("updateRestaurant".equals(action)) {
            long id = Long.parseLong(req.getParameter("id"));
            String addressName = req.getParameter("name");
            long flatNo = Long.parseLong(req.getParameter("flatNo"));
            String buildingName = req.getParameter("buildingName");
            String street = req.getParameter("street");
            String city = req.getParameter("city");
            long pinCode = Long.parseLong(req.getParameter("pinCode"));
            String state = req.getParameter("state");

            Address address = new Address(0, addressName, flatNo, buildingName, street, city, pinCode, state);
            Contact contact = new Contact(0, 0, address); // Placeholder for contact update

            Restaurant restaurant = new Restaurant(id, addressName, contact, new ArrayList<>());
            restaurantService.updateRestaurant(restaurant);
            resp.setContentType("application/json");
            resp.getWriter().write("{\"message\":\"Restaurant updated successfully\"}");
        } else {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid action");
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");
        if ("deleteRestaurant".equals(action)) {
            long id = Long.parseLong(req.getParameter("id"));
            restaurantService.deleteRestaurant();
            resp.setContentType("application/json");
            resp.getWriter().write("{\"message\":\"Restaurant deleted successfully\"}");
        } else resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid action");
    }
}
