package com.nickhumberstone.xpvoting.acceptance;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserContext;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;
import com.microsoft.playwright.Tracing;
import com.microsoft.playwright.options.AriaRole;
import com.nickhumberstone.xpvoting.ProposalService;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.server.LocalServerPort;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import java.nio.file.Paths;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
class UiAcceptanceIT {

    @LocalServerPort
    private Integer port;

    static Playwright playwright;
    static Browser browser;

    BrowserContext context;
    Page page;

    @Autowired
    ProposalService proposalService;

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
        context.tracing().start(new Tracing.StartOptions()
                .setScreenshots(true)
                .setSnapshots(true)
                .setSources(true));
        page = context.newPage();
        page.navigate("http://localhost:" + port);
    }

    @AfterEach
    void closeContext() {
        context.tracing().stop(new Tracing.StopOptions()
                .setPath(Paths.get("target/playwright/trace.zip")));
        context.close();
        proposalService.clearProposals();
    }

    private void submitProposal(String value) {
        page.getByLabel("Proposal").fill(value);
        page.getByRole(AriaRole.BUTTON).and(page.getByText("Submit")).click();
    }

    @Test
    void shouldBeAbleToProposeAndViewProposal() {
        submitProposal("XTC is cool");
        assertAll(
                () -> assertThat(page.getByRole(AriaRole.CELL).and(page.getByText("XTC is cool"))).isVisible(),
                () -> assertThat(page.getByLabel("Proposal")).isEmpty());
    }

    @Test
    void shouldBeAbleToSeeProposalsAsSeparateListItems() {
        submitProposal("Topic 1");
        submitProposal("Topic 2");
        assertAll(
                () -> assertThat(page.getByRole(AriaRole.CELL).and(page.getByText("Topic 1"))).isVisible(),
                () -> assertThat(page.getByRole(AriaRole.CELL).and(page.getByText("Topic 2"))).isVisible());
    }

    @Test
    void newTopicSubmissionShouldDisplayWithoutRefresh() {
        // Create secondary tab
        Page page2 = context.newPage();
        page2.navigate("http://localhost:" + port);
        assertThat(page2.getByRole(AriaRole.PARAGRAPH).and(page2.getByText("No topics yet"))).isVisible();
        submitProposal("Topic Refreshes");
        assertThat(page2.getByRole(AriaRole.CELL).and(page2.getByText("Topic Refreshes"))).isVisible();
    }

    @Test
    @Disabled
    void shouldBeAbleToVoteAndSeeVoteNumberIncrease() {
        submitProposal("Topic 1");
        // now we have a table with a topic and vote score of 0 displayed, and a vote
        // button
        page.getByRole(AriaRole.BUTTON).and(page.getByText("Vote")).click();
        assertThat(page.getByRole(AriaRole.CELL)).hasText(new String[] { "Topic 1", "Topic 2" });
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
