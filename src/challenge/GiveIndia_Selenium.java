package challenge;

import org.openqa.selenium.WebDriver;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.imageio.ImageIO;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.Point;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;


//this is the challenge program given for an interview

public class GiveIndia_Selenium {
	
		WebDriver driver;
		
		@BeforeTest
		public void OpenBrowser() {
		System.setProperty("webdriver.chrome.driver", "C:\\Users\\malli\\Downloads\\chromedriver_win32\\chromedriver.exe");
		driver = new ChromeDriver();
		driver.manage().window().maximize();
		driver.manage().deleteAllCookies();
		driver.manage().timeouts().pageLoadTimeout(20,TimeUnit.SECONDS);
		driver.manage().timeouts().implicitlyWait(20,TimeUnit.SECONDS);
		}
		
		@AfterTest
		public void TearDown() {
			driver.close();
			}
		@Test(priority = 0)
			void OpenWikiPage() {
			System.out.println("WIKIPAGE");
			//driver.get("https://en.wikipedia.org/wiki/Selenium");
		}
		
		@Test(priority = 1)
	     void VerifyExternalLinks() throws MalformedURLException, IOException {
			  driver.get("https://en.wikipedia.org/wiki/Selenium");
				List<WebElement> Elinks = driver.findElements(By.xpath("//ul/li/a[@class='external text'][@rel='nofollow']"));
				System.out.println(Elinks.size());
				for(int i=0; i<Elinks.size(); i++)
				{
					try
					{
						System.out.println(Elinks.get(i).getText());
					     Elinks.get(i).click();
					}
					catch(StaleElementReferenceException e)
					{
						 Elinks = driver.findElements(By.xpath("//ul/li/a[@class='external text'][@rel='nofollow']"));
						 System.out.println(Elinks.get(i).getText());
					     Elinks.get(i).click();	
					}
					String PageTitle = driver.getTitle();
					if(PageTitle.contains("error"))
						System.out.println("it is not working");
					else
						System.out.println("it is working");
					driver.navigate().back();
				}
		}


		//7. no of PDF links
		@Test(priority = 6)
		void NumberOfPDFLinks() {
			List<WebElement> references= driver.findElements(By.xpath("//ol/li[contains(@id,'cite_note')]"));
		    System.out.println(references.size());
		    
		    List <WebElement> PDFlinks= new ArrayList();
		    
		    for(int j=0; j<references.size(); j++)
		    {
		    	//System.out.println(references.get(j).getText());
		 	if(references.get(j).getText().contains("PDF"))
		   	PDFlinks.add(references.get(j));
		    }
		System.out.println("the number of PDF Links is :"+PDFlinks.size());

		for(int p=0;p<PDFlinks.size(); p++)
		{
			System.out.println(PDFlinks.get(p).getText());
		}
			}
		//3.click on the oxygen link in the periodic table
		@Test(priority = 2)
		void ClickOnOxgyen() throws InterruptedException {
			driver.findElement(By.xpath("//a[@title='Oxygen'][1]")).click();
			Thread.sleep(5000);
		}
		
		//4.verify whether it is a featured article
		@Test(priority = 3)
	      void VerifyFeaturedArticle() {
		  if(driver.findElement(By.xpath("//a[contains(@title,'featured')]")).isDisplayed()) 
		  System.out.println("it is featured article");
	      else
		  System.out.println("it is not a featured article");
		}
		//5.Take Screenshot
		@Test(priority = 4)
		void ScreenshotOfTheRightHandBox() throws IOException {
			 TakesScreenshot screenshot = (TakesScreenshot)driver;
			  WebElement box = driver.findElement(By.className("infobox")); 
			  File SS = screenshot.getScreenshotAs(OutputType.FILE);
			  Point p = box.getLocation();
			  int width = box.getSize().getWidth(); 
			  System.out.println(width); 
			  int height = box.getSize().getHeight();
			  System.out.println(height); 
			  BufferedImage img =ImageIO.read(SS); 
			  BufferedImage dest = img.getSubimage(p.getX(), p.getY(),width, height-1600);
			  ImageIO.write(dest, "png", SS); FileUtils.copyFile(SS,new File("C:\\Users\\malli\\OneDrive\\Desktop\\Screenshot.jpeg"));
		}
		
		//6.Verify second auto suggestion
		@Test(priority = 5)
		void VerifyPlutonium() {
			driver.get("https://en.wikipedia.org/wiki/Oxygen");
			driver.findElement(By.name("search")).sendKeys("pluto"); 
		    WebElement suggestion =driver.findElement(By.xpath("//div[@class='suggestions-results']/a[2]"));
			    if(suggestion.getAttribute("title").equalsIgnoreCase("plutonium")) 	
		    	System.out.println("2nd element is plutonium");
		        else
		    	System.out.println("is not plutonium");
			  }

		}
		
		




