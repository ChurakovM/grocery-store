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

## Order Rules

- **One order can have many items.**
- **Allowed item types:** BREAD, VEGETABLE, BEER.

### Breads
- Always have discounts: “buy 1 take 2” or “buy 1 take 3”.
- Discounts based on bread age:
    - 0–1 days old → **no discount**
    - 3 days old → **buy 1 take 2**
    - 6 days old → **buy 1 take 3**
    - Older than 6 days → **cannot be added** to orders

### Vegetables
- Discount is **percentage-based** depending on total weight of vegetables in the order:
    - 0g – 100g → **5% discount**
    - 101g – 500g → **7% discount**
    - Above 500g → **10% discount**
- Discount applies to **all vegetable items** in the order.

### Beers
- Discounts apply **only for packs of 6 beers**, fixed per pack type:
    - Belgian pack → €3.00 per pack
    - Dutch pack → €2.00 per pack
    - German pack → €4.00 per pack
- Single bottles/cans **can be added** to the order but **no discount** applies.
- Buying 6 separate bottles of the same beer **counts as one discounted pack**.


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

