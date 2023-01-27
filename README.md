# easy-rpc
- An easy rpc project, most refered from @CoderLeixiaoshuai

## Structure of code
·  
|-- easy-rpc-example    // an example using rpc<br>
|&emsp;   |-- easy-rpc-example-consumer   // consumer simulator<br>
|&emsp;   |&emsp;   |-- pom.xml<br>
|&emsp;   |&emsp;   |-- src<br>
|&emsp;   |&emsp;   &emsp;    |-- main<br>
|&emsp;   |&emsp;   &emsp;    |&emsp;   |-- java<br>
|&emsp;   |&emsp;   &emsp;    |&emsp;   |-- resources<br>
|&emsp;   |&emsp;   &emsp;    |-- test<br>
|&emsp;   |&emsp;   &emsp;    &emsp;    |-- java<br>
|&emsp;   |-- easy-rpc-example-provider   // provider simulator<br>
|&emsp;   |&emsp;   |-- pom.xml<br>
|&emsp;   |&emsp;   |-- src<br>
|&emsp;   |&emsp;   &emsp;    |-- main<br>
|&emsp;   |&emsp;   &emsp;    |&emsp;   |-- java<br>
|&emsp;   |&emsp;   &emsp;    |&emsp;   |-- resources<br>
|&emsp;   |&emsp;   &emsp;    |-- test<br>
|&emsp;   |&emsp;   &emsp;    &emsp;    |-- java<br>
|&emsp;   |-- pom.xml<br>
|-- easy-rpc-spring-boot-starter    // core code of easy-rpc<br>
|&emsp;   |-- pom.xml<br>
|&emsp;   |-- src<br>
|&emsp;   &emsp;    |-- main<br>
|&emsp;   &emsp;    |&emsp;   |-- java<br>
|&emsp;   &emsp;    |&emsp;   |&emsp;   |-- annotation<br>
|&emsp;   &emsp;    |&emsp;   |&emsp;   |-- client<br>
|&emsp;   &emsp;    |&emsp;   |&emsp;   |-- common<br>
|&emsp;   &emsp;    |&emsp;   |&emsp;   |-- config<br>
|&emsp;   &emsp;    |&emsp;   |&emsp;   |-- exception<br>
|&emsp;   &emsp;    |&emsp;   |&emsp;   |-- listener<br>
|&emsp;   &emsp;    |&emsp;   |&emsp;   |-- property<br>
|&emsp;   &emsp;    |&emsp;   |&emsp;   |-- serialization<br>
|&emsp;   &emsp;    |&emsp;   |&emsp;   |-- server<br>
|&emsp;   &emsp;    |&emsp;   |-- resources<br>
|&emsp;   &emsp;    |-- test<br>
|&emsp;   &emsp;    &emsp;    |-- java<br>
|-- pom.xml<br>