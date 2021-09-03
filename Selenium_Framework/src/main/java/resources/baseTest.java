package resources;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
//import org.testng.annotations.Parameters;

import com.aventstack.extentreports.ExtentTest;


import utils.utilsTest;

public class baseTest 
{
	public static WebDriver driver =null;
	public static ExtentTest logger;
	public static Properties properties;
	

	@BeforeTest
	public void beforeTestMethod() {
	}

	@BeforeMethod
	//@Parameters(value= {"browserName"})
	public void beforeMethodMethod(Method testMethod)
	{
		setUpDriver();
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(utilsTest.timeout, TimeUnit.SECONDS);
		driver.manage().timeouts().pageLoadTimeout(utilsTest.timeout, TimeUnit.SECONDS);
		
		String url = System.getProperty("url");
		driver.get(url);
	}

	@AfterMethod
	public void afterMethodMethod() {
		
		driver.quit();
	}

	@AfterTest
	public void afterTestMethod() {

	}
	
	//constructor
	public baseTest() 
	{
		try
		{
			properties= new Properties();
			FileInputStream file=new FileInputStream(System.getProperty("user.dir")
					+ "\\src\\main\\java\\resources\\config.properties");
			properties.load(file);
		}
		catch(FileNotFoundException e)
		{
			e.printStackTrace();
		}
		 catch(IOException e)
		{
			 e.printStackTrace();
		}	
	}
	
	public void setUpDriver() 
	{
		String browserName=properties.getProperty("browserName");
		
		if(browserName.equalsIgnoreCase("chrome"))
		{
			//WebDriverManager.chromedriver().setup();
			System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir") + File.separator +"drivers" + File.separator + "chromedriver.exe");
			driver = new ChromeDriver();
		}
		else if (browserName.equalsIgnoreCase("mozilla"))
		{
			//WebDriverManager.firefoxdriver().setup();
			System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir") + File.separator +"drivers" + File.separator + "geckodriver.exe");
			driver = new FirefoxDriver();
		}
		else
		{
			//WebDriverManager.edgedriver().setup();
			System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir") + File.separator +"drivers" + File.separator + "msedgedriver.exe");
			driver = new EdgeDriver();
		}
	}
}
