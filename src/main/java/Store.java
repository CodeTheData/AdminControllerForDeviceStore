import models.Device;
import services.*;

import java.sql.SQLException;
import java.util.Scanner;

public class Store {

    private static double amount = 0;

    public static void main(String[] args) throws SQLException {

        CustomerService customerService = new CustomerService();
        CrudDbService crudDbService = new CrudDbService();
        CrudFromConsole crudFromConsole = new CrudFromConsole();
        CheckService checkService = new CheckService();
        SalesService salesService = new SalesService(checkService);

        Scanner scanner = new Scanner(System.in);
        Device device;
        int choice;

        while (true) {
            System.out.println("\n----------------------");
            System.out.println("STORE   OF   JAVA");
            System.out.println("----------------------\n");
            System.out.println("1. Register client");
            System.out.println("2. Show costumer's list");
            System.out.println("3. Use DB utils (add, delete, update, drop)"); // свич в свиче можно!
            System.out.println("4. Sell device");
            System.out.println("5. Exit.");
            System.out.println("\n Enter your choice: ");
            choice = scanner.nextInt();
            scanner.nextLine();
            switch (choice) {
                case 1://firstName, lastName, address, phone, balance
                    customerService.registerCustomer();
                    break;

                case 2:
                    System.out.println(customerService.getCustomerMap());
                    break;

                case 3:
                    if (customerService.isValidateExistingName() == true) {
                        System.out.println("\n----------------------");
                        System.out.println("         WELCOME         ");
                        System.out.println("----------------------\n");
                        System.out.println("1. Add device");
                        System.out.println("2. Delete device"); // свич в свиче можно!
                        System.out.println("3. Update table");
                        System.out.println("4. Drop table");
                        System.out.println("5. Back to main menu");
                        System.out.println("\n Enter your choice: ");
                        choice = scanner.nextInt();
                        scanner.nextLine();
                        switch (choice) {
                            case 1:
                                device = crudFromConsole.createDeviceFromConsole();
                                if(device != null) {
                                    crudDbService.add(device);
                                    System.out.println("Добавлен следующий девайс: " + device.getNameDevice());
                                }
                                break;
                            case 2:
                                crudFromConsole.deleteDeviceFromConsole();
                                System.out.println("\nДевайс удален.\n");
                                break;
                            case 3:
                                crudFromConsole.updateDeviceFromConsole();
                                break;
                            case 4:
                                crudFromConsole.dropTablesFromConsole();
                                break;
                            case 5:
                                break;
                            default:
                                System.out.println("Неверный выбор. Попробуйте снова.");
                        }
                    }
                break;

                case 4:
                    System.out.println("Введите id: ");
                    int id = scanner.nextInt();
                    System.out.println("Введите количество: ");
                    int quantity = scanner.nextInt();
                    salesService.sellDevice(id, quantity);
                    break;

                case 5:
                    System.out.println("Выход из программы.");
                    scanner.close();
                    return;

                default:
                    System.out.println("Неверный выбор. Попробуйте снова.");
            }
        }
    }
}