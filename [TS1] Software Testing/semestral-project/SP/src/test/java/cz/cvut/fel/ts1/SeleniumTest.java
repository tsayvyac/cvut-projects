package cz.cvut.fel.ts1;

import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
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

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class SeleniumTest {
    WebDriver driver;
    HomePage homePage;

    private static class OrderDetails {
        String itemCount;
        String itemName;
        String total;

        private OrderDetails(String itemCount, String itemName, String total) {
            this.itemCount = itemCount;
            this.itemName = itemName;
            this.total = total;
        }

        @Override
        public String toString() {
            return String.format("%s, %s, %s", itemName, itemCount, total);
        }
    }

    @BeforeEach
    public void setup() {
        System.setProperty("webdriver.chrome.driver", "chromedriver.exe");
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(30));
        driver.get("https://demo.prestamoduleshop.com/cs/");
    }

    @AfterEach
    public void close() {
        driver.close();
    }

    @Test
    @Order(1)
    public void errorMessageForPsc() {
        homePage = new HomePage(driver);
        NewAddressPage newAddressPage = homePage.clickToSignIn()
                .signIn("test@test.cz", "TestTest1")
                .clickToAddress()
                .clickToNewAddress().fillFormNewAddress("testMessage",
                        "Selenium", "Test", "CVUT", "CZ1234567890",
                        "Technicka 2", "Dejvice", "20111", "Praha", "Česká Republika",
                        "+420123123123");

        String errorMessage = newAddressPage.getErrorMessage();
        String expectErrorMessage = "Nesprávné PSČ - mělo by vypadat takto: \"NNN NN\"";

        Assertions.assertEquals(expectErrorMessage, errorMessage);
    }

    @Test
    @Order(2)
    public void emptyMessage() {
        homePage = new HomePage(driver);
        ContactUsPage contactUsPage = homePage.clickToSignIn()
                .signIn("test@test.cz", "TestTest1")
                .clickToContactUs()
                .fillFormContactUs("Webmaster", "test@test.cz", "202237550", "");

        String errorMessage = contactUsPage.getErrorMessage();
        String expectErrorMessage = "Zpráva nemůže být prázdná";

        Assertions.assertEquals(expectErrorMessage, errorMessage);
    }

    @Test
    @Order(3)
    public void createNewAddress() {
        homePage = new HomePage(driver);
        homePage.clickToSignIn()
                .signIn("test@test.cz", "TestTest1")
                .clickToAddress()
                .clickToNewAddress().fillFormNewAddress("createNewAddress",
                        "Selenium", "Test", "CVUT", "CZ1234567890",
                        "Technicka 2", "Dejvice", "201 11", "Praha", "Česká Republika",
                        "+420123123123");
    }

    @Test
    @Order(4)
    public void itemMinQuantity() {
        homePage = new HomePage(driver);
        ShoppingCart shoppingCart = homePage.clickToItem(1).setOrder("1")
                .clickToDecrease();

        String errorMessage = shoppingCart.getErrorMessage();
        String expectErrorMessage = "The minimum purchase order quantity for the product Brown bear + Video product is 1.";

        Assertions.assertEquals(expectErrorMessage, errorMessage);
    }

    @Test
    @Order(5)
    public void itemOutOfStock() throws InterruptedException {
        homePage = new HomePage(driver);
        ItemPage itemPage = homePage.clickToItem(2).setQuantity("500");

        // Waiting until icon will be visible, because element with id 'product-availability' is in html code
        // The error message appears along with the icon
        WebElement waitingForIcon = new WebDriverWait(driver, Duration.ofSeconds(10)).
                until(driver1 -> driver.findElement(By.xpath("//*[@id=\"product-availability\"]/i")));

        String errorMessage = itemPage.getOutOfStockMessage();
        String expectErrorMessage = "\uE14B Na skladě není dostatek produktů";

        Assertions.assertEquals(expectErrorMessage, errorMessage);
    }

    @ParameterizedTest
    @CsvFileSource(resources = "/cz.cvut.fel.ts1/forShoppingCartTest.csv")
    @Order(6)
    public void shoppingCart(String expectItemName, String expectItemName1, String expectItemName2) {
        homePage = new HomePage(driver);
        for (int i = 1; i < 4 ; i++) {
            ItemPage item = homePage.clickToItem(i);
            item.continueShopping("1");

            driver.navigate().back();
        }
        ShoppingCart shoppingCart = homePage.clickToShoppingCart();
        String itemName = shoppingCart.getItemNameInCart(0);
        String itemName1 = shoppingCart.getItemNameInCart(1);
        String itemName2 = shoppingCart.getItemNameInCart(2);

        Assertions.assertEquals(expectItemName, itemName);
        Assertions.assertEquals(expectItemName1, itemName1);
        Assertions.assertEquals(expectItemName2, itemName2);
    }

    @Test
    @Order(9)
    public void orderingProcess() {
        homePage = new HomePage(driver);
        OrderPage orderPage = homePage.clickToSignIn().signIn("test@test.cz", "TestTest1")
                .clickToHomeLink().clickToItem(1)
                .setOrder("100").clickToOrderBtn();
        Boolean waitingForLoading = new WebDriverWait(driver, Duration.ofSeconds(10)).
                until(ExpectedConditions.attributeContains(By.xpath("//*[@id=\"content\"]/div/div[1]/div[1]"),
                        "style", "display: none;"));

        orderPage.clickToContinue();

        Boolean waitingForLoading2 = new WebDriverWait(driver, Duration.ofSeconds(10)).
                until(ExpectedConditions.attributeContains(By.xpath("//*[@id=\"content\"]/div/div[1]/div[1]"),
                        "style", "display: none;"));

        SuccessOrderPage successOrderPage = orderPage.selectPayment();

        String nameOfItem = successOrderPage.getItemName();
        String count = successOrderPage.getItemQuantity();
        String total = successOrderPage.getTotal();

        try (PrintWriter printWriter = new PrintWriter("src/test/resources/cz.cvut.fel.ts1/forHistoryTest.csv")) {
            printWriter.println(nameOfItem + ", " + count + ", " + total);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    @ParameterizedTest
    @CsvFileSource(resources = "/cz.cvut.fel.ts1/forHistoryTest.csv")
    @Order(10)
    public void history_1(String expectName, String expectCount, String expectTotal) {
        homePage = new HomePage(driver);
        DetailsPage detailsPage = homePage.clickToSignIn().signIn("test@test.cz", "TestTest1")
                .clickToHistory()
                .goToDetails(0);
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("window.scrollBy(0, 500)");

        String thisName = detailsPage.getItemName();
        String thisCount = detailsPage.getItemQuantity();
        String thisTotal = detailsPage.getTotal();

        Assertions.assertEquals(expectName + " " + expectCount + " " + expectTotal + ",00 Kč",
                thisName + " " + thisCount + " " + thisTotal);
    }


    @Test
    @Order(8)
    public void shoppingProcess() {
        homePage = new HomePage(driver);
        homePage.clickToSignIn().signIn("test@test.cz", "TestTest1")
                .clickToHomeLink().clickToItem(1)
                .continueShopping("10");

        driver.navigate().back();

        homePage.searchThisItem("TODAY IS A GOOD DAY FRAMED POSTER")
                .clickToItem(0)
                .continueShopping("2");

        driver.navigate().back();
        driver.navigate().back();

        homePage.clickToToArts()
                .clickToArtItem(0)
                .setOrder("5");
    }

    @ParameterizedTest
    @CsvFileSource(resources = "/cz.cvut.fel.ts1/forSearchTest.csv")
    @Order(7)
    public void search_1(String itemName, String itemCode) {
        homePage = new HomePage(driver);
        ResultPage resultPage = homePage.searchThisItem(itemName);
        ItemPage itemPage = resultPage.clickToItem(0);
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("window.scrollBy(0, 500)");
        itemPage.clickToItemDetails();
        WebElement waitingForItemCode = new WebDriverWait(driver, Duration.ofSeconds(3)).
                until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"product-details\"]/div[2]/span")));

        Assertions.assertEquals(itemCode, itemPage.getItemCode());
    }
}
