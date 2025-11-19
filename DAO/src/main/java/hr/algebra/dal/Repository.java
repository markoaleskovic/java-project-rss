/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package hr.algebra.dal;

import hr.algebra.model.Channel;
import java.util.List;
import java.util.Optional;

/**
 *
 * @author malesko
 */
public interface Repository {
    
    int createChannel(Channel channel) throws Exception;
    void createChannels(List<Channel> channels) throws Exception;
    void updateChannel(int id, Channel data) throws Exception;
    void deleteChannel(int id) throws Exception;
    void deleteChannels() throws Exception;
    Optional<Channel> selectChannel(int id) throws Exception;
    List<Channel> selectChannels() throws Exception;
    
}



