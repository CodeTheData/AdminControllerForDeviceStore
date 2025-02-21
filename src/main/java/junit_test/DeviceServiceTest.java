package junit_test;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import services.DBConnectService;

import java.sql.Connection;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class DeviceServiceTest {

    DBConnectService dbConnectService = new DBConnectService();
    private static Connection connection;

@Test
    public void shouldGetJdbcConnection() throws SQLException {
    try(Connection connection = dbConnectService.getConnection()) {
        assertTrue(connection.isValid(1));
        assertFalse(connection.isClosed());
    }
}



}
