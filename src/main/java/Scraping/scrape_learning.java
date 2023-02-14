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

//	@BeforeSuite
//	public void connectmongodb() {
//		MongoClient mongoClient = MongoClients.create("mongodb://127.0.0.1:27017");
//		MongoDatabase database = mongoClient.getDatabase("Screenercheck");
//		webCollection = database.getCollection("Checking");
//	}

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
		driver.get("https://www.screener.in/company/RELIANCE/consolidated/");
		
//		Document d1 = new Document();	
		
		// Market Cap
		List<WebElement> marketcap = driver.findElements(By.xpath("//div[@class='company-ratios']"));

		for (WebElement ele : marketcap) {
			String marketca = ele.getText();
			System.out.println("marketcap : " + marketca);
		}
		
		// PeerComparison
		WebElement PeerComparison = driver.findElement(By.xpath("//*[@id=\"peers-table-placeholder\"]/div[3]/table/tbody"));
		WebElement PeercomparisonMedian = driver.findElement(By.xpath("//*[@id=\"peers-table-placeholder\"]/div[3]/table/tfoot"));
		
		List<WebElement> rows = PeerComparison.findElements(By.tagName("tr"));
		List<WebElement> medianrow = PeercomparisonMedian.findElements(By.tagName("tr"));
		
		for (WebElement ele : rows) {
			String peercomparison = ele.getText();
			System.out.println("Peer Comparison Rows :" + peercomparison);
		}
		for (WebElement ele : medianrow) {
			String median = ele.getText();
			System.out.println("Peer Median : " + median);
		}
		
		// Quarterly Results
		WebElement Quarterlyresults = driver.findElement(By.xpath("//*[@id=\"quarters\"]/div[3]/table/thead"));
		WebElement Quarterlyresults1 = driver.findElement(By.xpath("//*[@id=\"quarters\"]/div[3]/table/tbody"));
		
		List<WebElement> quarreslt = Quarterlyresults.findElements(By.tagName("tr"));
		List<WebElement> quarreslt1 = Quarterlyresults1.findElements(By.tagName("tr"));
		
		for (WebElement ele : quarreslt) {
			String quarterlyheading = ele.getText();
			System.out.println("Quarterly Peers Heading :" + quarterlyheading);
		}
		
		for (WebElement ele : quarreslt1) {
			String quarterlybody = ele.getText();
			System.out.println("Quarterly Peers Body :" + quarterlybody);
		}
		
		
		//Profit and Loss
		WebElement Profitandlossheading = driver.findElement(By.xpath("//*[@id=\"profit-loss\"]/div[3]/table/thead"));
		WebElement Profitandlossbody = driver.findElement(By.xpath("//*[@id=\"profit-loss\"]/div[3]/table/tbody"));
		
		List<WebElement> pandlhead = Profitandlossheading.findElements(By.tagName("tr"));
		List<WebElement> pandlbody = Profitandlossbody.findElements(By.tagName("tr"));
		
		for (WebElement ele : pandlhead) {
			String headpandl = ele.getText();
			System.out.println("Heading of Profit and Loss : " + headpandl);
		}
		
		for (WebElement ele : pandlbody) {
			String pandltable = ele.getText();
			System.out.println("Profit and Loss Table Body : " + pandltable);
		}
		
		//Sales Growth
		
		WebElement salesgrowth = driver.findElement(By.xpath("//*[@id=\"profit-loss\"]/div[4]/table[1]/tbody"));
		List<WebElement> compoundedsalesandgrowth = salesgrowth.findElements(By.tagName("tr"));
		
		for (WebElement ele : compoundedsalesandgrowth) {
			String companysalesgrowth = ele.getText();
			System.out.println("Compunded Sales Growth : " + companysalesgrowth);
		}
		
		// Compounded Profit Growth
		WebElement profitgrowth = driver.findElement(By.xpath("//*[@id=\"profit-loss\"]/div[4]/table[2]/tbody"));
		List<WebElement> compoundedprofitgrowth = profitgrowth.findElements(By.tagName("tr"));
		
		for (WebElement ele : compoundedprofitgrowth) {
			String companyprofitgrowth = ele.getText();
			System.out.println("Compunded Profit Growth : " + companyprofitgrowth);
		}
		
		// Stock Price CAGR
		WebElement Stockprice = driver.findElement(By.xpath("//*[@id=\"profit-loss\"]/div[4]/table[3]/tbody"));
		List<WebElement> StockPriceCAGR = Stockprice.findElements(By.tagName("tr"));
		
		for (WebElement ele : StockPriceCAGR) {
			String SPCAGR = ele.getText();
			System.out.println("Stock Price CAGR : " + SPCAGR);
		}
		
		// Return On Equity
		WebElement Equity = driver.findElement(By.xpath("//*[@id=\"profit-loss\"]/div[4]/table[4]/tbody"));
		List<WebElement> retonequity = Equity.findElements(By.tagName("tr"));
		
		for (WebElement ele : retonequity) {
			String returns = ele.getText();
			System.out.println("Return on Equity : " + returns);
		}
		
		
		// Balance Sheet
		WebElement bsheet = driver.findElement(By.xpath("//*[@id=\"balance-sheet\"]/div[2]/table/thead"));
		WebElement bsheet1 = driver.findElement(By.xpath("//*[@id=\"balance-sheet\"]/div[2]/table/tbody"));
		
		List<WebElement> relbsheet = bsheet.findElements(By.tagName("tr"));
		List<WebElement> relbsheet1 = bsheet1.findElements(By.tagName("tr"));
		
		for (WebElement ele : relbsheet) {
			String relbalancesheet = ele.getText();
			System.out.println("Balancesheet heading : " + relbalancesheet  );
		}
		for (WebElement ele : relbsheet1) {
			String relbalancesheet1 = ele.getText();
			System.out.println("Balancesheet body : " + relbalancesheet1  );
		}
		
		
		
		
//		d1.append("Marketcapattribute", marketcaplist);
//
//		List<Document> docslist = new ArrayList();
//		docslist.add(d1);
//		webCollection.insertMany(docslist);

	}
}
