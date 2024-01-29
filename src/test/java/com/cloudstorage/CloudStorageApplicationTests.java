package com.cloudstorage;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.JavascriptExecutor;
import java.util.List;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class CloudStorageApplicationTests {

	private static String firstname = "firstname";
	private static String lastname = "lastname";
	private static String username = "user";
	private static String password = "pass";

	private static String noteTitle = "noteTest";
	private static String noteDescription = "noteDescriptionTest";

	private static String credURL = "test.com";


	@LocalServerPort
	private int port;

	private WebDriver driver;

	@BeforeAll
	static void beforeAll() {
		WebDriverManager.chromedriver().setup();
	}

	@BeforeEach
	public void beforeEach() {
		this.driver = new ChromeDriver();
	}

	@AfterEach
	public void afterEach() {
		if (this.driver != null) {
			driver.quit();
		}
	}

	@Test
	public void runTests() {
		getLoginPage();
		getSignupPage();
		getUnauthorizedAccess();
		getUnauthorizedResult();
		loginFlow();
		noteCreateTest();
		noteUpdateTest();
		noteDeleteTest();
		credCreateTest();
		credUpdateTest();
		credDeleteTest();
	}

	public void getLoginPage() {
		System.out.println("Testing Login Page!");
		driver.get("http://localhost:" + this.port + "/login");
		Assertions.assertEquals("Login", driver.getTitle());
		System.out.println("Valid!");
	}


	public void getSignupPage() {
		System.out.println("Testing Sign-Up Page!");
		driver.get("http://localhost:" + this.port + "/signup");
		Assertions.assertEquals("Sign Up", driver.getTitle());
		System.out.println("Valid!");
	}

	public void getUnauthorizedAccess() {
		System.out.println("Testing Unauthorized Access Attempt!");
		driver.get("http://localhost:" + this.port + "/home");
		Assertions.assertEquals("Login", driver.getTitle());
		System.out.println("Valid!");
	}

	public void getUnauthorizedResult() {
		System.out.println("Testing Unauthorized Results Attempt!");
		driver.get("http://localhost:" + this.port + "/result");
		Assertions.assertEquals("Login", driver.getTitle());
		System.out.println("Valid!");
	}

	public void loginFlow() {
		System.out.println("Testing Login Flow!");
		WebDriverWait wait = new WebDriverWait (driver, 15);
		userSignup();
		userLogin();
		driver.get("http://localhost:" + this.port + "/home");
		WebElement logoutButton = driver.findElement(By.id("logout"));
		logoutButton.click();
		wait.until(ExpectedConditions.titleContains("Login"));
		Assertions.assertEquals("Login", driver.getTitle());
		System.out.println("Valid!\nRetesting UnauthorizedAccess after Logout!");
		getUnauthorizedAccess();
	}

	private void userSignup() {
		driver.get("http://localhost:" + this.port + "/signup");
		WebElement inputFirstName = driver.findElement(By.id("inputFirstName"));
		inputFirstName.sendKeys(firstname);
		WebElement inputLastName = driver.findElement(By.id("inputLastName"));
		inputLastName.sendKeys(lastname);
		WebElement inputUsername = driver.findElement(By.id("inputUsername"));
		inputUsername.sendKeys(username);
		WebElement inputPassword = driver.findElement(By.id("inputPassword"));
		inputPassword.sendKeys(password);
		WebElement signUpButton = driver.findElement(By.id("signup"));
		signUpButton.click();
		Assertions.assertEquals("Sign Up", driver.getTitle());
		WebElement toLogin = driver.findElement(By.id("return"));
		toLogin.click();
	}

	private void userLogin() {
		WebDriverWait wait = new WebDriverWait (driver, 50);
		driver.get("http://localhost:" + this.port + "/login");
		WebElement inputUsername = driver.findElement(By.id("inputUsername"));
		inputUsername.sendKeys(username);
		WebElement inputPassword = driver.findElement(By.id("inputPassword"));
		inputPassword.sendKeys(password);
		WebElement loginButton = driver.findElement(By.id("login"));
		loginButton.click();
		wait.until(ExpectedConditions.titleContains("Home"));
		Assertions.assertEquals("Home", driver.getTitle());
	}


	public void noteCreateTest() {
		System.out.println("Testing Note Creation");
		WebDriverWait wait = new WebDriverWait (driver, 10);
		userLogin();
		driver.get("http://localhost:" + this.port + "/home");
		JavascriptExecutor exe = (JavascriptExecutor) driver;
		WebElement notesTab = driver.findElement(By.id("nav-notes-tab"));
		exe.executeScript("arguments[0].click()", notesTab);
		WebElement newNote = driver.findElement(By.id("addNote"));
		wait.until(ExpectedConditions.elementToBeClickable(newNote)).click();
		wait.until(ExpectedConditions.elementToBeClickable(By.id("note-title"))).sendKeys(noteTitle);
		WebElement nDesc = driver.findElement(By.id("note-description"));
		nDesc.sendKeys(noteDescription);
		WebElement saveChanges = driver.findElement(By.id("save-note"));
		saveChanges.click();
		Assertions.assertEquals("Result", driver.getTitle());
		driver.get("http://localhost:" + this.port + "/home");
		exe = (JavascriptExecutor) driver;
		notesTab = driver.findElement(By.id("nav-notes-tab"));
		exe.executeScript("arguments[0].click()", notesTab);
		WebElement notesTable = driver.findElement(By.id("userTable"));
		List<WebElement> notesList = notesTable.findElements(By.tagName("th"));
		boolean validNote = false;
		for(WebElement e: notesList) {
			if(e.getAttribute("innerHTML").equals(noteTitle)){
				validNote = true;
				break;
			}

		}
		Assertions.assertTrue(validNote);
		System.out.println("Valid!");
	}

	public void noteUpdateTest() {
		System.out.println("Testing Note Updating!");
		userLogin();
		driver.get("http://localhost:" + this.port + "/home");
		WebDriverWait wait = new WebDriverWait (driver, 10);
		JavascriptExecutor exe = (JavascriptExecutor) driver;
		String newNoteTitle = "notetest2";
		WebElement notesTab = driver.findElement(By.id("nav-notes-tab"));
		exe.executeScript("arguments[0].click()", notesTab);
		WebElement notesTable = driver.findElement(By.id("userTable"));
		List<WebElement> notesList = notesTable.findElements(By.tagName("td"));
		WebElement edit = driver.findElement(By.id("noteEdit"));
		wait.until(ExpectedConditions.elementToBeClickable(edit)).click();
		WebElement currTitle = driver.findElement(By.id("note-title"));
		wait.until(ExpectedConditions.elementToBeClickable(currTitle));
		currTitle.clear();
		currTitle.sendKeys(newNoteTitle);
		WebElement saveChanges = driver.findElement(By.id("save-note"));
		saveChanges.click();
		Assertions.assertEquals("Result", driver.getTitle());
		driver.get("http://localhost:" + this.port + "/home");
		notesTab = driver.findElement(By.id("nav-notes-tab"));
		exe.executeScript("arguments[0].click()", notesTab);
		notesTable = driver.findElement(By.id("userTable"));
		notesList = notesTable.findElements(By.tagName("th"));
		boolean wasEdited = false;
		for(WebElement e: notesList) {
			if (e.getAttribute("innerHTML").equals(newNoteTitle)) {
				wasEdited = true;
				break;
			}
		}
		Assertions.assertTrue(wasEdited);
		System.out.println("Valid!");

	}

	public void noteDeleteTest() {
		System.out.println("Testing Note Deletion!");
		userLogin();
		WebDriverWait wait = new WebDriverWait (driver, 10);
		JavascriptExecutor exe = (JavascriptExecutor) driver;
		WebElement notesTab = driver.findElement(By.id("nav-notes-tab"));
		exe.executeScript("arguments[0].click()", notesTab);
		WebElement notesTable = driver.findElement(By.id("userTable"));
		WebElement deleteNote = notesTable.findElement(By.id("noteDelete"));
		wait.until(ExpectedConditions.elementToBeClickable(deleteNote)).click();
		Assertions.assertEquals("Result", driver.getTitle());
		System.out.println("Valid!");
	}

	public void credCreateTest() {
		System.out.println("Testing Credential Creation!");
		WebDriverWait wait = new WebDriverWait (driver, 10);
		userLogin();
		JavascriptExecutor exe =(JavascriptExecutor) driver;
		WebElement credTab = driver.findElement(By.id("nav-credentials-tab"));
		exe.executeScript("arguments[0].click()", credTab);
		WebElement newCred = driver.findElement(By.id("addCred"));
		wait.until(ExpectedConditions.elementToBeClickable(newCred)).click();
		wait.until(ExpectedConditions.elementToBeClickable(By.id("credential-url"))).sendKeys(credURL);
		WebElement credUser = driver.findElement(By.id("credential-username"));
		credUser.sendKeys(username);
		WebElement credPass = driver.findElement(By.id("credential-password"));
		credPass.sendKeys(password);
		WebElement submit = driver.findElement(By.id("save-creds"));
		submit.click();
		Assertions.assertEquals("Result", driver.getTitle());
		driver.get("http://localhost:" + this.port + "/home");
		credTab = driver.findElement(By.id("nav-credentials-tab"));
		exe.executeScript("arguments[0].click()", credTab);
		WebElement credsTable = driver.findElement(By.id("credentialTable"));
		List<WebElement> credsList = credsTable.findElements(By.tagName("th"));
		boolean valid = false;
		for(WebElement e: credsList) {
			if (e.getAttribute("innerHTML").equals(credURL)) {
				valid = true;
				break;
			}
		}
		Assertions.assertTrue(valid);
		System.out.println("Valid!");
	}

	public void credUpdateTest() {
		System.out.println("Testing Credential Updating!");
			WebDriverWait wait = new WebDriverWait(driver, 10);
			userLogin();
			JavascriptExecutor exe = (JavascriptExecutor) driver;
			String newCredUser = "user2";
			WebElement credTab = driver.findElement(By.id("nav-credentials-tab"));
			exe.executeScript("arguments[0].click()", credTab);
			WebElement credsTable = driver.findElement(By.id("credentialTable"));
			List<WebElement> credsList = credsTable.findElements(By.tagName("td"));
			WebElement edit = driver.findElement(By.name("credEdit"));
			wait.until(ExpectedConditions.elementToBeClickable(edit)).click();
			WebElement credUser = driver.findElement(By.id("credential-username"));
			wait.until(ExpectedConditions.elementToBeClickable(credUser));
			credUser.clear();
			credUser.sendKeys(newCredUser);
			WebElement submit = driver.findElement(By.id("save-creds"));
			submit.click();
			driver.get("http://localhost:" + this.port + "/home");
			credTab = driver.findElement(By.id("nav-credentials-tab"));
			exe.executeScript("arguments[0].click()", credTab);
			credsTable = driver.findElement(By.id("credentialTable"));
			credsList = credsTable.findElements(By.id("credUser"));
			boolean valid = false;
			for (WebElement e : credsList) {
				if (e.getAttribute("innerHTML").equals(newCredUser)) {
					valid = true;
					break;
				}
			}
		Assertions.assertTrue(valid);
		System.out.println("Valid!");
		}

	public void credDeleteTest() {
		System.out.println("Testing Credential Deletion!");
		WebDriverWait wait = new WebDriverWait (driver, 10);
		userLogin();
		JavascriptExecutor exe =(JavascriptExecutor) driver;
		WebElement credTab = driver.findElement(By.id("nav-credentials-tab"));
		exe.executeScript("arguments[0].click()", credTab);
		WebElement credsTable = driver.findElement(By.id("credentialTable"));
		WebElement deleteCred = credsTable.findElement(By.id("credDelete"));
		wait.until(ExpectedConditions.elementToBeClickable(deleteCred)).click();
		Assertions.assertEquals("Result", driver.getTitle());
		System.out.println("Valid!");
	}
}