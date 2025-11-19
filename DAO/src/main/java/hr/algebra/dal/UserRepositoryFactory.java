/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package hr.algebra.dal;

import java.io.InputStream;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author malesko
 */
public class UserRepositoryFactory {
    private static final String PATH = "/config/repository.properties";
    private static final String CLASS_NAME = "USER_REPOSITORY";
    private static final Properties properties = new Properties();
    private static UserRepository userRepository;

    static {
        try (InputStream is = UserRepositoryFactory.class.getResourceAsStream(PATH)) {
            properties.load(is);
            userRepository = (UserRepository) Class
                    .forName(properties.getProperty(CLASS_NAME))
                    .getDeclaredConstructor()
                    .newInstance();
        } catch (Exception ex) {
            Logger.getLogger(UserRepositoryFactory.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static UserRepository getUserRepository() {
        return userRepository;
    }
}
