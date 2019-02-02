package com.orastays.smsserver.validation;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.orastays.smsserver.exceptions.FormExceptions;
import com.orastays.smsserver.helper.AuthConstant;
import com.orastays.smsserver.helper.MessageUtil;
import com.orastays.smsserver.helper.Util;
import com.orastays.smsserver.model.SMSModel;

@Component
public class SMSValidation extends AuthorizeUserValidation {

	private static final Logger logger = LogManager.getLogger(SMSValidation.class);
	
	@Autowired
	private MessageUtil messageUtil;
	
	@Autowired
	private HttpServletRequest request;
	
	public void validateSMS(SMSModel smsModel) throws FormExceptions {

		if (logger.isInfoEnabled()) {
			logger.info("validateSMS -- Start");
		}

		Util.printLog(smsModel, AuthConstant.INCOMING, "Send SMS", request);
		Map<String, Exception> exceptions = new LinkedHashMap<>();
		if(Objects.nonNull(smsModel)) {
			
			// Validate Message
			if(StringUtils.isBlank(smsModel.getMessage())) {
				exceptions.put(messageUtil.getBundle("sms.message.null.code"), new Exception(messageUtil.getBundle("sms.message.null.message")));
			}
			
			// Validate Mobile Number of the User
			if(StringUtils.isBlank(smsModel.getMobileNumber())) {
				exceptions.put(messageUtil.getBundle("sms.number.null.code"), new Exception(messageUtil.getBundle("sms.number.null.message")));
			}
		}
		
		if (exceptions.size() > 0)
			throw new FormExceptions(exceptions);
		
		if (logger.isInfoEnabled()) {
			logger.info("validateSMS -- End");
		}
	}
}
