package practiceGit;

import java.io.FileInputStream;
import java.time.Duration;
import java.util.List;
import java.util.Properties;
import java.util.Map.Entry;

import org.apache.commons.collections4.map.HashedMap;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;

public class Home_Test {

	
	public static void main(String[] args) throws Throwable {
		String Title = "Admin Login";
		//Reading Data From Property File
		FileInputStream fis = new FileInputStream(".\\src\\test\\resources\\Login_common_Data.properties");
		Properties pObj = new Properties();
		pObj.load(fis);
		//getting the Value From Property Key 
		String ADMINURL = pObj.getProperty("adminurl");
		String ADMINUSERNAME = pObj.getProperty("adminusername");
		String ADMINPASSWORD = pObj.getProperty("adminpassword");
		System.out.println(ADMINURL + "   " + ADMINUSERNAME + "  " + ADMINPASSWORD);
		//Launchung Chrome Driver 
		WebDriver driver = new ChromeDriver();
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(30));
		//Passing URL
		driver.get(ADMINURL);
		//Passing The Valid Values for Login
		driver.findElement(By.name("username")).sendKeys(ADMINUSERNAME);
		driver.findElement(By.name("password")).sendKeys(ADMINPASSWORD);
		driver.findElement(By.name("submit")).click();
		//Getting the Home Page Title
		String Actual = driver.getTitle();
		// validating the Login
		System.out.println(Actual);
		//Validating the Home Page
		if (Title.equals(Actual)) {

			System.out.println("Admin Login Successfull");
		}

		else {
			System.err.println("Admin Login Failed");
		}
		
		//Click on Restaurant OPtions
		driver.findElement(By.xpath("//span[.='Restaurant']")).click();
		driver.findElement(By.xpath("//a[.='Add Restaurant']")).click();
		//Reading The Values from Excel File
		FileInputStream fexcel = new FileInputStream(".\\src\\test\\resources\\testdaata.xlsx");
		Workbook wb = WorkbookFactory.create(fexcel);
		Sheet sh = wb.getSheet("input");
		int Count = sh.getLastRowNum();
		System.out.println(Count);
		//Getting the values from Excel for the DD 
		Sheet sh1 = wb.getSheet("DD");
		String HDD = sh1.getRow(0).getCell(0).getStringCellValue();
		String CHDD = sh1.getRow(4).getCell(1).getStringCellValue();
		String ODDD = sh1.getRow(3).getCell(2).getStringCellValue();
		String CDD = sh1.getRow(3).getCell(3).getStringCellValue();
		
		
		String key1 = sh.getRow(0).getCell(0).getStringCellValue();
		System.out.println(key1);
		System.out.println(HDD + CHDD + ODDD + CDD );

		String restName = sh.getRow(0).getCell(1).getStringCellValue();

		HashedMap<String, String> map = new HashedMap<String, String>();
		
		
		for (int i = 0; i <=Count; i++) {
			String key = sh.getRow(i).getCell(0).getStringCellValue();
			String value = sh.getRow(i).getCell(1).getStringCellValue();
			map.put(key, value);
		}

		for (Entry<String, String> entry : map.entrySet()) {
			String key = entry.getKey();
			String val = entry.getValue();
			System.out.println(key + "   "+ val);
			driver.findElement(By.name(entry.getKey())).sendKeys(entry.getValue());
			
		}
		
		//Handaling DD
		WebElement HoursDD = driver.findElement(By.xpath("//select[@name='o_hr']"));
		Select hoursDD = new Select(HoursDD);
		hoursDD.selectByVisibleText(HDD);
		
		//Handaling DD
		WebElement CloseHoursDD = driver.findElement(By.xpath("//select[@name='c_hr']"));
		Select closehoursDD = new Select(CloseHoursDD);
		 closehoursDD.selectByVisibleText(CHDD);
		
		//Handaling DD
		 WebElement OpenDaysDD = driver.findElement(By.xpath("//select[@name='o_days']"));
		Select opendaysDD = new Select(OpenDaysDD);
		opendaysDD.selectByVisibleText(ODDD);
		
		//Handaling DD
		WebElement CategoryDD = driver.findElement(By.xpath("//select[@name='c_name']"));
		Select categoryDD = new Select(CategoryDD);
		categoryDD.selectByVisibleText(CDD);
		//Passing Image
		
		driver.findElement(By.xpath("//input[@id='lastName']")).sendKeys("C:\\Users\\Raki\\eclipse-workspace\\com.ofos.HangerMantra\\src\\test\\resources\\2023-02-21.jpg");
		
		Thread.sleep(2000);
		//Click on Submit Button
		driver.findElement(By.xpath("//input[@name='submit']")).click();
		
		
		String NewResAdd = driver.findElement(By.xpath("//div[@class='alert alert-success alert-dismissible fade show']")).getText();
		System.out.println(NewResAdd);
		
		String ExpNewResAdd = "New Restaurant Added Successfully";
		
		if (NewResAdd.contains(ExpNewResAdd)) {
			System.out.println("Restaurant Added Successfully");
		}
		else {
			System.err.println("Restaurant Not Added");
		}
		
		driver.findElement(By.xpath("//img[@alt='user']")).click();
		driver.findElement(By.xpath("//a[.=' Logout']")).click();
		
		System.out.println("Admin Logged out Succesfullty");
		
		
		//Logging in User
		
		String URL = pObj.getProperty("url");
		String USERNAME = pObj.getProperty("username");
		String PASSWORD = pObj.getProperty("password");

		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(30));

		driver.get(URL);
		driver.findElement(By.linkText("Login")).click();

		driver.findElement(By.name("username")).sendKeys(USERNAME);
		driver.findElement(By.name("password")).sendKeys(PASSWORD);
		driver.findElement(By.name("submit")).click();

		System.out.println("User Login Successfull");
		
		driver.findElement(By.xpath("//a[.='Restaurants ']")).click();
		
		List<WebElement> ListofRest = driver.findElements(By.xpath("//h5"));
		
		for(WebElement lv:ListofRest) {
			
			String ActulRest = lv.getText();
			if (restName.contains(ActulRest)) {
				System.out.println("Restaurant is Being Displayed In User Restauratnt List");
				break;
			}
		}
		
		
	}
}
