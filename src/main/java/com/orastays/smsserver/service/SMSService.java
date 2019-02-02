package com.orastays.smsserver.service;

import com.orastays.smsserver.exceptions.FormExceptions;
import com.orastays.smsserver.exceptions.SMSSendException;
import com.orastays.smsserver.model.SMSModel;

public interface SMSService {

	void sendSMS(SMSModel smsModel) throws FormExceptions, SMSSendException;

}
