package Demo;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Properties;

import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.asserts.SoftAssert;
import io.appium.java_client.android.AndroidDriver;
import Utilities.LogGenerator;

public class LoginAndMyTests {
	String FILENAME = "F:\\Centic-FT-Automation\\Log\\FTLog.txt"; // File which will generate logs
	BufferedWriter bw = null;
	FileWriter fw = null;

	LogGenerator log = new LogGenerator();

	public LoginAndMyTests() throws IOException {
		fw = new FileWriter(FILENAME,true);
		bw = new BufferedWriter(fw);
	}

	// Scroll down
	public void scrollDown(AndroidDriver driver) throws InterruptedException {
		Dimension dimensions = driver.manage().window().getSize();
		Double screenHeightStart = dimensions.getHeight() * 0.5;
		int scrollStart = screenHeightStart.intValue();
		Double screenHeightEnd = dimensions.getHeight() * 0.2;
		int scrollEnd = screenHeightEnd.intValue();
		Thread.sleep(2000);
		driver.swipe(0, scrollStart, 0, scrollEnd, 2000);
		Thread.sleep(2000);
	}

	// TC:Validate login with valid credentials
	public void loginWithValidCredentials(AndroidDriver driver) throws InterruptedException, IOException {
		WebDriverWait wait = new WebDriverWait(driver, 40);
		log.print(bw);
		log.logInfo(bw, "TC:LOGIN WITH VALID CREDENTIALS");
		try {
			// Enter valid Org ID name
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//android.widget.EditText[@index=1]")));
			WebElement orgID = driver.findElement(By.xpath("//android.widget.EditText[@index=1]"));
			orgID.sendKeys("centric1");

			// Enter valid Email
			WebElement email = driver.findElement(By.xpath("//android.widget.EditText[@index=2]"));
			email.sendKeys("sshrtk@gmail.com");

			// Enter valid password followed by enter
			wait.until(ExpectedConditions.visibilityOfElementLocated(
					By.xpath("//android.widget.EditText[@index=2]//" + "following-sibling::android.widget.EditText")));
			driver.findElement(
					By.xpath("//android.widget.EditText[@index=2]//" + "following-sibling::android.widget.EditText"))
					.sendKeys("password" + "\n");

			// Make sure that user lands on ACTIVITY FEED screen(Login successful)
			wait.until(ExpectedConditions
					.visibilityOfElementLocated(By.xpath("//android.widget.TextView[@text='ACTIVITY FEED']")));
			driver.findElement(By.xpath("//android.widget.TextView[@text='ACTIVITY FEED']"));
			log.logInfo(bw, "User gets logged in successfully");
		//	postComment(driver);
		} catch (Exception e) {
			log.logException(bw, "An exception was thrown : " + e);
		} catch (Throwable t){
			log.logError(bw, "An error occured : " + t);
		} finally {
			if (bw != null) {
				try {
					bw.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	// Common method for add and edit comment
	public void commentInputAndAssert(AndroidDriver driver, String imagePickerOption, String feedbackType,
			String hoursTested, String commentDesc, String ImagePickerXpath) throws InterruptedException, IOException {		
		WebDriverWait wait = new WebDriverWait(driver, 40);
		String loggedInUsername = "Synerzip Automation-user1";
		SoftAssert softAssert = new SoftAssert();

		// Verify options Choose from Library and cancel options
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(ImagePickerXpath)));
	//	wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//android.widget.ImageView[@index=1]")));
		// Tap on camera icon and verify all image picker options Take
		// Photo,Choose from Library, Cancel
		
		driver.findElement(By.xpath(ImagePickerXpath)).click();
	//	driver.findElement(By.xpath("//android.widget.ImageView[@index=1]")).click();
		wait.until(ExpectedConditions
				.visibilityOfElementLocated(By.xpath("//android.widget.TextView[@text='Take Photo']")));
		WebElement eltTakePhoto = driver.findElement(By.xpath("//android.widget.TextView[@text='Take Photo']"));
		WebElement eltChooseFromLib = driver
				.findElement(By.xpath("//android.widget.TextView[@text='Choose from Library']"));
		WebElement eltCancel = driver.findElement(By.xpath("//android.widget.TextView[@text='Cancel']"));
		log.logInfo(bw, "Image picker options verified");

		// As per the choice take image from any option
		if (imagePickerOption.equals("Take Photo")) {
			wait.until(ExpectedConditions
					.visibilityOfElementLocated(By.xpath("//android.widget.TextView[@text='Take Photo']")));
			eltTakePhoto.click();
			wait.until(ExpectedConditions.visibilityOfElementLocated(
					By.xpath("//android.widget.ImageView[contains(@content-desc,'Capture photo')]")));
			driver.findElement(By.xpath("//android.widget.ImageView[contains(@content-desc,'Capture photo')]")).click();
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(
					"//android.widget.ImageButton[contains(@resource-id,'com.android.camera2:id/done_button')]")));
			// Thread.sleep(6000);
			driver.findElement(By
					.xpath("//android.widget.ImageButton[contains(@resource-id,'com.android.camera2:id/done_button')]"))
					.click();
			Thread.sleep(2000);
		}

		if (imagePickerOption.equals("ChooseFormLib")) {
			wait.until(ExpectedConditions
					.visibilityOfElementLocated(By.xpath("//android.widget.TextView[@text='Choose from Library']")));
			eltChooseFromLib.click();
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(
					"//android.widget.ImageView[contains(@resource-id,'com.android.documentsui:id/icon_mime')]")));
			driver.findElement(By
					.xpath("//android.widget.ImageView[contains(@resource-id,'com.android.documentsui:id/icon_mime')]"))
					.click();
			Thread.sleep(5000);
		}

		// Tap on Feedback Type
		scrollDown(driver);
		driver.findElement(By.xpath("//android.widget.ScrollView[@index=2]//android.view.ViewGroup[@index=0]"
				+ "//android.view.ViewGroup[@index=2]//android.widget.TextView[@index=0]")).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//android.widget.TextView[@text='Issue']")));

		// Verify Feedback Types
		driver.findElement(By.xpath("//android.widget.TextView[@text='Issue']"));
		driver.findElement(By.xpath("//android.widget.TextView[@text='Critical Issue']"));
		driver.findElement(By.xpath("//android.widget.TextView[@text='Other']"));
		driver.findElement(By.xpath("//android.widget.TextView[@text='Cancel']"));
		log.logInfo(bw, "Feedback Types verified");
		wait.until(ExpectedConditions
				.visibilityOfElementLocated(By.xpath("//android.widget.TextView[@text='" + feedbackType + "']")));
		driver.findElement(By.xpath("//android.widget.TextView[@text='" + feedbackType + "']")).click();

		// Enter hours tested
		wait.until(ExpectedConditions.visibilityOfElementLocated(
				By.xpath("//android.widget.ScrollView[@index=2]//android.view.ViewGroup[@index=0]"
						+ "//android.widget.EditText[@index=3]")));
		driver.findElement(By.xpath("//android.widget.ScrollView[@index=2]//android.view.ViewGroup[@index=0]"
				+ "//android.widget.EditText[@index=3]")).sendKeys(hoursTested);

		// Enter comment description
		wait.until(
				ExpectedConditions.visibilityOfElementLocated(By.xpath("//android.widget.EditText[@text='Comments']")));
		driver.findElement(By.xpath("//android.widget.EditText[@text='Comments']")).sendKeys("");
		driver.findElement(By.xpath("//android.widget.EditText[@text='Comments']")).sendKeys(commentDesc);

		// Tap on Post button
		driver.findElement(By.xpath("//android.widget.TextView[@text='Post']")).click();

		// Get current date and time
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("MM/dd/yy HH:mm");
		LocalDateTime now = LocalDateTime.now();
		log.logInfo(bw, "Posted comment time is : " + dtf.format(now));
		Thread.sleep(18000);
		wait.until(ExpectedConditions.visibilityOfElementLocated(
				By.xpath("//android.view.ViewGroup[@index=0]//android.widget.ImageView[@index=2]")));

		// Tap on right navigation arrow present on 1st sample
		driver.findElement(By.xpath("//android.view.ViewGroup[@index=0]//android.widget.ImageView[@index=2]")).click();

		// Scroll down
		scrollDown(driver);

		// Assert user-name who posted a comment
		String commentedUserName = driver.findElement(By.xpath("//android.view.ViewGroup[@index=3]/"
				+ "android.view.ViewGroup[@index=1]/android.widget.TextView[@index=2]")).getText();
		log.logInfo(bw, "Commentted user name is " + commentedUserName);
		softAssert.assertEquals(commentedUserName, loggedInUsername);
		log.logInfo(bw, "User name verified who posted a comment");

		// Assert date and time
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(
				"//android.view.ViewGroup[@index=3]/android.view.ViewGroup[@index=1]/android.widget.TextView[@index=3]")));
		String commentDatetime = driver
				.findElement(By
						.xpath("//android.view.ViewGroup[@index=3]/android.view.ViewGroup[@index=1]/android.widget.TextView[@index=3]"))
				.getText();
		softAssert.assertEquals(commentDatetime, dtf.format(now));
		log.logInfo(bw, "Comment date and time verified");

		// Verify 3-dot menu icon present or not
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//android.view.ViewGroup[@index=4]")));
		driver.findElement(By.xpath("//android.view.ViewGroup[@index=4]"));
		log.logInfo(bw, "3-dot menu present");

		driver.scrollTo("Checked Out");
	/*	wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(
				"//android.widget.TextView[@text='10 Hours Tested / 10 Hours Total']")));

		// Assert tested hours
		String actualHoursTested = driver
				.findElement(By.xpath("//android.widget.TextView[@text='10 Hours Tested / 10 Hours Total']")).getText();
		softAssert.assertTrue(actualHoursTested.contains(hoursTested));
		log.logInfo(bw, "Hours tested verified");
*/
		// Verify sub comment icon present or not
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(
				"//android.view.ViewGroup[@index=3]//android.view.ViewGroup[@index=1]/android.view.ViewGroup[3]/android.widget.ImageView")));
		driver.findElement(By.xpath(
				"//android.view.ViewGroup[@index=3]//android.view.ViewGroup[@index=1]/android.view.ViewGroup[3]/android.widget.ImageView"));
		log.logInfo(bw, "Sub-comment menu present");

		Thread.sleep(1000);
/*		// Assert comment description actual/expected
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//android.widget.TextView[@index=11]")));
		Assert.assertEquals(driver.findElement(By.xpath("//android.widget.TextView[@index=11]")).getText(),
				commentDesc);
		log.logInfo(bw, "Comment description verified");
*/
		// Assert Feedback Type
		Assert.assertEquals(driver
				.findElement(By.xpath("//android.widget.TextView[@text='IDEA']"))
				.getText(), feedbackType.toUpperCase());
		log.logInfo(bw, "Feedback type verified");

		// Tap on back button
		driver.findElement(By.xpath("//android.widget.TextView[@text='Back']")).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("MY TESTS")));
	}

	// TC:Post comment
	public void postComment(AndroidDriver driver) throws InterruptedException, IOException {
		WebDriverWait wait = new WebDriverWait(driver, 40);
		
		Properties prop = new Properties();
	    InputStream input = null;
	    
		log.print(bw);
		log.logInfo(bw, "TC:POST A COMMENT AND ASSERT THE VALUES");
		try {
			
			input = new FileInputStream("F:\\Centic-FT-Automation\\src\\Resources\\config.properties");

	        // load a properties file
	        prop.load(input);

			String hoursTested = "10";
			String commentDesc = "Comments";
			String feedbackType = "Idea";
			String imagePickerOption = "ChooseFormLib";
			
			//go to MY TESTS
			driver.findElement(By.xpath("//android.widget.ImageView[@bounds='[15,147][165,267]']")).click();
			wait.until(ExpectedConditions
					.visibilityOfElementLocated(By.name("My Tests")));

			driver.findElement(By.name("My Tests")).click();
			
			// Verify user lands on MY TESTS screen
			wait.until(ExpectedConditions
					.visibilityOfElementLocated(By.xpath("//android.widget.TextView[@text='MY TESTS']")));
			driver.findElement(By.xpath("//android.widget.TextView[@text='MY TESTS']"));
			log.logInfo(bw, "Screen title present-MY TESTS");

			// Verify Checked Out tab is present
			driver.findElement(By.xpath("//android.widget.TextView[@text='Checked Out']"));
			log.logInfo(bw, "Checked Out tab is present");

			// Verify Checked In tab present
			wait.until(ExpectedConditions
					.visibilityOfElementLocated(By.xpath("//android.widget.TextView[@text='Checked In']")));
			driver.findElement(By.xpath("//android.widget.TextView[@text='Checked In']"));
			log.logInfo(bw, "Checked In tab is present");

			// Tap on comment button of fresh assigned sample
			wait.until(ExpectedConditions
					.visibilityOfElementLocated(By.xpath("//android.widget.TextView[@text='Comment']")));
			driver.findElement(By.xpath("//android.widget.TextView[@text='Comment']")).click();
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(
					"//android.widget.ScrollView[@index=2]//preceding-sibling::android.view.ViewGroup[@index=0]//android.widget.TextView[@index=1]")));

			// Verify screen title "NEW COMMENT"
			Assert.assertEquals(driver
					.findElement(By
							.xpath("//android.widget.ScrollView[@index=2]//preceding-sibling::android.view.ViewGroup[@index=0]//android.widget.TextView[@index=1]"))
					.getText(), "NEW COMMENT");
			log.logInfo(bw, "Screen title verified-NEW COMMENT");

			// Verify buttons label
			Assert.assertEquals(
					driver.findElement(By.xpath("//android.widget.ScrollView[@index=2]//"
							+ "preceding-sibling::android.view.ViewGroup[@index=0]"
							+ "//android.view.ViewGroup[@index=0]/android.widget.TextView[@index=0]")).getText(),
					"Cancel");
			log.logInfo(bw, "Button label verified-Cancel");

			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(
					"//android.widget.ScrollView[@index=2]//" + "preceding-sibling::android.view.ViewGroup[@index=0]"
							+ "//android.view.ViewGroup[@index=2]/android.widget.TextView[@index=0]")));
			WebElement eltPost = driver.findElement(By.xpath(
					"//android.widget.ScrollView[@index=2]//" + "preceding-sibling::android.view.ViewGroup[@index=0]"
							+ "//android.view.ViewGroup[@index=2]/android.widget.TextView[@index=0]"));
			Assert.assertEquals(eltPost.getText(), "Post");
			Thread.sleep(1000);
			log.logInfo(bw, "Button label verified-Post");

			// Post comment with blank fields alert message assert
			eltPost.click();
			wait.until(ExpectedConditions.visibilityOfElementLocated(
					By.xpath("//android.widget.TextView[contains(@resource-id,'android:id/alertTitle')]")));
			String postAlertMessage = driver
					.findElement(By.xpath("//android.widget.TextView[contains(@resource-id,'android:id/alertTitle')]"))
					.getText();
			Assert.assertEquals(postAlertMessage, "Please enter at least one field");
			driver.findElement(By.xpath("//android.widget.Button[@text='OK']")).click();
			log.logInfo(bw, "Post comment with blank fields alert asserted");
			String ImagePickerXpath=prop.getProperty("ImagePickerXpath1");
			commentInputAndAssert(driver, imagePickerOption, feedbackType, hoursTested, commentDesc, ImagePickerXpath);
			log.logInfo(bw, "Comment posted successfully");
			editComment(driver);
			// deleteComment();

		} catch (Exception e) {
			e.printStackTrace();
			log.logException(bw, "An exception was thrown : " + e);
		} catch (Throwable t){
			log.logError(bw, "An error occured : " + t);
		} finally {
			if (bw != null) {
				try {
					bw.close();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
		}
	}

	// TC:Delete comment (Getting error-null is not an object) currently on hold
	public void deleteComment(AndroidDriver driver) throws IOException {
		WebDriverWait wait = new WebDriverWait(driver, 40);
		log.print(bw);
		log.logInfo(bw, "TC:DELETE COMMENT");
		try {
			// Tap on right navigation arrow present on 1st sample
			wait.until(ExpectedConditions.visibilityOfElementLocated(
					By.xpath("//android.view.ViewGroup[@index=0]//android.widget.ImageView[@index=2]")));
			driver.findElement(By.xpath("//android.view.ViewGroup[@index=0]//android.widget.ImageView[@index=2]"))
					.click();
			scrollDown(driver);

			// Tap on 3-dot menu icon
			driver.findElement(By.xpath("//android.view.ViewGroup[@index=4]")).click();
			wait.until(ExpectedConditions
					.visibilityOfElementLocated(By.xpath("//android.widget.TextView[@text='Delete']")));

			// Tap on delete
			driver.findElement(By.xpath("//android.widget.TextView[@text='Delete']")).click();

			// Tap on delete confirmation message Yes
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//android.widget.Button[@text='Yes']")));
			driver.findElement(By.xpath("//android.widget.Button[@text='Yes']")).click();

			// Tap on back button
			driver.findElement(By.xpath("//android.widget.TextView[@text='Back']")).click();
		//	logOut(driver);

		} catch (Exception e) {
			e.printStackTrace();
			log.logException(bw, "An exception was thrown : " + e);
		} catch (Throwable t){
			log.logError(bw, "An error occured : " + t);
		} finally {
			if (bw != null) {
				try {
					bw.close();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
		}

	}

	// TC:Edit comment
	public void editComment(AndroidDriver driver) throws IOException {
		WebDriverWait wait = new WebDriverWait(driver, 40);
		
		  Properties prop = new Properties();
		    InputStream input = null;
		
		log.print(bw);
		log.logInfo(bw, "TC:EDIT COMMENT AND ASSERT VALUES");
		String hoursTested = "10";
		String commentDesc = "This is edit test comment description";
		String feedbackType = "Idea";
		String imagePickerOption = "Take Photo";
		try {
			
			input = new FileInputStream("F:\\Centic-FT-Automation\\src\\Resources\\config.properties");

	        // load a properties file
	        prop.load(input);

			
			// Tap on right navigation arrow present on 1st sample
			wait.until(ExpectedConditions.visibilityOfElementLocated(
					By.xpath("//android.view.ViewGroup[@index=0]//android.widget.ImageView[@index=2]")));
			driver.findElement(By.xpath("//android.view.ViewGroup[@index=0]//android.widget.ImageView[@index=2]"))
					.click();
			scrollDown(driver);

			// Tap on 3-dot menu icon
			driver.findElement(By.xpath("//android.view.ViewGroup[@index=4]//android.widget.ImageView[@index=0]")).click();
			
			Thread.sleep(2000);

			// Tap on Edit option
			wait.until(
					ExpectedConditions.visibilityOfElementLocated(By.xpath("//android.widget.TextView[@text='Edit']")));
			driver.findElement(By.xpath("//android.widget.TextView[@text='Edit']")).click();

			// Verify Screen Title,Cancel and Post buttons
			wait.until(ExpectedConditions
					.visibilityOfElementLocated(By.xpath("//android.widget.TextView[@text='EDIT COMMENT']")));
			driver.findElement(By.xpath("//android.widget.TextView[@text='EDIT COMMENT']"));
			log.logInfo(bw, "Screen title present-EDIT COMMENT");
			Assert.assertEquals(
					driver.findElement(By.xpath("//android.widget.ScrollView[@index=2]//"
							+ "preceding-sibling::android.view.ViewGroup[@index=0]"
							+ "//android.view.ViewGroup[@index=0]/android.widget.TextView[@index=0]")).getText(),
					"Cancel");
			log.logInfo(bw, "Button label verified-Cancel");

			WebElement eltPost = driver.findElement(By.xpath(
					"//android.widget.ScrollView[@index=2]//" + "preceding-sibling::android.view.ViewGroup[@index=0]"
							+ "//android.view.ViewGroup[@index=2]/android.widget.TextView[@index=0]"));
			Assert.assertEquals(eltPost.getText(), "Post");
			Thread.sleep(1000);
			log.logInfo(bw, "Button label verified-Post");
			String ImagePickerXpath=prop.getProperty("ImagePickerXpath2");
			commentInputAndAssert(driver, imagePickerOption, feedbackType, hoursTested, commentDesc, ImagePickerXpath);
			// deleteComment();
		//	logOut(driver);
		} catch (Exception e) {
			e.printStackTrace();
			log.logException(bw, "An exception was thrown : " + e);
		} catch (Throwable t){
			log.logError(bw, "An error occured : " + t);
		} finally {
			if (bw != null) {
				try {
					bw.close();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
		}
	}

	// TC:Validate login (fail intentionally)
	public void LoginFail(AndroidDriver driver) throws InterruptedException, IOException {
		log.logInfo(bw, "TC: Assert fail intentionally");
		Thread.sleep(5000);
		WebDriverWait wait = new WebDriverWait(driver, 40);
		System.out.println("Started with negative test case");
		try {
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//android.widget.TextView[@index=1]")));
			String appName = driver.findElement(By.xpath("//android.widget.TextView[@index=1]")).getText();
			Assert.assertEquals(appName, "signin");
		} catch (Exception e) {	
			e.printStackTrace();
			log.logException(bw, "An exception was thrown : " + e);
		} catch (Throwable t){
			log.logError(bw, "An error occured : " + t);
		} 
		finally {
			if (bw != null) {
				try {
					bw.close();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
		}
	}

	// TC:Logout
	public void logOut(AndroidDriver driver) throws InterruptedException, IOException {
		WebDriverWait wait = new WebDriverWait(driver, 40);
		log.print(bw);
		log.logInfo(bw, "TC: LOGOUT");
		try {
			// Tap on left navigation menu
			wait.until(ExpectedConditions.visibilityOfElementLocated(
					By.xpath("//android.widget.ImageView[contains(@bounds,'[15,147][165,267]')]")));
			driver.findElement(By.xpath("//android.widget.ImageView[contains(@bounds,'[15,147][165,267]')]")).click();
			scrollDown(driver);
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//android.widget.TextView[@text='Sign Out']")));

			// Tap on Sign Out
			driver.findElement(By.xpath("//android.widget.TextView[@text='Sign Out']")).click();
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//android.widget.Button[@text='Yes']")));

			// Tap on Yes of confirmation message
			driver.findElement(By.xpath("//android.widget.Button[@text='Yes']")).click();
			log.logInfo(bw, "User logged out successfully");
			log.print(bw);
		} catch (Exception e) {
			e.printStackTrace();
			log.logException(bw, "An exception was thrown : " + e);
		} catch (Throwable t){
			log.logError(bw, "An error occured : " + t);
		} 
		finally {
			if (bw != null) {
				try {
					bw.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	
	
	public void survey(AndroidDriver driver) throws InterruptedException, IOException {
		WebDriverWait wait = new WebDriverWait(driver, 40);
		log.print(bw);
		log.logInfo(bw, "TC: POST SURVEY AND ASSERT SURVEY");
		try {
			String surveyNoOfQuestions = null;
			int i = 0;
			
			//go to MY TESTS
			driver.findElement(By.xpath("//android.widget.ImageView[@bounds='[15,147][165,267]']")).click();
			wait.until(ExpectedConditions
					.visibilityOfElementLocated(By.name("My Tests")));

			driver.findElement(By.name("My Tests")).click();
			
			// Verify user lands on MY TESTS screen
			wait.until(ExpectedConditions
					.visibilityOfElementLocated(By.xpath("//android.widget.TextView[@text='MY TESTS']")));
		
			
			// Tap on button survey
			wait.until(ExpectedConditions
					.visibilityOfElementLocated(By.xpath("//android.widget.TextView[@text='Survey']")));
			driver.findElement(By.xpath("//android.widget.TextView[@text='Survey']")).click();

			// Verify button-cancel, Take Survey, screentitle-SURVEY,
			// surveyname, surveydescription, no. of questions
			wait.until(ExpectedConditions
					.visibilityOfElementLocated(By.xpath("//android.widget.TextView[@text='Cancel']")));
			driver.findElement(By.xpath("//android.widget.TextView[@text='Cancel']"));
			log.logInfo(bw, "Verified Cancel button");

			driver.findElement(By.xpath("//android.widget.TextView[@text='Take Survey']"));
			log.logInfo(bw, "Verified Take Survey button");

			String surveyScreenTitle = driver.findElement(By.xpath("//android.widget.TextView[@text='SURVEY']"))
					.getText();
			Assert.assertEquals(surveyScreenTitle, "SURVEY");
			log.logInfo(bw, "Verified SURVEY screen title");

			String surveyName = driver.findElement(
					By.xpath("//android.widget.ScrollView[@index=2]/android.view.ViewGroup/android.view.ViewGroup"
							+ "/android.widget.TextView[@index=0]"))
					.getText();
			Assert.assertEquals(surveyName, "Automation Survey, Automation Survey Subtitle");
			log.logInfo(bw, "Verified surveyname");

			String surveyDescription = driver.findElement(
					By.xpath("//android.widget.ScrollView[@index=2]/android.view.ViewGroup/android.view.ViewGroup"
							+ "/android.widget.TextView[@index=1]"))
					.getText();
			Assert.assertEquals(surveyDescription, "Automation Survey Description");
			log.logInfo(bw, "Verified Survey description");

			wait.until(ExpectedConditions.visibilityOfElementLocated(
					By.xpath("//android.widget.ScrollView[@index=2]/android.view.ViewGroup/android.view.ViewGroup"
							+ "/android.widget.TextView[@index=3]")));

			surveyNoOfQuestions = driver.findElement(
					By.xpath("//android.widget.ScrollView[@index=2]/android.view.ViewGroup/android.view.ViewGroup"
							+ "/android.widget.TextView[@index=3]"))
					.getText();
			Assert.assertEquals(surveyNoOfQuestions, "5 Questions");
			log.logInfo(bw, "Verified no of Questions text");

			// Tap on survey button
			driver.findElement(By.xpath("//android.widget.TextView[@text='Take Survey']")).click();

			// Verify screen title,Cancel button, question,back
			// button,pagination,next button
			wait.until(ExpectedConditions
					.visibilityOfElementLocated(By.xpath("//android.widget.TextView[@text='Cancel']")));
			driver.findElement(By.xpath("//android.widget.TextView[@text='Cancel']"));
			log.logInfo(bw, "Verified Cancel button");

			int noOfQues = Integer.parseInt(surveyNoOfQuestions.replace(" Questions", ""));

			for (i = 1; i <= noOfQues; i++) {
				String newSurveyScreenTitle = driver
						.findElement(By.xpath("//android.widget.TextView[@text='NEW SURVEY']")).getText();
				Assert.assertEquals(newSurveyScreenTitle, "NEW SURVEY");
				log.logInfo(bw, "Verified SURVEY screen title");
				
				
				// Verify Back and Next button on each question screen
				wait.until(ExpectedConditions
						.visibilityOfElementLocated(By.xpath("//android.widget.TextView[@text='Back']")));
				driver.findElement(By.xpath("//android.widget.TextView[@text='Back']"));
				if (i == noOfQues) {
					driver.findElement(By.xpath("//android.widget.TextView[@text='Finish']"));
					log.logInfo(bw, "Verified Back and Finish buttons");
				} else {
					driver.findElement(By.xpath("//android.widget.TextView[@text='Next']"));
					log.logInfo(bw, "Verified Back and Next buttons");					
				}
				

				// Verify question answer numeric question
				if (i == 1) {
					String question1 = driver.findElement(By.xpath("//android.widget.EditText[@index=1]//"
							+ "preceding-sibling::android.view.ViewGroup//android.widget.TextView")).getText();
					Assert.assertEquals(question1, "1. Numeric Type Question?*");
					log.logInfo(bw, "Verified question 1. Numeric Type Question?*");
					
					int q_length=question1.length();
					char temp = question1.charAt(question1.length()-1);
					if(temp == '*')
					{
						//Click Next without entering value
						driver.findElement(By.xpath("//android.widget.TextView[@text='Next']")).click();
						wait.until(ExpectedConditions.visibilityOfElementLocated(
								By.xpath("//android.widget.TextView[contains(@resource-id,'android:id/alertTitle')]")));
						String compulsory_alert_msg=driver.findElement(By.xpath("//android.widget.TextView[contains(@resource-id,'android:id/alertTitle')]")).getText();
						Assert.assertEquals(compulsory_alert_msg, "All * marked questions are required");
						driver.findElement(By.xpath("//android.widget.Button[@text='OK']")).click();
						log.logInfo(bw, "Compulsory questions alert asserted");
					}
					wait.until(ExpectedConditions
							.visibilityOfElementLocated(By.xpath("//android.widget.EditText[@index=1]")));
					driver.findElement(By.xpath("//android.widget.EditText[@index=1]")).sendKeys("123");
					log.logInfo(bw, "Answered question 1");
					
				}
				if (i == 2) {
					
			/*		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//android.widget.EditText[@index=0]//android.view.ViewGroup[@index=1]"
							+ "preceding-sibling::android.view.ViewGroup//android.widget.TextView")));
					String question2 = driver.findElement(By.xpath("//android.widget.EditText[@index=0]//android.view.ViewGroup[@index=1]"
							+ "preceding-sibling::android.view.ViewGroup//android.widget.TextView")).getText(); 
			*/	
					wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.xpath("//android.widget.TextView[@bounds='[72,348][1008,405]']")));
					String question2=driver.findElement(By.xpath("//android.widget.TextView[@bounds='[72,348][1008,405]']")).getText();
					
					Assert.assertEquals(question2, "2. Multiple Choice Question?");
					log.logInfo(bw, "Verified question 2. Multiple Choice Question?");
				
					wait.until(ExpectedConditions
							.visibilityOfElementLocated(By.xpath("//android.widget.EditText[@index=0]")));
					driver.findElement(By.xpath("//android.widget.EditText[@index=0]")).click();
					
					//select one option
					wait.until(ExpectedConditions
							.visibilityOfElementLocated(By.xpath("//android.widget.TextView[@index=0]")));
					driver.findElement(By.xpath("//android.widget.TextView[@index=0]")).click();
					log.logInfo(bw, "Answered question 2");
					Thread.sleep(2000);
				}
				
				//answer short text
				if (i == 3) {
					
					wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.xpath("//android.widget.TextView[@bounds='[72,348][1008,405]']")));
					String question2=driver.findElement(By.xpath("//android.widget.TextView[@bounds='[72,348][1008,405]']")).getText();
					
					Assert.assertEquals(question2, "3. Short text Question?");
					log.logInfo(bw, "Verified question 3. Short text Question?");
					
					wait.until(ExpectedConditions
							.visibilityOfElementLocated(By.xpath("//android.widget.EditText[@index=1]")));
					driver.findElement(By.xpath("//android.widget.EditText[@index=1]")).sendKeys("Short question answered");
					log.logInfo(bw, "Answered question 3");
					
					//sub-question
					if(driver.findElement(By.xpath("//android.widget.TextView[contains(@text,'3A')]")).isDisplayed())	
					{
						
						String sub_question=driver.findElement(By.xpath("//android.widget.TextView[contains(@text,'3A.')]")).getText();
						
						Assert.assertEquals(sub_question, "3A. Short text Sub-Question?");
						log.logInfo(bw, "Verified sub-question 3A. Short text Sub-Question?");
						
						wait.until(ExpectedConditions
								.visibilityOfElementLocated(By.xpath("//android.widget.EditText[@index=3]")));
						driver.findElement(By.xpath("//android.widget.EditText[@index=3]")).sendKeys("Sub question answered");
						log.logInfo(bw, "Answered sub-question");
					}
				
				}
				
				// answer long paragraph
				if (i == 4) {
					
					wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.xpath("//android.widget.TextView[@bounds='[72,348][1008,405]']")));
					String question2=driver.findElement(By.xpath("//android.widget.TextView[@bounds='[72,348][1008,405]']")).getText();
					
					Assert.assertEquals(question2, "4. Paragraph text Question?");
					log.logInfo(bw, "Verified question 4. Paragraph text Question?");
					
					wait.until(ExpectedConditions
							.visibilityOfElementLocated(By.xpath("//android.widget.EditText[@index=1]")));
					driver.findElement(By.xpath("//android.widget.EditText[@index=1]")).sendKeys("Paragraph text question answered");
					log.logInfo(bw, "Answered question 4");
				}
				
				if (i == 5) {
					
					wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.xpath("//android.widget.TextView[@bounds='[72,348][1008,405]']")));
					String question2=driver.findElement(By.xpath("//android.widget.TextView[@bounds='[72,348][1008,405]']")).getText();
					
					Assert.assertEquals(question2, "5. Paragraph text Question?");
					log.logInfo(bw, "Verified Duplicate question 5. Paragraph text Question?");
					
					wait.until(ExpectedConditions
							.visibilityOfElementLocated(By.xpath("//android.widget.EditText[@index=1]")));
					driver.findElement(By.xpath("//android.widget.EditText[@index=1]")).sendKeys("Duplicate Paragraph text question answered");
					log.logInfo(bw, "Answered duplicate question 5");
				}
				
					
			
				
				
				// Verify pagination text
			/*	String paginationText = driver.findElement(By.xpath("//android.widget.EditText[@index=1]//"
						+ "following-sibling::android.widget.TextView[@index=3]")).getText();
				log.logInfo(bw, paginationText);
				log.logInfo(bw, surveyNoOfQuestions);
				if (paginationText.contains(i + "/" + noOfQues)) {
					log.logInfo(bw, "Pagination verified till" +i+ " No. Of questions");
				}
				
			*/
				if (i == noOfQues) {
					// Tap on finish
					wait.until(ExpectedConditions
							.visibilityOfElementLocated(By.xpath("//android.widget.TextView[@text='Finish']")));
					driver.findElement(By.xpath("//android.widget.TextView[@text='Finish']")).click();
				} else {
					// Tap on next button
					driver.findElement(By.xpath("//android.widget.TextView[@text='Next']")).click();
					Thread.sleep(5000);
				}
			}
			Thread.sleep(5000);
			assert_survey(driver);

		} catch (Exception e) {
			e.printStackTrace();
			log.logException(bw, "An exception was thrown : " + e);
		} catch (Throwable t) {
			log.logError(bw, "An error occured : " + t);
		} finally {
			if (bw != null) {
				try {
					bw.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	
	public void assert_survey(AndroidDriver driver) throws InterruptedException, IOException {
		WebDriverWait wait = new WebDriverWait(driver, 40);
		log.print(bw);
		log.logInfo(bw, "Assert SURVEY");
		try {
			
			// Tap on right navigation arrow present on 1st sample
			driver.findElement(By.xpath("//android.view.ViewGroup[@index=0]//android.widget.ImageView[@index=2]")).click();
			log.logInfo(bw, "Go to Sample Page");
			//scrolldown
			scrollDown(driver);
			
			// Tap on 3-dot menu icon
			
			wait.until(ExpectedConditions
					.visibilityOfElementLocated(By.name("Automation Survey, Automation Survey Subtitle")));
			
			driver.findElement(By.name("Automation Survey, Automation Survey Subtitle")).click();
			log.logInfo(bw, "Clicked on Edit Survey ");
			
			// Verify button-cancel, screentitle-Edit SURVEY,
						// Edit button, no. of questions, answers
			wait.until(ExpectedConditions
					.visibilityOfElementLocated(By.xpath("//android.widget.TextView[@text='Cancel']")));
			driver.findElement(By.xpath("//android.widget.TextView[@text='Cancel']"));
			log.logInfo(bw, "Verified Cancel button");
			
			driver.findElement(By.xpath("//android.widget.TextView[@text='EDIT SURVEY']"));
			log.logInfo(bw, "Verified Screen Title - EDIT SURVEY");
			Thread.sleep(2000);
			driver.findElement(By.xpath("//android.widget.TextView[@text='Edit']"));
			log.logInfo(bw, "Verified Edit button");
			
			driver.findElement(By.xpath("//android.widget.TextView[@text='Cancel']")).click();
			log.logInfo(bw, "Clicked on Cancel button");
			
			wait.until(ExpectedConditions
					.visibilityOfElementLocated(By.xpath("//android.widget.TextView[@text='Back']")));
			driver.findElement(By.xpath("//android.widget.TextView[@text='Back']")).click();
			log.logInfo(bw, "Clicked on Back button");
			
		} catch (Exception e) {
			e.printStackTrace();
			log.logException(bw, "An exception was thrown : " + e);
		} catch (Throwable t) {
			log.logError(bw, "An error occured : " + t);
		} finally {
			if (bw != null) {
				try {
					bw.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	
	public void edit_survey(AndroidDriver driver) throws InterruptedException, IOException {
		WebDriverWait wait = new WebDriverWait(driver, 40);
		log.print(bw);
		log.logInfo(bw, "TC: EDIT SURVEY");
		String edit_ans="123456789";
		
		try {
				
			// Tap on right navigation arrow present on 1st sample
			
			wait.until(ExpectedConditions
					.visibilityOfElementLocated(By.xpath("//android.view.ViewGroup[@index=0]//android.widget.ImageView[@index=2]")));
			driver.findElement(By.xpath("//android.view.ViewGroup[@index=0]//android.widget.ImageView[@index=2]")).click();
			log.logInfo(bw, "Go to Sample Page");
			
			//scrolldown
			scrollDown(driver);
						
			// Tap on 3-dot menu icon
						
			wait.until(ExpectedConditions
				.visibilityOfElementLocated(By.name("Automation Survey, Automation Survey Subtitle")));
						
			driver.findElement(By.name("Automation Survey, Automation Survey Subtitle")).click();
			log.logInfo(bw, "Clicked on Edit Survey ");			
			
					
			wait.until(ExpectedConditions
					.visibilityOfElementLocated(By.xpath("//android.widget.TextView[@text='EDIT SURVEY']")));
			
			String ans = driver.findElement(By.xpath("//android.widget.TextView[@text='123']")).getText();
			System.out.println("Ans1 = "+ans);
			Thread.sleep(2000);
			//click on edit button of 1st question
			driver.findElement(By.xpath("//android.widget.TextView[@text='Edit']")).click();
			log.logInfo(bw, "Edit Button Clicked");
			
			wait.until(ExpectedConditions
					.visibilityOfElementLocated(By.xpath("//android.widget.EditText[@index=1]")));
			//edit answer		
			driver.findElement(By.xpath("//android.widget.EditText[@index=1]")).click();
			driver.findElement(By.xpath("//android.widget.EditText[@index=1]")).clear();
			driver.findElement(By.xpath("//android.widget.EditText[@index=1]")).sendKeys(edit_ans);
			log.logInfo(bw, "Answer Edited");
			
			for(int i=1; i<=5 ; i++)
			{
			    Thread.sleep(2000);
			    if(i==5)
			    	driver.findElement(By.xpath("//android.widget.TextView[@text='Finish']")).click();
			    else
			    	driver.findElement(By.xpath("//android.widget.TextView[@text='Next']")).click();
			}
			
			//verify the survey is edited
			wait.until(ExpectedConditions
					.visibilityOfElementLocated(By.name("Automation Survey, Automation Survey Subtitle")));
							
			
			driver.findElement(By.name("Automation Survey, Automation Survey Subtitle")).click();
		

			wait.until(ExpectedConditions
					.visibilityOfElementLocated(By.xpath("//android.widget.TextView[@text='EDIT SURVEY']")));
			
			String get_ans = driver.findElement(By.xpath("//android.widget.TextView[contains(@bounds,'[66,469][1014,526]')]")).getText();
			Assert.assertEquals(edit_ans, edit_ans);
			log.logInfo(bw, "Asserted edited survey");
			
			driver.findElement(By.name("Cancel")).click();
			
			wait.until(ExpectedConditions
					.visibilityOfElementLocated(By.xpath("//android.widget.TextView[@text='Automation Sample']")));
			driver.findElement(By.name("Back")).click();
			
		} catch (Exception e) {
			e.printStackTrace();
			log.logException(bw, "An exception was thrown : " + e);
		} catch (Throwable t) {
			log.logError(bw, "An error occured : " + t);
		} finally {
			if (bw != null) {
				try {
					bw.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	
	public void create_sample(AndroidDriver driver) throws InterruptedException, IOException {
		WebDriverWait wait = new WebDriverWait(driver, 40);
		log.print(bw);
		log.logInfo(bw, "TC: CREATE SAMPLE");
		
		try {
	
				//click on on left navigation pane
			driver.findElement(By.xpath("//android.widget.ImageView[@bounds='[15,147][165,267]']")).click();
			
			//click on samples
			wait.until(ExpectedConditions
					.visibilityOfElementLocated(By.xpath("//android.widget.TextView[@text='Samples']")));
			
			driver.findElement(By.xpath("//android.widget.TextView[@text='Samples']")).click();
			
			//verify user is on Samples page
			wait.until(ExpectedConditions
					.visibilityOfElementLocated(By.xpath("//android.widget.TextView[@text='SAMPLES']")));
		
			//verify Search, +, Checkedout, Available, Archive, left navigation buttons on SAMPLES PAGE
			driver.findElement(By.xpath("//android.widget.EditText[@text='Search']"));
			driver.findElement(By.xpath("//android.widget.TextView[@text='Checked Out']"));
			driver.findElement(By.xpath("//android.widget.TextView[@text='Available']"));
			driver.findElement(By.xpath("//android.widget.TextView[@text='Archive']"));
			driver.findElement(By.xpath("//android.widget.ImageView[@bounds='[15,147][165,267]']"));
			driver.findElement(By.xpath("//android.widget.ImageView[@bounds='[930,147][1080,267]']"));
			log.logInfo(bw, "Verified all elements on SAMPLES Page");
						
			//click on + sign to create new sample
			driver.findElement(By.xpath("//android.widget.ImageView[@bounds='[930,147][1080,267]']")).click();
			
			//verify user lands on Create Sample Page
			wait.until(ExpectedConditions
					.visibilityOfElementLocated(By.xpath("//android.widget.TextView[@text='CREATE SAMPLE']")));
			log.logInfo(bw, "User on Create Sample Page");
			
			//verify Cancel and Done Button
			driver.findElement(By.xpath("//android.widget.TextView[@text='Done']"));
			driver.findElement(By.xpath("//android.widget.TextView[@text='Cancel']"));
			log.logInfo(bw, "Verified all elements on Create Sample Page");
			
			driver.findElement(By.xpath("//android.widget.TextView[@text='Done']")).click();
			
			wait.until(ExpectedConditions.visibilityOfElementLocated(
					By.xpath("//android.widget.TextView[contains(@resource-id,'android:id/alertTitle')]")));
			String requiredFieldsAlert = driver
					.findElement(By.xpath("//android.widget.TextView[contains(@resource-id,'android:id/alertTitle')]"))
					.getText();
			Assert.assertEquals(requiredFieldsAlert, "Please fill out all required fields");
			driver.findElement(By.xpath("//android.widget.Button[@text='OK']")).click();
			log.logInfo(bw, "Please fill out all required fields alert asserted");
			
			
			//sample details
			driver.findElement(By.name("Sample Name *")).sendKeys("Automation Sample");
			driver.findElement(By.name("Sample Code")).sendKeys("Automation Sample Code");
			driver.hideKeyboard();
			driver.findElement(By.name("Category")).click();
		
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("CATEGORY")));
			Thread.sleep(2000);
			driver.findElement(By.xpath("//android.widget.TextView[@text='Gender-Men']")).click();
			Thread.sleep(2000);
			driver.findElement(By.name("Done")).click();
			
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("CREATE SAMPLE")));
			//verify selected category appears on Create Sample page
		//	driver.findElement(By.xpath("//android.widget.EditText[@text='Gender-Men']"));
			
			
			driver.findElement(By.name("Size")).click();
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("MEASUREMENTS")));
			driver.findElement(By.name("BaseLayer")).click();
			Thread.sleep(2000);
			driver.findElement(By.name("BaseLayer-L")).click();
			driver.findElement(By.name("Done")).click();
			Thread.sleep(2000);
			driver.findElement(By.name("Done")).click();
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("CREATE SAMPLE")));
			//verify selected size appears on Create Sample page
			driver.findElement(By.xpath("//android.widget.EditText[@text='BaseLayer-L']"));
			
				
			//add photo
			driver.findElement(By.xpath("//android.view.ViewGroup[@bounds='[60,348][1020,480]']")).click();
			//verify image picker options
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("Image Picker")));
			driver.findElement(By.name("Take Photo"));
			driver.findElement(By.name("Choose from Library"));
			driver.findElement(By.name("Cancel"));
			
			//select image from library
			driver.findElement(By.name("Choose from Library")).click();
			wait.until(ExpectedConditions.visibilityOfElementLocated(
					By.xpath("//android.widget.ImageView[@resource-id='com.android.documentsui:id/icon_thumb']")));
			driver.findElement(By.xpath("//android.widget.ImageView[@resource-id='com.android.documentsui:id/icon_thumb']")).click();
			
			
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("CREATE SAMPLE")));
		//	scrollDown(driver);
			driver.scrollTo("Quantity");
		//	driver.findElement(By.xpath("//android.view.ViewGroup[@index='1']")).click();
			Thread.sleep(2000);
		
			driver.findElement(By.name("Description...*")).sendKeys("This is automation Test Sample");
			driver.findElement(By.name("Done")).click();
			log.logInfo(bw, "Sample Created");
			Thread.sleep(18000);
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("SAMPLES")));
			//verify created sample in available tab
			driver.findElement(By.xpath("//android.widget.TextView[@text='Available']")).click();
			Thread.sleep(2000);
			driver.findElement(By.name("Automation Sample"));
			driver.findElement(By.name("Automation Sample Code"));
			driver.findElement(By.name("BaseLayer-L"));
			log.logInfo(bw, "Created Sample Verified");
			
			
		} catch (Exception e) {
			e.printStackTrace();
			log.logException(bw, "An exception was thrown : " + e);
		} catch (Throwable t) {
			log.logError(bw, "An error occured : " + t);
		} finally {
			if (bw != null) {
				try {
					bw.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	
	
	public void create_survey(AndroidDriver driver) throws InterruptedException, IOException {
		WebDriverWait wait = new WebDriverWait(driver, 40);
		log.print(bw);
		log.logInfo(bw, "TC: CREATE SURVEY");
		
		try {
	
				//click on on left navigation pane
			driver.findElement(By.xpath("//android.widget.ImageView[@bounds='[15,147][165,267]']")).click();
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("Surveys")));
			driver.findElement(By.name("Surveys")).click();
			//verify user is on survey page
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("SURVEYS")));
			driver.findElement(By.name("SURVEYS"));
			driver.findElement(By.xpath("//android.widget.ImageView[@bounds='[900,147][1050,267]']")).click();
			
			//verify user is New Survey Page, verify all elements on survey page
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("NEW SURVEY")));
			driver.findElement(By.name("NEW SURVEY"));
			driver.findElement(By.name("Done"));
			driver.findElement(By.name("Cancel"));
			
			driver.findElement(By.name("Survey Title *"));
			driver.findElement(By.name("Survey Subtitle"));
			driver.findElement(By.name("Description... *"));
			log.logInfo(bw, "All elements on New Survey Page asserted");
			//Assert All required fields alert message
			driver.findElement(By.name("Done")).click();
			wait.until(ExpectedConditions.visibilityOfElementLocated(
					By.xpath("//android.widget.TextView[contains(@resource-id,'android:id/alertTitle')]")));
			
			String alert_msg=driver.findElement(By.xpath("//android.widget.TextView[contains(@resource-id,'android:id/alertTitle')]")).getText();
			Assert.assertEquals(alert_msg, "Please fill out all required fields");	
			log.logInfo(bw, "Please fill out all required fields alert asserted");
			driver.findElement(By.xpath("//android.widget.Button[@text='OK']")).click();
			
			driver.findElement(By.name("Survey Title *")).sendKeys("Automation Survey");
			driver.findElement(By.name("Survey Subtitle")).sendKeys("Automation Survey Subtitle");
			driver.findElement(By.name("Description... *")).sendKeys("Automation Survey Description");
			driver.findElement(By.name("Done")).click();
			
			wait.until(ExpectedConditions.visibilityOfElementLocated(
					By.xpath("//android.widget.TextView[@text='Automation Survey, Automation Survey Subtitle']")));
			
			//select category
		//	String initial_category = driver.findElement(By.xpath("//android.widget.TextView[@bounds='[960,567][984,624]']")).getText();
			driver.findElement(By.xpath("//android.widget.TextView[@bounds='[960,567][984,624]']")).click();
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("CATEGORY")));
			driver.findElement(By.xpath("//android.widget.TextView[@text='Feedback-Trial']")).click();
			driver.findElement(By.name("Done")).click();
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("NEW SURVEY")));
			
			//verify category number is incremented 
			String new_category = driver.findElement(By.xpath("//android.widget.TextView[@bounds='[960,567][984,624]']")).getText();
			Assert.assertEquals(new_category, "1");
			
			
			//select questions
			driver.findElement(By.xpath("//android.widget.ImageView[@bounds='[984,706][1050,790]']")).click();
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("QUESTIONS")));
			
			//assert all elements on QUESTIONS page
			driver.findElement(By.name("Back"));
			driver.findElement(By.name("Done"));
			driver.findElement(By.name("Edit"));
			driver.findElement(By.name("Preview"));
			driver.findElement(By.name("add question"));
			
			driver.findElement(By.name("add question")).click();
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("QUESTION")));
			
			//verify all elements on Question Page
			driver.findElement(By.name("Cancel"));
			driver.findElement(By.name("Done"));
			driver.findElement(By.name("Required question"));
			driver.findElement(By.name("Question Type"));
			driver.findElement(By.name("Question...*"));
			driver.findElement(By.xpath("//android.widget.Switch[@text='ON']"));
			
			driver.findElement(By.name("Done")).click();
			wait.until(ExpectedConditions.visibilityOfElementLocated(
					By.xpath("//android.widget.TextView[contains(@resource-id,'android:id/alertTitle')]")));
			alert_msg=driver.findElement(
					By.xpath("//android.widget.TextView[contains(@resource-id,'android:id/alertTitle')]")).getText();
			Assert.assertEquals(alert_msg, "Please fill out all required fields");	
			log.logInfo(bw, "Please fill out all required fields alert asserted");
			driver.findElement(By.xpath("//android.widget.Button[@text='OK']")).click();
		
			
			//question1: numeric question, compulsory
			driver.findElement(By.xpath("//android.widget.EditText[@text='Question Type']")).click();
	//		driver.findElement(By.name("Question Type")).click();
			wait.until(ExpectedConditions.visibilityOfElementLocated(
					By.xpath("//android.widget.TextView[contains(@resource-id,'android:id/text1')]")));
			
			//verify all question types
			driver.findElement(By.name("Numeric"));
			driver.findElement(By.name("Multiple Choice"));
			driver.findElement(By.name("Short Text"));
			driver.findElement(By.name("Paragraph Text"));
			driver.findElement(By.name("Cancel"));
			log.logInfo(bw, "All Question Type options asserted");
			
			
			driver.findElement(By.name("Numeric")).click();
			Thread.sleep(2000);
			//assert numeric is selected
			driver.findElement(By.xpath("//android.widget.EditText[@text='Numeric']"));
			driver.findElement(By.name("Question...*")).sendKeys("Numeric Type Question?");
			driver.hideKeyboard();
			driver.findElement(By.name("Numeric Label*")).sendKeys("Label");
			driver.findElement(By.name("Done")).click();
			
			// assert user goes to Questions page on clicking Done
			driver.findElement(By.name("QUESTIONS"));
			//assert question is added on Questions page
			driver.findElement(By.name("1. Numeric Type Question?"));
			driver.findElement(By.name("Number"));
			log.logInfo(bw, "Numeric Type Question added");
			
			// assert user goes to Questions page on clicking Done
			driver.findElement(By.name("QUESTIONS"));
			
			//Question2: Multiple Choice Question
			driver.findElement(By.name("add question")).click();
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("QUESTION")));
			
			//unswitch required question
			driver.findElement(By.xpath("//android.widget.Switch[@text='ON']")).click();
			driver.findElement(By.xpath("//android.widget.EditText[@text='Question Type']")).click();
			wait.until(ExpectedConditions.visibilityOfElementLocated(
				        	By.xpath("//android.widget.TextView[contains(@resource-id,'android:id/text1')]")));
			driver.findElement(By.name("Multiple Choice")).click();
			Thread.sleep(2000);
			//assert multiple choice is selected
			driver.findElement(By.xpath("//android.widget.EditText[@text='Multiple Choice']"));
			driver.findElement(By.xpath("//android.widget.EditText[@text='Question...*']")).sendKeys("Multiple Choice Question?");
			driver.hideKeyboard();
			//select options
			driver.findElement(By.name("Option 1*")).sendKeys("Option 1");
			driver.findElement(By.name("Option 2*")).sendKeys("Option 2");
			driver.hideKeyboard();
			driver.findElement(By.name("add")).click();
			driver.findElement(By.name("Option 3")).sendKeys("Option 3");
			driver.findElement(By.name("Done")).click();
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("QUESTIONS")));
			//assert multiple choice question added on Questions screen
			driver.findElement(By.name("2. Multiple Choice Question?"));
			driver.findElement(By.name("MultipleChoice"));
			log.logInfo(bw, "Multiple Choice question added");
			
			
			//Question3: Short text Question
			driver.findElement(By.name("add question")).click();
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("QUESTION")));
			
			//unswitch required question
			driver.findElement(By.xpath("//android.widget.Switch[@text='ON']")).click();
			driver.findElement(By.xpath("//android.widget.EditText[@text='Question Type']")).click();
			wait.until(ExpectedConditions.visibilityOfElementLocated(
				        	By.xpath("//android.widget.TextView[contains(@resource-id,'android:id/text1')]")));
			
			driver.findElement(By.name("Short Text")).click();
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("QUESTION")));
			//assert short text question is added on Question Screen
			driver.findElement(By.xpath("//android.widget.EditText[@text='Short Text']"));
			driver.findElement(By.xpath("//android.widget.EditText[@text='Question...*']")).sendKeys("Short text Question?");
			driver.hideKeyboard();
			driver.findElement(By.name("Hint")).sendKeys("Short text question hint");
			driver.findElement(By.name("Done")).click();
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("QUESTIONS")));
			
			//assert short text question is added on Questions page
			driver.findElement(By.name("3. Short text Question?"));
			driver.findElement(By.name("ShortText"));
			log.logInfo(bw, "Short text question added");
			//add sub-question to short text
			//click on 3dots next to short question
			driver.findElement(By.xpath("//android.widget.ImageView[@bounds='[945,862][1020,967]']")).click();
			wait.until(ExpectedConditions.visibilityOfElementLocated(
							By.xpath("//android.widget.TextView[contains(@resource-id,'android:id/text1')]")));
			
			//assert all options
			driver.findElement(By.name("Duplicate Question"));
			driver.findElement(By.name("Move Up"));
			driver.findElement(By.name("Move Down"));
			driver.findElement(By.name("Delete"));
			driver.findElement(By.name("Add Subquestion"));
			driver.findElement(By.name("Cancel"));
			log.logInfo(bw, "3dots options asserted");
			
			driver.findElement(By.name("Add Subquestion")).click();
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("QUESTION")));
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("Q: Short text Question?")));
			driver.findElement(By.xpath("//android.widget.Switch[@text='ON']")).click();
			driver.findElement(By.xpath("//android.widget.EditText[@text='Question Type']")).click();
			wait.until(ExpectedConditions.visibilityOfElementLocated(
				        	By.xpath("//android.widget.TextView[contains(@resource-id,'android:id/text1')]")));
			
			driver.findElement(By.name("Short Text")).click();
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("QUESTION")));
			//assert short text question is added on Question Screen
			driver.findElement(By.xpath("//android.widget.EditText[@text='Short Text']"));
			driver.findElement(By.xpath("//android.widget.EditText[@text='Question...*']")).sendKeys("Short text Sub-Question?");
			driver.hideKeyboard();
			driver.findElement(By.name("Hint")).sendKeys("Short text sub-question hint");
			driver.findElement(By.name("Done")).click();
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("QUESTIONS")));
			
			//assert sub question is added on Questions page
			driver.findElement(By.name("3A. Short text Sub-Question?"));
			driver.findElement(By.name("ShortText"));
			log.logInfo(bw, "Sub-question added");
			
			
			//Question4: Paragraph text Question
			driver.findElement(By.name("add question")).click();
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("QUESTION")));
			
			//unswitch required question
			driver.findElement(By.xpath("//android.widget.Switch[@text='ON']")).click();
			driver.findElement(By.xpath("//android.widget.EditText[@text='Question Type']")).click();
			wait.until(ExpectedConditions.visibilityOfElementLocated(
				        	By.xpath("//android.widget.TextView[contains(@resource-id,'android:id/text1')]")));
			
			driver.findElement(By.name("Paragraph Text")).click();
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("QUESTION")));
			//assert short text question is added on Question Screen
			driver.findElement(By.xpath("//android.widget.EditText[@text='Paragraph Text']"));
			driver.findElement(By.xpath("//android.widget.EditText[@text='Question...*']")).sendKeys("Paragraph text Question?");
			driver.hideKeyboard();
			driver.findElement(By.name("Hint")).sendKeys("Paragraph question hint");
			driver.findElement(By.name("Done")).click();
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("QUESTIONS")));
			
			//assert paragraph text question is added on Questions page
			driver.findElement(By.name("4. Paragraph text Question?"));
			driver.findElement(By.name("ParagraphText"));
			log.logInfo(bw, "Paragraph text question added");
			
			
			//add duplicate question
			driver.findElement(By.xpath("//android.widget.ImageView[@bounds='[945,1243][1020,1348]']")).click();
			wait.until(ExpectedConditions.visibilityOfElementLocated(
					By.xpath("//android.widget.TextView[contains(@resource-id,'android:id/text1')]")));
			driver.findElement(By.name("Duplicate Question")).click();
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("QUESTIONS")));
			
			//assert duplicate question is added
			driver.findElement(By.name("5. Paragraph text Question?"));
			driver.findElement(By.name("ParagraphText"));
			log.logInfo(bw, "Duplicate Paragraph text question added");
			driver.findElement(By.name("Done")).click();
			
			
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("NEW SURVEY")));
			
			//assert number of questions
			String ques_no = driver.findElement(By.xpath("//android.widget.TextView[@bounds='[960,720][984,777]']")).getText();
			Assert.assertEquals(ques_no, "5");
			driver.findElement(By.name("Done")).click();
			Thread.sleep(25000);
			
			//assert survey added to Surveys page
			driver.findElement(By.name("SURVEYS"));
			driver.findElement(By.name("Automation Survey, Automation Survey Subtitle"));
			log.logInfo(bw, "Survey Created");
			
		} catch (Exception e) {
			e.printStackTrace();
			log.logException(bw, "An exception was thrown : " + e);
		} catch (Throwable t) {
			log.logError(bw, "An error occured : " + t);
		} finally {
			if (bw != null) {
				try {
					bw.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	
	public void checkout(AndroidDriver driver) throws InterruptedException, IOException {
		WebDriverWait wait = new WebDriverWait(driver, 40);
		log.print(bw);
		log.logInfo(bw, "TC: CHECKOUT");
		
		try {
			
			//click on on left navigation pane
			driver.findElement(By.xpath("//android.widget.ImageView[@bounds='[15,147][165,267]']")).click();
			
			//click on samples
			wait.until(ExpectedConditions
					.visibilityOfElementLocated(By.xpath("//android.widget.TextView[@text='Samples']")));
			
			driver.findElement(By.xpath("//android.widget.TextView[@text='Samples']")).click();
			
			//verify user is on Samples page
			wait.until(ExpectedConditions
					.visibilityOfElementLocated(By.xpath("//android.widget.TextView[@text='SAMPLES']")));
	
			driver.findElement(By.xpath("//android.widget.TextView[@text='Available']")).click();
			Thread.sleep(2000);
			driver.findElement(By.xpath("//android.widget.TextView[@text='Automation Sample']")).click();
			wait.until(ExpectedConditions.visibilityOfElementLocated(
					By.xpath("//android.widget.TextView[@text='Automation Sample']")));
			Thread.sleep(2000);
			driver.findElement(By.xpath("//android.widget.TextView[@text='Check Out']")).click();
			wait.until(ExpectedConditions.visibilityOfElementLocated(
					By.xpath("//android.widget.TextView[@text='CHECKOUT']")));
			Thread.sleep(3000);
			//assert all elements on checkout screen
			driver.findElement(By.name("Cancel")).isDisplayed();
			driver.findElement(By.name("Next"));
			driver.findElement(By.name("Search"));
			driver.findElement(By.name("Testers"));
			driver.findElement(By.name("Managers"));
			log.logInfo(bw, "All elements on Checkout screen asserted");
			
			//assign sample to existing manager
			driver.findElement(By.name("Managers")).click();
			Thread.sleep(2000);
			wait.until(ExpectedConditions.visibilityOfElementLocated(
					By.xpath("//android.widget.TextView[@text='TestManager Mng']")));
			driver.findElement(By.xpath("//android.widget.TextView[@text='TestManager Mng']")).click();
			driver.findElement(By.name("Next")).click();
			Thread.sleep(2000);
			wait.until(ExpectedConditions.visibilityOfElementLocated(
					By.name("Surveys")));
			
			
			driver.findElement(By.name("Surveys")).click();
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("SURVEYS")));
			
			driver.findElement(By.name("Automation Survey, Automation Survey Subtitle")).click();
			driver.findElement(By.name("Done")).click();
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("CHECKOUT")));
			driver.findElement(By.name("Done")).click();
			
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("SAMPLES")));
			driver.findElement(By.name("Checked Out")).click();
			Thread.sleep(2000);
			
			//assert Sample has been checked out to existing manager
			driver.findElement(By.xpath("//android.widget.TextView[@text='Automation Sample']"));
			Thread.sleep(2000);
		//	driver.findElement(By.xpath("//android.widget.TextView[@text='TestManager Mng']"));
			log.logInfo(bw, "Sample assigned to Manager");
			
		} catch (Exception e) {
			e.printStackTrace();
			log.logException(bw, "An exception was thrown : " + e);
		} catch (Throwable t) {
			log.logError(bw, "An error occured : " + t);
		} finally {
			if (bw != null) {
				try {
					bw.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	
}
