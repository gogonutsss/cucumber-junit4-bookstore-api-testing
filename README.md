# 🚀 Bookstore API Test Automation Framework

> **An API testing framework for the [FakeRestAPI](https://fakerestapi.azurewebsites.net/) Bookstore using Cucumber BDD, RestAssured, Junit4 and Java 17**

---

## 🚀 Quick Start Guide

### Prerequisites
- **Java 17+**
- **Maven 3.6+**
- **Git** (for cloning and CI/CD)

### Setup & Installation

```powershell
# Clone the repository
git clone https://github.com/gogonutsss/cucumber-junit4-bookstore-api-testing
cd cucumber-junit4-bookstore-api-testing

# Install dependencies
mvn clean install

# Verify setup (run smoke tests)
mvn test -Dcucumber.filter.tags="@smoke"
```

### IDE Configuration
- Import as Maven project
- Set JDK to 17+
- Install Cucumber plugins for Gherkin syntax highlighting

## 🧪 Test Execution Options

### **IDE Integration**
- **IntelliJ IDEA or Eclipse**: Run `TestRunner.java` directly as JUnit Test

### **Maven - Parallel Execution Control**

By default, feature files will run through 4 parallel threads for faster execution. 
Override options:

```powershell
# Set different thread count
mvn clean test -DsurefireThreadCount=2

# Enforce sequential execution of scenarios and features (debugging)
mvn clean test -DsurefireParallel=none
```

### **Maven - Basic Test Execution**

```powershell
# Run all @regression tests (default)
mvn test

# Run specific test groups
mvn test -Dcucumber.filter.tags="@smoke"        # Quick smoke tests
mvn test -Dcucumber.filter.tags="@books"        # Books API only
mvn test -Dcucumber.filter.tags="@authors"      # Authors API only
mvn test -Dcucumber.filter.tags="@integration"  # Integration tests

# Combined tag filtering
mvn test -Dcucumber.filter.tags="@books and not @smoke"

# Error handling scenarios
mvn test -Dcucumber.filter.tags="@error-handling"

# Data-driven tests with examples
mvn test -Dcucumber.filter.tags="@data-driven"

# Specific feature file
mvn test -Dcucumber.features="src/test/resources/features/book_management.feature"
```

**Note**: Use `mvn clean test` only when needed (after configuration changes, dependency updates, or for fresh builds). Regular `mvn test` is faster for iterative development.

### **Maven - Logging**
```powershell
# Debug mode with detailed logging
mvn test -DlogLevel=DEBUG
```

---

## 📊 Reporting

### **1. Surefire JUnit Report**
- **Location**: `target/surefire-reports/`
- **Format**: XML (CI/CD compatible)

### **2. Native Cucumber Reports**
- **Location**: `target/cucumber-reports/`
- **Format**: HTML, JSON

### **3. Native Cucumber Timeline Report**
- **Location**: `target/timeline/`
- **Features**: Parallel execution visualization across time

### **4. Enhanced Masterthought Cucumber Report**

```powershell
# Requires running tests through maven with verify goal
mvn clean verify
```

- **Location**: `target/cucumber-html-reports/`
- **Format**: HTML

### **GitHub Actions CI**
- Automated test execution with every code push/pull request, running the complete Cucumber test suite generating multiple report formats (HTML, JSON, XML, Timeline) that are automatically uploaded as downloadable artifacts. 
- Publishes per run the native Cucumber json and Surefire xml report.
- Finally, it deploys beautiful Masterthought HTML report to GitHub Pages for public viewing when pushing to the main branch.

---

## 📖 Getting Started Resources & Learning Resources

- **[RestAssured Guide](https://github.com/rest-assured/rest-assured/wiki/Usage)**
- **[Maven Surefire Plugin](https://maven.apache.org/surefire/maven-surefire-plugin/)**
- **[Cucumber Documentation](https://cucumber.io/docs/cucumber/)**
- **[Cucumber Masterthought Reporting](https://github.com/damianszczepanik/cucumber-reporting)**
- **[GitHub Actions](https://docs.github.com/en/actions)**

---

*Happy Testing! 🚀*