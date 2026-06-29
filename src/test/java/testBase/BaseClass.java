package testBase;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URI;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;
import java.util.Properties;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;

public class BaseClass {
	public WebDriver driver;
	public Logger logger;
	public Properties p;
	@BeforeClass(groups={"Sanity","Regression","Master"})
	@Parameters({"os","browser"})
	public void setup(String os,String br) throws IOException {
		FileReader file = new FileReader("./src/test/resources/config.properties");
		p=new Properties();
		p.load(file);
		logger=LogManager.getLogger(this.getClass());
		if(p.getProperty("execution_env").equalsIgnoreCase("remote")) {
			String platformName = null;
			if(os.equalsIgnoreCase("windows")) {
				platformName="Windows 11";
			}	
			else if(os.equalsIgnoreCase("mac")) {
				platformName="macOS";
			}
			else if(os.equalsIgnoreCase("linux")) {
				platformName="Linux";
			}
			else {
				System.out.println("No matching OS");
				return;
			}
			URI griduri=URI.create("http://10.127.179.119:4444");
			if(br.equalsIgnoreCase("chrome")) {
				ChromeOptions options=new ChromeOptions();
				options.setPlatformName(platformName);
				driver=new RemoteWebDriver(griduri.toURL(),options);
			}
			else if(br.equalsIgnoreCase("edge")) {
				EdgeOptions options=new EdgeOptions();
				options.setPlatformName(platformName);
				driver=new RemoteWebDriver(griduri.toURL(),options);
			}
			else if(br.equalsIgnoreCase("firefox")) {
				FirefoxOptions options=new FirefoxOptions();
				options.setPlatformName(platformName);
				driver=new RemoteWebDriver(griduri.toURL(),options);
			}
			else {
				System.out.println("No matching browser");
				return;
			}
			
		}
		if(p.getProperty("execution_env").equalsIgnoreCase("local")) {
			switch(br.toLowerCase()) {  
			case "chrome":
				driver=new ChromeDriver();
				break;
			case "edge":
				driver=new EdgeDriver();
				break;
			case "firefox":
				driver=new FirefoxDriver(); 
				break;
			default:
				System.out.println("Invalid Browser");
				return;
			}
		}
		driver.manage().deleteAllCookies();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
		driver.get(p.getProperty("appURL"));
		driver.manage().window().maximize();
	}
	
	@AfterClass(groups={"Sanity","Regression","Master"})
	public void teardown() {
		driver.quit();
	}
	@SuppressWarnings("deprecation")
	public String randomString() {
		String generatedString=RandomStringUtils.randomAlphabetic(5);
		return generatedString;
	}
	@SuppressWarnings("deprecation")
	public String randomNumber() {
		String generatedString=RandomStringUtils.randomNumeric(10);
		return generatedString;
	}
	@SuppressWarnings("deprecation")
	public String randomAlphaNumeric() {
		String str=RandomStringUtils.randomAlphabetic(3);
		String num=RandomStringUtils.randomNumeric(3);
		return (str+"@"+num);
	} 
	public String captureScreen(String tname) {
		String timeStamp=new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
		TakesScreenshot takesScreenshot=(TakesScreenshot) driver;
		File sourcefile=takesScreenshot.getScreenshotAs(OutputType.FILE);
		String targetfilepath=System.getProperty("user.dir")+"/screenshots/"+tname+"_"+timeStamp+".png";
		File targetfile=new File(targetfilepath);
		sourcefile.renameTo(targetfile);
		return targetfilepath;
	}
	

	
}
