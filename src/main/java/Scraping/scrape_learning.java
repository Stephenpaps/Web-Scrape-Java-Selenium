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
	MongoCollection<Document> webCollection;

	@BeforeSuite
	public void connectmongodb() {
		MongoClient mongoClient = MongoClients.create("mongodb://127.0.0.1:27017");

		MongoDatabase database = mongoClient.getDatabase("Scrape-test");

		webCollection = database.getCollection("web");
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
		driver.get("https://screener.in");

		String url = driver.getCurrentUrl();
		System.out.println("url :" + url);

		String title = driver.getTitle();
		System.out.println("title :" + title);

		int linkscount = driver.findElements(By.tagName("a")).size();
		System.out.println("linkscount :" + linkscount);

		int imagecount = driver.findElements(By.tagName("img")).size();
		System.out.println("imagecount :" + imagecount);

		List<WebElement> linkslist = driver.findElements(By.tagName("a"));
		List<String> linksAttrlist = new ArrayList();

		List<WebElement> imageslist = driver.findElements(By.tagName("img"));
		List<String> imagesrclist = new ArrayList();

		Document d1 = new Document();
		d1.append("url", url);
		d1.append("title", title);
		d1.append("totalLinks", linkscount);
		d1.append("totalImages", imagecount);

		for (WebElement ele : linkslist) {
			String hrefvalue = ele.getAttribute("href");
			linksAttrlist.add(hrefvalue);
			System.out.println("hrefvalue :" + hrefvalue);
		}

		for (WebElement ele : imageslist) {
			String srcvalue = ele.getAttribute("src");
			imagesrclist.add(srcvalue);
			System.out.println("srcvalue :" + srcvalue);
		}

		d1.append("linksAttribute", linksAttrlist);
		d1.append("SrcValue", imagesrclist);

		List<Document> docslist = new ArrayList();
		docslist.add(d1);

		webCollection.insertMany(docslist);

	}
}
