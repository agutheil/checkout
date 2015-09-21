package com.mightymerce.checkout.web.controller;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringEscapeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.mightymerce.checkout.integration.paypal.PayPal;

@Controller
@RequestMapping("/Checkout")
public class PaypalCheckout {
	
	@Autowired
	private HttpServletRequest request;
	
	@Autowired
	private HttpServletResponse response;
	
	
	@RequestMapping(method = RequestMethod.POST)
	public String doPost()
					throws ServletException, IOException {
		HttpSession session = request.getSession();
		PayPal paypal = new PayPal();
		/*
'------------------------------------
' The returnURL is the location where buyers return to when a
' payment has been successfully authorized.
'------------------------------------
		 */

		String returnURL = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+request.getContextPath()+"/Return?page=review";
		if(paypal.getUserActionFlag().equals("true"))
			returnURL = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+request.getContextPath()+"/Return?page=return";

		/*
'------------------------------------
' The cancelURL is the location buyers are sent to when they hit the
' cancel button during authorization of payment during the PayPal flow
'------------------------------------
		 */
		String cancelURL = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+request.getContextPath()+"/cancel.jsp";
		Map<String,String> checkoutDetails = new HashMap<String, String>() ;
		checkoutDetails=setRequestParams(request);
		//Redirect to check out page for check out mark
		if(!isSet(request.getParameter("Confirm")) && isSet(request.getParameter("checkout"))){
			return doCheckout(checkoutDetails, session); //checkout.jsp
		}
		else{
			Map<String, String> nvp=null;
			if(isSet(session.getAttribute("EXPRESS_MARK")) && session.getAttribute("EXPRESS_MARK").equals("ECMark")){
				checkoutDetails.putAll((Map<String, String>) session.getAttribute("checkoutDetails"));
				checkoutDetails.putAll(setRequestParams(request));
				if(isSet(checkoutDetails.get("shipping_method"))) {
					BigDecimal new_shipping = new BigDecimal(checkoutDetails.get("shipping_method")); //need to change this value, just for testing
					BigDecimal shippingamt = new BigDecimal(checkoutDetails.get("PAYMENTREQUEST_0_SHIPPINGAMT"));
					BigDecimal paymentAmount = new BigDecimal(checkoutDetails.get("PAYMENTREQUEST_0_AMT"));
					if(shippingamt.compareTo(new BigDecimal(0)) > 0){ 
						paymentAmount = paymentAmount.add(new_shipping).subtract(shippingamt) ;
					}
					//session.setAttribute("PAYMENTREQUEST_0_AMT",paymentAmount.toString());  //.replace(".00", "")
					//session.setAttribute("PAYMENTREQUEST_0_SHIPPINGAMT",new_shipping.toString());	
					//session.setAttribute("shippingAmt",new_shipping.toString());
					String val = checkoutDetails.put("PAYMENTREQUEST_0_AMT",paymentAmount.toString());  //.replace(".00", "")

					checkoutDetails.put("PAYMENTREQUEST_0_SHIPPINGAMT",new_shipping.toString());	
					checkoutDetails.put("shippingAmt",new_shipping.toString());
				}
				//TODO jsp umwandeln
				returnURL = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+request.getContextPath()+"/lightboxreturn.jsp";
				nvp = paypal.callMarkExpressCheckout(checkoutDetails, returnURL, cancelURL);  
				session.setAttribute("checkoutDetails", checkoutDetails);
			} else {
				session.invalidate();
				session = request.getSession();
				nvp = paypal.callShortcutExpressCheckout (checkoutDetails, returnURL, cancelURL);
				session.setAttribute("checkoutDetails", checkoutDetails);
			}

			String strAck = nvp.get("ACK").toString().toUpperCase();
			if(strAck !=null && (strAck.equals("SUCCESS") || strAck.equals("SUCCESSWITHWARNING") ))
			{
				handleSuccess(paypal, nvp, session); //redirect via headers
			}
			else
			{
				return handleError(nvp, session); //error.jsp
			}
		}
		return "error"; //never reached
	}


	private String handleError(Map<String, String> nvp, HttpSession session) throws ServletException,
			IOException {
		// Display a user friendly Error on the page using any of the following error information returned by PayPal
		String ErrorCode = nvp.get("L_ERRORCODE0").toString();
		String ErrorShortMsg = nvp.get("L_SHORTMESSAGE0").toString();
		String ErrorLongMsg = nvp.get("L_LONGMESSAGE0").toString();
		String ErrorSeverityCode = nvp.get("L_SEVERITYCODE0").toString();

		String errorString = "SetExpressCheckout API call failed. "+
				"<br>Detailed Error Message: " + ErrorLongMsg +
				"<br>Short Error Message: " + ErrorShortMsg +
				"<br>Error Code: " + ErrorCode +
				"<br>Error Severity Code: " + ErrorSeverityCode;
		request.setAttribute("error", errorString);
		return "error";
	}


	private void handleSuccess(PayPal paypal, Map<String, String> nvp, HttpSession session) {
		session.setAttribute("TOKEN", nvp.get("TOKEN").toString());
		//Redirect to paypal.com
		paypal.redirectURL(response, nvp.get("TOKEN").toString(),(isSet(session.getAttribute("EXPRESS_MARK")) && session.getAttribute("EXPRESS_MARK").equals("ECMark") || (paypal.getUserActionFlag().equalsIgnoreCase("true"))) );
	}

	private String doCheckout(Map<String, String> checkoutDetails,HttpSession session)
			throws ServletException, IOException {
		session.setAttribute("checkoutDetails", checkoutDetails);

		if(isSet(request.getParameter("checkout")) || isSet(session.getAttribute("checkout"))) {
			session.setAttribute("checkout", StringEscapeUtils.escapeHtml4(request.getParameter("checkout")));
		}

		//Assign the Return and Cancel to the Session object for ExpressCheckout Mark
		session.setAttribute("EXPRESS_MARK", "ECMark");

		request.setAttribute("PAYMENTREQUEST_0_AMT", StringEscapeUtils.escapeHtml4(request.getParameter("PAYMENTREQUEST_0_AMT")));
		//redirect to check out page
		return "checkout";
	}


	private Map<String,String> setRequestParams(HttpServletRequest request){
		Map<String,String> requestMap = new HashMap<String,String>();
		Map<String, String[]> m = request.getParameterMap();
		for (String key : m.keySet()) {
			requestMap.put(key, StringEscapeUtils.escapeHtml4(m.get(key)[0]));
		}

		return requestMap;

	}
	private boolean isSet(Object value){
		return (value !=null && value.toString().length()!=0);
	}
}
