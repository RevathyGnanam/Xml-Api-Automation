package testBase;

import java.io.*;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;
import java.util.Properties;

import org.apache.commons.lang3.RandomStringUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.*;

public class BaseClass {
	
	static public WebDriver driver;
	public Properties properties;
	
	@BeforeClass(groups= {"Regression","Smoke"})
	@Parameters({"browser"})
	public void launchUrl(String br) throws Exception {
		FileReader file = new FileReader(".//src//test//resources//config.properties");
		properties = new Properties();
		properties.load(file);
		
		switch(br.toLowerCase()) {
		case "chrome": driver = new ChromeDriver(); break;
		case "firefox": driver = new FirefoxDriver();break;
		case "edge": driver = new EdgeDriver();break;
		default: System.err.println("No Browser launched"); return;
		}
		
		driver.manage().deleteAllCookies();
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
		driver.get(properties.getProperty("appUrl"));	
	}
	
	@AfterClass
	public void tearDown() {
		driver.quit();
	}
	
	public String nameGeneration() {
		String names = RandomStringUtils.randomAlphabetic(5);
		return names;
	}
	
	public String emailGeneration() {
		return RandomStringUtils.randomAlphabetic(5)+"$"+RandomStringUtils.randomNumeric(3);
	}

	public String passwordGeneration() {
		return RandomStringUtils.randomAlphabetic(5)+RandomStringUtils.randomNumeric(3);
	}
	
	public String captureScreen(String tname) {
		String timeStamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());

		TakesScreenshot screenShot = (TakesScreenshot)driver;
		File srcFile = screenShot.getScreenshotAs(OutputType.FILE);

		String targetFilePath = System.getProperty("user.dir")+"\\screenshots\\" + tname +"_" + timeStamp;
		File targetFile = new File(targetFilePath);

		srcFile.renameTo(targetFile);

		return targetFilePath;



		}


}
