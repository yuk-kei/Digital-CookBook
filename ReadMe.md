# Digital Cookbook

Digital Cookbook implemented in Java FX, MySQL, used Maven to manage. If you had any questions or constructive suggestions or found bugs regarding our projects, feel free to contact me or simply [create an issue](https://github.com/yuk-kei/Digital-CookBook/issues):



## Brief Introduction

- **What is Chef Only?**

  Have you ever wonder what to cook each day? Then you should try our Chef's Only! It is a digital cook book which helps you to manage the recipe easily . 

  

- **GUI：**
  
  - Home Window
  
    <img src="guide/GUI/HomeView.png" alt="Home Window" style="zoom: 51%;" />
  
  - Search Window
  
    <img src="guide/GUI/SearchView.png" alt="Search Window" style="zoom: 47%;" />
  
  - View Recipe Window
  
    <img src="guide/GUI/RecipeView.png" alt="View Recipe Window" style="zoom: 43%;" />
  
  - Add / Edit Recipe Window
  
    <img src="guide/GUI/Add(Edit)View.png" alt="Add/Edit Window" style="zoom: 43%;" />



## How to build this project ?

### Prerequisites for Environment :

- **Operating System:** Windows, Mac & Linux based systems 
- **Software Development Kit:** JDK 11+ is recommended
- **Management tool:** Maven
- **Database:** MySQL server (8+ is recommended)

### Set up :

**example for IntelliJ Idea:**

- Clone this project or just download it

- Open this project in IntelliJ and wait until maven has downloaded all the needed dependencies.

- Create a database/schema in mySQL named "cookbook" and then execute the SQL file **guide/DataBase/cookbook.sql** in this database.

- Find the following code in **"src/main/java/chefOnly/utils/ConnectionUtil.java"** 

  ```` java
  try {
      Class.forName("com.mysql.cj.jdbc.Driver");
      connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/cookbook?useSSL=false&characterEncoding=utf8&serverTimezone=UTC", "user", "password"};
      return connection;
      }
      // change ther "user" and "password" to your own username and password
      // if your are using sql connecter before 8, the Class.forName("com.mysql.jdbc.Driver")
  ````

  

- If you are using Java 11 and later, you should reference [this](https://www.jetbrains.com/help/idea/javafx.html#vm-options) to add a VM option.

  - In brief, in the main menu, select **Run | Edit** Configurations, Select **Application | Main** from the list on the left. From the **More options** list, select **Add VM options**. And  add your path to the lib directory of the downloaded JavaFX SDK distribution.

  ``` shell
  --module-path /path/to/javafx/sdk --add-modules javafx.controls,javafx.fxml
  exp: --module-path C:\Enviroment\javafx-sdk-11.0.2\lib --add-modules javafx.controls,javafx.fxml
  ```

  

- The Entrance of our Digital Cook book is at **"src/main/java/chefOnly/.Main.java"**,
  find and run it as Java Application. 
  
  

**example for eclipse:**

- import the project as existing Maven project ( File --> Import --> Maven:  Existing Maven Project )
- For the database configuration, same as the example for IntelliJ Idea.
- In the main menu, find **Run->Coverage configurations** , find Arguments on the right side and add your path to the lib directory of the downloaded JavaFX SDK distribution in the test field of **VM arguments** as following:

``` shell
--module-path /path/to/javafx/sdk --add-modules javafx.controls,javafx.fxml
exp: --module-path C:\Enviroment\javafx-sdk-11.0.2\lib --add-modules javafx.controls,javafx.fxml
```

- The Entrance of our Digital Cook book is at **"src/main/java/chefOnly/.Main.java"**,
  find and run it as Java Application. 

## Used Design Patterns

- **MVC**

