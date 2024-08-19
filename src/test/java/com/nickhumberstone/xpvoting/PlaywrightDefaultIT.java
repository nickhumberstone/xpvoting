package com.nickhumberstone.xpvoting;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.server.LocalServerPort;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;
import static com.microsoft.playwright.assertions.PlaywrightAssertions.*;
import com.microsoft.playwright.options.AriaRole;

import java.nio.file.Paths;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class PlaywrightDefaultIT {

    @LocalServerPort
    private Integer port;

    private static Page page;
    private static Playwright playwright;

    @BeforeAll
    static void launchInstance() {
        playwright = Playwright.create();
        Browser browser = playwright.webkit().launch();
        page = browser.newPage();
    }

    @AfterAll
    static void closeBrowser() {
        playwright.close();
    }

    @Test
    void shouldScreenshot() {
        page.navigate("http://localhost:" + port);
        page.screenshot(new Page.ScreenshotOptions().setPath(Paths.get("example.png")));
    }

    @Test
    void shouldHaveHeading() {
        assertThat(page
                .getByRole(AriaRole.HEADING,
                        new Page.GetByRoleOptions().setName("XP Voting")))
                .isVisible();
    }

    @Test
    void shouldHaveSubHeading() {
        assertThat(page
                .getByRole(AriaRole.HEADING,
                        new Page.GetByRoleOptions().setName("Proposed topic titles")))
                .isVisible();
    }
}
