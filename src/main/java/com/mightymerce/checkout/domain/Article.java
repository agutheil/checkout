package com.mightymerce.checkout.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;

import com.mightymerce.checkout.domain.enumeration.Currency;

/**
 * A Article.
 */
@Document(collection = "ARTICLE")
public class Article implements Serializable {

    @Id
    private String id;
    

    @NotNull        
    @Field("core_id")
    private Long coreId;

    @NotNull        
    @Field("code")
    private String code;
    
    @Field("name")
    private String name;
    
    @Field("description")
    private String description;
    
    @Field("price")
    private BigDecimal price;
    
    @Field("delivery_costs")
    private BigDecimal deliveryCosts;
    
    @Field("currency")
    private Currency currency;
    
    @Field("image1")
    private byte[] image1;
    
    @Field("image2")
    private byte[] image2;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Long getCoreId() {
        return coreId;
    }

    public void setCoreId(Long coreId) {
        this.coreId = coreId;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public BigDecimal getDeliveryCosts() {
        return deliveryCosts;
    }

    public void setDeliveryCosts(BigDecimal deliveryCosts) {
        this.deliveryCosts = deliveryCosts;
    }

    public Currency getCurrency() {
        return currency;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }

    public byte[] getImage1() {
        return image1;
    }

    public void setImage1(byte[] image1) {
        this.image1 = image1;
    }

    public byte[] getImage2() {
        return image2;
    }

    public void setImage2(byte[] image2) {
        this.image2 = image2;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Article article = (Article) o;

        if ( ! Objects.equals(id, article.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Article{" +
                "id=" + id +
                ", coreId='" + coreId + "'" +
                ", code='" + code + "'" +
                ", name='" + name + "'" +
                ", description='" + description + "'" +
                ", price='" + price + "'" +
                ", deliveryCosts='" + deliveryCosts + "'" +
                ", currency='" + currency + "'" +
                ", image1='" + image1 + "'" +
                ", image2='" + image2 + "'" +
                '}';
    }
}
