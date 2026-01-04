# GitHub Actions Regression Test Workflow Setup Guide

## Overview
This GitHub Actions workflow automates regression test execution with the following features:
- ✅ Dropdown selection of TestNG XML files
- ✅ Ubuntu OS environment
- ✅ Java 17 setup
- ✅ Maven build and test execution
- ✅ Allure reporting
- ✅ Email notifications with detailed HTML report

## Prerequisites

### 1. Repository Secrets Configuration
Add the following secrets to your GitHub repository settings (`Settings > Secrets and variables > Actions > New repository secret`):

| Secret Name | Description | Example |
|------------|-------------|---------|
| `EMAIL_SERVER` | SMTP server address | `smtp.gmail.com` |
| `EMAIL_PORT` | SMTP port | `587` |
| `EMAIL_USERNAME` | Email sender username | `your-email@gmail.com` |
| `EMAIL_PASSWORD` | Email app password or password | `your-app-password` |
| `EMAIL_FROM` | Sender email address | `your-email@gmail.com` |

### 2. Gmail Setup (if using Gmail)
If using Gmail, follow these steps:
1. Enable 2-Factor Authentication on your Google Account
2. Generate an App Password:
   - Go to [myaccount.google.com/apppasswords](https://myaccount.google.com/apppasswords)
   - Select "Mail" and "Windows Computer" (or your device)
   - Copy the generated 16-character password
   - Use this as `EMAIL_PASSWORD` secret
3. Set `EMAIL_SERVER` to `smtp.gmail.com`
4. Set `EMAIL_PORT` to `587`

### 3. Other Email Providers
- **Outlook/Office365**: `smtp.office365.com:587`
- **Yahoo**: `smtp.mail.yahoo.com:587`
- **SendGrid**: `smtp.sendgrid.net:587`
- **AWS SES**: `email-smtp.[region].amazonaws.com:587`

## Workflow Features

### 1. Dropdown Test File Selection
When triggering the workflow manually, you can choose from:
- `testng.xml` (default test suite)
- `src/test/resources/testng.xml` (alternative location)

You can add more options by editing the `test_xml_file` input options in the workflow.

### 2. Java 17 Setup
The workflow automatically:
- Installs JDK 17 (Temurin distribution)
- Caches Maven dependencies for faster builds
- Configures Maven compiler to use Java 17

### 3. Test Execution
The workflow:
- Runs `mvn clean test` with specified TestNG XML file
- Continues even if tests fail to generate reports
- Supports environment and browser parameters

### 4. Allure Reporting
- Automatically generates Allure reports after test execution
- Uploads allure-results as artifacts for download
- Provides detailed test metrics and analytics

### 5. Email Notification
Sends HTML email with:
- Test suite file name
- Execution date and time
- Repository and branch information
- Commit SHA
- Total, passed, failed, and skipped test counts
- Link to GitHub Actions run
- Technology stack details
- Link to Allure reports

## How to Use

### Trigger the Workflow
1. Go to your GitHub repository
2. Click on **Actions** tab
3. Select **"Regression Test Suite"** workflow
4. Click **"Run workflow"**
5. Select the TestNG XML file from the dropdown
6. Click **"Run workflow"** button

### View Results
1. **Test Execution**: Check the workflow logs in real-time
2. **Artifacts**: Download `allure-results` artifact containing:
   - `index.html` - Main Allure report
   - Test history and detailed metrics
3. **Email**: Check your inbox (a@gmail.com, b@gmail.com) for notification

## Customization

### Add More XML Test Files
Edit the workflow file and add more options under `test_xml_file`:
```yaml
options:
  - testng.xml
  - src/test/resources/testng.xml
  - src/test/resources/smoke-tests.xml
  - src/test/resources/sanity-tests.xml
```

### Change Recipients
Edit the `to` field in the email step:
```yaml
to: recipient1@gmail.com,recipient2@gmail.com,recipient3@gmail.com
```

### Modify Environment Parameters
Change the Maven command in the workflow:
```bash
mvn clean test \
  -DsuiteXmlFile=${{ github.event.inputs.test_xml_file }} \
  -Denvironment=staging \
  -Dbrowser=firefox
```

### Schedule Automated Runs
Add cron scheduling to the workflow trigger:
```yaml
on:
  workflow_dispatch: ...
  schedule:
    - cron: '0 2 * * *'  # Run daily at 2 AM UTC
```

## Troubleshooting

### Email Not Sending
- Verify all EMAIL_* secrets are correctly set
- Check if "Less secure app access" is enabled (for Gmail)
- Ensure SMTP server and port are correct
- Check workflow logs for detailed error messages

### Tests Not Running
- Verify the XML file path is correct
- Ensure testng.xml exists in the repository
- Check Maven dependencies are downloading correctly
- Review the "Install Maven dependencies" step logs

### Allure Report Not Generated
- Ensure Allure dependencies are in pom.xml
- Check target/allure-results directory has files
- Verify `mvn allure:aggregate` command runs successfully

### Artifacts Not Uploading
- Verify the tests generated results in target/surefire-reports/
- Check the allure-results directory exists
- Ensure sufficient GitHub Actions storage quota

## File Structure
```
.github/workflows/
├── regression.yml              # Main workflow file
└── (this configuration guide)
```

## Dependencies
The workflow relies on:
- **Maven Surefire Plugin** - Test execution
- **Allure Maven Plugin** - Report generation
- **TestNG** - Test framework
- **GitHub Actions** - CI/CD platform

## Advanced Configuration

### Matrix Testing (Multiple Java Versions)
```yaml
strategy:
  matrix:
    java-version: [17, 21]
```

### Conditional Steps
```yaml
if: failure()  # Run only if previous steps fail
if: success()  # Run only if successful
```

### Artifact Retention
```yaml
retention-days: 30  # Keep artifacts for 30 days
```

## Support
For issues or questions:
1. Check GitHub Actions logs
2. Review pom.xml configuration
3. Verify email SMTP settings
4. Check Allure plugin version compatibility

---
Last Updated: January 2026

