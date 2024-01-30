# Calendar Events RestAPI

<p style="justify-content: center">
   <img src="https://img.shields.io/badge/versión-v1.0-blue.svg" alt="version">
   <img src="https://img.shields.io/badge/status-completed-green" alt="status">
</p>

✨ Rest API developed to Collaborative Calendar Events.

## ⚡️ Description
The Collaborative Events Calendar RestAPI is a powerful tool designed to help you manage and coordinate events in a collaborative environment. Built using the Spring Framework, this API allows users to register, log in, and efficiently manage their events. Whether you're a developer looking to integrate this API into your application or an end user seeking to harness its capabilities, this documentation will provide you with a clear understanding of what the API does and how to use it effectively.

## 👨‍💻 Documentation OpenAPI
- [Swagger](http://ec2-3-144-222-220.us-east-2.compute.amazonaws.com:90/api/swagger-ui/index.html)
- [Postman Collections & Documentation](https://documenter.getpostman.com/view/21748987/2s9YXe6PVp)

## ✍🏻 Getting Started
Before you get started, make sure you have the following:
1. Java Development Kit (JDK): Install JDK 8 or above on your system.
2. Maven: Ensure you have Maven installed to manage dependencies and build the project.
3. Database: Configure a MongoDB and update the `application.yml` uri.

## 🚀 Setup
> This is the local version for execution.

1. Clone this project: `git clone https://github.com/chrisjosuedev/calendar-rest-api.git`
2. Go to the project folder:
   `cd calendar-rest-api`
3. Configure your MongoDB Cluster and replace `uri` in `application.yml` with your database uri.
4. Configure Env Variable: `SECRET_KEY`, with a new value.
5. Build the project using Maven `mvn clean install -DskipTests`

## 🛠 Run

1. Once the dependencies are installed, you can run via IDE or Maven.
2. Test Endpoints via Postman (or your preferred API tester) on port 9090: `http://localhost:9090/api/...`
   > Please check the documentation (Postman) above to see the available endpoints and change `Dev Env`

## 🦀 Technologies
![SpringBoot badge](https://img.shields.io/badge/springboot-java-brightgreen)
![Java badge](https://img.shields.io/badge/java-21-red)
![MongoDB badge](https://img.shields.io/badge/mongodb-db-green)

## 🧾 License

The MIT License (MIT)