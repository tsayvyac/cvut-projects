package cz.cvut.fel.ts1;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

public class Main {
    private static WebDriver driver;
    private static HomePage homePage;

    private static class Item {
        String itemName;
        String itemCode;

        private Item(String itemName, String itemCode) {
            this.itemName = itemName;
            this.itemCode = itemCode;
        }

        @Override
        public String toString() {
            return String.format("%s, %s", itemName, itemCode);
        }
    }
    public static void main(String[] args) {
        System.setProperty("webdriver.chrome.driver", "chromedriver.exe");
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(20));
        driver.get("https://demo.prestamoduleshop.com/cs/");

        List<Item> itemList =new ArrayList<>();

        homePage = new HomePage(driver);
        ArtsPage artsPage = homePage.clickToToArts();
        for (int i = 2; i < 6; i++) {
            ItemPage itemPage = artsPage.clickToArtItem(i);
            String itemName = itemPage.getItemName();
            JavascriptExecutor js = (JavascriptExecutor) driver;
            js.executeScript("window.scrollBy(0, 500)");
            itemPage.clickToItemDetails();
            WebElement waitingForItemCode = new WebDriverWait(driver, Duration.ofSeconds(3)).
                    until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"product-details\"]/div[2]/span")));
            String itemCode = itemPage.getItemCode();
            Item item = new Item(itemName, itemCode);
            itemList.add(item);

            driver.navigate().back();
        }

        try (PrintWriter printWriter = new PrintWriter("src/test/resources/cz.cvut.fel.ts1/forSearchTest.csv")) {
            for (Item item : itemList) {
                printWriter.println(item.toString());
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        driver.close();
    }
}
