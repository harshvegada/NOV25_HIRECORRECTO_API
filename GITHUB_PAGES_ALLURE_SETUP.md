# GitHub Pages Allure Report Deployment Guide

## 🎯 Overview

Your GitHub Actions workflow now automatically deploys Allure test reports to GitHub Pages. This allows you to:
- ✅ Access reports from anywhere using a URL
- ✅ View historical test reports in one place
- ✅ Share reports with team members
- ✅ Maintain a long-term test execution history

---

## 📋 Prerequisites

### 1. Enable GitHub Pages
You need to enable GitHub Pages in your repository:

1. Go to **Settings → Pages**
2. Under "Build and deployment" select:
   - **Source**: Deploy from a branch
   - **Branch**: `gh-pages`
   - **Folder**: `/ (root)`
3. Click **Save**

### 2. Initial Setup (One-time)
The workflow will automatically create the `gh-pages` branch on first run. You don't need to create it manually.

---

## 🚀 How It Works

### Workflow Steps

```
1. Run Tests
   ↓
2. Generate Allure Report
   ├─ mvn allure:aggregate
   └─ Creates: target/site/allure/
   ↓
3. Prepare Report for Deployment
   ├─ Copy report to allure-report-deploy/
   └─ Create index.html for listing
   ↓
4. Deploy to GitHub Pages
   ├─ Push to gh-pages branch
   └─ Create directory: reports/[XML-file]-run-[number]/
   ↓
5. Send Email Notification
   └─ Include link to deployed report
```

### Report Organization

Each test run creates a new directory:
```
gh-pages (branch)
└── reports/
    ├── testng.xml-run-1/
    │   └── index.html (Allure report)
    ├── testng.xml-run-2/
    │   └── index.html
    └── src-test-resources-testng.xml-run-1/
        └── index.html
```

---

## 🔗 Accessing Reports

### Method 1: From Email
When you receive the test execution email, click the link:
```
View the comprehensive Allure test report: 
Allure Report - Run #N
```

### Method 2: GitHub Pages URL
Direct access to reports:
```
https://your-username.github.io/NOV25_HIRECORRECTO_API/reports/testng.xml-run-1/

Breakdown:
- your-username = Your GitHub username
- NOV25_HIRECORRECTO_API = Repository name
- testng.xml-run-1 = Test suite file and run number
```

### Method 3: Repository Settings
1. Go to your repository
2. Click **Settings → Pages**
3. Your site URL will be displayed

---

## 📊 Report Structure

Each Allure report includes:

```
index.html (Main report page)
├── Test Timeline
├── Test Statistics
│   ├── Total Tests
│   ├── Passed/Failed/Skipped
│   └── Duration
├── Tests by Status
├── Tests by Severity
├── History & Trends
└── Detailed Test Information
    ├── Test names
    ├── Execution logs
    ├── Screenshots (if any)
    └── Attachments
```

---

## 🎨 Customizing Report Names

The default format is: `[xml-file]-run-[number]`

Examples:
- `testng.xml-run-1/`
- `testng.xml-run-42/`
- `src-test-resources-testng.xml-run-1/`

To customize, edit the workflow:
```yaml
destination_dir: custom-name-${{ github.run_number }}
```

---

## 📈 Viewing Report History

GitHub Pages will keep all reports. To view previous runs:

1. Navigate to your GitHub Pages site
2. Manually change the URL to a previous run number:
   ```
   https://your-site.github.io/.../testng.xml-run-1/
   ```

3. Or check the Actions tab:
   - Go to **Actions → Regression Test Suite**
   - Select a previous run
   - Find the commit message showing deployment

---

## ✅ Workflow Configuration Details

### GitHub Pages Deployment Step

```yaml
- name: Deploy Allure Report to GitHub Pages
  if: always()
  uses: peaceiris/actions-gh-pages@v3
  with:
    github_token: ${{ secrets.GITHUB_TOKEN }}  # Built-in token
    publish_dir: allure-report-deploy          # Directory with reports
    destination_dir: reports/${{ github.event.inputs.test_xml_file }}-run-${{ github.run_number }}
    user_name: 'GitHub Actions Bot'
    user_email: 'actions@github.com'
    commit_message: 'Deploy Allure Report for ...'
    keep_files: true                           # Preserve previous reports
```

### Key Parameters

| Parameter | Description |
|-----------|-------------|
| `github_token` | Automatic GitHub token (no setup needed) |
| `publish_dir` | Directory containing the report |
| `destination_dir` | Where to place report in gh-pages |
| `keep_files` | Keep previous reports (prevent deletion) |

---

## 🔐 Security & Permissions

- ✅ GitHub Pages deployment uses built-in `GITHUB_TOKEN`
- ✅ No additional secrets needed for GitHub Pages
- ✅ Reports are public (adjust repository settings if needed)
- ✅ Deployment happens automatically with every test run

### Restrict Repository Access
If you want to make reports private:
1. Go to **Settings → General**
2. Change repository from "Public" to "Private"
3. Reports will only be accessible to collaborators

---

## 🐛 Troubleshooting

### Pages Not Building

**Problem**: Workflow succeeds but pages don't update

**Solution**:
1. Check **Settings → Pages**
2. Ensure "Build and deployment" is set to:
   - Source: Deploy from a branch
   - Branch: `gh-pages`
3. Wait 2-3 minutes for deployment

### Reports Not Visible

**Problem**: URL shows 404 or blank page

**Solution**:
1. Check the workflow logs for deployment step
2. Verify `gh-pages` branch exists in your repo
3. Check **Actions** tab for failed deployments
4. Wait for DNS propagation (usually < 5 minutes)

### Allure Report Not Generated

**Problem**: Report directory is empty

**Solution**:
1. Check if tests are generating results
2. Verify `target/surefire-reports/` exists
3. Check Maven Allure plugin is installed in `pom.xml`
4. Review workflow logs for `mvn allure:aggregate` step

### Old Reports Deleted

**Problem**: Previous run reports are missing

**Solution**:
- The workflow has `keep_files: true` to prevent deletion
- If still having issues, check for conflicting Actions jobs
- Ensure only one deployment job is running

---

## 📱 Mobile Friendly

Allure reports are responsive and work on:
- ✅ Desktop browsers
- ✅ Tablets
- ✅ Mobile phones
- ✅ Different screen sizes

---

## 🔄 Report Retention

GitHub Pages will store reports indefinitely unless you:
1. Delete them manually from `gh-pages` branch
2. Change repository settings
3. Delete the entire repository

**Best Practice**: Archive old reports periodically if storage is a concern.

---

## 📧 Email Integration

The email notification includes:

```
📈 Allure Report on GitHub Pages:
View the comprehensive Allure test report: 
[Link to Report - Run #N]

Report Path: gh-pages/reports/[xml-file]-run-[number]/

📦 Artifacts:
Raw Allure results available in artifacts: [Download link]
```

---

## 🎯 Complete Workflow

```
Manual Trigger
    ↓
Select XML file → Run Tests
    ↓
Generate Allure Report
    ↓
Deploy to GitHub Pages
    ├─ Create gh-pages branch (if needed)
    ├─ Push to: gh-pages/reports/[name]/
    └─ Keep previous reports
    ↓
Send Email with Links
    ├─ Direct report URL
    ├─ GitHub Actions run details
    └─ Download raw artifacts
    ↓
View Report
    ├─ Click email link
    ├─ Share with team
    └─ Archive for history
```

---

## 📚 Additional Resources

- [GitHub Pages Documentation](https://docs.github.com/en/pages)
- [Allure Report Documentation](https://docs.qameta.io/allure/)
- [GitHub Actions - peaceiris/actions-gh-pages](https://github.com/peaceiris/actions-gh-pages)

---

## ✅ Setup Checklist

- [ ] GitHub Pages enabled in Settings → Pages
- [ ] Branch set to `gh-pages` with `/ (root)` folder
- [ ] Workflow file has deployment step
- [ ] Email secrets configured
- [ ] First workflow run completed
- [ ] `gh-pages` branch created automatically
- [ ] Report deployed to GitHub Pages
- [ ] Can access report via URL
- [ ] Email notification received with link

---

## 🚀 First Run Checklist

1. Ensure `.github/workflows/regression.yml` is committed
2. Enable GitHub Pages in Settings
3. Go to Actions tab
4. Select "Regression Test Suite"
5. Click "Run workflow"
6. Select TestNG XML file
7. Wait for completion (~5-10 minutes)
8. Check email for report link
9. Click link to view Allure report
10. Celebrate! 🎉

---

**Last Updated**: January 2026
**Status**: ✅ Ready for Production

