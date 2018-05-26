package backend.xml;

import java.io.*;

import static gdTicketsUaDesctop.utils.TestFailure.failTest;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.io.SAXReader;

import static gdTicketsUaDesctop.Constants.XML_REQUEST_TEMPLATES_LOCATION;


public class XmlRequestTemplateConverter {


    public static Document convertXmlFileToDocument(String fileName) {

        Document document = null;
        String fullFilePath = XML_REQUEST_TEMPLATES_LOCATION + fileName;
        SAXReader saxReader = new SAXReader();
        try {
            document = saxReader.read(new FileReader(fullFilePath));
        } catch (FileNotFoundException e) {
            failTest(String.format("File doesn't exist: %s", fullFilePath));
        } catch (DocumentException e) {
            failTest(String.format("SAXReader can't build document by path: %s", fullFilePath),
                    e.getMessage());
        }
        return document;
    }

}
