package Scraping;

import java.util.ArrayList;
import java.util.List;
import org.bson.Document;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import io.github.bonigarcia.wdm.WebDriverManager;

public class scrape_learning {

	public static WebDriver driver;
	public static JavascriptExecutor js;
	MongoCollection<Document> webCollection;// To Hold collections

	@BeforeSuite
	// Method to connect mongodb and creating collection
	public void connectmongodb() {
		//creating a client
		MongoClient mongoClient = MongoClients.create("mongodb://127.0.0.1:27017");
		//creating database in mongodb
		MongoDatabase database = mongoClient.getDatabase("Teststorage");
		//create collection
		webCollection = database.getCollection("Scrapetest");
	}

	@BeforeTest
	public void setup() {
		WebDriverManager.chromedriver().setup();
		driver = new ChromeDriver();
		driver.manage().window().maximize();
		driver.manage().deleteAllCookies();
		js = (JavascriptExecutor) driver;

	}

	@Test
	public void webscrapetest() {
		driver.get("https://books.toscrape.com/");

		String url = driver.getCurrentUrl();
		System.out.println("url : " + url);

		String title = driver.getTitle();
		System.out.println("Title : " + title);
		
		int linksCount = driver.findElements(By.tagName("1")).size();
		System.out.println("linkscount : " + linksCount);

		int imageCount = driver.findElements(By.tagName("img")).size();
		System.out.println("imagescount : " + imageCount);

		List<WebElement> linkslist = driver.findElements(By.tagName("a"));
		List<String> linkAttrlist = new ArrayList<>();

		List<WebElement> imagelist = driver.findElements(By.tagName("img"));
		List<String> Imagesrclist = new ArrayList<>();

		Document d1 = new Document();// Creating document to store in collection
		d1.append("url", url);
		d1.append("title", title);
		d1.append("totalLinks", linksCount);
		d1.append("totalImages", imageCount);


		for (WebElement ele  : linkslist) {
			String hrefvalue = ele.getAttribute("href");
			linkAttrlist.add(hrefvalue);
			System.out.println("href : " + hrefvalue);
		}

		for (WebElement ele : imagelist) {
			String srcValue = ele.getAttribute("src");
			Imagesrclist.add(srcValue);
			System.out.println("src : " + srcValue);
		}

		d1.append("linksAttribute", linkAttrlist);
		d1.append("SrcValue", Imagesrclist);

		List<Document> docsList = new ArrayList<>();//Holding the documents created
		docsList.add(d1);

		webCollection.insertMany(docsList);// Adding the documents holded to the Collection
	}
}
