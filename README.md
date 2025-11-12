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

## 🗄️ Database

You can inspect the in-memory H2 database by visiting:  
👉 http://localhost:8080/h2-console

**Default credentials:**
- **JDBC URL:** `jdbc:h2:mem:testdb`
- **Username:** `sa`
- **Password:** *(leave empty)*

---

## 📘 Swagger UI

Explore all available endpoints (orders, discounts, prices):  
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
