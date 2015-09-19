	package com.mightymerce.checkout;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.social.oauth2.AccessGrant;
import org.springframework.social.oauth2.OAuth2Template;
import org.springframework.social.oauth2.TokenStrategy;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/Return")
public class PaypalReturn {
	@Autowired
	private HttpServletRequest request;	
	
	@Autowired
	private HttpServletResponse response;
	
	@Autowired
	private OrderRepository orderRepository;
	
	private OAuth2Template oAuth2Template;
	private MultiValueMap<String, String> params = new LinkedMultiValueMap<String, String>();
	@Value("${mightymerce.coreUrl}")
    private String coreUrl;
    @Value("${mightymerce.istestmode}")
    private boolean isTestmode;
	@Value("${mightymerce.user}")
	private String mightyUser;
	@Value("${mightymerce.pw}")
	private String mightyPw;
    
	
    @Inject
    public PaypalReturn(OAuth2Template oAuth2Template) {
        this.oAuth2Template = oAuth2Template;
        params.set("scope", "read write");

    }

    @RequestMapping(method = RequestMethod.GET)
    public String doGet(Model model)
            		throws ServletException, IOException {
    	HttpSession session = request.getSession(true);
    	//String finalPaymentAmount = (String)session.getAttribute("Payment_Amount");

    	if (isSet(request.getParameter("PayerID")))
    		session.setAttribute("payer_id", request.getParameter("PayerID"));
    	String token = "";
    	if (isSet(request.getParameter("token"))){
    		session.setAttribute("TOKEN", request.getParameter("token"));
    		token = request.getParameter("token");
    	}else{
    		token = (String) session.getAttribute("TOKEN");
    	}

    	// Check to see if the Request object contains a variable named 'token'	
    	PayPal pp = new PayPal();   	
    	//Map<String, String> result = new HashMap<String, String>();
    	// If the Request object contains the variable 'token' then it means that the user is coming from PayPal site.	
    	Map<String,String> shippingDetailsResults = null;
    	if (isSet(token))
    	{
    		/*
    		* Calls the GetExpressCheckoutDetails API call
    		*/
    		shippingDetailsResults = pp.getShippingDetails(token );
    	    String strAck = shippingDetailsResults.get("ACK").toString();
    		if(strAck !=null && (strAck.equalsIgnoreCase("SUCCESS") || strAck.equalsIgnoreCase("SUCCESSWITHWARNING") ))
    		{
    	    	session.setAttribute("payer_id", shippingDetailsResults.get("PAYERID"));
    			//result.putAll(results);
    			model.addAllAttributes(shippingDetailsResults);	
    		} 
    		else  
    		{
    			//Display a user friendly Error on the page using any of the following error information returned by PayPal
                String errorCode = shippingDetailsResults.get("L_ERRORCODE0").toString();
                String errorShortMsg = shippingDetailsResults.get("L_SHORTMESSAGE0").toString();
                String errorLongMsg = shippingDetailsResults.get("L_LONGMESSAGE0").toString();
                String errorSeverityCode = shippingDetailsResults.get("L_SEVERITYCODE0").toString();
                
                String errorString = "SetExpressCheckout API call failed. "+

               		"<br>Detailed Error Message: " + errorLongMsg +
    		        "<br>Short Error Message: " + errorShortMsg +
    		        "<br>Error Code: " + errorCode +
    		        "<br>Error Severity Code: " + errorSeverityCode;
                request.setAttribute("error", errorString);
            	session.invalidate();
            	return "error";
            }
    	}   
    	
    	Map<String, String> checkoutDetails = new HashMap<String, String>();
		checkoutDetails.putAll((Map<String, String>) session.getAttribute("checkoutDetails"));
		checkoutDetails.putAll(setRequestParams(request));
		checkoutDetails.put("TOKEN", token);
		checkoutDetails.put("payer_id", (String) session.getAttribute("payer_id"));
		model.addAttribute("PAYMENTREQUEST_0_SHIPPINGAMT", checkoutDetails.get("PAYMENTREQUEST_0_SHIPPINGAMT"));
		if(isSet(request.getParameter("shipping_method"))){
    		BigDecimal newShipping = new BigDecimal(checkoutDetails.get("shipping_method")); //need to change this value, just for testing
    		BigDecimal shippingamt = new BigDecimal(checkoutDetails.get("PAYMENTREQUEST_0_SHIPPINGAMT"));
    		BigDecimal paymentAmount = new BigDecimal(checkoutDetails.get("PAYMENTREQUEST_0_AMT"));
    		if(shippingamt.compareTo(new BigDecimal(0)) > 0){ 
    			paymentAmount = paymentAmount.add(newShipping).subtract(shippingamt) ;
    		}
    		checkoutDetails.put("PAYMENTREQUEST_0_AMT",paymentAmount.toString());  //.replace(".00", "")
    		checkoutDetails.put("PAYMENTREQUEST_0_SHIPPINGAMT",newShipping.toString());	
    		checkoutDetails.put("shippingAmt",newShipping.toString());
		} else {
			BigDecimal shippingamt = new BigDecimal(checkoutDetails.get("PAYMENTREQUEST_0_SHIPPINGAMT"));
			checkoutDetails.put("shippingAmt",shippingamt.toString());
		}

    	
    	/*
    	* Calls the DoExpressCheckoutPayment API call
    	*/
       	String page="return_4_failure";
    	if (isSet(request.getParameter("page")) && request.getParameter("page").equals("return")){  
	    	// FIXME - The method 'request.getServerName()' must be sanitized before being used.
			HashMap results = pp.confirmPayment (checkoutDetails,request.getServerName() );
			request.setAttribute("payment_method","");
	    	String strAck = results.get("ACK").toString().toUpperCase();
	    	
	    	if(strAck !=null && (strAck.equalsIgnoreCase("Success") || strAck.equalsIgnoreCase("SuccessWithWarning"))){
		    	model.addAllAttributes(results);
		    	model.addAllAttributes(checkoutDetails);
		    	request.setAttribute("ack", strAck);
		    	session.invalidate();
		    	updateCoreWithCheckoutDetails(results, checkoutDetails, shippingDetailsResults, strAck);
		    	if(request.getAttribute("payment_method").equals("credit_card"))  { 
		    		page="return_4_credit_card";
		    	} else {
		    		page="return_4_other_methods";
		    	}		
	    	}else{
	    		//Display a user friendly Error on the page using any of the following error information returned by PayPal
	            String errorCode = results.get("L_ERRORCODE0").toString();
	            String errorShortMsg = results.get("L_SHORTMESSAGE0").toString();
	            String errorLongMsg = results.get("L_LONGMESSAGE0").toString();
	            String errorSeverityCode = results.get("L_SEVERITYCODE0").toString();	            
	            String errorString = "SetExpressCheckout API call failed. "+
	           		"<br>Detailed Error Message: " + errorLongMsg +
	    	        "<br>Short Error Message: " + errorShortMsg +
	    	        "<br>Error Code: " + errorCode +
	    	        "<br>Error Severity Code: " + errorSeverityCode;
	            request.setAttribute("error", errorString);
	        	session.invalidate();	        	
	        	return "error";
	    	}
    	}else{
    		page="review";
    	}
    	return page;
    }
	
   
    
   
    private void updateCoreWithCheckoutDetails(HashMap results, Map<String, String> checkoutDetails, Map<String,String> shippingDetailsResults, String strAck) {
    	//extract L_PAYMENTREQUEST_0_NUMBER0 from checkoutDetails
        Long articleId = Long.parseLong(checkoutDetails.get("L_PAYMENTREQUEST_0_NUMBER0"));
        String payerId = checkoutDetails.get("payer_id");
        String txId = (String) results.get("PAYMENTINFO_0_TRANSACTIONID");
        String paymentStatus = (String) results.get("PAYMENTINFO_0_PAYMENTSTATUS");
//        String amtStr = (String) results.get("PAYMENTINFO_0_AMT");
        /*
		* The information that is returned by the GetExpressCheckoutDetails call should be integrated by the partner into his Order Review 
		* page		
		*/
		String email 				= shippingDetailsResults.get("EMAIL"); // ' Email address of payer.
//		String payerId 			= shippingDetailsResults.get("PAYERID"); // ' Unique PayPal customer account identification number.
		String payerStatus		= shippingDetailsResults.get("PAYERSTATUS"); // ' Status of payer. Character length and limitations: 10 single-byte alphabetic characters.
		String firstName			= shippingDetailsResults.get("FIRSTNAME"); // ' Payer's first name.
		String lastName			= shippingDetailsResults.get("LASTNAME"); // ' Payer's last name.
		String shipToName			= shippingDetailsResults.get("PAYMENTREQUEST_0_SHIPTONAME"); // ' Person's name associated with this address.
		String shipToStreet		= shippingDetailsResults.get("PAYMENTREQUEST_0_SHIPTOSTREET"); // ' First street address.
		String shipToCity			= shippingDetailsResults.get("PAYMENTREQUEST_0_SHIPTOCITY"); // ' Name of city.
		String shipToState		= shippingDetailsResults.get("PAYMENTREQUEST_0_SHIPTOSTATE"); // ' State or province
		String shipToCntryCode	= shippingDetailsResults.get("PAYMENTREQUEST_0_SHIPTOCOUNTRYCODE"); // ' Country code. 
		String shipToZip			= shippingDetailsResults.get("PAYMENTREQUEST_0_SHIPTOZIP"); // ' U.S. Zip code or other country-specific postal code.
		String addressStatus 		= shippingDetailsResults.get("ADDRESSSTATUS"); // ' Status of street address on file with PayPal 
		String totalAmt   		= shippingDetailsResults.get("PAYMENTREQUEST_0_AMT"); // ' Total Amount to be paid by buyer
		String currencyCode       = shippingDetailsResults.get("CURRENCYCODE"); // 'Currency being used 

		Order order = new Order();
        order.setArticle(articleId);
        order.setTransactionId(txId);
        order.setPaymentStatus(paymentStatus);
        order.setEmail(email);
        order.setPayerId(payerId);
        order.setPayerStatus(payerStatus);
        order.setFirstName(firstName);
        order.setLastName(lastName);
        order.setShipToName(shipToName);
        order.setShipToStreet(shipToStreet);
        order.setShipToCity(shipToCity);
        order.setShipToState(shipToState);
        order.setShipToCntryCode(shipToCntryCode);
        order.setShipToZip(shipToZip);
        order.setAddressStatus(addressStatus);
        order.setTotalAmt(totalAmt);
        order.setCurrencyCode(currencyCode);
//        if(!isTestmode){
//	    	AccessGrant ag = oAuth2Template.exchangeCredentialsForAccess(mightyUser, mightyPw,params);
//	        MightyCore mightyCore = new MightyCore(ag.getAccessToken(), TokenStrategy.AUTHORIZATION_HEADER, coreUrl);
//	        mightyCore.createOrder(order);
//        }
        order = orderRepository.save(order);
	}

    @RequestMapping(method = RequestMethod.POST)
	public String doPost(Model model)
    		throws ServletException, IOException {
    			return doGet(model);
    		}
    
	private Map<String,String> setRequestParams(HttpServletRequest request){
		Map<String,String> requestMap = new HashMap<String,String>();
		Map<String, String[]> m = request.getParameterMap();
		for (String key : m.keySet()) {
			requestMap.put(key, m.get(key)[0]);
			}
		
		return requestMap;
		
	}	
	private boolean isSet(Object value){
		return (value !=null && value.toString().length()!=0);
	}
}
