# HireCorrect API Test Automation Framework - Architecture Documentation

## 📋 Table of Contents
1. [Overview](#overview)
2. [Framework Architecture](#framework-architecture)
3. [Package Structure](#package-structure)
4. [Design Patterns](#design-patterns)
5. [Data Flow](#data-flow)
6. [Class Relationships](#class-relationships)

---

## 🎯 Overview

This is a REST API test automation framework built with:
- **Language**: Java
- **Build Tool**: Maven
- **API Framework**: RestAssured
- **Test Framework**: TestNG
- **Design Pattern**: Service Layer Pattern, Builder Pattern
- **Purpose**: Automated testing of candidate screening APIs

---

## 🏗️ Framework Architecture

```
┌─────────────────────────────────────────────────────────────────────┐
│                         TEST LAYER                                  │
│  ┌──────────────────────────────────────────────────────────────┐   │
│  │         testScripts/CandidateScreeningTest.java              │   │
│  │  - Orchestrates test scenarios                              │   │
│  │  - Uses services to execute API calls                       │   │
│  └──────────────────────────────────────────────────────────────┘   │
└─────────────────────────────────────────────────────────────────────┘
                              ↓ ↓ ↓
┌─────────────────────────────────────────────────────────────────────┐
│                      SERVICE LAYER                                  │
│  ┌────────────┐  ┌──────────────┐  ┌─────────────┐                 │
│  │ MCQService │  │ VideoServices│  │AudioService │  etc...         │
│  └─────┬──────┘  └──────┬───────┘  └──────┬──────┘                 │
│        └─────────────────┴─────────────────┘                        │
│                          ↓                                           │
│              ┌───────────────────────┐                              │
│              │    BaseService        │                              │
│              │  - setJsonHeaders()   │                              │
│              │  - generateUploadUrl()│                              │
│              │  - uploadFileToAzure()│                              │
│              │  - updateCandidate()  │                              │
│              └───────────┬───────────┘                              │
└──────────────────────────┼──────────────────────────────────────────┘
                           ↓
┌─────────────────────────────────────────────────────────────────────┐
│                    BASE/CONTROL LAYER                               │
│  ┌──────────────────────────────────────────────────────────────┐   │
│  │            APIControlActions.java                            │   │
│  │  - setHeader() / setHeaders()                                │   │
│  │  - setBody() / setQueryParams()                              │   │
│  │  - executeGetAPI() / executePostAPI()                        │   │
│  │  - executePatchAPI() / executePutAPI()                       │   │
│  │  - RestAssured RequestSpecBuilder wrapper                    │   │
│  └──────────────────────────────────────────────────────────────┘   │
│  ┌──────────────────────────────────────────────────────────────┐   │
│  │            ScreeningControl.java                             │   │
│  │  - candidateScreeningId (static)                             │   │
│  │  - jobRoleID (static)                                        │   │
│  │  - jobApplicationID (static)                                 │   │
│  └──────────────────────────────────────────────────────────────┘   │
└─────────────────────────────────────────────────────────────────────┘
                           ↓
┌─────────────────────────────────────────────────────────────────────┐
│                      ENTITY/POJO LAYER                              │
│  ┌─────────────────────────────────────────────────────────────┐   │
│  │  entity/common/ (SHARED COMPONENTS)                         │   │
│  │  ┌──────────────────┐  ┌────────────────────┐              │   │
│  │  │CopyPasteAnalysis │  │  TypingAnalysis    │              │   │
│  │  │- CopyBreakdown   │  │  - PasteAnalysis   │              │   │
│  │  │- QuestionCopies  │  │  - FocusAnalysis   │              │   │
│  │  │- OptionCopies    │  │  - QualityAnalysis │              │   │
│  │  └──────────────────┘  └────────────────────┘              │   │
│  │  ┌──────────────────────────────────────────┐              │   │
│  │  │   FileGeneratorPayload                   │              │   │
│  │  │   BaseAnswerPayload (abstract)           │              │   │
│  │  └──────────────────────────────────────────┘              │   │
│  └─────────────────────────────────────────────────────────────┘   │
│                           ↑                                         │
│  ┌─────────────────────────────────────────────────────────────┐   │
│  │  entity/mcqPOJO/                                            │   │
│  │  - MCQRootPayload (uses shared CopyPasteAnalysis)          │   │
│  └─────────────────────────────────────────────────────────────┘   │
│  ┌─────────────────────────────────────────────────────────────┐   │
│  │  entity/audioPOJO/                                          │   │
│  │  - AudioAnswerPayload (uses shared CopyPasteAnalysis)      │   │
│  │  - AudioSubmitAnswerPayload                                │   │
│  └─────────────────────────────────────────────────────────────┘   │
│  ┌─────────────────────────────────────────────────────────────┐   │
│  │  entity/videoPOJO/                                          │   │
│  │  - VideoAnswerPayload (uses shared CopyPasteAnalysis)      │   │
│  │  - VideoAnswerAnalysisPayload                              │   │
│  └─────────────────────────────────────────────────────────────┘   │
│  ┌─────────────────────────────────────────────────────────────┐   │
│  │  entity/programmingPOJO/                                    │   │
│  │  - ProgrammingPayload (uses shared TypingAnalysis)         │   │
│  └─────────────────────────────────────────────────────────────┘   │
│  ┌─────────────────────────────────────────────────────────────┐   │
│  │  entity/subjectivePOJO/                                     │   │
│  │  - SubjectiveRootPayload (uses shared TypingAnalysis)      │   │
│  └─────────────────────────────────────────────────────────────┘   │
└─────────────────────────────────────────────────────────────────────┘
                           ↓
┌─────────────────────────────────────────────────────────────────────┐
│                      UTILITY LAYER                                  │
│  ┌──────────────────┐  ┌────────────────┐                          │
│  │  JavaToJSON      │  │ PropertyUtil   │                          │
│  │  - POJO to JSON  │  │ - Config read  │                          │
│  └──────────────────┘  └────────────────┘                          │
└─────────────────────────────────────────────────────────────────────┘
                           ↓
┌─────────────────────────────────────────────────────────────────────┐
│                   EXTERNAL DEPENDENCIES                             │
│  ┌──────────────┐  ┌──────────────┐  ┌────────────┐                │
│  │  RestAssured │  │    TestNG    │  │   Lombok   │                │
│  └──────────────┘  └──────────────┘  └────────────┘                │
└─────────────────────────────────────────────────────────────────────┘
```

---

## 📦 Package Structure

```
src/main/java/
├── base/
│   ├── APIControlActions.java      # Core API execution wrapper
│   ├── ApplicationConfig.java      # Configuration management
│   └── ScreeningControl.java       # Test data holder
│
├── constants/
│   ├── FilePaths.java              # File path constants
│   └── StatusCodes.java            # HTTP status codes
│
├── entity/
│   ├── common/                     # ✨ SHARED/REUSABLE POJOs
│   │   ├── CopyPasteAnalysis.java
│   │   ├── CopyBreakdown.java
│   │   ├── QuestionCopies.java
│   │   ├── OptionCopies.java
│   │   ├── FullQuestionCopies.java
│   │   ├── TypingAnalysis.java
│   │   ├── PasteAnalysis.java
│   │   ├── FocusAnalysis.java
│   │   ├── QualityAnalysis.java
│   │   ├── GlobalEventAnalysis.java
│   │   ├── CopyPasteCorrelations.java
│   │   ├── CopySourceDistribution.java
│   │   ├── FileGeneratorPayload.java
│   │   └── BaseAnswerPayload.java
│   │
│   ├── mcqPOJO/
│   │   └── MCQRootPayload.java
│   │
│   ├── audioPOJO/
│   │   ├── AudioAnswerPayload.java
│   │   └── AudioSubmitAnswerPayload.java
│   │
│   ├── videoPOJO/
│   │   ├── VideoAnswerPayload.java
│   │   └── VideoAnswerAnalysisPayload.java
│   │
│   ├── programmingPOJO/
│   │   └── ProgrammingPayload.java
│   │
│   └── subjectivePOJO/
│       └── SubjectiveRootPayload.java
│
├── services/
│   ├── BaseService.java            # ✨ NEW - Common service methods
│   ├── MCQService.java
│   ├── AudioService.java
│   ├── VideoServices.java
│   ├── SubjectiveService.java
│   ├── ProgrammingService.java
│   └── ScreeningDetailsServices.java
│
└── utility/
    ├── JavaToJSON.java             # JSON conversion
    └── PropertyUtil.java           # Property file reader

src/test/java/
└── testScripts/
    └── CandidateScreeningTest.java  # Test orchestration
```

---

## 🎨 Design Patterns

### 1. **Service Layer Pattern**

```
┌─────────────────────────────────────────────────────────┐
│  Test Layer (What to test)                             │
│  ↓                                                       │
│  Service Layer (How to test) - Business logic          │
│  ↓                                                       │
│  API Control Layer (Technical implementation)           │
│  ↓                                                       │
│  RestAssured (HTTP Client)                             │
└─────────────────────────────────────────────────────────┘
```

**Benefits**:
- Separates test logic from API implementation
- Reusable service methods
- Easy to maintain and extend

### 2. **Builder Pattern (Lombok)**

```java
// Instead of this:
MCQRootPayload payload = new MCQRootPayload();
payload.setQuestionId("123");
payload.setType("mcq");
// ... 15 more setters

// We use this:
MCQRootPayload payload = MCQRootPayload.builder()
    .questionId("123")
    .type("mcq")
    .build();
```

**Benefits**:
- Immutable objects
- Fluent API
- Type-safe construction

### 3. **Inheritance Pattern**

```
            BaseService
                 ↑
     ┌───────────┼───────────┬──────────────┐
     │           │           │              │
 MCQService  AudioService  VideoServices  etc...
```

**Benefits**:
- Code reuse (common methods in BaseService)
- Consistent behavior across all services
- Easy to add new services

---

## 🔄 Data Flow Diagram

### Complete API Test Flow

```
┌──────────────────────────────────────────────────────────────────────┐
│ STEP 1: Test Initialization                                         │
└──────────────────────────────────────────────────────────────────────┘
    @Test Method in CandidateScreeningTest
                ↓
    Set ScreeningControl static variables:
    - candidateScreeningId
    - jobRoleID
    - jobApplicationID

┌──────────────────────────────────────────────────────────────────────┐
│ STEP 2: Service Method Call                                         │
└──────────────────────────────────────────────────────────────────────┘
    Test calls → MCQService.submitMCQAnswer(...)
                ↓
    Service builds payload using:
    - MCQRootPayload.builder()
    - CopyPasteAnalysis.createDefault()  ← Shared utility
                ↓
    Converts to JSON using JavaToJSON.convertToJSON()

┌──────────────────────────────────────────────────────────────────────┐
│ STEP 3: API Request Preparation                                     │
└──────────────────────────────────────────────────────────────────────┘
    BaseService.updateCandidateResult(...)
                ↓
    setJsonHeaders()  → Sets Content-Type, Accept
    setBody(jsonPayload)
                ↓
    APIControlActions.executePatchAPI(endpoint)

┌──────────────────────────────────────────────────────────────────────┐
│ STEP 4: HTTP Request Execution                                      │
└──────────────────────────────────────────────────────────────────────┘
    APIControlActions builds RequestSpecBuilder
                ↓
    RestAssured.given()
        .spec(requestSpec)
        .baseUri(from config)
        .when()
        .patch(endpoint)
        .then()
        .extract()
        .response()

┌──────────────────────────────────────────────────────────────────────┐
│ STEP 5: Response Handling                                           │
└──────────────────────────────────────────────────────────────────────┘
    Response returned to Service
                ↓
    Service returns Response to Test
                ↓
    Test validates:
    - Status code
    - Response body
    - Business logic
```

---

## 🔗 Class Relationships

### Service Layer Hierarchy

```
                    APIControlActions
                           ↑
                           │ extends
                           │
                      BaseService
                           ↑
         ┌─────────────────┼─────────────────┬──────────────────┐
         │                 │                 │                  │
    MCQService      AudioService      VideoServices      SubjectiveService
         │                 │                 │                  │
         │                 │                 │                  │
    Uses MCQ         Uses Audio         Uses Video        Uses Subjective
    POJOs            POJOs              POJOs             POJOs
         │                 │                 │                  │
         └─────────────────┴─────────────────┴──────────────────┘
                           │
                   All use entity/common/
                   - CopyPasteAnalysis
                   - TypingAnalysis
                   - FileGeneratorPayload
```

### Entity Layer Relationships

```
                    entity/common/
                           │
        ┌──────────────────┼──────────────────┐
        │                  │                  │
   CopyPasteAnalysis  TypingAnalysis  FileGeneratorPayload
        │                  │                  │
        │                  │                  │
   Used by:           Used by:          Used by:
   - MCQRootPayload   - Programming     - Audio
   - AudioPayloads    - Subjective      - Video
   - VideoPayloads
```

### Composition Diagram

```
MCQRootPayload
├── questionId: String
├── type: String
├── skill: String
└── copyPasteAnalysis: CopyPasteAnalysis  ← Shared component
    ├── totalDuration: Integer
    ├── riskLevel: String
    └── copyBreakdown: CopyBreakdown      ← Shared component
        ├── questionCopies: QuestionCopies ← Shared component
        ├── optionCopies: OptionCopies     ← Shared component
        └── fullQuestionCopies: FullQuestionCopies ← Shared component
```

---

## 📊 Before vs After Optimization

### Code Duplication Eliminated

**BEFORE** (❌ Duplicated across 5 files):
```
MCQRootPayload.CopyPasteAnalysis
AudioAnswerPayload.CopyPasteAnalysis
AudioSubmitAnswerPayload.CopyPasteAnalysis
VideoAnswerPayload.CopyPasteAnalysis
VideoAnswerAnalysisPayload.CopyPasteAnalysis
```

**AFTER** (✅ Single shared class):
```
entity.common.CopyPasteAnalysis (used by all)
```

### Lines of Code Reduction

```
Component                  | Before | After | Savings
─────────────────────────────────────────────────────
Entity POJOs              |  ~700  | ~350  |  50%
Service Classes           |  ~400  | ~280  |  30%
Duplicate Inner Classes   |   25   |   0   |  100%
─────────────────────────────────────────────────────
Total Maintainable Code   | ~1100  | ~630  |  43% ↓
```

---

## 🚀 Key Features

### ✅ Implemented Optimizations

1. **Shared Entity Classes** (`entity/common/`)
   - Eliminated 25+ duplicate inner classes
   - Single source of truth for common structures

2. **BaseService Class**
   - Common HTTP operations
   - Reusable header setup
   - File upload utilities
   - Reduces boilerplate by ~40%

3. **Factory Methods**
   - `CopyPasteAnalysis.createDefault()`
   - `TypingAnalysis.createDefault()`
   - `FileGeneratorPayload.forAudio()`
   - `FileGeneratorPayload.forVideo()`

4. **Encapsulation**
   - Changed `public` fields to `private`
   - Proper use of Lombok @Getter/@Setter

5. **Static Utilities**
   - Centralized header creation
   - Reusable payload builders

---

## 🎯 Usage Examples

### Example 1: Submit MCQ Answer

```java
@Test
public void testMCQSubmission() {
    // Initialize screening context
    ScreeningControl.candidateScreeningId = "screen123";
    ScreeningControl.jobRoleID = "role456";
    ScreeningControl.jobApplicationID = "app789";
    
    // Create service
    MCQService mcqService = new MCQService();
    
    // Submit answer
    Response response = mcqService.submitMCQAnswer(
        "question123",
        "3.0",
        "Option A"
    );
    
    // Validate
    Assert.assertEquals(200, response.statusCode());
}
```

### Example 2: Submit Video Answer

```java
@Test
public void testVideoSubmission() {
    VideoServices videoService = new VideoServices();
    
    Response response = videoService.submitVideoAnswer(
        "videoQ123",
        "5.0",
        "C:\\path\\to\\video.mp4"
    );
    
    Assert.assertEquals(200, response.statusCode());
}
```

---

## 📝 Configuration

### Environment Configuration (`envConfig.properties`)
```properties
qa=https://qa-api.example.com
staging=https://staging-api.example.com
production=https://api.example.com
```

### User Configuration (`userConfig.properties`)
```properties
username=testuser@example.com
password=SecurePass123
```

---

## 🔧 How to Extend

### Adding a New Question Type

1. **Create POJO** in `entity/newTypePOJO/`
   ```java
   public class NewTypePayload {
       private String questionId;
       private CopyPasteAnalysis copyPasteAnalysis; // Reuse shared
   }
   ```

2. **Create Service** extending `BaseService`
   ```java
   public class NewTypeService extends BaseService {
       public Response submitAnswer(...) {
           // Use inherited methods
           String payload = buildPayload();
           return updateCandidateResult(id, payload);
       }
   }
   ```

3. **Write Test** in `CandidateScreeningTest`
   ```java
   @Test
   public void testNewType() {
       NewTypeService service = new NewTypeService();
       Response response = service.submitAnswer(...);
       Assert.assertEquals(200, response.statusCode());
   }
   ```

---

## 🎓 Learning Path

For someone new to this framework:

1. **Start Here**: `entity/common/` - Understand shared components
2. **Then Study**: `BaseService.java` - Learn common operations
3. **Next**: `MCQService.java` - Simplest service example
4. **Finally**: `CandidateScreeningTest.java` - See it all in action

---

## 📚 Additional Resources

- **RestAssured Docs**: https://rest-assured.io/
- **TestNG Docs**: https://testng.org/doc/
- **Lombok Docs**: https://projectlombok.org/

---

**Framework Version**: 2.0 (Optimized)  
**Last Updated**: December 30, 2025  
**Maintained By**: QA Automation Team

