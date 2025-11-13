# 🛒 Grocery Store Discount Service

A simple **Spring Boot (Java 21)** application that calculates grocery order totals with discounts applied.  
The service supports discounts for:
- 🍞 Bread (based on freshness)
- 🍺 Beer (based on pack size and type)
- 🥦 Vegetables (based on weight range)

It also exposes endpoints to list all **current discounts** and **product prices**.

---

## ⚙️ How to Run

### 🧩 Option 1 — Run in your IDE
Open the project in your IDE (e.g., IntelliJ IDEA or Eclipse),  
then run the `GroceryStoreApplication` class.

### 💻 Option 2 — Run via Maven
```bash
mvn clean compile spring-boot:run
```

The application will start at:  
👉 http://localhost:8080

---

### ⚙️ Running on a Different Port

By default, the application runs on port **8080**.  
If that port is already in use, you can run it on a different port in two ways:

**Option 1 – Override via Maven command:**
```bash
mvn spring-boot:run -Dspring-boot.run.arguments="--server.port=9090"
```

**Option 2 – Update the configuration:**  
Open `src/main/resources/application.properties` and add the line:

```properties
server.port=9091
```

---

## 🗄️ Database

When the application is up and running, you can inspect the in-memory H2 database by visiting:  
👉 http://localhost:8080/h2-console

**Default credentials:**
- **JDBC URL:** `jdbc:h2:mem:testdb`
- **Username:** `sa`
- **Password:** *(leave empty)*

---

## 📘 Swagger UI

While the application is running, explore all available endpoints (orders, discounts, prices):  
👉 http://localhost:8080/swagger-ui/index.html#/

---

## 📮 Postman Collection

You can easily test all API endpoints using the provided Postman collection:

**File:**  
`/postman/Grocery Store.postman_collection.json`

**How to use:**
1. Open Postman.
2. Click **Import** → **files**.
3. Select the collection file from the `postman` folder.
4. Run the requests directly against your local server (`http://localhost:8080`).

## 🧪 Running Tests

The project includes comprehensive tests for all product types — **bread**, **beer**, and **vegetables** —  
covering validation logic, discount rules, and error handling.

### ▶️ Run all tests via Maven
```bash
mvn test
```

### 🧠 Run individual test classes in your IDE

You can also open any test class (e.g.,  
`OrdersBreadsTests`, `OrdersBeersTests`, or `OrdersVegetablesTests`)  
and execute it directly from your IDE using the **Run Test** option.

