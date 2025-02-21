package services;

import models.Customer;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.regex.Pattern;

public class CustomerService {

    private Map<String, Customer> customerMap = new HashMap<>();
    private String username;
    private Customer customer;

    private static final String ADMINUSERNAME = "Admin";
    private static final String ADMINPASSWORD = "0092";

    public CustomerService() {

    }

    public Customer registerCustomer() {
        Scanner scanner = new Scanner(System.in);

        while(true) {
            System.out.println("Введите данные клиента в формате: First name, Last Name, Address, Phone Number");
            System.out.println("Пример: Жора, Бумага, Шелка, 88888888888");

            try{
                String input = scanner.nextLine();

                String[] parts = input.split(",");

                if(parts.length != 4) {
                    System.out.println("Неверное количество данных. Нужно ввести 4 значений!");
                    return null;
                }

                //trim() - удаляет начальные и конечные пробелы
                String firstName = parts[0].trim();
                String lastName = parts[1].trim();
                String address = parts[2].trim();
                String phone = parts[3].trim();

                if(!isValidateName(firstName)) {
                    System.out.println("\nОперация прервана.");
                    System.out.println("Имя должно содержать только буквы!");
                    return null;
                }
                if(!isValidateName(lastName)) {
                    System.out.println("\nОперация прервана.");
                    System.out.println("Имя должно содержать только буквы!");
                    return null;
                }
                if(!isValidatePhoneNumber(phone)) {
                    System.out.println("\nОперация прервана.");
                    System.out.println("Номер телефона должен состоять из 11 цифр!");
                    return null;
                }
                customer= new Customer(firstName, lastName, address, phone, new Date());

                System.out.printf("Клиент с никнеймом - %s, занесен в базу.\n", customer.getFirstName());

                customerMap.put(customer.getFirstName(), customer);
                return customer;
            } catch (Exception e) {
                System.out.println("Произошла ошибка: " + e.getMessage());
                return null;
            }
        }
    }

    public static boolean isDigit(String input) {
        for(char c : input.toCharArray()) {
            if(!Character.isDigit(c)) {
                return false;
            }
        }
        return true;
    }

    public static boolean isValidatePhoneNumber(String phoneNumber) {
        if(phoneNumber == null || phoneNumber.length() != 11) {
            return false;
        }
        for(char c : phoneNumber.toCharArray()) {
            if(!Character.isDigit(c)) {
                return false;
            }
        }
        return true;
    }

    public static boolean isValidateName(String name) {
        if(name == null || name.isEmpty()) {
            return false;
        }
        for(char c : name.toCharArray()) {
            if(!Character.isLetter(c)) {
                return false;
            }
        }
        return true;
    }

    public static boolean isValidatePhoneNumberRegex(String phoneNumber) {
        return Pattern.matches("\\d{11}", phoneNumber);
    }

    public static boolean isValidateNameRegex(String name) {
        return Pattern.matches("[a-zA-Z]+", name);
    }

    public boolean isValidateExistingName() {
        Scanner scanner = new Scanner(System.in);

        System.out.printf("Введите имя пользователя: ");
        String inputName = scanner.nextLine();
        System.out.printf("Введите пароль администратора: ");
        String inputPassword = scanner.nextLine();


        if(customerMap.containsKey(inputName) && !ADMINPASSWORD.equals(inputPassword)){
            System.out.println("Введены неверные данные! Попробуйте ещё раз.");
        } else {
            System.out.println("Совершен вход в систему.");
        }
        return true;
    }

    public Map<String, Customer> getCustomerMap() {
        return customerMap;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
