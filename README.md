# ForgerServer
A RESTful Spring-based backend for a neural network image processing application like prisma

# Setting up the neural network image processing module
Simply go to https://github.com/jcjohnson/neural-style/blob/master/INSTALL.md and follow the instructions

# Setting up the server:

The server is based on the Spring Java framework. First you set up a basic build script. You can use any build system you like when building apps with Spring, but the code you need to work with Gradle is included in the server files. If you’re not familiar with either, refer to Building Java Projects with Gradle.

# Before you build, you need to set up a few variables.
1. Go into ForgerServer/src/main/java/main/server/Application.java and change the NEURAL_HOME variable to where you have installed neural-style. Make sure that the application will have all the necessary permissions to access that folder and its contents!
2. In the same file, change the NVIDIA_GPU_WITH_CUDA variable to “true” if the server machine has an Nvidia GPU with CUDA support, otherwise, set it to “false”.
3. Go into ForgerServer/src/main/resources/application.properties and change the maxFileSize variables, it regulates the maximum size of the images that are uploaded to the server, as well as the server port (the default port is 51731)

# Building with Gradle:
The Gradle Wrapper is the preferred way of starting a Gradle build. It consists of a batch script for Windows and a shell script for OS X and Linux. These scripts allow you to run a Gradle build without requiring that Gradle be installed on your system.

The Gradle Wrapper is available in the root directory of the server for building your project. Add it to your version control system, and everyone that clones your project can build it just the same. It can be used in the exact same way as an installed version of Gradle. Run the wrapper script to perform the build task:
`./gradlew build`
The first time you run the wrapper for a specified version of Gradle, it downloads and caches the Gradle binaries for that version. The Gradle Wrapper files are designed to be committed to source control so that anyone can build the project without having to first install and configure a specific version of Gradle. At this stage, you will have built your code. You can see the results in the “build” folder. Now, you can run the server side of the app using the following command:
`./gradlew run`


Alternatively, if you use an IDE like IntelliJ, you can simply import a Gradle project:
With IntelliJ IDEA up and running, click Import Project on the Welcome Screen, or File | Open on the main menu:

In the pop-up dialog make sure to select Gradle's build.gradle file under the complete folder:

IntelliJ IDEA will create a project with all the code from already entered.

# Testing it out without the client
When the server boots up it automatically sets up an upload form at the address (with default port) http://localhost:51731/, You can choose two images and see if everything works correctly.

# Troubleshooting
Some errors that you may encounter:

**Problem: the server doesn't process the images after receiving them.**
Solution: make sure that the NEURAL_HOME variable is set correctly and that the application has all the necessary permissions.

**Problem: the server print log contains errors from neural-style**
Solution: make sure the NVIDIA_GPU_WITH_CUDA variable is set correctly and that the package itself is installed correctly.

**Problem: When the client uploads the image the processing lasts forever.**
Solution: make sure that the UPLOAD_URL is set correctly.