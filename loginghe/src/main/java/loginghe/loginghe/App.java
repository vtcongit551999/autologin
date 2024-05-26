package loginghe.loginghe;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.Duration;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;

public class App {
	public static void main(String[] args) {
		
		WebDriver driver = null;
		ChromeOptions chromeOptions = null;
		EdgeOptions edgeOptions = null;
		FirefoxOptions firefoxOptions = null;

		
		Information infor = getInformationFromKey();
		
		// check operation system
		String browserName = System.getProperty("os.name").toLowerCase();
		if (browserName.contains("win")) {
			// check default browser on windows
			String defaultBrowser = infor.getBrowserDefault().toLowerCase();
			switch (defaultBrowser) {
			case "chrome":
				System.setProperty("webdriver.chrome.driver", "lib/chromedriver-win64/chromedriver.exe");
				chromeOptions = new ChromeOptions();
				chromeOptions.addArguments("--start-maximized");
				driver = new ChromeDriver(chromeOptions);
				break;
			case "firefox":
            	firefoxOptions= new FirefoxOptions();
            	firefoxOptions.addArguments("--start-maximized");
            	driver = new FirefoxDriver(firefoxOptions);
				break;
			case "microsoftedge":
			case "edge":
				System.setProperty("webdriver.edge.driver", "lib/edgedriver_win64/msedgedriver.exe");
            	edgeOptions = new EdgeOptions();
            	edgeOptions.addArguments("--start-maximized");
            	driver = new EdgeDriver(edgeOptions);
				break;
			default:
				return;
			}
		}
			
		System.out.println(" ============ Starting Session Management Tests ===============\n\n");
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(20));

        String URL = infor.getUrl();
        driver.navigate().to(URL);  // url website  need login
        
      driver.findElement(By.xpath("//a[@class='nav-v2-dynamic__login' and @href='/session/new']")).click(); // Click on Log In button
      

      driver.findElement(By.cssSelector("input[name='login']")).sendKeys(infor.getUsername()); // Enter Username
      driver.findElement(By.cssSelector("input[name='password']")).sendKeys(infor.getPassword()); // Enter Password
      driver.findElement(By.xpath("//form/input[@type='submit' and @value='Sign In']")).click(); // Click Login Button

      System.out.println(" Login Successful ...");
      
      driver.quit();
	
	}
	
	private static Information getInformationFromKey() {
		Information infor = new Information(); 
		String filePath = "config/keyconfig.txt";
		try (Stream<String> lines = Files.lines(Paths.get(filePath))) {
			List<String> fileLines = lines.collect(Collectors.toList());
			infor.setBrowserDefault(fileLines.get(0).substring(fileLines.get(0).indexOf(":")+1));
			infor.setUrl(fileLines.get(1).substring(fileLines.get(1).indexOf(":")+1));
			infor.setUsername(fileLines.get(2).substring(fileLines.get(2).indexOf(":")+1));
			infor.setPassword(fileLines.get(3).substring(fileLines.get(3).indexOf(":")+1));
		} catch (IOException e) {
			System.out.println("An error occurred while reading the file.");
		}
		return infor;
	}
	
	
	
}
