/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package hr.algebra.dal.sql;

import hr.algebra.dal.UserRepository;
import hr.algebra.model.User;
import hr.algebra.model.UserRole;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 *
 * @author malesko
 */
public class H2UserRepository implements UserRepository {

    private static final String CREATE_USER = """
        INSERT INTO "User" (Username, Password, IDRole) 
        VALUES (?, ?, ?)
    """;
    
    private static final String SELECT_USER_BY_ID = """
        SELECT u.*, r.RoleName 
        FROM "User" u
        JOIN UserRole r ON u.IDRole = r.IDRole 
        WHERE u.IDUser = ?
    """;
    
    private static final String SELECT_ALL_USERS = """
        SELECT u.*, r.RoleName 
        FROM "User" u
        JOIN UserRole r ON u.IDRole = r.IDRole
    """;
    
    private static final String UPDATE_USER = """
        UPDATE "User" SET 
        Username = ?, Password = ?, IDRole = ? 
        WHERE IDUser = ?
    """;
    
    private static final String DELETE_USER = "DELETE FROM \"User\" WHERE IDUser = ?";
    private static final String FIND_BY_USERNAME = SELECT_ALL_USERS + " WHERE u.Username = ?";

    @Override
    public int createUser(User user) throws Exception {
        try (Connection con = DataSourceSingleton.getInstance().getConnection();
             PreparedStatement stmt = con.prepareStatement(CREATE_USER, Statement.RETURN_GENERATED_KEYS)) {
            
            stmt.setString(1, user.getUsername());
            stmt.setString(2, user.getPassword());
            stmt.setInt(3, user.getRole().getId());
            stmt.executeUpdate();
            
            try (ResultSet rs = stmt.getGeneratedKeys()) {
                return rs.next() ? rs.getInt(1) : -1;
            }
        }
    }

    @Override
    public void updateUser(int id, User user) throws Exception {
        try (Connection con = DataSourceSingleton.getInstance().getConnection();
             PreparedStatement stmt = con.prepareStatement(UPDATE_USER)) {
            
            stmt.setString(1, user.getUsername());
            stmt.setString(2, user.getPassword());
            stmt.setInt(3, user.getRole().getId());
            stmt.setInt(4, id);
            stmt.executeUpdate();
        }
    }

    @Override
    public void deleteUser(int id) throws Exception {
        try (Connection con = DataSourceSingleton.getInstance().getConnection();
             PreparedStatement stmt = con.prepareStatement(DELETE_USER)) {
            
            stmt.setInt(1, id);
            stmt.executeUpdate();
        }
    }

    @Override
    public Optional<User> findByUsername(String username) throws Exception {
        try (Connection con = DataSourceSingleton.getInstance().getConnection();
             PreparedStatement stmt = con.prepareStatement(FIND_BY_USERNAME)) {
            
            stmt.setString(1, username);
            try (ResultSet rs = stmt.executeQuery()) {
                return rs.next() ? Optional.of(mapUser(rs)) : Optional.empty();
            }
        }
    }

    @Override
    public List<User> selectUsers() throws Exception {
        List<User> users = new ArrayList<>();
        try (Connection con = DataSourceSingleton.getInstance().getConnection();
             Statement stmt = con.createStatement();
             ResultSet rs = stmt.executeQuery(SELECT_ALL_USERS)) {
            
            while (rs.next()) {
                users.add(mapUser(rs));
            }
        }
        return users;
    }

    private User mapUser(ResultSet rs) throws SQLException {
        return new User(
            rs.getInt("IDUser"),
            rs.getString("Username"),
            rs.getString("Password"),
            UserRole.valueOf(rs.getString("RoleName"))
        );
    }
}
