package lk.ijse.gdse72.util;

import org.apache.commons.dbcp2.BasicDataSource;

import javax.sql.DataSource;

public class DatabaseConfig {
    private static final BasicDataSource dataSource = new BasicDataSource();

    static {
        try {
            dataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
            dataSource.setUrl("jdbc:mysql://localhost:3306/complaint_management_system");
            dataSource.setUsername("root");
            dataSource.setPassword("Ijse@1234");

            dataSource.setInitialSize(5);
            dataSource.setMaxTotal(20);
            dataSource.setMinIdle(5);
            dataSource.setMaxIdle(10);
            dataSource.setMaxWaitMillis(10000);

            System.out.println("[DatabaseConfig] ✅ Connection Pool Initialized");

        } catch (Exception e) {
            System.err.println("[DatabaseConfig] ❌ Error Initializing DB Pool: " + e.getMessage());
        }
    }

    public static DataSource getDataSource() {
        return dataSource;
    }

    public static void shutdown() {
        try {
            dataSource.close();
            System.out.println("[DatabaseConfig] 🛑 Connection Pool Closed");
        } catch (Exception e) {
            System.err.println("[DatabaseConfig] ❌ Error closing DB pool: " + e.getMessage());
        }
    }
}
