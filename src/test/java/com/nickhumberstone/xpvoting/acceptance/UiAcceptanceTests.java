package com.nickhumberstone.xpvoting.acceptance;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserContext;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;
import com.microsoft.playwright.options.AriaRole;
import org.junit.jupiter.api.*;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.server.LocalServerPort;

import java.nio.file.Paths;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
class UiAcceptanceTests {

    @LocalServerPort
    private Integer port;

    static Playwright playwright;
    static Browser browser;

    BrowserContext context;
    Page page;

    @BeforeAll
    static void launchBrowser() {
        playwright = Playwright.create();
        browser = playwright.chromium().launch();
    }

    @AfterAll
    static void closeBrowser() {
        playwright.close();
    }

    @BeforeEach
    void createContextAndPage() {
        context = browser.newContext();
        page = context.newPage();
        page.navigate("http://localhost:" + port);
    }

    @AfterEach
    void closeContext() {
        context.close();
    }

    @Test
    @Disabled("prevent every commit containing a new PNG")
    void shouldScreenshot() {
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

    @Test
    void shouldBeAbleToProposeAndViewProposal() {
        page.getByLabel("Proposal").fill("XTC is cool");
        page.getByRole(AriaRole.BUTTON).and(page.getByText("Submit")).click();
        assertThat(page.getByRole(AriaRole.LISTITEM).and(page.getByText("XTC is cool"))).isVisible();
    }
}
