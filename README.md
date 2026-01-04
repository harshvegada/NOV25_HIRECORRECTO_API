# 🚀 HireCorrect API Test Automation Framework

## 📖 Overview

A comprehensive REST API test automation framework for the HireCorrect candidate screening platform. Built with Java, RestAssured, and TestNG, this framework provides a clean, maintainable architecture for API testing.

## 🎯 Key Features

✅ **Clean Architecture** - Service layer pattern with clear separation of concerns  
✅ **Reusable Components** - Shared entity classes eliminate code duplication  
✅ **Builder Pattern** - Fluent API for payload construction using Lombok  
✅ **Type Safety** - Strong typing with POJOs for all API requests/responses  
✅ **Easy Configuration** - Environment-based configuration management  
✅ **Comprehensive Testing** - Support for MCQ, Audio, Video, Programming, and Subjective questions  

## 📚 Documentation

- **[ARCHITECTURE.md](./ARCHITECTURE.md)** - Complete framework architecture with diagrams
- **[FLOW_DIAGRAMS.md](./FLOW_DIAGRAMS.md)** - Visual flow diagrams for different scenarios
- **This README** - Quick start and usage guide

## 🏗️ Framework Structure (Simplified)

```
┌─────────────────────────────────────────────────┐
│  Tests                                          │
│  └─ CandidateScreeningTest                     │
└─────────────┬───────────────────────────────────┘
              ↓
┌─────────────────────────────────────────────────┐
│  Services (What to test)                        │
│  ├─ MCQService                                  │
│  ├─ VideoServices                               │
│  ├─ AudioService                                │
│  └─ BaseService (common operations)            │
└─────────────┬───────────────────────────────────┘
              ↓
┌─────────────────────────────────────────────────┐
│  API Control (How to test)                      │
│  └─ APIControlActions (RestAssured wrapper)    │
└─────────────┬───────────────────────────────────┘
              ↓
┌─────────────────────────────────────────────────┐
│  Entities (Data models)                         │
│  ├─ entity/common/ (Shared POJOs) ✨            │
│  ├─ entity/mcqPOJO/                             │
│  ├─ entity/videoPOJO/                           │
│  └─ entity/audioPOJO/                           │
└─────────────────────────────────────────────────┘
```

## 🚀 Quick Start

### Prerequisites

- Java 11 or higher
- Maven 3.6+
- IDE (IntelliJ IDEA recommended)

### Setup

1. **Clone the repository**
   ```bash
   cd NOV25_HIRECORRECTO_API
   ```

2. **Install dependencies**
   ```bash
   mvn clean install
   ```

3. **Configure environment**
   
   Edit `src/main/resources/config/envConfig.properties`:
   ```properties
   qa=https://qa-api.hirecorrect.com
   staging=https://staging-api.hirecorrect.com
   production=https://api.hirecorrect.com
   ```

4. **Run tests**
   ```bash
   mvn test
   ```

## 💻 Usage Examples

### Example 1: Submit MCQ Answer

```java
import services.MCQService;
import base.ScreeningControl;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

public class MCQTest {
    
    @Test
    public void testMCQSubmission() {
        // Setup context
        ScreeningControl.candidateScreeningId = "screen_123";
        ScreeningControl.jobRoleID = "role_456";
        ScreeningControl.jobApplicationID = "app_789";
        
        // Create service and submit answer
        MCQService mcqService = new MCQService();
        Response response = mcqService.submitMCQAnswer(
            "question_001",
            "3.5",  // experience in years
            "Option A"
        );
        
        // Verify
        Assert.assertEquals(200, response.statusCode());
        Assert.assertTrue(response.jsonPath().getBoolean("success"));
    }
}
```

### Example 2: Submit Video Answer

```java
import services.VideoServices;
import base.ScreeningControl;
import io.restassured.response.Response;
import org.testng.annotations.Test;

public class VideoTest {
    
    @Test
    public void testVideoSubmission() {
        // Setup
        ScreeningControl.candidateScreeningId = "screen_123";
        ScreeningControl.jobRoleID = "role_456";
        ScreeningControl.jobApplicationID = "app_789";
        
        // Submit video
        VideoServices videoService = new VideoServices();
        Response response = videoService.submitVideoAnswer(
            "video_question_001",
            "5.0",
            "C:\\path\\to\\video.mp4"
        );
        
        // Verify
        Assert.assertEquals(200, response.statusCode());
    }
}
```

### Example 3: Using Shared Components

```java
import entity.common.CopyPasteAnalysis;
import entity.common.TypingAnalysis;
import entity.common.FileGeneratorPayload;

// Create default copy-paste analysis
CopyPasteAnalysis copyPaste = CopyPasteAnalysis.createDefault(5000);

// Create typing analysis
TypingAnalysis typing = TypingAnalysis.createDefault(60000, 500, 120);

// Create file upload payload
FileGeneratorPayload videoPayload = FileGeneratorPayload.forVideo();
FileGeneratorPayload audioPayload = FileGeneratorPayload.forAudio();
```

## 📦 Project Structure

```
NOV25_HIRECORRECTO_API/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   ├── base/
│   │   │   │   ├── APIControlActions.java
│   │   │   │   ├── ApplicationConfig.java
│   │   │   │   └── ScreeningControl.java
│   │   │   ├── constants/
│   │   │   │   ├── FilePaths.java
│   │   │   │   └── StatusCodes.java
│   │   │   ├── entity/
│   │   │   │   ├── common/               ✨ NEW - Shared POJOs
│   │   │   │   │   ├── CopyPasteAnalysis.java
│   │   │   │   │   ├── TypingAnalysis.java
│   │   │   │   │   ├── FileGeneratorPayload.java
│   │   │   │   │   └── ... (11 shared classes)
│   │   │   │   ├── mcqPOJO/
│   │   │   │   ├── audioPOJO/
│   │   │   │   ├── videoPOJO/
│   │   │   │   ├── programmingPOJO/
│   │   │   │   └── subjectivePOJO/
│   │   │   ├── services/
│   │   │   │   ├── BaseService.java      ✨ NEW - Common service
│   │   │   │   ├── MCQService.java
│   │   │   │   ├── AudioService.java
│   │   │   │   ├── VideoServices.java
│   │   │   │   ├── SubjectiveService.java
│   │   │   │   ├── ProgrammingService.java
│   │   │   │   └── ScreeningDetailsServices.java
│   │   │   └── utility/
│   │   │       ├── JavaToJSON.java
│   │   │       └── PropertyUtil.java
│   │   └── resources/
│   │       └── config/
│   │           ├── envConfig.properties
│   │           └── userConfig.properties
│   └── test/
│       ├── java/
│       │   └── testScripts/
│       │       └── CandidateScreeningTest.java
│       └── resources/
│           ├── schemaFiles/
│           └── testData/
├── pom.xml
├── ARCHITECTURE.md            ✨ NEW - Architecture guide
├── FLOW_DIAGRAMS.md           ✨ NEW - Visual flows
└── README.md                  ← You are here
```

## 🎨 Design Patterns Used

### 1. Service Layer Pattern
Separates business logic (services) from technical implementation (API controls)

### 2. Builder Pattern (via Lombok)
Clean, fluent API for object construction

### 3. Inheritance
BaseService provides common functionality to all service classes

### 4. Factory Methods
Static factory methods for creating default objects:
- `CopyPasteAnalysis.createDefault()`
- `TypingAnalysis.createDefault()`
- `FileGeneratorPayload.forVideo()`

## 🔑 Key Components

### BaseService
Common operations inherited by all service classes:

```java
public abstract class BaseService extends APIControlActions {
    // Set standard JSON headers
    protected void setJsonHeaders();
    
    // Generate upload URL for media files
    protected Response generateUploadUrl(FileGeneratorPayload payload);
    
    // Upload file to Azure
    protected Response uploadFileToAzure(String azureURL, String filePath);
    
    // Update candidate result
    protected Response updateCandidateResult(String screeningId, String payload);
    
    // Analyze response
    protected Response analyzeResponseUri(String payload);
}
```

### Shared Entities (entity/common/)

Before optimization: **25+ duplicate inner classes** across multiple files  
After optimization: **11 shared classes** used everywhere  

**Benefits:**
- ✅ 50% reduction in entity code
- ✅ Single source of truth
- ✅ Easier maintenance
- ✅ Consistent structure

## 📊 Optimization Results

| Metric | Before | After | Improvement |
|--------|--------|-------|-------------|
| Entity LOC | ~700 | ~350 | 50% ↓ |
| Service LOC | ~400 | ~280 | 30% ↓ |
| Duplicate Classes | 25+ | 0 | 100% ↓ |
| Code Reusability | Low | High | ↑↑↑ |
| Maintainability | Medium | High | ↑↑ |

## 🧪 Test Execution

### Run all tests
```bash
mvn test
```

### Run specific test class
```bash
mvn test -Dtest=CandidateScreeningTest
```

### Run specific test method
```bash
mvn test -Dtest=CandidateScreeningTest#testMCQSubmission
```

### Run with specific environment
```bash
mvn test -Denv=qa
mvn test -Denv=staging
```

## 📝 Configuration

### Environment Configuration
File: `src/main/resources/config/envConfig.properties`

```properties
qa=https://qa-api.example.com
staging=https://staging-api.example.com
production=https://api.example.com
```

### User Configuration
File: `src/main/resources/config/userConfig.properties`

```properties
username=test.user@example.com
password=SecurePassword123
```

## 🔧 Extending the Framework

### Adding a New Question Type

1. **Create POJO**
   ```java
   // entity/newTypePOJO/NewTypePayload.java
   @Builder
   @Getter
   @Setter
   public class NewTypePayload {
       private String questionId;
       private CopyPasteAnalysis copyPasteAnalysis; // Reuse shared
   }
   ```

2. **Create Service**
   ```java
   // services/NewTypeService.java
   public class NewTypeService extends BaseService {
       public Response submitAnswer(String questionId, String answer) {
           NewTypePayload payload = NewTypePayload.builder()
               .questionId(questionId)
               .copyPasteAnalysis(CopyPasteAnalysis.createDefault(1000))
               .build();
           
           String json = JavaToJSON.convertToJSON(payload);
           return updateCandidateResult(ScreeningControl.candidateScreeningId, json);
       }
   }
   ```

3. **Write Test**
   ```java
   @Test
   public void testNewType() {
       NewTypeService service = new NewTypeService();
       Response response = service.submitAnswer("Q123", "answer");
       Assert.assertEquals(200, response.statusCode());
   }
   ```

## 🐛 Troubleshooting

### Issue: Tests fail with 401 Unauthorized
**Solution:** Check your credentials in `userConfig.properties`

### Issue: Connection refused
**Solution:** Verify the base URL in `envConfig.properties` is correct

### Issue: File upload fails
**Solution:** Ensure the file path is absolute and the file exists

### Issue: JSON serialization error
**Solution:** Verify all POJO fields have proper getters/setters (use Lombok)

## 📚 Learning Resources

### For Beginners
1. Start with `ARCHITECTURE.md` - understand the structure
2. Read `FLOW_DIAGRAMS.md` - see visual flows
3. Run example tests - hands-on practice
4. Modify existing tests - learn by doing

### For Advanced Users
1. Study `BaseService.java` - common patterns
2. Review shared entities - reusability patterns
3. Explore service implementations - business logic
4. Extend framework - add new features

## 🤝 Contributing

When adding new features:
1. Follow existing patterns (Service Layer, Builder)
2. Reuse shared entities when possible
3. Add tests for new functionality
4. Update documentation
5. Keep code clean and maintainable

## 📞 Support

For questions or issues:
1. Check documentation (ARCHITECTURE.md, FLOW_DIAGRAMS.md)
2. Review examples in this README
3. Contact QA team lead

## 📄 License

Internal use only - HireCorrect QA Team

---

## 🎯 Quick Reference

### Common Tasks

| Task | Code |
|------|------|
| Submit MCQ | `mcqService.submitMCQAnswer(qId, exp, ans)` |
| Submit Video | `videoService.submitVideoAnswer(qId, exp, path)` |
| Submit Audio | `audioService.submitAudioAnswer(qId, exp, path)` |
| Get Details | `screeningService.getScreeningDetails(testId)` |
| Create CopyPaste | `CopyPasteAnalysis.createDefault(duration)` |
| Create Typing | `TypingAnalysis.createDefault(dur, chars, keys)` |

### Test Template

```java
@Test
public void testName() {
    // 1. Setup context
    ScreeningControl.candidateScreeningId = "id";
    ScreeningControl.jobRoleID = "role";
    ScreeningControl.jobApplicationID = "app";
    
    // 2. Execute service
    Service service = new Service();
    Response response = service.method(params);
    
    // 3. Verify results
    Assert.assertEquals(200, response.statusCode());
}
```

---

**Last Updated:** December 30, 2025  
**Version:** 2.0 (Optimized)  
**Maintained by:** QA Automation Team

Happy Testing! 🚀

