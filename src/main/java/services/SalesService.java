package services;

import models.Device;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

public class SalesService {

    private final DBConnectService dbConnect;
    private final CheckService checkService;

    public SalesService(CheckService checkService) {
        this.dbConnect = new DBConnectService();
        this.checkService = checkService;
    }

    private List<Device> devices;
    int id;

    public void sellDevice(int id, int quantityRequest) {

        Scanner scanner = new Scanner(System.in);

        String selectQuery = "SELECT id, quantity, price FROM computers WHERE brand = ? AND name_device = ?";
        try (PreparedStatement selectStatement = dbConnect.getConnection().prepareStatement(selectQuery)) {
            selectStatement.setString(1, devices.get(id).getBrand());
            selectStatement.setString(2, devices.get(id).getNameDevice());
            ResultSet resultSet = selectStatement.executeQuery();

            if (resultSet.next()) {
                int deviceId = resultSet.getInt("id");
                int availableQuantity = resultSet.getInt("quantity");
                int price = resultSet.getInt("price");

                if (availableQuantity >= devices.get(id).getQuantity()) {
                    dbConnect.getConnection().setAutoCommit(false); // Начинаем транзакцию
                    // Юра помоги
                    try {
                        // Обновляем количество устройств
                        String updateQuery = "UPDATE computers SET quantity = quantity - ? WHERE id = ?";
                        try (PreparedStatement updateStatement = dbConnect.getConnection().prepareStatement(updateQuery)) {
                            updateStatement.setInt(1, quantityRequest);
                            updateStatement.setInt(2, deviceId);
                            updateStatement.executeUpdate();
                        }

                        // Записываем продажу
                        String insertSaleQuery = "INSERT INTO sales (device_id, quantity_sold) VALUES (?, ?) RETURNING id";
                        int saleId;
                        try (PreparedStatement insertStatement = dbConnect.getConnection().prepareStatement(insertSaleQuery)) {
                            insertStatement.setInt(1, deviceId);
                            insertStatement.setInt(2, quantityRequest);
                            ResultSet rs = insertStatement.executeQuery();
                            rs.next();
                            saleId = rs.getInt("id");
                        }

                        // Записываем чек
                        String insertReceiptQuery = "INSERT INTO receipts (sale_id, total_price) VALUES (?, ?)";
                        try (PreparedStatement receiptStatement = dbConnect.getConnection().prepareStatement(insertReceiptQuery)) {
                            receiptStatement.setInt(1, saleId);
                            receiptStatement.setInt(2, price * quantityRequest);
                            receiptStatement.executeUpdate();
                        }

                        System.out.println("Вы хотите распечатать чек да/нет ?");
                        String answerCheck = scanner.nextLine();

                        if(answerCheck.equalsIgnoreCase("да")) {
                            checkService.printCheck(deviceId);
                        } else {
                            System.out.println("\nОперация продолжается...\n");
                        }

                        dbConnect.getConnection().commit(); // Завершаем транзакцию

                        System.out.println("Продано " + quantityRequest + " устройств модели " + devices.get(id).getNameDevice());
                    } catch (SQLException e) {
                        dbConnect.getConnection().rollback(); // Откат в случае ошибки
                        throw e;
                    } finally {
                        dbConnect.getConnection().setAutoCommit(true);
                    }
                } else {
                    System.out.println("Недостаточно устройств " + devices.get(id).getNameDevice() +
                            ". Доступно: " + availableQuantity);
                }
            } else {
                System.out.println("Устройство " + devices.get(id).getNameDevice() + " не найдено.");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (IndexOutOfBoundsException e) {
            System.out.println("Выход за рамки массива" + e);
        }
    }
}
