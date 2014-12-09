# Introduction
------------

This project demonstrates the usage of the [Concordion](http://concordion.org) [Logback Extension](http://github.com/concordion/concordion-logback-extension) 

    
# Running the tests
---------------------------

The download includes support to run the tests with either <a href="http://www.gradle.org/">Gradle</a>.  
    
### Using Gradle
1. [Download](http://www.gradle.org/downloads.html) and [install](http://www.gradle.org/installation.html) Gradle (this has been tested with 2.1)
1. From a command line opened at the location to which this package has been unzipped, run `gradle clean test`
1. View the Concordion output under the subfolder `build/reports/spec/org/concordion/ext/demo/`
    
### Running from your IDE
Import as a Gradle or as a Maven project. This may require additional plugins to be installed to support Gradle.

Under the `src/test/java` folder, find the `StoryboardDemo` class in the `org.concordion.ext.demo` package and run as a JUnit test. The location of the Concordion output is shown on the standard output console.


What you should see
--------------------------------
    
### JUnit output
The test should pass successfully, though the console output will show a failure with the message:

> <-- Note: This test has been marked as EXPECTED_TO_FAIL

This test deliberately fails in order to demonstrate the extension.  It uses Concordion's `@ExpectedToFail` annotation to keep the JUnit passing (you'd normally only use this when you have a partially implemented feature).


Additional Gradle Files
-----------------------
`dev.gradle` is only needed if you want to run against snapshot or local builds of the concordion-screenshot-extension.

If copying the project for your own use, you probably won't want either of these files.


Mailing List
-----------------
Feel free to discuss this demo project on the Concordion [mailing list](https://groups.google.com/d/forum/concordion).
