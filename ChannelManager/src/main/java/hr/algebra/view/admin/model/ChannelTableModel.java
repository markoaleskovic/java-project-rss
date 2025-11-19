/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package hr.algebra.view.admin.model;

import hr.algebra.model.Channel;
import java.util.Arrays;
import java.util.List;
import javax.swing.table.AbstractTableModel;


/**
 *
 * @author malesko
 */
public class ChannelTableModel extends AbstractTableModel{

   
    private static final String[] COLUMN_NAMES = {"Id", "Title", "Link", "Description", "Picture path", "Published date"};
    private List<Channel> channels;
    
    public ChannelTableModel(List<Channel> channels) {
        this.channels = channels;
    }
    
    public void setChannels(List<Channel> channels) {
        this.channels = channels;
        fireTableDataChanged();
    }
    
    @Override
    public int getRowCount() {
        return this.channels.size();
    }

    @Override
    public int getColumnCount() {
        return COLUMN_NAMES.length;
    }
    
    @Override
    public String getColumnName(int column) {
        return COLUMN_NAMES[column];
    }


    // important for the id ordering
    @Override
    public Class<?> getColumnClass(int columnIndex) {
        switch (columnIndex) {
            case 0:
                return Integer.class;
        }
        return super.getColumnClass(columnIndex); 
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        switch (columnIndex) {
            case 0:
                return channels.get(rowIndex).getId();
            case 1:
                return channels.get(rowIndex).getTitle();
            case 2:
                return channels.get(rowIndex).getLink();
            case 3:
                return channels.get(rowIndex).getDescription();
            case 4:
                return channels.get(rowIndex).getPicturePath();
            case 5:
                return channels.get(rowIndex).getPublishedDate().format(Channel.DATE_FORMATTER);
            default:
                throw new RuntimeException("No such column");
        }
    }
    
    
    
    
}
