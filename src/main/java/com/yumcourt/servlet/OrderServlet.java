package com.yumcourt.servlet;

import com.yumcourt.model.Order;
import com.yumcourt.model.Customer;
import com.yumcourt.model.Menu;
import com.yumcourt.model.DeliveryExecutive;
import com.yumcourt.repository.*;
import com.yumcourt.service.OrderService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;

@WebServlet("/order")
public class OrderServlet extends HttpServlet {

    private OrderService orderService;

    @Override
    public void init() throws ServletException {
        // Initialize dependencies
        ContactRepository contactRepository = new ContactRepository();
        CustomerRepository customerRepository = new CustomerRepository(contactRepository);
        MenuRepository menuRepository = new MenuRepository();
        DeliveryExecutiveRepository deliveryExecutiveRepository = new DeliveryExecutiveRepository();
        OrderRepository orderRepository = new OrderRepository(customerRepository, menuRepository, deliveryExecutiveRepository);
        orderService = new OrderService(orderRepository, customerRepository, menuRepository, deliveryExecutiveRepository);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");
        if ("getOrder".equals(action)) {
            try {
                long orderId = Long.parseLong(req.getParameter("id"));
                Order order = orderService.findOrderById(orderId);
                if (order != null) {
                    resp.setContentType("application/json");
                    resp.getWriter().write(order.toJson()); // Ensure toJson() method exists
                } else {
                    resp.sendError(HttpServletResponse.SC_NOT_FOUND, "Order not found");
                }
            } catch (NumberFormatException e) {
                resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid ID format");
            }
        } else {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid action");
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");
        if ("createOrder".equals(action)) {
            try {
                long id = Long.parseLong(req.getParameter("id"));
                long customerId = Long.parseLong(req.getParameter("customerId"));
                long menuId = Long.parseLong(req.getParameter("menuId"));
                long deliveryExecutiveId = Long.parseLong(req.getParameter("deliveryExecutiveId"));
                LocalDateTime timestamp = LocalDateTime.now();

                Customer customer = orderService.findCustomerById(customerId);
                Menu menu = orderService.findMenuById(menuId);
                DeliveryExecutive deliveryExecutive = orderService.findDeliveryExecutiveById(deliveryExecutiveId);

                if (customer != null && menu != null && deliveryExecutive != null) {
                    Order order = new Order(id, customer, menu, deliveryExecutive, timestamp);
                    orderService.createOrder(order);
                    resp.setContentType("application/json");
                    resp.getWriter().write("{\"message\":\"Order created successfully\"}");
                } else {
                    resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid customer, menu, or delivery executive");
                }
            } catch (NumberFormatException e) {
                resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid number format");
            }
        } else {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid action");
        }
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");
        if ("updateOrder".equals(action)) {
            try {
                long id = Long.parseLong(req.getParameter("id"));
                long customerId = Long.parseLong(req.getParameter("customerId"));
                long menuId = Long.parseLong(req.getParameter("menuId"));
                long deliveryExecutiveId = Long.parseLong(req.getParameter("deliveryExecutiveId"));
                LocalDateTime timestamp = LocalDateTime.now();

                Customer customer = orderService.findCustomerById(customerId);
                Menu menu = orderService.findMenuById(menuId);
                DeliveryExecutive deliveryExecutive = orderService.findDeliveryExecutiveById(deliveryExecutiveId);

                if (customer != null && menu != null && deliveryExecutive != null) {
                    Order order = new Order(id, customer, menu, deliveryExecutive, timestamp);
                    orderService.updateOrder(order);
                    resp.setContentType("application/json");
                    resp.getWriter().write("{\"message\":\"Order updated successfully\"}");
                } else {
                    resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid customer, menu, or delivery executive");
                }
            } catch (NumberFormatException e) {
                resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid number format");
            }
        } else {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid action");
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");
        if ("deleteOrder".equals(action)) {
            try {
                long id = Long.parseLong(req.getParameter("id"));
                orderService.deleteOrder(id);
                resp.setContentType("application/json");
                resp.getWriter().write("{\"message\":\"Order deleted successfully\"}");
            } catch (NumberFormatException e) {
                resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid number format");
            }
        } else {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid action");
        }
    }
}
