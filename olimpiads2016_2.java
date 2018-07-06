package com.Olimpiads2016;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.TreeSet;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import io.github.bonigarcia.wdm.WebDriverManager;

public class olimpiads2016_2 {

	WebDriver driver;

	@BeforeClass
	public void setup() {

		WebDriverManager.chromedriver().setup();
		driver = new ChromeDriver();
		driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
		driver.manage().window().maximize();
	}

	@AfterClass
	public void down() throws InterruptedException {
		Thread.sleep(2000);
		driver.close();
	}

	@Test (priority=1)
	public void sortTest() {

		// 1
		driver.get("https://en.wikipedia.org/wiki/2016_Summer_Olympics#Medal_table");
		List<WebElement> tableContents = driver
				.findElements(By.xpath("//*[@id=\"mw-content-text\"]/div/table[8]/tbody/tr/td[1]"));


		// 2
		isIntSorted(tableContents, " Rank before Click NOC", 1);


		// 3
		driver.findElement(By.xpath("//th[@scope='col'][contains(text(),'NOC')]")).click();

		// 4
		tableContents = driver
				.findElements(By.xpath("//*[@id=\"mw-content-text\"]/div/table[8]/tbody/tr/th/a"));
		isStringSorted(tableContents, " Country After Click NOC");

		// 5
		tableContents = driver
				.findElements(By.xpath("//*[@id=\"mw-content-text\"]/div/table[8]/tbody/tr/td[1]"));
		isIntSorted(tableContents, " Rank After Click NOC", 0);





	}
	
	
	@Test(priority=2)
	public void theMost() {
		int column = 3;
		
		//Gold Test
		String [] gold = returnTheMosts(column);
		Assert.assertEquals(gold[0].trim(), "United States");
		Assert.assertEquals(gold[1].trim(), "46");
		
		//Silver Test
		String [] silver = returnTheMosts(++column);
		Assert.assertEquals(silver[0].trim(), "United States");
		Assert.assertEquals(silver[1].trim(), "37");
		
		//Bronze Test
		String [] bronze = returnTheMosts(++column);
		Assert.assertEquals(bronze[0].trim(), "United States");
		Assert.assertEquals(bronze[1].trim(), "38");
		
		// total medal
		String [] total = returnTheMosts(++column);
		Assert.assertEquals(total[0].trim(), "United States");
		Assert.assertEquals(total[1].trim(), "121");
		
	}
	
	@Test(priority=3)
	public void equalToMedalNumberTest() {
		
		int silver = 3;
		
		driver.findElement(By.xpath("//*[@id=\"mw-content-text\"]/div/table[8]/tfoot/tr[1]/th/i/a")).click();
		driver.findElement(By.xpath("//*[@id=\"mw-content-text\"]/div/table[2]/thead/tr/th[1]")).click();
		
		TreeSet<String> countries = getNumberOfMedalEquality(silver,18);
		// //*[@id="mw-content-text"]/div/table[2]/tbody/tr[1]/td[3]
		// //*[@id="mw-content-text"]/div/table[2]/tbody/tr[1]/th/a
		
		TreeSet <String> verification = new TreeSet<String>();
		verification.add("China"); 
		verification.add("France");
		Assert.assertEquals(countries, verification);
		//Assert.assertEquals(countries, new TreeSet<String>(Arrays.asList(new String[] {"China", "France"}))); 
	}
	
	@Test(priority=4)
	public void columnNthRowFromName() {
		//*[@id="mw-content-text"]/div/table[2]/tbody/tr[6]/th/a
		
		String countryName = "Japan";
		String rowColumn = getRowColumn(countryName);
		Assert.assertEquals(rowColumn, "Row: 6 Column: 2");
		
		
		countryName = "hungary";
		rowColumn = getRowColumn(countryName);
		Assert.assertEquals(rowColumn, "Row: 12 Column: 2");
	}
	
	
	@Test(priority=5)
	public void getSumSearch() {
		
		int silver = 3;
		int number = 18;
		TreeSet<PairCountry> results = getSum(silver, number);
		for(PairCountry p: results) {
			System.out.println(p.getP1() + ": " + p.getP2());
		}
		
		Assert.assertTrue(results.contains(new PairCountry("France", "Portugal")));
		
	}
	
	private TreeSet<PairCountry> getSum(int medalType, int number) { //O(2*n = n)
		HashMap<Integer, ArrayList<String>> mapCountries = new HashMap<Integer, ArrayList<String>>();
		List<WebElement> tableCountiries = driver.findElements(By.xpath("//*[@id=\"mw-content-text\"]/div/table[2]/tbody/tr/th/a"));
		List<WebElement> tableMedals = driver.findElements(By.xpath("//*[@id=\"mw-content-text\"]/div/table[2]/tbody/tr/td[" + medalType + "]"));
		for (int i = 0; i < tableMedals.size(); i++) {
			if(mapCountries.containsKey(Integer.parseInt(tableMedals.get(i).getText().trim()))){
				mapCountries.get(Integer.parseInt(tableMedals.get(i).getText().trim())).add(tableCountiries.get(i).getText().trim());
			}else {
				ArrayList<String> temp = new ArrayList<String>();
				temp.add(tableCountiries.get(i).getText().trim());
				mapCountries.put(Integer.parseInt(tableMedals.get(i).getText().trim()), temp);
			}
		}
		
		
		
		TreeSet<PairCountry> toReturn  =  new TreeSet<PairCountry>();
		for (int i = 0; i < tableMedals.size(); i++) {
			int key = number - Integer.parseInt(tableMedals.get(i).getText().trim());
			if(key >= 0  && mapCountries.containsKey(key)){
				ArrayList<String> temp = mapCountries.get(key);
				for (String country: temp) {
					if (!tableCountiries.get(i).getText().trim().equals(country)) {
						//System.out.println(tableCountiries.get(i).getText().trim() + " " + country);
						PairCountry p  = new PairCountry(tableCountiries.get(i).getText().trim(), country);
						//if (!toReturn.contains(p))
							//System.out.println(p.p1 + " " + p.p2);
							//System.out.println(toReturn.contains(p));
							toReturn.add(p);
					}
				}
			}
		}
		
		return toReturn;
	
	}
	//====================================================================================================================================//
	
	private String getRowColumn(String countryName) {
		List<WebElement> tableCountiries = driver.findElements(By.xpath("//*[@id=\"mw-content-text\"]/div/table[2]/tbody/tr/th/a"));
		String toReturn = " Column: 2";
		for (int i = 0; i < tableCountiries.size(); i++) {
			if(tableCountiries.get(i).getText().trim().equalsIgnoreCase(countryName)) {
				toReturn = "Row: " + (i+1) + toReturn;
				break;
			}
			
		}
		return toReturn;
	}

	private TreeSet<String> getNumberOfMedalEquality(int medalType, int number) {
		TreeSet<String> toReturn = new TreeSet<String>();
		List<WebElement> tableCountiries = driver.findElements(By.xpath("//*[@id=\"mw-content-text\"]/div/table[2]/tbody/tr/th/a"));
		List<WebElement> tableMedals = driver.findElements(By.xpath("//*[@id=\"mw-content-text\"]/div/table[2]/tbody/tr/td[" + medalType + "]"));
		
		for(int i=0 ; i< tableMedals.size() ; i++) {
			if (tableMedals.get(i).getText().trim().equals(""+number)) {
				toReturn.add(tableCountiries.get(i).getText().trim());
			}
		}
		
		return toReturn;
	}

	private String [] returnTheMosts(int column) {
		driver.findElement(By.xpath("//*[@id=\"mw-content-text\"]/div/table[8]/thead/tr/th[" + column + "]")).click();
		driver.findElement(By.xpath("//*[@id=\"mw-content-text\"]/div/table[8]/thead/tr/th[" + column + "]")).click();
		
		String [] toReturn = new String[2];
		toReturn[0] = driver.findElement(By.xpath("//*[@id=\"mw-content-text\"]/div/table[8]/tbody/tr[1]/th/a")).getText();// country name
		toReturn[1] = driver.findElement(By.xpath("//*[@id=\"mw-content-text\"]/div/table[8]/tbody/tr[1]/td[" + (column - 1) + "]")).getText();
		return toReturn;
	}

	private void isIntSorted(List<WebElement> tableContents, String note, int errorDynamicTable) {
		ArrayList<Integer> test = new ArrayList<Integer>();
		for (int i = 0; i < tableContents.size() - errorDynamicTable; i++) {
			test.add(Integer.parseInt(tableContents.get(i).getText()));

		}

		ArrayList<Integer> sorted = new ArrayList<Integer>(test); 
		Collections.sort(sorted);

		try {
			Assert.assertEquals(test, sorted);
			System.out.println("Sorted: " + note);
		} catch (AssertionError e) {
			System.out.println("Not Sorted: " + note);
		}


	}

	private void isStringSorted(List<WebElement> tableContents, String note) {
		ArrayList<String> test = new ArrayList<String>();
		for (int i = 0; i < tableContents.size() - 1; i++) {
			test.add(tableContents.get(i).getText());
		}

		ArrayList<String> sorted = new ArrayList<String>(test); 
		Collections.sort(sorted);

		try {
			Assert.assertEquals(test, sorted);
			System.out.println("Sorted: " + note);
		} catch (AssertionError e) {
			System.out.println("Not Sorted: " + note);
		}

	}
	
}