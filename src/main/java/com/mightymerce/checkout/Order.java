package com.mightymerce.checkout;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.springframework.data.annotation.Id;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Order {

	@Id private String id;
    private Long article;
    private String transactionId;    
    private String paymentStatus;
    private String email; 			
    private String payerId 		;	// ' Unique PayPal customer account identification number.
    private String payerStatus	;	// ' Status of payer. Character length and limitations: 10 single-byte alphabetic characters.
    private String firstName	;	// ' Payer's first name.
    private String lastName		;	// ' Payer's last name.
    private String shipToName	;	// ' Person's name associated with this address.
    private String shipToStreet	;	// ' First street address.
    private String shipToCity	;	// ' Name of city.
    private String shipToState	;	// ' State or province
    private String shipToCntryCode;	// ' Country code. 
    private String shipToZip	;	// ' U.S. Zip code or other country-specific postal code.
    private String addressStatus;	// ' Status of street address on file with PayPal 
    private String totalAmt ;	// ' Total Amount to be paid by buyer
    private String currencyCode ;    // 'Currency being used 
     
	
	public Long getArticle() {
		return article;
	}

	public void setArticle(Long article) {
		this.article = article;
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

	public String getCurrencyCode() {
		return currencyCode;
	}

	public void setCurrencyCode(String currencyCode) {
		this.currencyCode = currencyCode;
	}

	@Override
	public String toString() {
		return "Order [article=" + article + ", transactionId=" + transactionId
				+ ", paymentStatus=" + paymentStatus + ", email=" + email
				+ ", payerId=" + payerId + ", payerStatus=" + payerStatus
				+ ", firstName=" + firstName + ", lastName=" + lastName
				+ ", shipToName=" + shipToName + ", shipToStreet="
				+ shipToStreet + ", shipToCity=" + shipToCity
				+ ", shipToState=" + shipToState + ", shipToCntryCode="
				+ shipToCntryCode + ", shipToZip=" + shipToZip
				+ ", addressStatus=" + addressStatus + ", totalAmt=" + totalAmt
				+ ", currencyCode=" + currencyCode + "]";
	}

	@Override
	public int hashCode() {
		int prime = 31;
		int result = 1;
		result = prime * result
				+ ((addressStatus == null) ? 0 : addressStatus.hashCode());
		result = prime * result + ((article == null) ? 0 : article.hashCode());
		result = prime * result
				+ ((currencyCode == null) ? 0 : currencyCode.hashCode());
		result = prime * result + ((email == null) ? 0 : email.hashCode());
		result = prime * result
				+ ((firstName == null) ? 0 : firstName.hashCode());
		result = prime * result
				+ ((lastName == null) ? 0 : lastName.hashCode());
		result = prime * result + ((payerId == null) ? 0 : payerId.hashCode());
		result = prime * result
				+ ((payerStatus == null) ? 0 : payerStatus.hashCode());
		result = prime * result
				+ ((paymentStatus == null) ? 0 : paymentStatus.hashCode());
		result = prime * result
				+ ((shipToCity == null) ? 0 : shipToCity.hashCode());
		result = prime * result
				+ ((shipToCntryCode == null) ? 0 : shipToCntryCode.hashCode());
		result = prime * result
				+ ((shipToName == null) ? 0 : shipToName.hashCode());
		result = prime * result
				+ ((shipToState == null) ? 0 : shipToState.hashCode());
		result = prime * result
				+ ((shipToStreet == null) ? 0 : shipToStreet.hashCode());
		result = prime * result
				+ ((shipToZip == null) ? 0 : shipToZip.hashCode());
		result = prime * result
				+ ((totalAmt == null) ? 0 : totalAmt.hashCode());
		result = prime * result
				+ ((transactionId == null) ? 0 : transactionId.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Order other = (Order) obj;
		if (addressStatus == null) {
			if (other.addressStatus != null)
				return false;
		} else if (!addressStatus.equals(other.addressStatus))
			return false;
		if (article == null) {
			if (other.article != null)
				return false;
		} else if (!article.equals(other.article))
			return false;
		if (currencyCode == null) {
			if (other.currencyCode != null)
				return false;
		} else if (!currencyCode.equals(other.currencyCode))
			return false;
		if (email == null) {
			if (other.email != null)
				return false;
		} else if (!email.equals(other.email))
			return false;
		if (firstName == null) {
			if (other.firstName != null)
				return false;
		} else if (!firstName.equals(other.firstName))
			return false;
		if (lastName == null) {
			if (other.lastName != null)
				return false;
		} else if (!lastName.equals(other.lastName))
			return false;
		if (payerId == null) {
			if (other.payerId != null)
				return false;
		} else if (!payerId.equals(other.payerId))
			return false;
		if (payerStatus == null) {
			if (other.payerStatus != null)
				return false;
		} else if (!payerStatus.equals(other.payerStatus))
			return false;
		if (paymentStatus == null) {
			if (other.paymentStatus != null)
				return false;
		} else if (!paymentStatus.equals(other.paymentStatus))
			return false;
		if (shipToCity == null) {
			if (other.shipToCity != null)
				return false;
		} else if (!shipToCity.equals(other.shipToCity))
			return false;
		if (shipToCntryCode == null) {
			if (other.shipToCntryCode != null)
				return false;
		} else if (!shipToCntryCode.equals(other.shipToCntryCode))
			return false;
		if (shipToName == null) {
			if (other.shipToName != null)
				return false;
		} else if (!shipToName.equals(other.shipToName))
			return false;
		if (shipToState == null) {
			if (other.shipToState != null)
				return false;
		} else if (!shipToState.equals(other.shipToState))
			return false;
		if (shipToStreet == null) {
			if (other.shipToStreet != null)
				return false;
		} else if (!shipToStreet.equals(other.shipToStreet))
			return false;
		if (shipToZip == null) {
			if (other.shipToZip != null)
				return false;
		} else if (!shipToZip.equals(other.shipToZip))
			return false;
		if (totalAmt == null) {
			if (other.totalAmt != null)
				return false;
		} else if (!totalAmt.equals(other.totalAmt))
			return false;
		if (transactionId == null) {
			if (other.transactionId != null)
				return false;
		} else if (!transactionId.equals(other.transactionId))
			return false;
		return true;
	}
}
