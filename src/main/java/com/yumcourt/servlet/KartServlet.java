package com.yumcourt.servlet;

import com.yumcourt.model.Kart;
import com.yumcourt.service.KartService;
import com.yumcourt.repository.KartRepository;
import com.yumcourt.repository.ContactRepository;
import com.yumcourt.repository.CustomerRepository;
import com.yumcourt.repository.RestaurantRepository;
import com.yumcourt.model.Customer;
import com.yumcourt.model.Restaurant;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/kart")
public class KartServlet extends HttpServlet {
    private KartService kartService;

    @Override
    public void init() throws ServletException {
        KartRepository kartRepository = new KartRepository();
        kartService = new KartService(kartRepository);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");
        if ("getKart".equals(action)) {
            long kartId = Long.parseLong(req.getParameter("id"));
            Kart kart = kartService.findKartById(kartId);
            if (kart != null) {
                resp.setContentType("application/json");
                resp.getWriter().write(kart.toString()); // Adjust according to your JSON handling
            } else {
                resp.sendError(HttpServletResponse.SC_NOT_FOUND, "Kart not found");
            }
        } else {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid action");
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");
        if ("createKart".equals(action)) {
            double finalPrice = Double.parseDouble(req.getParameter("finalPrice"));
            long customerId = Long.parseLong(req.getParameter("customerId"));
            long restaurantId = Long.parseLong(req.getParameter("restaurantId"));

            ContactRepository contactRepository = new ContactRepository();
            CustomerRepository customerRepository = new CustomerRepository(contactRepository);
            Customer customer = customerRepository.findById(customerId);
            RestaurantRepository restaurantRepository = new RestaurantRepository(contactRepository);
            Restaurant restaurant = restaurantRepository.findById(restaurantId);

            Kart kart = new Kart(0, finalPrice, customer, restaurant);
            kartService.createKart(kart);
            resp.setContentType("application/json");
            resp.getWriter().write("{\"message\":\"Kart created successfully\"}");
        } else {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid action");
        }
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");
        if ("updateKart".equals(action)) {
            long id = Long.parseLong(req.getParameter("id"));
            double finalPrice = Double.parseDouble(req.getParameter("finalPrice"));
            long customerId = Long.parseLong(req.getParameter("customerId"));
            long restaurantId = Long.parseLong(req.getParameter("restaurantId"));

            ContactRepository contactRepository = new ContactRepository();
            CustomerRepository customerRepository = new CustomerRepository(contactRepository);
            Customer customer = customerRepository.findById(customerId);
            RestaurantRepository restaurantRepository = new RestaurantRepository(contactRepository);
            Restaurant restaurant = restaurantRepository.findById(restaurantId);

            Kart kart = new Kart(id, finalPrice, customer, restaurant);
            kartService.updateKart(kart);
            resp.setContentType("application/json");
            resp.getWriter().write("{\"message\":\"Kart updated successfully\"}");
        } else {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid action");
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");
        if ("deleteKart".equals(action)) {
            long id = Long.parseLong(req.getParameter("id"));
            kartService.deleteKart(id);
            resp.setContentType("application/json");
            resp.getWriter().write("{\"message\":\"Kart deleted successfully\"}");
        } else {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid action");
        }
    }
}
