package com.yumcourt;

import com.yumcourt.model.*;
import com.yumcourt.repository.*;
import com.yumcourt.service.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Scanner;

public class App {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        AddressRepository addressRepository = new AddressRepository();
        ContactRepository contactRepository = new ContactRepository();
        CustomerRepository customerRepository = new CustomerRepository(contactRepository);
        MenuRepository menuRepository = new MenuRepository();
        DeliveryExecutiveRepository deliveryExecutiveRepository = new DeliveryExecutiveRepository();
        OrderRepository orderRepository = new OrderRepository(customerRepository, menuRepository, deliveryExecutiveRepository);
        RestaurantRepository restaurantRepository = new RestaurantRepository(contactRepository);

        AddressService addressService = new AddressService(addressRepository);
        ContactService contactService = new ContactService(contactRepository);
        CustomerService customerService = new CustomerService(customerRepository, contactRepository);
        DeliveryExecutiveService deliveryExecutiveService = new DeliveryExecutiveService(deliveryExecutiveRepository);
        KartService kartService = new KartService(new KartRepository());
        MenuService menuService = new MenuService(menuRepository);
        OrderService orderService = new OrderService(orderRepository, customerRepository, menuRepository, deliveryExecutiveRepository);
        RestaurantService restaurantService = new RestaurantService(restaurantRepository, contactRepository);

        while (true) {
            System.out.println("===================================================");
            System.out.println("-----------Online Food Ordering System-----------");
            System.out.println("===================================================");
            System.out.println("Select an operation:");
            System.out.println("1. Address Operations");
            System.out.println("2. Contact Operations");
            System.out.println("3. Customer Operations");
            System.out.println("4. Delivery Executive Operations");
            System.out.println("5. Kart Operations");
            System.out.println("6. Menu Operations");
            System.out.println("7. Order Operations");
            System.out.println("8. Restaurant Operations");
            System.out.println("9. Exit");

            System.out.print("Enter your choice here: ");
            int choice = Integer.parseInt(scanner.nextLine());

            switch (choice) {
                case 1:
                    handleAddressOperations(addressService, scanner);
                    break;
                case 2:
                    handleContactOperations(contactService, scanner);
                    break;
                case 3:
                    handleCustomerOperations(customerService, scanner);
                    break;
                case 4:
                    handleDeliveryExecutiveOperations(deliveryExecutiveService, scanner);
                    break;
                case 5:
                    handleKartOperations(kartService, scanner);
                    break;
                case 6:
                    handleMenuOperations(menuService, scanner);
                    break;
                case 7:
                    handleOrderOperations(orderService, scanner);
                    break;
                case 8:
                    handleRestaurantOperations(restaurantService, scanner);
                    break;
                case 9:
                    System.out.println("Exited");
                    scanner.close();
                    return;
                default:
                    System.out.println("Invalid choice, please try again.");
            }
        }
    }

    private static void handleAddressOperations(AddressService service, Scanner scanner) {
        int choice;
        do {
            System.out.println("Address Operations:");
            System.out.println("1. Retrieve Addresses");
            System.out.println("2. Create Address");
            System.out.println("3. Update Address");
            System.out.println("4. Delete Address");
            System.out.println("5. Find Address by ID");
            System.out.println("6. Return to Main Menu");

            System.out.print("Enter your choice here: ");
            choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    service.retrieveAddresses().forEach(System.out::println);
                    break;
                case 2:
                    Address address = getAddressDetails(scanner);
                    service.createAddress(address);
                    break;
                case 3:
                    Address updatedAddress = getAddressDetails(scanner);
                    service.updateAddress(updatedAddress);
                    break;
                case 4:
                    System.out.print("Enter Address ID to delete: ");
                    long deleteAddressId = scanner.nextLong();
                    service.deleteAddress(deleteAddressId);
                    break;
                case 5:
                    System.out.print("Enter Address ID to find: ");
                    long findAddressId = scanner.nextLong();
                    System.out.println(service.findAddressById(findAddressId));
                    break;
                case 6:
                    // Exit the do-while loop
                    break;
                default:
                    System.out.println("Invalid choice.");
            }
        } while (choice != 6);
    }

    private static void handleContactOperations(ContactService service, Scanner scanner) {
        int choice;
        do {
            System.out.println("Contact Operations:");
            System.out.println("1. Retrieve Contacts");
            System.out.println("2. Create Contact");
            System.out.println("3. Update Contact");
            System.out.println("4. Delete Contact");
            System.out.println("5. Find Contact by ID");
            System.out.println("6. Return to Main Menu");

            System.out.print("Enter your choice here: ");
            choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    service.retrieveContacts().forEach(System.out::println);
                    break;
                case 2:
                    Contact contact = getContactDetails(scanner);
                    service.createContact(contact);
                    break;
                case 3:
                    Contact updatedContact = getContactDetails(scanner);
                    service.updateContact(updatedContact);
                    break;
                case 4:
                    System.out.print("Enter Contact ID to delete: ");
                    long deleteContactId = scanner.nextLong();
                    service.deleteContact(deleteContactId);
                    break;
                case 5:
                    System.out.print("Enter Contact ID to find: ");
                    long findContactId = scanner.nextLong();
                    System.out.println(service.findContactById(findContactId));
                    break;
                case 6:
                    // Exit the do-while loop
                    break;
                default:
                    System.out.println("Invalid choice.");
            }
        } while (choice != 6);
    }

    private static void handleCustomerOperations(CustomerService service, Scanner scanner) {
        int choice;
        do {
            System.out.println("Customer Operations:");
            System.out.println("1. Retrieve Customers");
            System.out.println("2. Create Customer");
            System.out.println("3. Update Customer");
            System.out.println("4. Delete Customer");
            System.out.println("5. Find Customer by ID");
            System.out.println("6. Return to Main Menu");

            System.out.print("Enter your choice here: ");
            choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    service.retrieveCustomers().forEach(System.out::println);
                    break;
                case 2:
                    Customer customer = getCustomerDetails(scanner);
                    service.createCustomer();
                    break;
                case 3:
                    Customer updatedCustomer = getCustomerDetails(scanner);
                    service.updateCustomer();
                    break;
                case 4:
                    System.out.print("Enter Customer ID to delete: ");
                    long deleteCustomerId = scanner.nextLong();
                    service.deleteCustomer();
                    break;
                case 5:
                    System.out.print("Enter Customer ID to find: ");
                    long findCustomerId = scanner.nextLong();
                    System.out.println(service.findCustomerById(findCustomerId));
                    break;
                case 6:
                    // Exit the do-while loop
                    break;
                default:
                    System.out.println("Invalid choice.");
            }
        } while (choice != 6);
    }

    private static void handleDeliveryExecutiveOperations(DeliveryExecutiveService service, Scanner scanner) {
        int choice;
        do {
            System.out.println("Delivery Executive Operations:");
            System.out.println("1. Retrieve Delivery Executives");
            System.out.println("2. Create Delivery Executive");
            System.out.println("3. Update Delivery Executive");
            System.out.println("4. Delete Delivery Executive");
            System.out.println("5. Find Delivery Executive by ID");
            System.out.println("6. Return to Main Menu");

            System.out.print("Enter your choice here: ");
            choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    service.retrieveDeliveryExecutives().forEach(System.out::println);
                    break;
                case 2:
                    DeliveryExecutive deliveryExecutive = getDeliveryExecutiveDetails(scanner);
                    service.createDeliveryExecutive(deliveryExecutive);
                    break;
                case 3:
                    DeliveryExecutive updatedDeliveryExecutive = getDeliveryExecutiveDetails(scanner);
                    service.updateDeliveryExecutive(updatedDeliveryExecutive);
                    break;
                case 4:
                    System.out.print("Enter Delivery Executive ID to delete: ");
                    long deleteDeliveryExecutiveId = scanner.nextLong();
                    service.deleteDeliveryExecutive(deleteDeliveryExecutiveId);
                    break;
                case 5:
                    System.out.print("Enter Delivery Executive ID to find: ");
                    long findDeliveryExecutiveId = scanner.nextLong();
                    System.out.println(service.findDeliveryExecutiveById(findDeliveryExecutiveId));
                    break;
                case 6:
                    // Exit the do-while loop
                    break;
                default:
                    System.out.println("Invalid choice.");
            }
        } while (choice != 6);
    }

    private static void handleKartOperations(KartService service, Scanner scanner) {
        int choice;
        do {
            System.out.println("Kart Operations:");
            System.out.println("1. Retrieve Karts");
            System.out.println("2. Create Kart");
            System.out.println("3. Update Kart");
            System.out.println("4. Delete Kart");
            System.out.println("5. Find Kart by ID");
            System.out.println("6. Return to Main Menu");

            System.out.print("Enter your choice here: ");
            choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    service.retrieveKarts().forEach(System.out::println);
                    break;
                case 2:
                    Kart kart = getKartDetails(scanner);
                    service.createKart(kart);
                    break;
                case 3:
                    Kart updatedKart = getKartDetails(scanner);
                    service.updateKart(updatedKart);
                    break;
                case 4:
                    System.out.print("Enter Kart ID to delete: ");
                    long deleteKartId = scanner.nextLong();
                    service.deleteKart(deleteKartId);
                    break;
                case 5:
                    System.out.print("Enter Kart ID to find: ");
                    long findKartId = scanner.nextLong();
                    System.out.println(service.findKartById(findKartId));
                    break;
                case 6:
                    // Exit the do-while loop
                    break;
                default:
                    System.out.println("Invalid choice.");
            }
        } while (choice != 6);
    }

    private static void handleMenuOperations(MenuService service, Scanner scanner) {
        int choice;
        do {
            System.out.println("Menu Operations:");
            System.out.println("1. Retrieve Menus");
            System.out.println("2. Create Menu");
            System.out.println("3. Update Menu");
            System.out.println("4. Delete Menu");
            System.out.println("5. Find Menu by ID");
            System.out.println("6. Return to Main Menu");

            System.out.print("Enter your choice here: ");
            choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    service.retrieveMenus().forEach(System.out::println);
                    break;
                case 2:
                    Menu menu = getMenuDetails(scanner);
                    service.createMenu(menu);
                    break;
                case 3:
                    Menu updatedMenu = getMenuDetails(scanner);
                    service.updateMenu(updatedMenu);
                    break;
                case 4:
                    System.out.print("Enter Menu ID to delete: ");
                    long deleteMenuId = scanner.nextLong();
                    service.deleteMenu(deleteMenuId);
                    break;
                case 5:
                    System.out.print("Enter Menu ID to find: ");
                    long findMenuId = scanner.nextLong();
                    System.out.println(service.findMenuById(findMenuId));
                    break;
                case 6:
                    // Exit the do-while loop
                    break;
                default:
                    System.out.println("Invalid choice.");
            }
        } while (choice != 6);
    }

    private static void handleOrderOperations(OrderService service, Scanner scanner) {
        int choice;
        do {
            System.out.println("Order Operations:");
            System.out.println("1. Retrieve Orders");
            System.out.println("2. Create Order");
            System.out.println("3. Update Order");
            System.out.println("4. Delete Order");
            System.out.println("5. Find Order by ID");
            System.out.println("6. Return to Main Menu");

            System.out.print("Enter your choice here: ");
            choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    service.retrieveOrders().forEach(System.out::println);
                    break;
                case 2:
                    Order order = getOrderDetails(scanner);
                    service.createOrder(order);
                    break;
                case 3:
                    Order updatedOrder = getOrderDetails(scanner);
                    service.updateOrder(updatedOrder);
                    break;
                case 4:
                    System.out.print("Enter Order ID to delete: ");
                    long deleteOrderId = scanner.nextLong();
                    service.deleteOrder(deleteOrderId);
                    break;
                case 5:
                    System.out.print("Enter Order ID to find: ");
                    long findOrderId = scanner.nextLong();
                    System.out.println(service.findOrderById(findOrderId));
                    break;
                case 6:
                    // Exit the do-while loop
                    break;
                default:
                    System.out.println("Invalid choice.");
            }
        } while (choice != 6);
    }

    private static void handleRestaurantOperations(RestaurantService service, Scanner scanner) {
        int choice;
        do {
            System.out.println("Restaurant Operations:");
            System.out.println("1. Retrieve Restaurants");
            System.out.println("2. Create Restaurant");
            System.out.println("3. Update Restaurant");
            System.out.println("4. Delete Restaurant");
            System.out.println("5. Find Restaurant by ID");
            System.out.println("6. Return to Main Menu");

            System.out.print("Enter your choice here: ");
            choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    service.retrieveRestaurants().forEach(System.out::println);
                    break;
                case 2:
                    Restaurant restaurant = getRestaurantDetails(scanner);
                    service.createRestaurant();
                    break;
                case 3:
                    Restaurant updatedRestaurant = getRestaurantDetails(scanner);
                    service.updateRestaurant(updatedRestaurant);
                    break;
                case 4:
                    System.out.print("Enter Restaurant ID to delete: ");
                    long deleteRestaurantId = scanner.nextLong();
                    service.deleteRestaurant();
                    break;
                case 5:
                    System.out.print("Enter Restaurant ID to find: ");
                    long findRestaurantId = scanner.nextLong();
                    service.findRestaurantById(findRestaurantId);
                    break;
                case 6:
                    // Exit the do-while loop
                    break;
                default:
                    System.out.println("Invalid choice.");
            }
        } while (choice != 6);
    }

    private static Address getAddressDetails(Scanner scanner) {
        System.out.print("Enter Address ID: ");
        long id = scanner.nextLong();
        scanner.nextLine(); // Consume newline
        System.out.print("Enter Name: ");
        String name = scanner.nextLine();
        System.out.print("Enter Flat No: ");
        long flatNo = scanner.nextLong();
        scanner.nextLine(); // Consume newline
        System.out.print("Enter Building Name: ");
        String buildingName = scanner.nextLine();
        System.out.print("Enter Street: ");
        String street = scanner.nextLine();
        System.out.print("Enter City: ");
        String city = scanner.nextLine();
        System.out.print("Enter Pin Code: ");
        long pinCode = scanner.nextLong();
        scanner.nextLine(); // Consume newline
        System.out.print("Enter State: ");
        String state = scanner.nextLine();

        return new Address(id, name, flatNo, buildingName, street, city, pinCode, state);
    }

    private static Contact getContactDetails(Scanner scanner) {
        System.out.print("Enter Contact ID: ");
        long id = scanner.nextLong();
        scanner.nextLine(); // Consume newline
        System.out.print("Enter Phone Number: ");
        long phone = scanner.nextLong();
        scanner.nextLine(); // Consume newline
        System.out.print("Enter Address ID: ");
        long addressId = scanner.nextLong();
        Address address = AddressService.findAddressById(addressId);
        return new Contact(id, phone, address);
    }

    private static Customer getCustomerDetails(Scanner scanner) {
        System.out.print("Enter Customer ID: ");
        long id = scanner.nextLong();
        scanner.nextLine(); // Consume newline
        System.out.print("Enter Name: ");
        String name = scanner.nextLine();
        System.out.print("Enter Email: ");
        String email = scanner.nextLine();
        System.out.print("Enter Password: ");
        String password = scanner.nextLine();
        System.out.print("Enter Contact ID: ");
        long contactId = scanner.nextLong();
        Contact contact = ContactService.findContactById(contactId);
        return new Customer(id, name, email, password, contact);
    }

    private static DeliveryExecutive getDeliveryExecutiveDetails(Scanner scanner) {
        System.out.print("Enter Delivery Executive ID: ");
        long id = scanner.nextLong();
        scanner.nextLine(); // Consume newline
        System.out.print("Enter Name: ");
        String name = scanner.nextLine();
        System.out.print("Enter Phone Number: ");
        long phone = scanner.nextLong();
        System.out.print("Enter Active Status (true/false): ");
        boolean isActive = scanner.nextBoolean();
        return new DeliveryExecutive(id, name, phone, isActive);
    }

    private static Menu getMenuDetails(Scanner scanner) {
        System.out.print("Enter Menu ID: ");
        long id = scanner.nextLong();
        scanner.nextLine(); // Consume newline
        System.out.print("Enter Name: ");
        String name = scanner.nextLine();
        System.out.print("Enter Description: ");
        String description = scanner.nextLine();
        System.out.print("Enter Price: ");
        double price = scanner.nextDouble();
        scanner.nextLine();
        System.out.print("Enter Restaurant ID: ");
        long restaurantId = scanner.nextLong();
        Restaurant restaurant = (Restaurant) RestaurantService.findRestaurantById(restaurantId);
        System.out.print("Enter Availability (true/false): ");
        boolean available = scanner.nextBoolean();
        return new Menu(id, name, description, price, restaurant, available);
    }

    private static Kart getKartDetails(Scanner scanner) {
        System.out.print("Enter Kart ID: ");
        long id = scanner.nextLong();
        scanner.nextLine(); // Consume newline
        System.out.print("Enter Amount: ");
        double amount = scanner.nextDouble();
        scanner.nextLine(); // Consume newline
        System.out.print("Enter Customer ID: ");
        long customerId = scanner.nextLong();
        Customer customer = CustomerService.findCustomerById(customerId); // Assuming customerService is available
        System.out.print("Enter Restaurant ID: ");
        long restaurantId = scanner.nextLong();
        Restaurant restaurant = (Restaurant) RestaurantService.findRestaurantById(restaurantId); // Assuming restaurantService is available
        return new Kart(id, amount, customer, restaurant);
    }

    private static Order getOrderDetails(Scanner scanner) {
        System.out.print("Enter Order ID: ");
        long id = scanner.nextLong();
        scanner.nextLine(); // Consume newline
        System.out.print("Enter Customer ID: ");
        long customerId = scanner.nextLong();
        Customer customer = CustomerService.findCustomerById(customerId); // Assuming customerService is available
        System.out.print("Enter Menu ID: ");
        long menuId = scanner.nextLong();
        Menu menu = MenuService.findMenuById(menuId); // Assuming menuService is available
        System.out.print("Enter Delivery Executive ID: ");
        long deliveryExecutiveId = scanner.nextLong();
        DeliveryExecutive deliveryExecutive = DeliveryExecutiveService.findDeliveryExecutiveById(deliveryExecutiveId); // Assuming deliveryExecutiveService is available
        LocalDateTime timestamp = null;

        return new Order(id, customer, menu, deliveryExecutive, null);
    }

    private static Restaurant getRestaurantDetails(Scanner scanner) {
        System.out.print("Enter Restaurant ID: ");
        long id = scanner.nextLong();
        scanner.nextLine(); // Consume newline
        System.out.print("Enter Name: ");
        String name = scanner.nextLine();
        System.out.print("Enter Contact ID: ");
        long contactId = scanner.nextLong();
        Contact contact = ContactService.findContactById(contactId);
        List<Menu> menus = RestaurantService.findRestaurantById(contactId);
        return new Restaurant(id, name, contact, menus);
    }
}
