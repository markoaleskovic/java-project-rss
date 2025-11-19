/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package hr.algebra.model;

import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.IOException;

/**
 *
 * @author malesko
 */
public class ChannelTransferable implements Transferable {
    
    public static final DataFlavor CHANNEL_FLAVOR = new DataFlavor(Channel.class, "Channel");
    private static final DataFlavor[] SUPPORTED_FLAVORS = {CHANNEL_FLAVOR};

    private final Channel channel;

    public ChannelTransferable(Channel channel) {
        this.channel = channel;
    }

    @Override
    public DataFlavor[] getTransferDataFlavors() {
        return SUPPORTED_FLAVORS;
    }

    @Override
    public boolean isDataFlavorSupported(DataFlavor flavor) {
        return CHANNEL_FLAVOR.equals(flavor);
    }

    @Override
    public Object getTransferData(DataFlavor flavor) throws UnsupportedFlavorException, IOException {
       if (isDataFlavorSupported(flavor)) {
            return channel;
        }
        throw new UnsupportedFlavorException(flavor);        
    }
}
