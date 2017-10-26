[![Build Status](https://travis-ci.org/concordion/concordion-logback-extension-demo.svg?branch=master)](https://travis-ci.org/concordion/concordion-logback-extension-demo)
[![Apache License 2.0](https://img.shields.io/badge/license-Apache%202.0-blue.svg)](http://www.apache.org/licenses/LICENSE-2.0.html)

# Introduction
--------------

This project demonstrates the usage of the [Concordion](http://concordion.org) [Logback Extension](http://github.com/concordion/concordion-logback-extension) 

Example output is shown [here](http://concordion.github.io/concordion-logback-extension-demo/spec/org/concordion/demo/LogbackLoggingDemo.html).

# Running the tests
-------------------

The download includes support to run the tests with <a href="http://www.gradle.org/">Gradle</a>.  The code base includes the Gradle Wrapper, which will automatically download the correct version of Gradle.

*If running from behind a proxy then Config.java will need to be updated with the proxy host and port.* 

Gradle can be run from the command line or from your IDE:

Command line
============
From the command line, `cd` to the folder containing a copy of this project, and run 

  `./gradlew clean test` on Unix-based systems, or 
  `.\gradlew clean test` on Windows.

This will download the required dependencies, clean the existing project, recompile all source code and run all the tests. 

View the Concordion output in `build/reports/spec/org/concordion/demo/LogbackLoggingDemo.html`.


IDE
===
For Eclipse and NetBeans, you will need to install a Gradle plugin to your IDE before importing the project. See [Gradle tooling](https://www.gradle.org/tooling) for details.

On importing the project to your IDE, the required dependencies will be downloaded.

Under the `src/test/java` folder, find the `LogbackLoggingDemo` class in the `org.concordion.demo` package and run as a JUnit test. The location of the Concordion output is shown on the standard output console.

What you should see
-------------------

### JUnit output
The test should pass successfully:

```Successes: 1, Failures: 0```

### Concordion output
The output folder should contain the following specification. (You can see an example of it [here](http://concordion.github.io/concordion-logback-extension-demo/spec/org/concordion/demo/LogbackLoggingDemo.html).
    
#### LogbackLoggingDemo.html

A Tooltip displaying "My tooltip here!"

A Log File link at the bottom right of the page, clicking that will bring up a log file viewer

    
Additional Gradle Files
-----------------------
`dev.gradle` is only needed if you want to run against snapshot or local builds of the concordion-logback-extension.

`publish.gradle` is only needed if you want to publish the output to Github pages.

If copying the project for your own use, you probably won't want either of these files.


Mailing List
-----------------
Feel free to discuss this demo project on the Concordion [mailing list](https://groups.google.com/d/forum/concordion).
