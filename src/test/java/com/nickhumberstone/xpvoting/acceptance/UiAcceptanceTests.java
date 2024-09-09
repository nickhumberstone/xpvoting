package com.nickhumberstone.xpvoting.acceptance;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;
import com.microsoft.playwright.options.AriaRole;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.server.LocalServerPort;

import java.nio.file.Paths;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
class UiAcceptanceTests {

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

    @Test
    void shouldBeAbleToProposeAndViewProposal() {
        page.getByLabel("Proposal").fill("XTC is cool");
        page.getByRole(AriaRole.BUTTON).and(page.getByText("Submit")).click();
        assertThat(page.getByRole(AriaRole.LISTITEM).and(page.getByText("XTC is cool"))).isVisible();
    }
}
