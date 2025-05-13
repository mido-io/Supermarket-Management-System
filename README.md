# Supermarket Management System

**A JavaFX-based application for managing supermarket operations, including product and employee management for admins, and purchase handling with receipt generation for employees.**

![Java](https://img.shields.io/badge/Java-17-blue)
![JavaFX](https://img.shields.io/badge/JavaFX-17-orange)
![MySQL](https://img.shields.io/badge/MySQL-8.0+-blue)

## Features
###  User Authentication
- Secure login system for both **Admin** and **Employee** roles.
- Credential validation fully functional.

### Admin Dashboard
- View sales and income statistics.
- Manage products and employees.
- Partial implementation of sales trend analytics (advanced analytics planned).

### Employee Interface
- Process customer purchases.
- Add items to cart, calculate change, and generate receipts.
- Basic functionality complete; UI enhancements pending.

### Database Integration
- Powered by **MySQL** for reliable data storage.
- Core tables implemented: Admins, Employees, Products, Customers, Receipts.
- Additional database features and tables are planned.

### User Interface
- Built using **FXML** for structured, responsive layouts.
- Styled with **CSS** for a modern and clean look.

## Incomplete Features
- **Sales Analytics**: Advanced reporting (e.g., monthly trends, product performance) is not yet implemented.
- **Inventory Management**: Full stock tracking and alerts for low inventory are planned.
- **Employee Features**: Shift management and performance tracking are incomplete.
- **Testing**: Unit tests are limited; full test coverage is a future goal.


## Prerequisites
- **Java**: JDK 17
- **Maven**: For dependency management
- **MySQL**: MySQL Server 8.0+
- **IDE**: IntelliJ IDEA (recommended)
- **Images**: Ensure the following are in `src/main/resources/com/supermarket/images/`:
    - logo.png
    - escIcon.png
    - searchIcon.png
    - LogOutIcon.png
    - moneyIcon.png
    - todaysales-icon.png
    - inventory-stutsIcon.png

## Setup Instructions

### 1. Clone the Repository
```bash
git clone https://github.com/MideoHub/Supermarket-Management-System.git
cd Supermarket-Management-System
```
## 2. Configure MySQL

1. **Install MySQL and start the server**  
   Ensure MySQL Server 8.0+ is installed and running

2. **Create the database and tables**:
   ```bash
   mysql -u root -p < sql/schema.sql
   mysql -u root -p < sql/seed-data.sql
   ```

3.**Update database credentials in DatabaseConnection.java:**

```java
private static final String URL = "jdbc:mysql://localhost:3306/supermarket";
private static final String USER = "root"; 
private static final String PASSWORD = "";
```
## 3. Setup in IntelliJ IDEA

### Open Project
1. Launch IntelliJ IDEA
2. Select "Open" and navigate to the `Supermarket-Management-System` directory
3. Click "Load Maven Project" if prompted

### Configure JDK
1. Go to `File > Project Structure > Project`
2. Set Project SDK to JDK 17

### Add JavaFX SDK
1. Download [JavaFX SDK 17](https://gluonhq.com/products/javafx/)
2. Go to `File > Project Structure > Libraries`
3. Click "+" → "Java"
4. Select the JavaFX SDK `lib` folder
5. Click "OK"

### Add MySQL Connector
1. Go to `File > Project Structure > Modules > Dependencies`
2. Click "+" → "JARs or directories"
3. Select `lib/mysql-connector-j-8.3.0.jar`
4. Click "OK"

### Configure Run Settings
1. Go to `Run > Edit Configurations`
2. Add new "Application" configuration with these settings:
- Main class: com.supermarket.Main
- VM options: --module-path /path/to/javafx-sdk-17/lib --add-modules javafx.controls,javafx.fxml
- Working directory: `$PROJECT_DIR$`
3. Click "Apply" → "OK"

## 4. Run the Application

1. **Click the Run button (▶) in IntelliJ**

2. **Login credentials**:

**Admin:**-
- Username: `MideoHubAdmin`
- Password: `MideoHub`

**Employee:**-
- Username: `MideoHubEmp`
- Password: `MideoHub`


## 4. Troubleshooting
**Database Connection Error**
- Verify MySQL service is running
- Check credentials in DatabaseConnection.java
- Confirm `database/tables` exist via:
```sql
SHOW DATABASES;
```

** JavaFX Runtime Error**
- Validate JavaFX SDK path in VM options
-  Ensure `--add-modules` includes
```java
javafx.controls,javafx.fxml
```
**FXML Loading Issues**
- Confirm FXML files exist in:
  src/main/resources/com/supermarket/fxml/
-  Verify controller paths in FXML match the Java package structure.

**Missing Images**
- All images must be in:
  src/main/resources/com/supermarket/images/


### Collaborator

[Mohamed Khiarey](https://github.com/khairy-nio)


## Contributing

Contributions are welcome!

-------------
#### Feel free to reach out with feedback or collaboration ideas!
