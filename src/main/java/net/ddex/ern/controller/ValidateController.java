package net.ddex.ern.controller;

import net.ddex.ern.exception.ERNException;
import net.ddex.ern.service.SchemaService;
import net.ddex.ern.service.SchematronService;
import net.ddex.ern.vo.XMLSchemaValidation;
import net.ddex.util.XMLHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.stream.XMLStreamException;
import javax.xml.transform.TransformerException;
import javax.xml.xpath.XPathExpressionException;
import java.io.IOException;
import java.security.Principal;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

@RestController
public class ValidateController {

  private static final Logger logger = LoggerFactory.getLogger(ValidateController.class);

  @Autowired
  private SchemaService schemaService;

  @Autowired
  private SchematronService schematronService;

  // TODO: Create a custom Exception
  @PostMapping(path = "/xml/validate", produces = "text/plain")
  public String validateXML(@RequestParam(value = "ernFile") MultipartFile file)
          throws ParserConfigurationException, SAXException, IOException,
          XMLStreamException, TransformerException, XPathExpressionException {
    logger.info("Validating ERN {} as version {}. ", file.getOriginalFilename(), "ERN-3.4.1");
    return schematronService.schematron2XML(file.getInputStream());
  }

  @PostMapping(path = "/json/validate", produces = "application/json")
  public List<Map<String, String>> validateJSON(@RequestParam(value = "ernFile") MultipartFile file)
          throws ParserConfigurationException, SAXException, IOException,
          XMLStreamException, TransformerException, XPathExpressionException {
    logger.info("Validating ERN {} as version {}. ", file.getOriginalFilename(), "ERN-3.4.1");
    return schematronService.schematron2Map(file.getInputStream());
  }

}
