package gdTicketsUaDesctop.utils.testCrestWest;

import backend.xml.NetsuiteGetListRequestBuilder;
import backend.xml.XmlApi;
import backend.xml.XmlRequestTemplateConverter;
import gdTicketsUaDesctop.utils.PropertyReader;
import org.dom4j.Document;

import java.util.HashMap;
import java.util.Map;

import static gdTicketsUaDesctop.Constants.PROPERTY_LOCATION;
import static gdTicketsUaDesctop.Constants.NETSUITE_LOAD_HOST;

public class XmlRequestMain {

    public static void main(String[] args) {

        PropertyReader prop = new PropertyReader(PROPERTY_LOCATION + "netsuite/getListRequest.properties");

        Document xml = XmlRequestTemplateConverter.convertXmlFileToDocument("netsuiteGetList.xml");
        NetsuiteGetListRequestBuilder xmlBuilder = new NetsuiteGetListRequestBuilder(xml);
        String xmlRequest = xmlBuilder.withAccount(prop.getValue("netsuite_load_account")).
                withApplicationId(prop.getValue("netsuite_load_applicationId")).
                withEmail(prop.getValue("netsuite_load_email")).
                withPassword(prop.getValue("netsuite_load_password")).
                withRole(prop.getValue("netsuite_load_role")).
                withExternalId("DIG_947359150").
                withType("invoice").
                buildXmlRequestAsString();

        Map<String, String> getListHeaders = new HashMap<>();
        getListHeaders.put("SOAPAction", "getList");

        XmlApi xmlApi = new XmlApi();
        xmlApi.xmlPost(NETSUITE_LOAD_HOST, getListHeaders, xmlRequest);
    }
}
