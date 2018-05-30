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
  * npm-watch
  * browserify
* MithrilJS

### Dev Journal

Goals: 
* module parts so that I can rewrite some things in a different language if/when I desire. 
* fast development cycle
  * spring-boot-devtools handles rebuilding and reloading the java application
  * npm-watch handles rebuilding the web app
* use latest releases of technologies

Thoughts:
* Use yoda conditions (e.g. constant.equals(values) instead of value.equals(constant)) everywhere. In places where value may be null this prevents a null pointer exception and elsewhere it maintains consistency.


### Build

NPM scripts can be used to build both the mithril web app and the spring boot application. The NPM script calls maven to build the spring application.

Build javascript web app. This will bundle all the javascript for the mithril web app into a single file (src/main/resources/static/js/app.js)
```
npm run -s build-js
```

Build static resources. If the application is running the resources will be hot-reloaded without restarting the application.
```
npm run -s build-static

```

Build java class files. If the application is running spring-dev-tools will trigger an automatic restart on a classpath change.
```
npm run -s build-java
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