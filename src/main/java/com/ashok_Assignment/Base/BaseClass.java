package com.ashok_Assignment.Base;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.ashok_Assignment.utils.DriverFactory;
import com.ashok_Assignment.utils.UtilClass;



public class BaseClass {

	public static WebDriver driver=null;
	public static Properties prop=null;
	private static WebDriverWait wait ;
	//Initialize the property file
	public BaseClass(){
		prop=new Properties();
		
		try {
			FileInputStream fis=new FileInputStream(new File(UtilClass.propFilePath));
			prop.load(fis);
		} catch (FileNotFoundException e) {
			System.out.println("Properties File not found at given location");
			e.printStackTrace();
		} catch (IOException e) {
			
			e.printStackTrace();
		} 
	}
	
	//Initialize the browser
	public void initialization(){
		
		driver=DriverFactory.getDriver(prop.getProperty("browser"), prop.getProperty("url"), prop.getProperty("chromeDriverPath"));
		wait=new WebDriverWait(driver, UtilClass.wedriverWait);
	}
	
	public void takeScreenShot(String methodName) {
	   	 File scrFile = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
	        //The below method will save the screen shot in the drive with test method name 
	           try {
					FileUtils.copyFile(scrFile, new File(UtilClass.screenShotFilePath+methodName+"_failed.jpg"));
					System.out.println("***Placed screen shot in "+UtilClass.screenShotFilePath+" ***");
				} catch (IOException e) {
					e.printStackTrace();
				}
	   }
	
	//wait until element clickable state and click
    public void waitUntilElementclickable(WebElement element){
    	
    	wait.until(ExpectedConditions.elementToBeClickable(element)).click();
    	
	}
    
    //wait until element present
    public void waitUntilElementVisible(WebElement element){
    	wait.until(ExpectedConditions.visibilityOf(element));
    }
    
    //wait and switch to frame
	public void waitUntilFrametobeAvailableAndSwitchTo(WebElement element){
		wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(element));
	}
	
	public void killBrowser(){
		driver.quit();
		if(driver!=null){
			driver=null;
		}
		
	}

}
