# TaskBuddy Project Readme

## Welcome to Task Buddy

TaskBuddy is a robust task management solution designed to empower teams in efficiently organizing and prioritizing tasks. The application provides a user-friendly interface and incorporates features to streamline task management. Below are important details about the application.

## User Access and Permissions

Most features require logging in. Non-authorized users have access to the welcome screen and the login or registration panel.

### Admin (Manager)

- Create tasks and assign tasks to any user.
- View a list of all users with the ability to delete users.
- View a list of all tasks with the option to edit or delete tasks.
- View a list of all users with the option to edit or delete tasks.

### Common User (Employee)

- Create tasks only for themselves.
- View a list of all tasks but edit or delete only tasks for which they are responsible.

### Every Authorized User

- View their own profile.
- Switch tasks as completed/uncompleted.


## Technologies Used

The application leverages a set of technologies to ensure security, efficiency, and a user-friendly experience:

- **Spring Boot:** Backend development framework.
- **Spring Security:** Authentication and access control.
- **MySQL Database:** Efficient data storage.
- **Maven:** Project management and build automation.
- **Thymeleaf:** Server-side template engine for dynamic UI.
- **Bootstrap:** Front-end framework for modern and mobile-friendly UI.
- **jQuery:** JavaScript library for client-side scripting.
- **CSS:** Styling for a polished design.
- **HTML:** Markup language forming the backbone of the user interface.

## Additional Configuration

For additional configuration options and advanced settings, refer to the `application.properties` file and the project's documentation.

## Getting Started

Follow these steps to set up and run the application:

1. **Clone the Repository:**
   ```bash
   git clone https://github.com/your-username/your-repository.git

2. **Configure the Database:**

3. **Open the application.properties file.**
Update the database configuration properties.
4. **Build the Application:**
mvn clean install

5. **Run the Application:**
java -jar target/your-application.jar

7. **Access the Application:**
Open a web browser and go to http://localhost:8081
