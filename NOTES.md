Our first test: a test of tests, screenshotting playwright.dev

Published at: https://bit.ly/xpvoting

Need to make the form submit do something - no POST method currently
This application has no explicit mapping for /error, so you are seeing this as a fallback.

Mon Sep 02 20:55:29 BST 2024
There was an unexpected error (type=Method Not Allowed, status=405).
Method 'POST' is not supported.
org.springframework.web.HttpRequestMethodNotSupportedException: Request method 'POST' is not supported

Mon 9 Sep
Need to fix the list display so each list item isn't the entire list of topics, and is instead one topic of all of them
Need to add proper CI/CD automated testing

Mon 7 Oct
Proposals are an object that have a topicTitle and a voting score
Thymeleaf should be just a presentation layer -

24 Oct 
Added voting backend implementation, need to add frontend for this. Buttons to trigger the vote increase, display of the vote numbers
When do we add style? (Times New Roman is too classy for us - comic sans is the way to go!)
Need to add test - what happens when a proposal is not found? 

8 Nov
Extracted proposal to a Thymeleaf fragment
Started htmx-izing proposal voting
- Added htmx button in parallel
- Added POST endpoint in parallel
- Added query for Proposal details to ProposalService

13 Nov
Removed old vote button
Got htmx to target the table row correctly
Made endpoint return the right model attribute

TODO:
- ~~Update htmx button and its endpoint (returning the content for the table row)~~
- Re-evaluate our Ubiquitous Language - add Domain Glossary to README
- Update README (who uses Tailwind anyway?)
- Make the button swap-oob just the vote count, not the entire row
