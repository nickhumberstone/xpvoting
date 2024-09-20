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

    private void submitProposal(String value) {
        page.getByLabel("Proposal").fill(value);
        page.getByRole(AriaRole.BUTTON).and(page.getByText("Submit")).click();
    }

    @Test
    void shouldBeAbleToProposeAndViewProposal() {
        submitProposal("XTC is cool");
        assertAll(
                () -> assertThat(page.getByRole(AriaRole.LISTITEM).and(page.getByText("XTC is cool"))).isVisible(),
                () -> assertThat(page.getByLabel("Proposal")).isEmpty());
    }

    @Test
    void shouldBeAbleToSeeProposalsAsSeparateListItems() {
        submitProposal("Topic 1");
        submitProposal("Topic 2");
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
            submitProposal("Topic Refreshes");
            // Check if topic added is visible in the secondary tab (without refreshing)

        });
        assertThat(page2.getByRole(AriaRole.LISTITEM)
                .and(page2.getByText("Topic Refreshes", new Page.GetByTextOptions().setExact(true)))).isVisible();
    }

    @Test
    @Disabled("Incomplete - two forms on one index.html causing clashes")
    void shouldBeAbleToClearProposals() {
        submitProposal("Topic 1");
        page.getByLabel("Password").fill("XTC2024");
        page.getByRole(AriaRole.BUTTON).and(page.getByText("Clear Proposals")).click();
        assertThat(page.getByText("No topics yet")).isVisible();
    }
}
