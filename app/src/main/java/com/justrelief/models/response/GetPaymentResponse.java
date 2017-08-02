package com.justrelief.models.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by kartikeya-pc on 6/10/17.
 */

public class GetPaymentResponse implements Serializable{
    @SerializedName("PaymentId")
    @Expose
    private Integer paymentId;
    @SerializedName("label")
    @Expose
    private String label;

    public Integer getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(Integer paymentId) {
        this.paymentId = paymentId;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }
}
