/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package hr.algebra.dal.sql;

import hr.algebra.dal.Repository;
import hr.algebra.model.Channel;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


/**
 *
 * @author malesko
 */
public class SqlRepository implements Repository{

//    private static final String ID_CHANNEL = "IDChannel";
//    private static final String TITLE = "Title";
//    private static final String LINK = "Link";
//    private static final String DESCRIPTION = "Description";
//    private static final String PICTURE_PATH = "PicturePath";
//    private static final String PUBLISHED_DATE = "PublishedDate";

   private static final String CREATE_CHANNEL = """
        INSERT INTO Channel(Title, Link, Description, PicturePath, PublishedDate) 
        VALUES (?, ?, ?, ?, ?)
    """;
    
    private static final String SELECT_CHANNEL_BY_ID = """
        SELECT * FROM Channel WHERE IDChannel = ?
    """;
    
    private static final String SELECT_ALL_CHANNELS = "SELECT * FROM Channel";
    
    private static final String UPDATE_CHANNEL = """
        UPDATE Channel SET 
        Title = ?, Link = ?, Description = ?, 
        PicturePath = ?, PublishedDate = ? 
        WHERE IDChannel = ?
    """;
    
    private static final String DELETE_CHANNEL = "DELETE FROM Channel WHERE IDChannel = ?";
    private static final String DELETE_ALL_CHANNELS = "DELETE FROM Channel";    
    private static final String RESET_CHANNEL_IDENTITY = "ALTER TABLE Channel ALTER COLUMN IDChannel RESTART WITH 1";
    
    @Override
    public int createChannel(Channel channel) throws Exception {
        try (Connection con = DataSourceSingleton.getInstance().getConnection();
             PreparedStatement stmt = con.prepareStatement(CREATE_CHANNEL, Statement.RETURN_GENERATED_KEYS)) {
            
            setChannelParameters(stmt, channel);
            stmt.executeUpdate();
            
            try (ResultSet rs = stmt.getGeneratedKeys()) {
                return rs.next() ? rs.getInt(1) : -1;
            }
        }
    }

    @Override
    public void createChannels(List<Channel> channels) throws Exception {
        try (Connection con = DataSourceSingleton.getInstance().getConnection();
             PreparedStatement stmt = con.prepareStatement(CREATE_CHANNEL)) {
            
            for (Channel channel : channels) {
                setChannelParameters(stmt, channel);
                stmt.addBatch();
            }
            stmt.executeBatch();
        }
    }

     @Override
    public void updateChannel(int id, Channel data) throws Exception {
        try (Connection con = DataSourceSingleton.getInstance().getConnection();
             PreparedStatement stmt = con.prepareStatement(UPDATE_CHANNEL)) {
            
            setChannelParameters(stmt, data);
            stmt.setInt(6, id);
            stmt.executeUpdate();
        }
    }

    @Override
    public void deleteChannel(int id) throws Exception {
        try (Connection con = DataSourceSingleton.getInstance().getConnection();
             PreparedStatement stmt = con.prepareStatement(DELETE_CHANNEL)) {
            
            stmt.setInt(1, id);
            stmt.executeUpdate();
        }
    }

    @Override
    public void deleteChannels() throws Exception {
        try (Connection con = DataSourceSingleton.getInstance().getConnection();
             Statement stmt = con.createStatement()) {
            stmt.executeUpdate(DELETE_ALL_CHANNELS);
            stmt.execute(RESET_CHANNEL_IDENTITY);
        }
    }
    
     @Override
    public Optional<Channel> selectChannel(int id) throws Exception {
        try (Connection con = DataSourceSingleton.getInstance().getConnection();
             PreparedStatement stmt = con.prepareStatement(SELECT_CHANNEL_BY_ID)) {
            
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                return rs.next() ? Optional.of(mapResultSetToChannel(rs)) : Optional.empty();
            }
        }
    }

     @Override
    public List<Channel> selectChannels() throws Exception {
        List<Channel> channels = new ArrayList<>();
        try (Connection con = DataSourceSingleton.getInstance().getConnection();
             Statement stmt = con.createStatement();
             ResultSet rs = stmt.executeQuery(SELECT_ALL_CHANNELS)) {
            
            while (rs.next()) {
                channels.add(mapResultSetToChannel(rs));
            }
        }
        return channels;
    }
    
    private void setChannelParameters(PreparedStatement stmt, Channel channel) throws SQLException {
        stmt.setString(1, channel.getTitle());
        stmt.setString(2, channel.getLink());
        stmt.setString(3, channel.getDescription());
        stmt.setString(4, channel.getPicturePath());
        stmt.setString(5, channel.getPublishedDate().format(Channel.DATE_FORMATTER));
    }

    private Channel mapResultSetToChannel(ResultSet rs) throws SQLException {
        return new Channel(
            rs.getInt("IDChannel"),
            rs.getString("Title"),
            rs.getString("Link"),
            rs.getString("Description"),
            rs.getString("PicturePath"),
            LocalDateTime.parse(rs.getString("PublishedDate"), Channel.DATE_FORMATTER)
        );
    }
}
