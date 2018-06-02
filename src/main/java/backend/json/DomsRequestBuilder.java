package backend.json;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import gdTicketsUaDesctop.utils.Log;


public class DomsRequestBuilder {


    private String orderLineId;
    private String event;
    private EventMetaData eventMetadata;
    private RefundAmount refundAmount;

    public DomsRequestBuilder(JsonObject domsTemplateRequestJsonObject){
        eventMetadata = new EventMetaData();
        refundAmount = new RefundAmount();
        new Gson().fromJson(domsTemplateRequestJsonObject.toString(), DomsRequestBuilder.class);
    }

    public DomsRequestBuilder withOrderLineId(String value){
        orderLineId = value;
        Log.info(String.format("\'orderLineId\' set \'%s\'", value));
        return this;
    }

    public DomsRequestBuilder withEvent(String value){
        event = value;
        Log.info(String.format("\'event\' set \'%s\'", value));
        return this;
    }

    public DomsRequestBuilder withEventMetadataResumeDate(String value){
        eventMetadata.resumeDate = value;
        Log.info(String.format("\'eventMetadata.resumeDate\' set \'%s\'", value));
        return this;
    }

    public DomsRequestBuilder withEventMetadataInternalId(String value){
        eventMetadata.internalId = value;
        Log.info(String.format("\'eventMetadata.internalId\' set \'%s\'", value));
        return this;
    }

    public DomsRequestBuilder withRefundAmountTax(Double value){
        refundAmount.tax = value;
        Log.info(String.format("\'refundAmount.tax\' set \'%s\'", value));
        return this;
    }

    public DomsRequestBuilder withRefundAmountTotal(Double value){
        refundAmount.total = value;
        Log.info(String.format("\'refundAmount.total\' set \'%s\'", value));
        return this;
    }

    public DomsRequestBuilder withRefundAmountItemPrice(Double value){
        refundAmount.itemPrice = value;
        Log.info(String.format("\'refundAmount.itemPrice\' set \'%s\'", value));
        return this;
    }

    public DomsRequestBuilder withRefundAmountShippingPrice(Double value){
        refundAmount.shippingPrice = value;
        Log.info(String.format("\'refundAmount.shippingPrice\' set \'%s\'", value));
        return this;
    }

    public JsonObject buildJsonRequestObject(){
        JsonElement jsonElement = new GsonBuilder().create().toJsonTree(this);
        return (JsonObject) jsonElement;
    }


    private class EventMetaData {
        private String resumeDate;
        private String internalId;
    }

    private class RefundAmount {
        private Double shippingPrice;
        private Double tax;
        private Double total;
        private Double itemPrice;
    }
}
