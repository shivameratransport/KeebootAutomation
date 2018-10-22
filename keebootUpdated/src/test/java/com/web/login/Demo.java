package com.web.login;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver.SystemProperty;
import org.testng.annotations.DataProvider;

import com.keeboot.utils.dataProviders.CSVDataProvider;
import com.keeboot.web.WebBaseTest;

public class Demo extends WebBaseTest{
	@DataProvider(name = "dataScenario", parallel = true)
    public Object[][] scenarios() {
        return CSVDataProvider.getData("/datasheets/VerifyUserIsAbleToLogin.csv");
    }
	public static void main(String[] args) {
        // declaration and instantiation of objects/variables
    	System.setProperty("webdriver.chrome.driver", "D:\\Softwares\\chromedriver.exe");
		WebDriver driver = new ChromeDriver();
    	
        String baseUrl = "https://www.gmail.com";
        

        // launch Fire fox and direct it to the Base URL
        driver.get(baseUrl);

        driver.findElement(By.xpath("//input[@type='email']")).sendKeys("shivakumar@meratransport.com");
        
        driver.findElement(By.xpath("//*[text()='Next']")).click();
        
        //driver.findElement(By.xpath("//input[@type='password']")).sendKeys("sairambaba");
        
        
        driver.findElement(By.xpath("//*[text()='Next']")).click();
        
        driver.findElement(By.xpath("//*[@class='zA zE']")).click();
        
        String otp = driver.findElement(By.xpath("//*[@id=\":2q\"]/div/div/span/text()")).getText();
        System.out.println(otp);
    }

}
