package backend.xml;

import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.Node;

public class NetsuiteGetListRequestBuilder {

    private String email;
    private String password;
    private String role;
    private String applicationId;
    private String account;

    private Document xmlDocument;

    public NetsuiteGetListRequestBuilder(Document xmlDocument){
        this.xmlDocument = xmlDocument;
    }

    public NetsuiteGetListRequestBuilder withEmail(String value){
        Node email = xmlDocument.selectSingleNode("//*[name()='urn1:email']");
        email.setText(value);
        return this;
    }

    public NetsuiteGetListRequestBuilder withPassword(String value){
        Node password = xmlDocument.selectSingleNode("//*[name()='urn1:password']");
        password.setText(value);
        return this;
    }

    public NetsuiteGetListRequestBuilder withRole(String value){
        Element role = (Element)xmlDocument.selectSingleNode("//*[name()='urn1:role']");
        Attribute attr = role.attribute("internalId");
        attr.setValue(value);
        return this;
    }

    public NetsuiteGetListRequestBuilder withApplicationId(String value){
        Node applicationId = xmlDocument.selectSingleNode("//*[name()='urn:applicationId']");
        applicationId.setText(value);
        return this;
    }

    public NetsuiteGetListRequestBuilder withAccount(String value){
        Node account = xmlDocument.selectSingleNode("//*[name()='urn1:account']");
        account.setText(value);
        return this;
    }

    public NetsuiteGetListRequestBuilder withExternalId(String value){
        Element getList = (Element)xmlDocument.selectSingleNode("//*[name()='urn:getList']/baseRef");
        Attribute attr = getList.attribute("externalId");
        attr.setValue(value);
        return this;
    }

    public NetsuiteGetListRequestBuilder withType(String value){
        Element getList = (Element)xmlDocument.selectSingleNode("//*[name()='urn:getList']/baseRef");
        Attribute attr = getList.attribute("type");
        attr.setValue(value);
        return this;
    }

    public String buildXmlRequestAsString(){
        return xmlDocument.asXML();
    }
}
