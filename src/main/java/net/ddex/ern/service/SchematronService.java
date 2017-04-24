package net.ddex.ern.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import javax.xml.namespace.NamespaceContext;
import javax.xml.stream.XMLStreamException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMResult;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.sax.SAXSource;
import javax.xml.transform.sax.SAXTransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import javax.xml.xpath.*;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.util.*;

/**
 * Created by rdewilde on 4/16/2017.
 */

@Service("schematronService")
public class SchematronService {

    private static final Logger logger = LoggerFactory.getLogger(SchematronService.class);

    public String schematron2XML(InputStream is) throws XMLStreamException, IOException, TransformerException {
        String XSLT_FILE = "xslt/release-profiles/NewReleaseMessage_ReleaseProfile_AudioAlbumMusicOnly_14.xslt";
        SAXSource saxSource = new SAXSource(new InputSource(is));
        // Object to hold the result
        StringWriter writer = new StringWriter();
        StreamResult result = new StreamResult(writer);
        SAXTransformerFactory stf = new net.sf.saxon.TransformerFactoryImpl();

        Transformer transformer = stf.newTransformer(new StreamSource(XSLT_FILE));
        // the actual transformation
        transformer.transform(saxSource, result);
        return writer.toString();

    }

    public List<Map<String, String>> schematron2Map(InputStream is) throws XMLStreamException, IOException,
            TransformerException, XPathExpressionException {
        String XSLT_FILE = "xslt/release-profiles/NewReleaseMessage_ReleaseProfile_AudioAlbumMusicOnly_14.xslt";
        SAXSource saxSource = new SAXSource(new InputSource(is));
        DOMResult result = new DOMResult();
        SAXTransformerFactory stf = new net.sf.saxon.TransformerFactoryImpl();
        Transformer transformer = stf.newTransformer(new StreamSource(XSLT_FILE));
        transformer.transform(saxSource, result);
        XPathFactory xPathfactory = XPathFactory.newInstance();
        XPath xpath = xPathfactory.newXPath();

        xpath.setNamespaceContext(new NamespaceContext() {
            @Override
            public String getNamespaceURI(String prefix) {
                if(prefix.equals("svrl")) {
                    return "http://purl.oclc.org/dsdl/svrl";
                } else {
                    return null;
                }
            }
            @Override public String getPrefix(String namespaceURI) {
                return null; // not used
            }
            @Override
            public Iterator<?> getPrefixes(String namespaceURI) {
                return null; // not used
            }
        });

        XPathExpression expr = xpath.compile("/svrl:schematron-output/svrl:failed-assert");
        NodeList nl = (NodeList) expr.evaluate(result.getNode(),  XPathConstants.NODESET);

        StringWriter writer = new StringWriter();
        transformer = TransformerFactory.newInstance().newTransformer();

        List<Map<String, String>> data = new ArrayList<Map<String, String>>();

        for(int i=0; i < nl.getLength(); i++) {
            String role = String.format("/svrl:schematron-output/svrl:failed-assert[%d]/@role", i+1);
            String msg = String.format("/svrl:schematron-output/svrl:failed-assert[%d]/svrl:text", i+1);
            XPathExpression exprMsg = xpath.compile(msg);
            XPathExpression exprRole = xpath.compile(role);

            Map<String, String> failure = new HashMap<String, String>();
            failure.put("role", exprRole.evaluate(result.getNode()));
            failure.put("msg", exprMsg.evaluate(result.getNode()));
            data.add(failure);
        }

        transformer.transform(new DOMSource(result.getNode()), new StreamResult(writer));
        String xml = writer.toString();
        return data;

    }

}
