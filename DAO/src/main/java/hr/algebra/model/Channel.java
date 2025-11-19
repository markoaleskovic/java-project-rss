/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package hr.algebra.model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;

/**
 *
 * @author malesko
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(propOrder = {"title", "description", "picturePath","link"})
public final class Channel implements Comparable<Channel>{
    
    public static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ISO_LOCAL_DATE_TIME;
    
    @XmlAttribute
    private int id;
    
    
    @XmlElement(name="title")
    private String title;
    @XmlElement(name="link")
    private String link;
    @XmlElement(name="description")
    private String description;
    @XmlElement(name="enclosure")
    private String picturePath;
    
    @XmlTransient
    private LocalDateTime publishedDate;

    public Channel(){}

    public Channel(String title, String link, String description, String picturePath, LocalDateTime publishedDate) {
        this.title = title;
        this.link = link;
        this.description = description;
        this.picturePath = picturePath;
        this.publishedDate = publishedDate;
    }
    
    public Channel(int id, String title, String link, String description, String picturePath, LocalDateTime publishedDate) {
        this(title, link, description, picturePath, publishedDate);
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPicturePath() {
        return picturePath;
    }

    public void setPicturePath(String picturePath) {
        this.picturePath = picturePath;
    }

    public LocalDateTime getPublishedDate() {
        if (publishedDate != null)
            return publishedDate;
        else
            return LocalDateTime.now();
    }

    public void setPublishedDate(LocalDateTime publishedDate) {
        this.publishedDate = publishedDate;
    }
    
    @Override
    public String toString() {
        return id + " - " + title;
    }
    
    public String stringView()
    {
        String date = (publishedDate != null) ? publishedDate.toString() : "N/A";
        return title + "-" + date; 
    }

    @Override
    public int compareTo(Channel t) {
        return Integer.compare(id, t.id);
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 67 * hash + this.id;
        hash = 67 * hash + Objects.hashCode(this.title);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Channel other = (Channel) obj;
        return this.id == other.id;
    }
 
    
    
}
