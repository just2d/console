package com.nuoshi.console.common.smstask;

import java.io.Serializable;

import com.nuoshi.console.service.SmsService;

public   class  SmsTaskRunnable  implements  Runnable, Serializable {
	
  
	private static final long serialVersionUID = 1L;
	private String phone; 
	
	private String content ;
   
	public String getPhone() {
		return phone;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	SmsTaskRunnable(String phone,String content) {
    	 this.phone = phone; 
    	 this.content = content ;
    }   
    public   void  run() {
    	
    	try {
    		Thread.sleep(1000);
    		SmsService.sentSms(phone, content);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    } 
  /*  public static void main(String[] args) {
    	SmsTaskRunnable SmsTaskRunnable = new SmsTaskRunnable("13401021305");
    	SmsTaskRunnable.run();
	}*/
}   
