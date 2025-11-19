/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package hr.algebra.rss;

import hr.algebra.factory.ParserFactory;
import hr.algebra.factory.UrlConnectionFactory;
import hr.algebra.model.Channel;
import hr.algebra.utilities.FileUtils;
import java.io.IOException;
import java.io.File;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.List;
import java.util.Optional;
import javax.xml.namespace.QName;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.events.Characters;
import javax.xml.stream.events.Attribute;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;
import javax.xml.stream.XMLStreamException;

/**
 *
 * @author malesko
 */
public class ChannelRssParser {
    
    
    private static final String RSS_URL = "https://feeds.skynews.com/feeds/rss/";
    //the rss url has extenstions /*.xml {home,uk,us,business,world,politics,technology,entertainment,strange}
    //will be set through the parameter of the constructor
    private static final String ATTRIBUTE_URL = "url";
    private static final String EXT = ".jpg";
    private static final String DIR = "assets";
    
    
    public static List<Channel> parseChannelItems(String ext) throws IOException, XMLStreamException {
        List<Channel> channels = new ArrayList<>();
        HttpURLConnection con = UrlConnectionFactory.getHttpUrlConnection(RSS_URL + ext);
        try (InputStream is = con.getInputStream()) {
            XMLEventReader reader = ParserFactory.createStaxParser(is);

            Optional<TagType> tagType = Optional.empty();
            Channel channel = null;
            StartElement startElement = null;
            while (reader.hasNext()) {
                XMLEvent event = reader.nextEvent();
                switch (event.getEventType()) {
                    case XMLStreamConstants.START_ELEMENT -> {
                        startElement = event.asStartElement();
                        String qName = startElement.getName().getLocalPart();
                        tagType = TagType.from(qName);

                        if (tagType.isPresent() && tagType.get().equals(TagType.ITEM)) {
                            channel = new Channel();
                            channels.add(channel);
                        }
                    }
                    case XMLStreamConstants.CHARACTERS -> {
                        if (tagType.isPresent() && channel != null) {
                            Characters characters = event.asCharacters();
                            String data = characters.getData().trim();
                            switch (tagType.get()) {
                                case TITLE -> {
                                    if (!data.isEmpty()) {
                                        channel.setTitle(data);
                                    }
                                }
                                case LINK -> {
                                    if (!data.isEmpty()) {
                                        channel.setLink(data);
                                    }
                                }
                                case DESCRIPTION -> {
                                    if (!data.isEmpty()) {
                                        channel.setDescription(data);
                                    }
                                }
                                case ENCLOSURE -> {
                                    if (startElement != null && channel.getPicturePath() == null) {
                                        Attribute urlAttribute = startElement.getAttributeByName(new QName(ATTRIBUTE_URL));
                                        if (urlAttribute != null) {
                                            handlePicture(channel, urlAttribute.getValue());
                                        }
                                    }
                                }
                                case PUB_DATE -> {
                                    if (!data.isEmpty()) {
                                        LocalDateTime publishedDate = LocalDateTime.parse(data, DateTimeFormatter.RFC_1123_DATE_TIME);
                                        channel.setPublishedDate(publishedDate);
                                    }
                                }
                            }
                        }
                    }
                }
            }

        }
        return channels;

    }
    
    
    private static void handlePicture(Channel channel, String pictureUrl) {
        // if picture is not ok, we must continue!!!
        try {
            String ext = pictureUrl.substring(pictureUrl.lastIndexOf("."));
            if (ext.length() > 4) {
                ext = EXT;
            }
            String pictureName = UUID.randomUUID() + ext;
            String localPicturePath = DIR + File.separator + pictureName;

            FileUtils.copyFromUrl(pictureUrl, localPicturePath);
            // put breakpoint
            channel.setPicturePath(localPicturePath);
        } catch (IOException ex) {
            Logger.getLogger(ChannelRssParser.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
    
  
    
    private enum TagType {
        
        CHANNEL("channel"),
        ITEM("item"),
        TITLE("title"),
        LINK("link"),
        DESCRIPTION("description"),
        ENCLOSURE("enclosure"), //for images in <item>
        URL("url"), //for the channel image
        PUB_DATE("pubDate"),
        LASTBUILDDATE("lastBuildDate");

        private final String name;

        private TagType(String name) {
            this.name = name;
        }

        private static Optional<TagType> from(String name) {
            for (TagType value : values()) {
                if (value.name.equals(name)) {
                    return Optional.of(value);
                }
            }
            return Optional.empty();
        }
    }
    
}
