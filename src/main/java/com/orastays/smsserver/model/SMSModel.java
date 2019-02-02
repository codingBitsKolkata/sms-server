package com.orastays.smsserver.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@EqualsAndHashCode(callSuper = false)
@ToString
public class SMSModel {

	private String message;
	private String mobileNumber;
}
