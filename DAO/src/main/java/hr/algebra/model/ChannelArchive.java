/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package hr.algebra.model;

import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author malesko
 */
@XmlRootElement(name = "channelarchive")
@XmlAccessorType(XmlAccessType.FIELD)
public class ChannelArchive {
    
    @XmlElementWrapper
    @XmlElement(name="channel")
    private List<Channel> channels;

    public ChannelArchive() {
    }

    
    
    public ChannelArchive(List<Channel> channels) {
        this.channels = channels;
    }

    public List<Channel> getChannels() {
        return channels;
    }

    
    
}
