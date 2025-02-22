package services;

import models.Device;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CrudDbService {

    private final DBConnectService dbConnectService;

    public CrudDbService() {
        this.dbConnectService = new DBConnectService();
    }

    public void add(Device device) {

        String query = "INSERT INTO devices (type_device, brand, name_device, ram, ssd, price, quantity) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?)";
        /*
        ВСТАВКА ДАННЫХ В ТАБЛИЦУ, ? - ПЛЕЙСХОЛДЕР
         */
        try(PreparedStatement prstm = dbConnectService.getConnection().prepareStatement(query)) {
            prstm.setString(1, device.getTypeDevice());
            prstm.setString(2, device.getBrand());
            prstm.setString(3, device.getNameDevice());
            prstm.setInt(4, device.getRam());
            prstm.setInt(5, device.getSsd());
            prstm.setInt(6, device.getPrice());
            prstm.setInt(7, device.getQuantity());
            /*
            ЗАПОЛНИЛИ ЗНАЧЕНИЯ ПАРАМЕТРАМИ МЕТОДА
             */
            prstm.executeUpdate();
            //ВЫПОЛНЯЕТ SQL ЗАПРОС
            System.out.println("Девайс добавлен в таблицу devices\n");
        } catch (NullPointerException e) {
            System.out.println("Произошла ошибка: " + e.getMessage());
        } catch (SQLException e) {
            System.out.println("Произошла ошибка: " + e.getMessage());
        }
    }

    public void update(String type_device, String brand, String name_device, Integer ram,
                       Integer ssd, Integer price, Integer quantity, Integer id) {

        //Вариант №1
//        String query = "UPDATE devices SET typeDevice = ?, brand = ?, namedevice = ?, ram = ?, ssd = ?, " +
//                "price = ?, quantity = ? WHERE id = ?";
//
//        try(PreparedStatement prstm = dbService.getConnection().prepareStatement(query)) {
//            prstm.setString(1, typeDevice);
//            prstm.setString(2, brand);
//            prstm.setString(3, nameDevice);
//            prstm.setInt(4, ram);
//            prstm.setInt(5, ssd);
//            prstm.setInt(6, price);
//            prstm.setInt(7, quantity);
//            prstm.setInt(8, id);
//            prstm.executeUpdate();
//        }
//        catch (SQLException e) {
//            throw new RuntimeException();
//        }

        //Вариант №2
        List<String> fieldsToUpdate = new ArrayList<>();
        List<Object> values = new ArrayList<>();

        if(type_device != null && !type_device.isEmpty()) {
            fieldsToUpdate.add("type_device = ?");
            values.add(type_device);
        }
        if(brand != null && !brand.isEmpty()) {
            fieldsToUpdate.add("brand = ?");
            values.add(brand);
        }
        if(name_device != null && !name_device.isEmpty()) {
            fieldsToUpdate.add("name_device = ?");
            values.add(name_device);
        }
        if(ram != null && ram > 0) {
            fieldsToUpdate.add("ram = ?");
            values.add(ram);
        }
        if(ssd != null && ssd > 0) {
            fieldsToUpdate.add("ssd = ?");
            values.add(ssd);
        }
        if(price != null && price > 0) {
            fieldsToUpdate.add("price = ?");
            values.add(price);
        }
        if(quantity != null && quantity > 0) {
            fieldsToUpdate.add("quantity = ?");
            values.add(quantity);
        }
        if(fieldsToUpdate.isEmpty()) {
            System.out.println("Нет полей для обновления.");
            return;
        }

        String query = "UPDATE devices SET " + String.join(", ", fieldsToUpdate) + " WHERE id = ?";
        values.add(id);

        try(PreparedStatement prstm = dbConnectService.getConnection().prepareStatement(query)) {
            for (int i = 0; i < values.size(); i++) {
                prstm.setObject(i + 1, values.get(i));
            }
            prstm.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public void delete(int id) throws SQLException {

        String query = "DELETE FROM devices WHERE id = ?";

        try(PreparedStatement prstm = dbConnectService.getConnection().prepareStatement(query)) {
            prstm.setInt(1, id);
            prstm.executeUpdate();
        }
    }

    public void dropTables() throws SQLException {

        String query1 = "TRUNCATE devices RESTART IDENTITY CASCADE";
        try (PreparedStatement prstm = dbConnectService.getConnection().prepareStatement(query1)) {
            prstm.executeUpdate();
        }

        String query2 = "TRUNCATE sales RESTART IDENTITY CASCADE";
        try (PreparedStatement prstm = dbConnectService.getConnection().prepareStatement(query2)) {
            prstm.executeUpdate();
        }

        String query3 = "TRUNCATE receipts RESTART IDENTITY CASCADE";
        try (PreparedStatement prstm = dbConnectService.getConnection().prepareStatement(query3)) {
            prstm.executeUpdate();
        }

        System.out.println("Таблицы успешно очищена!");

    }

}
