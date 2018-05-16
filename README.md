## PROJECT

Project is a tool to help track spending and manage budgets. 

Project is also a side project for me to try out different technologies.
I choose to implement a financial tool because it is something that will be useful for the rest of my life so I will be motivated to keep working on it.

### Technologies

* Git
* Spring Boot
* Maven
* Thymeleaf
* NPM
* MithrilJS

### Dev Journal

One goal of this project is to be able to re-implement parts of it

Another goal is to have a fast development cycle


### Build

NPM scripts can be used to build both the mithril web app and the spring boot application. The NPM script calls maven to build the spring application.

Build javascript web app
```
npm run -s build-js
```

Build spring application
```
npm run -s build-java
```

Build javascript web app then spring application
```
npm run -s build
```

### Run

Launch application
```
npm run -s launch
```

Launch application and watch for any changes. If changes are detected in the un-bundled javascript files then we first rebuild the web app then rebuild the spring application. After rebuilding the spring some application the changes will either be visible on refresh or after spring-boot-devtools has automatically restarted the application

```
npm run -s watch
```