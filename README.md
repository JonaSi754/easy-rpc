# easy-rpc
- An easy rpc project, most refered from @CoderLeixiaoshuai

## Structure of code
·  
|-- easy-rpc-example    // an example using rpc<br>
|&ensp;   |-- easy-rpc-example-consumer   // consumer simulator<br>
|&ensp;   |&ensp;   |-- pom.xml<br>
|&ensp;   |&ensp;   |-- src<br>
|&ensp;   |&ensp;   &ensp;    |-- main<br>
|&ensp;   |&ensp;   &ensp;    |&ensp;   |-- java<br>
|&ensp;   |&ensp;   &ensp;    |&ensp;   └-- resources<br>
|&ensp;   |&ensp;   &ensp;    └-- test<br>
|&ensp;   |&ensp;   &ensp;    &ensp;    └-- java<br>
|&ensp;   |-- easy-rpc-example-provider   // provider simulator<br>
|&ensp;   |&ensp;   |-- pom.xml<br>
|&ensp;   |&ensp;   └-- src<br>
|&ensp;   |&ensp;   &ensp;    |-- main<br>
|&ensp;   |&ensp;   &ensp;    |&ensp;   |-- java<br>
|&ensp;   |&ensp;   &ensp;    |&ensp;   └-- resources<br>
|&ensp;   |&ensp;   &ensp;    └-- test<br>
|&ensp;   |&ensp;   &ensp;    &ensp;    └-- java<br>
|&ensp;   └-- pom.xml<br>
|── easy-rpc-spring-boot-starter    // core code of easy-rpc<br>
|&ensp;   |-- pom.xml<br>
|&ensp;   └-- src<br>
|&ensp;   &ensp;    |-- main<br>
|&ensp;   &ensp;    |&ensp;   |-- java<br>
|&ensp;   &ensp;    |&ensp;   |&ensp;   |-- annotation<br>
|&ensp;   &ensp;    |&ensp;   |&ensp;   |-- client<br>
|&ensp;   &ensp;    |&ensp;   |&ensp;   |-- common<br>
|&ensp;   &ensp;    |&ensp;   |&ensp;   |-- config<br>
|&ensp;   &ensp;    |&ensp;   |&ensp;   |-- exception<br>
|&ensp;   &ensp;    |&ensp;   |&ensp;   |-- listener<br>
|&ensp;   &ensp;    |&ensp;   |&ensp;   |-- property<br>
|&ensp;   &ensp;    |&ensp;   |&ensp;   |-- serialization<br>
|&ensp;   &ensp;    |&ensp;   |&ensp;   └-- server<br>
|&ensp;   &ensp;    |&ensp;   └-- resources<br>
|&ensp;   &ensp;    └-- test<br>
|&ensp;   &ensp;    &ensp;    └-- java<br>
└-- pom.xml<br>