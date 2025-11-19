/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package hr.algebra.dal.sql;

import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sql.DataSource;
import org.h2.jdbcx.JdbcDataSource;
import org.h2.tools.Server;


/**
 *
 * @author malesko
 */
public final class DataSourceSingleton {
    private static final String PATH = "/config/db.properties";
    private static final Properties properties = new Properties();
    private static JdbcDataSource instance;

    static {
    try {
            // Start H2 TCP Server
            Server.createTcpServer("-tcp", "-tcpAllowOthers", "-tcpPort", "9092", "-ifNotExists").start();

            // Existing code to load properties...
            try (InputStream is = DataSourceSingleton.class.getResourceAsStream(PATH)) {
                properties.load(is);
                instance = new JdbcDataSource();
                instance.setURL(properties.getProperty("URL"));
                instance.setUser(properties.getProperty("USER"));
                instance.setPassword(properties.getProperty("PASSWORD"));
            }
        } 
        catch (SQLException | IOException ex) {
                Logger.getLogger(DataSourceSingleton.class.getName()).log(Level.SEVERE, null, ex);
        }
    }


    public static DataSource getInstance() {
        return instance;
    }
}
