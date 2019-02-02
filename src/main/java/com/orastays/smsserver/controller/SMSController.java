package com.orastays.smsserver.controller;

import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.orastays.smsserver.exceptions.FormExceptions;
import com.orastays.smsserver.exceptions.SMSSendException;
import com.orastays.smsserver.helper.AuthConstant;
import com.orastays.smsserver.helper.MessageUtil;
import com.orastays.smsserver.helper.Util;
import com.orastays.smsserver.model.ResponseModel;
import com.orastays.smsserver.model.SMSModel;
import com.orastays.smsserver.service.SMSService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api")
@Api(value = "sms", description = "Rest API for SMS", tags = "SMS API")
public class SMSController {

	private static final Logger logger = LogManager.getLogger(SMSController.class);
	
	@Autowired
	private SMSService smsService;
	
	@Autowired
	private MessageUtil messageUtil;
	
	@Autowired
	private HttpServletRequest request;
	
	@RequestMapping(value = "/send-sms", method = RequestMethod.POST, produces = "application/json")
	@ApiOperation(value = "Send SMS", response = ResponseModel.class)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "OK"),
			@ApiResponse(code = 201, message = "Please Try after Sometime!!!"),
			@ApiResponse(code = 600, message = "Please write some message"),
			@ApiResponse(code = 601, message = "Please provide Mobile Number") })
	public ResponseEntity<ResponseModel> sendSMS(@RequestBody SMSModel smsModel) {

		if (logger.isInfoEnabled()) {
			logger.info("sendSMS -- START");
		}

		ResponseModel responseModel = new ResponseModel();

		try {
			smsService.sendSMS(smsModel);
			responseModel.setResponseBody(messageUtil.getBundle("sms.send.success"));
			responseModel.setResponseCode(messageUtil.getBundle(AuthConstant.COMMON_SUCCESS_CODE));
			responseModel.setResponseMessage(messageUtil.getBundle(AuthConstant.COMMON_SUCCESS_MESSAGE));
		} catch (FormExceptions fe) {

			for (Entry<String, Exception> entry : fe.getExceptions().entrySet()) {
				responseModel.setResponseCode(entry.getKey());
				responseModel.setResponseMessage(entry.getValue().getMessage());
				break;
			}
		} catch (SMSSendException e) {
			e.printStackTrace();
			responseModel.setResponseBody(messageUtil.getBundle("sms.send.failure"));
			responseModel.setResponseCode(messageUtil.getBundle(AuthConstant.COMMON_ERROR_CODE));
			responseModel.setResponseMessage(messageUtil.getBundle(AuthConstant.COMMON_ERROR_MESSAGE));
		}

		Util.printLog(responseModel, AuthConstant.OUTGOING, "Send SMS", request);

		if (logger.isInfoEnabled()) {
			logger.info("sendSMS -- END");
		}
		
		if (responseModel.getResponseCode().equals(messageUtil.getBundle(AuthConstant.COMMON_SUCCESS_CODE))) {
			return new ResponseEntity<>(responseModel, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(responseModel, HttpStatus.BAD_REQUEST);
		}
	}
}
