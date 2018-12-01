package com.orastays.sms.smsserver.service;

import com.orastays.sms.smsserver.exceptions.FormExceptions;
import com.orastays.sms.smsserver.exceptions.SMSSendException;
import com.orastays.sms.smsserver.model.SMSModel;

public interface SMSService {

	void sendSMS(SMSModel smsModel) throws FormExceptions, SMSSendException;

}
