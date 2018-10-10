package com.keeboot.login;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.DataProvider;

import com.keeboot.utils.Sleeper;
import com.keeboot.utils.dataProviders.CSVDataProvider;
import com.keeboot.web.PageLoaded;
import com.keeboot.web.WebBaseTest;

public class ReadOtp extends WebBaseTest{
	
	@DataProvider(name = "dataScenario", parallel = true)
    public Object[][] scenarios() {
        return CSVDataProvider.getData("/datasheets/VerifyUserIsAbleToLogin.csv");
    }
	public static void main(String[] args) {
        // declaration and instantiation of objects/variables
    	System.setProperty("webdriver.chrome.driver", "C:\\shiva\\chromedriver_win32\\chromedriver.exe");
		WebDriver driver = new ChromeDriver();
    	
        String baseUrl = "https://accounts.google.com/ServiceLogin/signinchooser?service=mail&passive=true&rm=false&continue=https%3A%2F%2Fmail.google.com%2Fmail%2F&ss=1&scc=1&ltmpl=default&ltmplcache=2&emr=1&osid=1&flowName=GlifWebSignIn&flowEntry=ServiceLogin";
        
        //to click on the existing email id's 
        driver.findElement(By.xpath("//*[text()='shivakumar@meratransport.com']")).click();
        
        
        //to set the password
        driver.findElement(By.xpath("//input[@type='password']")).sendKeys("sairambaba3");
        
       Sleeper.sleep(3000);         //pageload time to wait till homepage loads
       
       //to select the first mail from inbox
       driver.findElement(By.xpath("//*[@id=\":2k\"]")).click();
       
       //to read the otp from that mail
    String Otp =  driver.findElement(By.xpath("")).getText();
        
	}
}
