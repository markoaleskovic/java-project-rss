/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package hr.algebra.model;

import java.time.LocalDate;
import javax.xml.bind.annotation.adapters.XmlAdapter;

/**
 *
 * @author malesko
 */
public class PublishedDateAdapter extends XmlAdapter<String, LocalDate> {
    
    @Override
    public LocalDate unmarshal(String text) throws Exception {
        return LocalDate.parse(text, Channel.DATE_FORMATTER);
    }
    
    @Override
    public String marshal(LocalDate date) throws Exception {
        return date.format(Channel.DATE_FORMATTER);
    }
    
}
