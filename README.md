# DevOps Engineer Task

## Description

Provided is: 
- Modbus Server: This is a simulation of a physical power device that can be monitored and controlled by a program.
- Web application: a web-based user interface that can plot data from a REST endpoint.
- Modbus Client / Middleware app: a Java application that can read and write to the modbus server and interacts with the web application. 

The stages of this task are the following.

1. Build each of the applications provided in this task.
2. Containarize each of the applications provided.
3. Create a script that:
   - Automates the build of the the applications and containers
   - Publish containers to a container registry

> Note: Heila Technologies uses Github Actions in its CI/CD pipeline. An outstanding solution will automate this step with a Github Action, but feel free to use any other technology to complete the task.

4. Create a docker compose configuration to run the applications and make sure the Web application is accessible to users via a web browser.

We anticipate that this task will take between 2-3 hours, so please allow at least that amount of time. To give you enough time to work on it, the deadline is one week after you receive this package. It is expected that some changes will be required to the provided applications. Partially completed solutions and comments assist us in determining where you ran out of time. Any extensions to parts of this code base that demonstrate your thought process if you were turning this into a production application are welcome. If you provide documentation on a README, we will gladly run any procedure to see the results.

Feel free to email jdunia@heilatech.com with questions, feedback, or if there are some critical bugs in the structure / layout of the problem.


