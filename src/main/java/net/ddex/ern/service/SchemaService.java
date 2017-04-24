package net.ddex.ern.service;

import net.ddex.util.XMLHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;


/**
 * Created by rdewilde on 4/16/2017.
 */

@Service("schemaService")
public class SchemaService {

    private static final Logger logger = LoggerFactory.getLogger(SchematronService.class);

    public Document parseDocument() throws ParserConfigurationException, SAXException, IOException{
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        dbf.setNamespaceAware(true);
        DocumentBuilder parser = dbf.newDocumentBuilder();
        System.out.println("Zoinks!!!!");
        return null;
        //return parser.parse(new File("schema/motherboard.pdf"));
    }
}
