package services;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CheckService {

    private final DBConnectService dbConnect;

    public CheckService() {
        this.dbConnect = new DBConnectService();
    }

    public void printCheck(int saleID) {

        String query = "SELECT c.type_device, c.brand, c.name_device, c.price, s.quantity_sold, r.total_price " +
                "FROM sales s " +
                "JOIN computers c ON s.device_id = c.id " +
                "JOIN receipts r ON s.id = r.sale_id " +
                "WHERE s.id = ?";
        try(PreparedStatement prstm = dbConnect.getConnection().prepareStatement(query)) {
            prstm.setInt(1, saleID);
            ResultSet rs = prstm.executeQuery();
            if(rs.next()) {
                String folderName = "checks";
                String fileName = "check_" + saleID + ".txt";

                try(PrintWriter writer = new PrintWriter(new File(folderName, fileName))) {
                    writer.println("ЧЕК ПРОДАЖИ");
                    writer.println("======================");
                    writer.println("Тип устройства: " + rs.getString("type_device"));
                    writer.println("Бренд: " + rs.getString("brand"));
                    writer.println("Наименование модели: " + rs.getString("name_device"));
                    writer.println("Количество: " + rs.getInt("quantity_sold"));
                    writer.println("Цена за единицу: " + rs.getInt("price") + " руб.");
                    writer.println("Итого: " + rs.getInt("total_price") + " руб.");
                    writer.println("=======================");
                    System.out.println("Чек сохранен в " + fileName);
                }
                catch (IOException e) {
                    e.getMessage();
                }
            } else {
                System.out.println("Чек не найден для продажи ID " + saleID);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

}
