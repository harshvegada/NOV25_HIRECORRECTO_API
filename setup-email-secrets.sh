#!/bin/bash

# GitHub Actions Secrets Configuration Helper
# This script helps you set up GitHub Actions secrets for email notifications

echo "╔════════════════════════════════════════════════════════════════╗"
echo "║  GitHub Actions Email Secrets Configuration Helper            ║"
echo "╚════════════════════════════════════════════════════════════════╝"
echo ""

# Detect email provider
echo "Select your email provider:"
echo "1. Gmail"
echo "2. Outlook/Office365"
echo "3. Yahoo"
echo "4. SendGrid"
echo "5. AWS SES"
echo "6. Custom (Other Provider)"
echo ""
read -p "Enter your choice (1-6): " choice

case $choice in
    1)
        echo ""
        echo "📧 Gmail Configuration"
        echo "────────────────────────────────────────"
        echo "EMAIL_SERVER=smtp.gmail.com"
        echo "EMAIL_PORT=587"
        echo ""
        echo "Steps to get EMAIL_PASSWORD:"
        echo "1. Enable 2-Factor Authentication: https://myaccount.google.com/security"
        echo "2. Generate App Password: https://myaccount.google.com/apppasswords"
        echo "3. Select 'Mail' and 'Windows Computer'"
        echo "4. Copy the 16-character password"
        echo ""
        echo "EMAIL_USERNAME=your-email@gmail.com"
        echo "EMAIL_PASSWORD=xxxx xxxx xxxx xxxx (from step above)"
        echo "EMAIL_FROM=your-email@gmail.com"
        ;;
    2)
        echo ""
        echo "📧 Outlook/Office365 Configuration"
        echo "────────────────────────────────────────"
        echo "EMAIL_SERVER=smtp.office365.com"
        echo "EMAIL_PORT=587"
        echo "EMAIL_USERNAME=your-email@company.com"
        echo "EMAIL_PASSWORD=your-office365-password"
        echo "EMAIL_FROM=your-email@company.com"
        ;;
    3)
        echo ""
        echo "📧 Yahoo Configuration"
        echo "────────────────────────────────────────"
        echo "EMAIL_SERVER=smtp.mail.yahoo.com"
        echo "EMAIL_PORT=587"
        echo ""
        echo "Note: Use App Password if 2FA is enabled"
        echo "Steps: https://login.yahoo.com/account/security"
        echo ""
        echo "EMAIL_USERNAME=your-email@yahoo.com"
        echo "EMAIL_PASSWORD=your-app-password"
        echo "EMAIL_FROM=your-email@yahoo.com"
        ;;
    4)
        echo ""
        echo "📧 SendGrid Configuration"
        echo "────────────────────────────────────────"
        echo "EMAIL_SERVER=smtp.sendgrid.net"
        echo "EMAIL_PORT=587"
        echo "EMAIL_USERNAME=apikey"
        echo "EMAIL_PASSWORD=SG.xxxxxxxxxxxxxxxxxxxx (your SendGrid API key)"
        echo "EMAIL_FROM=noreply@yourdomain.com"
        ;;
    5)
        echo ""
        echo "📧 AWS SES Configuration"
        echo "────────────────────────────────────────"
        echo "EMAIL_SERVER=email-smtp.us-east-1.amazonaws.com"
        echo "(Replace us-east-1 with your AWS region)"
        echo "EMAIL_PORT=587"
        echo "EMAIL_USERNAME=AKIA... (AWS Access Key ID)"
        echo "EMAIL_PASSWORD=.... (AWS Secret Access Key)"
        echo "EMAIL_FROM=your-verified-email@domain.com"
        ;;
    6)
        echo ""
        echo "📧 Custom Provider Configuration"
        echo "────────────────────────────────────────"
        read -p "Enter SMTP Server Address: " server
        read -p "Enter SMTP Port: " port
        read -p "Enter Email Username: " username
        read -p "Enter Email Password: " password
        read -p "Enter From Email Address: " from_email

        echo ""
        echo "EMAIL_SERVER=$server"
        echo "EMAIL_PORT=$port"
        echo "EMAIL_USERNAME=$username"
        echo "EMAIL_PASSWORD=$password"
        echo "EMAIL_FROM=$from_email"
        ;;
    *)
        echo "Invalid choice!"
        exit 1
        ;;
esac

echo ""
echo "════════════════════════════════════════════════════════════════"
echo ""
echo "🔐 GitHub Secrets Setup Instructions:"
echo ""
echo "1. Go to your GitHub Repository"
echo "2. Navigate to: Settings → Secrets and variables → Actions"
echo "3. Click 'New repository secret' and add each of these:"
echo ""
echo "   • EMAIL_SERVER"
echo "   • EMAIL_PORT"
echo "   • EMAIL_USERNAME"
echo "   • EMAIL_PASSWORD"
echo "   • EMAIL_FROM"
echo ""
echo "4. Copy the values from above into each secret"
echo "5. Click 'Add secret' for each one"
echo ""
echo "════════════════════════════════════════════════════════════════"
echo ""
echo "✅ After adding secrets:"
echo "   • Push your changes to GitHub"
echo "   • Go to Actions tab"
echo "   • Select 'Regression Test Suite'"
echo "   • Click 'Run workflow'"
echo "   • Select TestNG XML file from dropdown"
echo "   • Workflow will run and send email notification"
echo ""
echo "Happy Testing! 🚀"

