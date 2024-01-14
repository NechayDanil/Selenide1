import com.codeborne.selenide.SelenideElement;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.$x;
import static com.codeborne.selenide.Selenide.*;
import static java.time.Duration.ofSeconds;

public class OrderDeliveryTest {

    public String generateDate(int days) {
        return LocalDate.now().plusDays(days).format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
    }

    String planningDate = generateDate(7);
    SelenideElement notification = $x("//div[@data-test-id='notification']");


    @BeforeEach
    public void setUp() {
        open("http://localhost:9999/");
    }

    @Test
    public void ShouldCardDelivery() {
        $x(".//span[@data-test-id='city']//input").setValue("Самара");
        $("[data-test-id='date'] input").sendKeys(Keys.chord(Keys.SHIFT,Keys.HOME),Keys.BACK_SPACE);
        $("[data-test-id='date'] input").setValue(planningDate);
        $x(".//span[@data-test-id='name']//input").setValue("Иванов Иван");
        $x(".//span[@data-test-id='phone']//input").setValue("+79178173838");
        $x(".//label[@data-test-id='agreement']").click();
        $x(".//span[contains(text(),'Забронировать')]").click();
        notification.should(visible, ofSeconds(15));
        notification.$x(".//div[@class='notification__title']").should(text("Успешно!"));
        notification.$x(".//div[@class='notification__content']").should(text("Встреча успешно забронирована на " + planningDate));
        notification.$x(".//button").click();
        notification.should(hidden);

    }
}
