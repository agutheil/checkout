package com.mightymerce.checkout.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.io.Serializable;
import java.util.Objects;

import com.mightymerce.checkout.domain.enumeration.Currency;

/**
 * A Order.
 */
@Document(collection = "ORDER")
public class Order implements Serializable {

    @Id
    private String id;
    
    
    @Field("core_article_id")
    private Long coreArticleId;
    
    @Field("transaction_id")
    private String transactionId;
    
    @Field("payment_status")
    private String paymentStatus;
    
    @Field("email")
    private String email;
    
    @Field("payer_id")
    private String payerId;
    
    @Field("payer_status")
    private String payerStatus;
    
    @Field("first_name")
    private String firstName;
    
    @Field("last_name")
    private String lastName;
    
    @Field("ship_to_name")
    private String shipToName;
    
    @Field("ship_to_street")
    private String shipToStreet;
    
    @Field("ship_to_city")
    private String shipToCity;
    
    @Field("ship_to_state")
    private String shipToState;
    
    @Field("ship_to_cntry_code")
    private String shipToCntryCode;
    
    @Field("ship_to_zip")
    private String shipToZip;
    
    @Field("address_status")
    private String addressStatus;
    
    @Field("total_amt")
    private String totalAmt;
    
    @Field("currency_code")
    private Currency currencyCode;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Long getCoreArticleId() {
        return coreArticleId;
    }

    public void setCoreArticleId(Long coreArticleId) {
        this.coreArticleId = coreArticleId;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public String getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(String paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPayerId() {
        return payerId;
    }

    public void setPayerId(String payerId) {
        this.payerId = payerId;
    }

    public String getPayerStatus() {
        return payerStatus;
    }

    public void setPayerStatus(String payerStatus) {
        this.payerStatus = payerStatus;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getShipToName() {
        return shipToName;
    }

    public void setShipToName(String shipToName) {
        this.shipToName = shipToName;
    }

    public String getShipToStreet() {
        return shipToStreet;
    }

    public void setShipToStreet(String shipToStreet) {
        this.shipToStreet = shipToStreet;
    }

    public String getShipToCity() {
        return shipToCity;
    }

    public void setShipToCity(String shipToCity) {
        this.shipToCity = shipToCity;
    }

    public String getShipToState() {
        return shipToState;
    }

    public void setShipToState(String shipToState) {
        this.shipToState = shipToState;
    }

    public String getShipToCntryCode() {
        return shipToCntryCode;
    }

    public void setShipToCntryCode(String shipToCntryCode) {
        this.shipToCntryCode = shipToCntryCode;
    }

    public String getShipToZip() {
        return shipToZip;
    }

    public void setShipToZip(String shipToZip) {
        this.shipToZip = shipToZip;
    }

    public String getAddressStatus() {
        return addressStatus;
    }

    public void setAddressStatus(String addressStatus) {
        this.addressStatus = addressStatus;
    }

    public String getTotalAmt() {
        return totalAmt;
    }

    public void setTotalAmt(String totalAmt) {
        this.totalAmt = totalAmt;
    }

    public Currency getCurrencyCode() {
        return currencyCode;
    }

    public void setCurrencyCode(Currency currencyCode) {
        this.currencyCode = currencyCode;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Order order = (Order) o;

        if ( ! Objects.equals(id, order.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", coreArticleId='" + coreArticleId + "'" +
                ", transactionId='" + transactionId + "'" +
                ", paymentStatus='" + paymentStatus + "'" +
                ", email='" + email + "'" +
                ", payerId='" + payerId + "'" +
                ", payerStatus='" + payerStatus + "'" +
                ", firstName='" + firstName + "'" +
                ", lastName='" + lastName + "'" +
                ", shipToName='" + shipToName + "'" +
                ", shipToStreet='" + shipToStreet + "'" +
                ", shipToCity='" + shipToCity + "'" +
                ", shipToState='" + shipToState + "'" +
                ", shipToCntryCode='" + shipToCntryCode + "'" +
                ", shipToZip='" + shipToZip + "'" +
                ", addressStatus='" + addressStatus + "'" +
                ", totalAmt='" + totalAmt + "'" +
                ", currencyCode='" + currencyCode + "'" +
                '}';
    }
}
