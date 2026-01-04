# GitHub Actions Regression Test Workflow - Quick Reference

## ✅ What's Included

### 1. **Workflow Trigger**
```
Manual trigger (workflow_dispatch)
↓
Dropdown Menu for Test XML File Selection
├── testng.xml
└── src/test/resources/testng.xml
```

### 2. **Environment Setup**
```
Ubuntu Latest
├── Java 17 (Temurin Distribution)
├── Maven (with dependency caching)
└── Project Dependencies Installation
```

### 3. **Test Execution Pipeline**
```
Checkout Code
    ↓
Setup Java 17
    ↓
Install Dependencies
    ↓
Run Tests (with selected XML file)
    ↓
Generate Allure Report
    ↓
Upload Artifacts
    ↓
Parse Results & Send Email
```

### 4. **Email Notification Features**
- **Recipients**: a@gmail.com, b@gmail.com
- **HTML Body**: Professional formatted report
- **Content**:
  - Test Suite File Name
  - Execution Date & Time
  - Repository & Branch Info
  - Commit SHA
  - Test Metrics (Total, Passed, Failed, Skipped)
  - Technology Stack
  - Allure Report Link

## 🔧 Configuration Required

### Step 1: Add GitHub Secrets
Go to: `Settings → Secrets and variables → Actions → New repository secret`

| Secret Name | Value |
|------------|-------|
| `EMAIL_SERVER` | `smtp.gmail.com` |
| `EMAIL_PORT` | `587` |
| `EMAIL_USERNAME` | Your Gmail address |
| `EMAIL_PASSWORD` | Your App Password (16 chars) |
| `EMAIL_FROM` | Your Gmail address |

### Step 2: Enable Gmail App Password (if using Gmail)
1. Enable 2FA on Google Account
2. Visit: https://myaccount.google.com/apppasswords
3. Select Mail + Windows Computer
4. Copy the 16-character password
5. Paste in `EMAIL_PASSWORD` secret

### Step 3: Commit the Workflow File
The workflow file is already created at:
```
.github/workflows/regression.yml
```

Just push your changes to GitHub!

## 🚀 How to Run

1. Navigate to **Actions** tab in your GitHub repository
2. Select **Regression Test Suite** from the left panel
3. Click **"Run workflow"**
4. Select desired TestNG XML file from dropdown
5. Click **"Run workflow"** button

Wait for completion... You'll receive an email with test results!

## 📊 Email Report Includes

```
┌─────────────────────────────────────────┐
│     🔬 Regression Test Execution Report  │
├─────────────────────────────────────────┤
│ Test Suite File:     testng.xml          │
│ Execution Date:      2024-01-04 14:30:00 │
│ Repository:          org/project         │
│ Branch:              main                │
│ Commit SHA:          abc123def456        │
├─────────────────────────────────────────┤
│ Test Execution Summary                  │
│ ┌──────┬────────┬────────┬────────────┐ │
│ │Total │Passed  │Failed  │Skipped     │ │
│ │  25  │  22    │   3    │    0       │ │
│ └──────┴────────┴────────┴────────────┘ │
├─────────────────────────────────────────┤
│ 📊 Test Execution Details                │
│ Framework: TestNG                       │
│ Java Version: 17                        │
│ OS: Ubuntu Latest                       │
│ Build Tool: Apache Maven                │
├─────────────────────────────────────────┤
│ 📈 Allure Report                         │
│ Download artifact for detailed analysis │
└─────────────────────────────────────────┘
```

## 🎯 Key Features

✅ **Dropdown Selection** - Choose test XML file from dropdown menu
✅ **Ubuntu OS** - Runs on latest Ubuntu runner
✅ **Java 17** - Temurin distribution with automatic caching
✅ **Allure Reports** - Automatically generated with every run
✅ **Email Notifications** - HTML formatted reports to recipients
✅ **Artifact Upload** - Allure results available for download
✅ **Continue on Failure** - Generates reports even if tests fail
✅ **Professional HTML** - Styled email with gradients and metrics

## 📁 Files Modified/Created

```
.github/
└── workflows/
    └── regression.yml                   ← Created
GITHUB_ACTIONS_SETUP.md                  ← Detailed setup guide
GITHUB_ACTIONS_QUICK_REFERENCE.md        ← This file
```

## 🔗 Useful Links

- [GitHub Actions Documentation](https://docs.github.com/en/actions)
- [TestNG Documentation](https://testng.org/)
- [Allure Report Documentation](https://docs.qameta.io/allure/)
- [Maven Surefire Plugin](https://maven.apache.org/surefire/)

## ⚠️ Troubleshooting Checklist

- [ ] Email secrets are set correctly in GitHub
- [ ] Gmail app password is generated (if using Gmail)
- [ ] Repository has .github/workflows/regression.yml file
- [ ] pom.xml has Allure dependencies
- [ ] TestNG XML files exist in specified locations
- [ ] Maven is configured correctly in pom.xml

## 📝 Next Steps

1. ✅ Workflow file created → `.github/workflows/regression.yml`
2. ⏳ Add GitHub Secrets (EMAIL_SERVER, EMAIL_PORT, etc.)
3. ⏳ Push changes to your repository
4. ⏳ Navigate to Actions tab and trigger the workflow
5. ⏳ Select test XML file and run
6. ⏳ Check email for detailed report!

---
**Status**: ✅ Ready to Deploy
**Last Updated**: January 2026

