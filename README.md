# Financial Tracker

## Description of the Project

The Financial Tracker project allows for a user to manually enter and view transactions from a csv file. 
A **transaction** can be either a payment or deposit, and consists of:
- Date 
- Time 
- Description 
- Vendor 
- Amount

Transactions are stored within the program's ledger which can be printed out in various configurations. 
The ledger print configurations consist of:
- Month To Date
- Previous Month
- Year To Date
- Previous Year
- By Vendor

The ledger writes into the "transactions.csv" file, given the correct input of transaction data.

## User Stories

List the user stories that guided the development of your application. Format these stories as: "As a [type of user], I want [some goal] so that [some reason]."

- As a user, I want to be able to input my data, so that the application can process it accordingly.
- As a user, I want to receive immediate feedback, so I can understand what to do next.

## Setup

Instructions on how to set up and run the project using IntelliJ IDEA.

### Prerequisites

- IntelliJ IDEA: Ensure you have IntelliJ IDEA installed, which you can download from [here](https://www.jetbrains.com/idea/download/).
- Java SDK: Make sure Java SDK is installed and configured in IntelliJ.

### Running the Application in IntelliJ

Follow these steps to get your application running within IntelliJ IDEA:

1. Open IntelliJ IDEA.
2. Select "Open" and navigate to the directory where you cloned or downloaded the project.
3. After the project opens, wait for IntelliJ to index the files and set up the project.
4. Find the main class with the `public static void main(String[] args)` method.
5. Right-click on the file and select 'Run 'YourMainClassName.main()'' to start the application.

### Using a pre-made .csv file

In order to use a pre-made .csv file with transactions, the file must be named "transactions.csv" placed in the main program folder,
and the information needs to follow this format:
> date|time|description|vendor|amount

 
## Technologies Used

- IntelliJ IDEA 2023.3.3 (Ultimate Edition)
- Java 17 (Amazon Corretto 17.0.12)

## Demo

Include screenshots or GIFs that show your application in action. Use tools like [Giphy Capture](https://giphy.com/apps/giphycapture) to record a GIF of your application.

![Application Screenshot](path/to/your/screenshot.png)

## Future Work

Outline potential future enhancements or functionalities you might consider adding:

- Adapt table visual to allow for more characters per cell.
- Improvement of current functionalities.

## Resources

List resources such as tutorials, articles, or documentation that helped you during the project.

- [Java Programming Tutorial](https://www.example.com)
- [Effective Java](https://www.example.com)

## Team Members

- **Name 1** - Specific contributions or roles.
- **Name 2** - Specific contributions or roles.

## Thanks

Express gratitude towards those who provided help, guidance, or resources:

- Thank you to [Mentor's Name] for continuous support and guidance.
- A special thanks to all teammates for their dedication and teamwork.