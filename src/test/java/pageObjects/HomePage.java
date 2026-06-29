package pageObjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class HomePage extends BasePage {
	public HomePage(WebDriver driver){
		super(driver);
	}
	
	@FindBy(xpath="//*[@id=\"top-links\"]/ul/li[2]/a/span[1]") WebElement lnkMyaccount;
	@FindBy(xpath="//*[@id=\"top-links\"]/ul/li[2]/ul/li[1]/a") WebElement lnkRegister; 
	@FindBy(xpath="//*[@id=\"top-links\"]/ul/li[2]/ul/li[2]/a") WebElement lnkLogin;
	
	public void clickMyAccount() {
		lnkMyaccount.click();
	} 
	public void clickRegister() {
		lnkRegister.click();
	}
	public void clickLogin() {
		lnkLogin.click();
	}
	
}
