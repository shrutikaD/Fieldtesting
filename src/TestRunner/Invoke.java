package TestRunner;

import java.io.IOException;
import java.net.URL;

import org.openqa.selenium.remote.BrowserType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import org.yaml.snakeyaml.tokens.FlowEntryToken;

import Demo.LoginAndMyTests;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.remote.MobileCapabilityType;

public class Invoke {
	AndroidDriver driver;

	@BeforeTest
	public void setup() throws InterruptedException, IOException {
		DesiredCapabilities capabilities = DesiredCapabilities.android();
		capabilities.setCapability(MobileCapabilityType.BROWSER_NAME, BrowserType.ANDROID);
		capabilities.setCapability(MobileCapabilityType.PLATFORM_NAME, "Android");
		capabilities.setCapability(MobileCapabilityType.DEVICE_NAME, "07fd7d3601895217");
		capabilities.setCapability(MobileCapabilityType.VERSION, "6.0.1");
		capabilities.setCapability("appPackage", "com.centricsoftware.fieldtesting");
		capabilities.setCapability("appActivity", "com.centricsoftware.fieldtesting.MainActivity");
		driver = new AndroidDriver(new URL("http://127.0.0.1:4723/wd/hub"), capabilities);
	}

	@Test(priority = 0)
	public void Login() throws IOException, InterruptedException {
		LoginAndMyTests flow = null;
		try {
			flow = new LoginAndMyTests();
			flow.loginWithValidCredentials(driver);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	@Test(priority = 1)
	public void Create_Sample() throws IOException, InterruptedException {
		try{
				LoginAndMyTests flow1 = null;
				flow1 = new LoginAndMyTests();
				flow1.create_sample(driver);
	
		} catch (Exception e){
			e.printStackTrace();
		}
	}
	
	
	@Test(priority = 2)
	public void Create_Survey() throws IOException, InterruptedException {
		try{
				LoginAndMyTests flow1 = null;
				flow1 = new LoginAndMyTests();
				flow1.create_survey(driver);;
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test(priority = 3)
	public void CheckOut() throws IOException, InterruptedException {
		try{
				LoginAndMyTests flow1 = null;
				flow1 = new LoginAndMyTests();
				flow1.checkout(driver);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	
	@Test(priority = 4)
	public void AddEditComment() throws IOException, InterruptedException {
		LoginAndMyTests flow = null;
		try {
			flow = new LoginAndMyTests();
			flow.postComment(driver);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

		
	@Test(priority = 5)
	public void Survey() throws IOException, InterruptedException {
		try {
			LoginAndMyTests flow1 = null;
			flow1 = new LoginAndMyTests();
		//	flow1.loginWithValidCredentials(driver);
			flow1.survey(driver);
			
		} catch (Exception e){
		e.printStackTrace();
	}
	}
	
	@Test(priority = 6)
	public void Edit_Survey() throws IOException, InterruptedException {
		try {
			LoginAndMyTests flow1 = null;
			flow1 = new LoginAndMyTests();
		//	flow1.loginWithValidCredentials(driver);
			flow1.edit_survey(driver);
			
		} catch (Exception e){
		e.printStackTrace();
	}
	}

	@Test(priority =7)
	public void Logout() throws IOException, InterruptedException {
		try {
			LoginAndMyTests flow1 = null;
			flow1 = new LoginAndMyTests();
			flow1.logOut(driver);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test(priority = 8)
	public void LoginFail() throws IOException, InterruptedException {
		try {
			LoginAndMyTests flow1 = null;
			flow1 = new LoginAndMyTests();
			flow1.LoginFail(driver);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
}
