package com.keeboot.login;

import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.By;
import com.keeboot.web.KeebootDriver;
import com.keeboot.web.webelements.Button;
import com.keeboot.web.webelements.Element;
import com.keeboot.web.webelements.impl.internal.ElementFactory;

public class LoginPage{

    // ----------------------------------------------------
    // Variable Declaration/Object Repository
    // ----------------------------------------------------
	
	private KeebootDriver driver = null;
	
	@FindBy(xpath="//a[@class='loginBtn']")
	private Button loginBtn;
	
	@FindBy(xpath="//button[text()='Sign In']")
	private Button signInBtn;
	
	@FindBy(xpath="//button[text()='Next']")
	private Button NextBtn;
	
	
	@FindBy(xpath="//div[@class='recaptcha-checkbox-checkmark']")
	private Element eleCapthaCheckBox;
	
	
	@FindBy(xpath="//button[text()='Send OTP']")
	private Button sendOtpBtn;
	
	 // ----------------------------------------------------
    // Class Constructor
    // ----------------------------------------------------
    public LoginPage(KeebootDriver driver) {
        // Build the page area.
        this.driver = driver;
        ElementFactory.initElements(driver, this);
    }
    
//	public void sendSMS() throws Exception{
//    OutboundNotification outboundNotification = new OutboundNotification();
//    SerialModemGateway gateway = new SerialModemGateway("modem.com5", "COM5", 9600, "ZTE", "COM5");
//    gateway.setInbound(true);
//    gateway.setOutbound(true);
//    gateway.setSmscNumber("+91XXXXXXXXXX"); // 10-digit Mobile Number
//    Service.getInstance().setOutboundMessageNotification(outboundNotification);
//    Service.getInstance().addGateway(gateway);
//    Service.getInstance().startService();
//    OutboundMessage msg = new OutboundMessage(ExcelConnect.strSMSTo, ExcelConnect.strSMSText);
//    Service.getInstance().sendMessage(msg);
//    System.out.println(msg);
//    System.out.println(ExcelConnect.strSMSTo + "-" + ExcelConnect.strSMSText);
//    Service.getInstance().stopService();
//    Service.getInstance().removeGateway(gateway);
//}
    public boolean verifyLoginBtn()
    {
    	return loginBtn.isDisplayed();
    }
    public void clickOnLoginButton()
    {
    	boolean loginDisplayed=loginBtn.isDisplayed();
    	if(loginDisplayed)
    	loginBtn.jsClick();
    }
    
    public void clickOnSignIn()
    {
    	signInBtn.jsClick();
    }
    
    public void selectCompany(int j)
    {
    	int i=0;
    	driver.findElement(By.xpath("//div[contains(@id,'tenant_"+i+"')]")).click();
    	
    }
}
