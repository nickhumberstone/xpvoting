package com.nickhumberstone.xpvoting.acceptance;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserContext;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;
import com.microsoft.playwright.Request;
import com.microsoft.playwright.options.AriaRole;
import org.junit.jupiter.api.*;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.server.LocalServerPort;

import java.nio.file.Paths;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

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
        assertAll(
                () -> assertThat(page.getByRole(AriaRole.LISTITEM).and(page.getByText("XTC is cool"))).isVisible(),
                () -> assertThat(page.getByLabel("Proposal")).isEmpty());
    }

    @Test
    void shouldBeAbleToSeeProposalsAsSeparateListItems() {
        page.getByLabel("Proposal").fill("Topic 1");
        page.getByRole(AriaRole.BUTTON).and(page.getByText("Submit")).click();
        page.getByLabel("Proposal").fill("Topic 2");
        page.getByRole(AriaRole.BUTTON).and(page.getByText("Submit")).click();
        assertAll(
                () -> assertThat(page.getByRole(AriaRole.LISTITEM)
                        .and(page.getByText("Topic 1", new Page.GetByTextOptions().setExact(true)))).isVisible(),
                () -> assertThat(page.getByRole(AriaRole.LISTITEM)
                        .and(page.getByText("Topic 2", new Page.GetByTextOptions().setExact(true)))).isVisible());
    }

    @Disabled("Refactor other tests before implementing this")
    @Test
    void newTopicSubmissionShouldDisplayWithoutRefresh() {
        // Create secondary tab
        Page page2 = context.newPage();
        page2.waitForRequest("http://localhost:" + port.toString(), () -> {
            // Triggers the request
            // Submit form in first tab to add topic (happens after secondary tab loaded)
            page.getByLabel("Proposal").fill("Topic Refreshes");
            page.getByRole(AriaRole.BUTTON).and(page.getByText("Submit")).click();
            // Check if topic added is visible in the secondary tab (without refreshing)

        });
        assertThat(page2.getByRole(AriaRole.LISTITEM)
                .and(page2.getByText("Topic Refreshes", new Page.GetByTextOptions().setExact(true)))).isVisible();
    }

    @Test
    @Disabled("Incomplete - two forms on one index.html causing clashes")
    void shouldBeAbleToClearProposals() {
        page.getByLabel("Proposal").fill("Topic 1");
        page.getByRole(AriaRole.BUTTON).and(page.getByText("Submit")).click();
        page.getByLabel("Password").fill("XTC2024");
        page.getByRole(AriaRole.BUTTON).and(page.getByText("Clear Proposals")).click();
        assertThat(page.getByText("No topics yet")).isVisible();
    }
}
