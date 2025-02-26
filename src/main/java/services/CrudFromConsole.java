package services;

import Entity.Device;

import java.sql.SQLException;
import java.util.Scanner;
import java.util.regex.Pattern;

public class CrudFromConsole {

    private final CrudDbService crudDbService;

    public CrudFromConsole() {
        this.crudDbService = new CrudDbService();
    }

    public Device createDeviceFromConsole() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Введите данные устройства в формате: Тип, Бренд, Название, " +
                "ОЗУ (ГБ), SSD (ГБ), Цена, Количество ");
        System.out.println("Пример: Ноутбук, Apple, MackBook Pro, 16, 512, 20000, 10");
        System.out.print("- ");
        try{
            String input = scanner.nextLine();

            String[] parts = input.split(",");

            if(parts.length != 7) {
                System.out.println("Неверное количество данных. Нужно ввести 7 значений!");
                return null;
            }

            //trim() - удаляет начальные и конечные пробелы
            String type_device = parts[0].trim();
            String brand = parts[1].trim();
            String name_device = parts[2].trim();
            int ram = parsePositiveInt(parts[3].trim(), "ОЗУ");
            int ssd = parsePositiveInt(parts[4].trim(), "SSD");
            int price = parsePositiveInt(parts[5].trim(), "Цена");
            int quantity = parsePositiveInt(parts[6].trim(), "Количество");

            if(ram == -1 || ssd == -1 || price == -1 || quantity == -1) {
                return null;
            }
            System.out.println("Девайс создан\n");
            return new Device(type_device, brand, name_device, ram, ssd, price, quantity);
        } catch (Exception e) {
            System.out.println("Произошла ошибка: " + e.getMessage());
            return null;
        }
    }

    public void deleteDeviceFromConsole() throws SQLException {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Введите id девайса, который необходимо удалить: ");
        String input = scanner.nextLine();
        int id = parsePositiveInt(input.trim(), "Айди");
        crudDbService.delete(id);
    }

    public void updateDeviceFromConsole() throws SQLException {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Введите id устройства, которое нужно обновить: ");
        int id = parsePositiveInt(scanner.nextLine().trim(), "Айди");

        System.out.println("Введите новые данные устройства в формате: Тип, Бренд, Название, ОЗУ (ГБ), SSD (ГБ), Цена, Количество");
        System.out.println("Пример: Ноутбук, Apple, MackBook Pro, 16, 512, 20000, 10");
        System.out.print("- ");
        String input = scanner.nextLine();

        String[] parts = input.split(",");
        if (parts.length != 7) {
            System.out.println("Неверное количество данных. Нужно ввести 7 значений!");
            return;
        }

        String type_device = parts[0].trim();
        String brand = parts[1].trim();
        String name_device = parts[2].trim();
        int ram = parsePositiveInt(parts[3].trim(), "ОЗУ");
        int ssd = parsePositiveInt(parts[4].trim(), "SSD");
        int price = parsePositiveInt(parts[5].trim(), "Цена");
        int quantity = parsePositiveInt(parts[6].trim(), "Количество");

        if (ram == -1 || ssd == -1 || price == -1 || quantity == -1) {
            return;
        }

        crudDbService.update(type_device, brand, name_device, ram, ssd, price, quantity, id);
        System.out.println("Устройство обновлено.\n");
    }

    public void dropTablesFromConsole() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Вы уверены, что хотите очистить все таблицы: да / нет ? \n");

        String input = scanner.nextLine();

        try{
            if(input.equalsIgnoreCase("да")) {
                crudDbService.dropTables();
                System.out.println("Таблицы успешно очищены.");
            } else {
                System.out.println("Операция прервана.");
            }
        } catch (SQLException e) {
            e.getMessage();
        }
    }

    public static boolean isValidNameRegex(String input) {
        return Pattern.matches("\\d{255}", input);
    }

    protected static int parsePositiveInt(String input, String fieldName) {
        try {
            int value = Integer.parseInt(input);
            if(value <= 0) {
                System.out.println("Ошибка " + fieldName + " должно быть положительным числом.");
                return -1;
            }
            return value;
        } catch (NumberFormatException e) {
            System.out.println("Ошибка " + fieldName + " должно быть числом.");
            return -1;
        }
    }




}
