/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package hr.algebra.service;

import hr.algebra.dal.UserRepository;
import hr.algebra.model.User;
import java.util.Optional;

/**
 * 
 * @author malesko
 */
 //Auth service should be extending a interface IAuthService in order for dependency injections be possible
public class AuthService {
    private final UserRepository userRepository;

    public AuthService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Optional<User> authenticate(String username, String password) {
        try {
            return userRepository.findByUsername(username)
                .filter(user -> user.getPassword().equals(password));
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return Optional.empty();
        }
    }
}
