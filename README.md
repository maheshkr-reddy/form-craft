# FormCraft — Spring Boot

A complete Spring Boot **FormCraft Visual Form Builder** application.



## Project structure

```
form-builder-spring/
├── pom.xml                                          # Maven build (Spring Boot 3.2.5, Java 17)
├── README.md
└── src/main/
    ├── java/com/formcraft/formbuilder/
    │   ├── FormBuilderApplication.java              # @SpringBootApplication entry point
    │   ├── controller/
    │   │   └── FormController.java                  # All routes (page + REST API)
    │   ├── model/
    │   │   ├── Form.java                            # Form entity 
    │   │   ├── SaveFormRequest.java                 # POST body DTO
    │   │   └── SaveFormResponse.java                # POST response DTO
    │   └── service/
    │       └── FormService.java                     # In-memory form store
    └── resources/
        ├── application.properties                   # Server config (port=3000)
        ├── static/
        │   ├── css/builder.css                      # Unchanged from original
        │   ├── css/preview.css                      # Unchanged from original
        │   ├── js/builder.js                        # Unchanged from original
        │   └── js/renderer.js                       # Unchanged from original
        └── templates/
            ├── builder.html                         # Converted from builder.ejs
            ├── preview.html                         # Converted from preview.ejs
            └── error/
                └── 404.html                         # Form-not-found page
```

## Requirements

- **Java 17+**
- **Maven 3.6+**

## Running the application

```bash
# Build
mvn clean package

# Run
mvn spring-boot:run

# Or run the JAR directly
java -jar target/form-builder-1.0.0.jar
```

The app starts at **http://localhost:3000**

<img width="1891" height="953" alt="form-craft" src="https://github.com/user-attachments/assets/a5497149-7a17-4509-b804-981e022a2576" />


<img width="1558" height="953" alt="form-craft-preview" src="https://github.com/user-attachments/assets/4d59c115-0ed5-47e0-9f7f-8f61035a7adf" />


## API endpoints

| Method | Path | Description |
|--------|------|-------------|
| `GET` | `/` | Builder UI |
| `GET` | `/preview/{id}` | Preview a saved form |
| `POST` | `/api/forms/save` | Save a form |
| `GET` | `/api/forms` | List all forms |
| `GET` | `/api/forms/{id}` | Get one form |

### POST /api/forms/save — request body

```json
{
  "id": "optional-existing-id",
  "name": "My Form",
  "schema": [...],
  "formsJson": null
}
```

### POST /api/forms/save — response

```json
{ "success": true, "id": "form_1717920000000" }
```

## Upgrading to persistent storage (optional)

The current `FormService` stores forms in a `LinkedHashMap` — data is lost on restart.

To persist forms, add these dependencies to `pom.xml`:

```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-data-jpa</artifactId>
</dependency>
<dependency>
    <groupId>com.h2database</groupId>
    <artifactId>h2</artifactId>
    <scope>runtime</scope>
</dependency>
```

Then:
1. Annotate `Form` with `@Entity`, `@Id`, `@Column(columnDefinition="TEXT")` for `schema`/`formsJson`.
2. Create a `FormRepository extends JpaRepository<Form, String>`.
3. Inject the repository into `FormService` and replace the `LinkedHashMap` calls with `repository.save()`, `repository.findAll()`, `repository.findById()`.
