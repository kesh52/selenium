package Selenium.bahn.de;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;

import java.util.concurrent.TimeUnit;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

public class AppTest {
    private WebDriver driver = null;
    private String website = "https://www.bahn.de";

    @Before
    public void setUp() {
        System.setProperty("webdriver.chrome.driver", "C:/_Progs/ChromeDriver/chromedriver.exe");
        this.driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
    }

    @Test
    public void testPresenseOfDBSymbol() {
        this.driver.get(this.website);
        WebElement dbSymbol = this.driver.findElement(By.id("logo"));
        assertNotNull(dbSymbol);
    }

    @Test
    public void testPresenceOfRentCarAndEnvBar() {
        this.driver.get(this.website);
        WebElement rentCarAndEnv = this.driver.findElement(By.id("qf-node-mwagent-umwelt"));
        rentCarAndEnv.click();

        WebElement searchRentCarBtn = this.driver.findElement(By.name("qf.mietwagen.button.suchen"));
        assertNotNull(searchRentCarBtn);
    }

    @Test
    public void testCalendar() throws InterruptedException {
        this.driver.get(this.website);
        WebElement rentCarAndEnv = this.driver.findElement(By.id("qf-node-mwagent-umwelt"));
        rentCarAndEnv.click();

        WebElement rentalDate = this.driver.findElement(By.id("qf-mietwagen-rental-date"));
        rentalDate.clear();
        rentalDate.sendKeys("24.12.16");
        WebElement returnDate = this.driver.findElement(By.id("qf-mietwagen-return-date"));
        returnDate.click();

        WebElement test = this.driver.findElement(By.id("callink5_df_2016/11/25"));

        assertEquals("enabled active", test.getAttribute("class"));
    }

    public void testMarburgRentCarStation() {
        this.driver.get(this.website);
        WebElement rentCarAndEnv = this.driver.findElement(By.id("qf-node-mwagent-umwelt"));
        rentCarAndEnv.click();

        WebElement searchRentCarStation = this.driver.findElement(By.id("qf-mietwagen-rent"));
        searchRentCarStation.sendKeys("Marburg");

        WebElement searchRentCarBtn = this.driver.findElement(By.name("qf.mietwagen.button.suchen"));
        searchRentCarBtn.click();

        /*
         * "Ort der Anmietstation" wird aus irgendeinem Grund nicht übernommen, ich habe das mehrmals in Chrome und Firefox
         * ausprobiert. Das kann wohl ein Bug bei www.bahn.de sein.
         */
    }

    @After
    public void tearDown() {
        this.driver.close();
    }

}
