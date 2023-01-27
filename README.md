# easy-rpc
- An easy rpc project, most refered from @CoderLeixiaoshuai

## Structure of code
└── easy-rpc
    ├── easy-rpc-example    // an example using rpc
    │   ├── easy-rpc-example-consumer   // consumer simulator
    │   │   ├── pom.xml
    │   │   └── src
    │   │       ├── main
    │   │       │   ├── java
    │   │       │   └── resources
    │   │       └── test
    │   │           └── java
    │   ├── easy-rpc-example-provider   // provider simulator
    │   │   ├── pom.xml
    │   │   └── src
    │   │       ├── main
    │   │       │   ├── java
    │   │       │   └── resources
    │   │       └── test
    │   │           └── java
    │   └── pom.xml
    ├── easy-rpc-spring-boot-starter    // core code of easy-rpc
    │   ├── pom.xml
    │   └── src
    │       ├── main
    │       │   ├── java
    │       │   │   ├── annotation
    │       │   │   ├── client
    │       │   │   ├── common
    │       │   │   ├── config
    │       │   │   ├── exception
    │       │   │   ├── listener
    │       │   │   ├── property
    │       │   │   ├── serialization
    │       │   │   └── server
    │       │   └── resources
    │       └── test
    │           └── java
    └── pom.xml