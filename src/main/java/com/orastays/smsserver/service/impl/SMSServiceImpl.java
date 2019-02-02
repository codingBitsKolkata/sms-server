package com.orastays.smsserver.service.impl;

import java.util.Collections;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.orastays.smsserver.exceptions.FormExceptions;
import com.orastays.smsserver.exceptions.SMSSendException;
import com.orastays.smsserver.helper.MessageUtil;
import com.orastays.smsserver.model.SMSModel;
import com.orastays.smsserver.service.SMSService;
import com.orastays.smsserver.validation.SMSValidation;
import com.plivo.api.Plivo;
import com.plivo.api.models.message.Message;

@Service
@Transactional
public class SMSServiceImpl implements SMSService {

	private static final Logger logger = LogManager.getLogger(SMSServiceImpl.class);
	
	@Autowired
	private SMSValidation smsValidation;
	
	@Autowired
	private MessageUtil messageUtil;

	@Override
	public void sendSMS(SMSModel smsModel) throws FormExceptions, SMSSendException {
		
		if (logger.isInfoEnabled()) {
			logger.info("sendSMS -- START");
		}
		
		smsValidation.validateSMS(smsModel);
		try {
	        Plivo.init(messageUtil.getBundle("auth.id"), messageUtil.getBundle("auth.token"));
	        Message.creator("919742729028", Collections.singletonList(smsModel.getMobileNumber()), smsModel.getMessage()).create();
		} catch (Exception e) {
			throw new SMSSendException("");
		}
		
		if (logger.isInfoEnabled()) {
			logger.info("sendSMS -- END");
		}		
	}
}
