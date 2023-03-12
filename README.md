# Spring Kafka Documentation
This tool is created for auto generated kafka documentation with spring projects. YAML/JSON document is built in Async API format.

## How to use
- Implement these to build.gradle.kts.
```
maven { url = uri("https://jitpack.io") }
```

```
implementation("com.github.DogusTeknoloji:springkafkadoc:1.0.4")
```

- Add asyncapi configuration file

``` kotlin
@Configuration
@EnableAsyncApi
class AsyncApiConfiguration {

    @Bean
    fun asyncApiDocket(): AsyncApiDocket {

        val info = Info.builder()
            .version("1.0.0")
            .title("ZubizuLF Customer API")
            .build()

        val kafkaServer = Server.builder()
            .protocol("kafka")
            .url(BOOTSTRAP_SERVERS)
            .build()

        return AsyncApiDocket.builder()
            .consumerBasePackage(KAFKA_CONSUMER_BASE_PACKAGE)
            .producerBasePackage(KAFKA_PRODUCER_BASE_PACKAGE)
            .info(info)
            .server("kafka", kafkaServer)
            .build()
    }

    private companion object {
        const val BOOTSTRAP_SERVERS = "0.0.0.0:9092"
        const val KAFKA_CONSUMER_BASE_PACKAGE = "com.dteknoloji.*.consumer"
        const val KAFKA_PRODUCER_BASE_PACKAGE = "com.dteknoloji.*.producer"
    }
}
```

Consumers are included in the consumer package, and producer in the producer package. A project does not have to be both a consumer and a producer. 
Even if it's one of the two, this tool works properly.

In order for this tool to work properly, consumers and producers must be as follows.

Consumer example:

``` kotlin
@Component
class CustomerCreatedEventConsumer {

    @KafkaListener(topicPattern = "\${kafka.topics.customerCreated}")
    fun receive(@Payload payload: CustomerCreatedEvent) {
        // TODO
    }
}
```

Producer example:

``` kotlin
@Component
class CustomerCreatedEventProducer {

    @AsyncApiDocProducer("customerCreated")
    fun send(@Payload payload: CustomerCreatedEvent) {
        // TODO
    }
}
```

In the added configuration file, we specify which package the consumers and producers have. 
The tool detects the consumers and producers in the project by finding the classes that use the @Component annotation.

Normally, an annotation is not required for the methods or payloads of the producers. 
But, we use kafka's @Payload annotation for payloads to create the producer document.
In addition, we use the @AsyncApiProducer annotation created in the tool to understand which topic the events are thrown to.

We use the topicPattern parameter, which we want to pull the consumer topic names from the config files. 
There is a rule to get the last word here. 
There is a topicPattern parameter in the @AsyncApiProducer annotation, but whatever value is given to that parameter, that value is used as it is.

## Endpoints
In this project, there are two endpoints.
- GET /springkafkadoc/json
- GET /springkafkadoc/yaml

By using these endpoints, you can get AsyncAPI documents in JSON/YAML formats.

Sample of YAML:

``` yaml
asyncapi: "2.0.0"
info:
  title: "Customer"
  version: "1.0.0"
  description: "Nice, short desc"
servers:
  kafka:
    url: "ip-of-service:port"
    protocol: "kafka"
channels:
  customerCreated_publish:
    publish:
      operationId: "customerCreated_publish_receive"
      description: "Auto-generated description"
      bindings:
        kafka:
          groupId:
            type: "string"
            enum:
            - "customer"
      message:
        name: "CustomerCreatedEvent"
        title: "CustomerCreatedEvent"
        payload:
          $ref: "#/components/schemas/CustomerCreatedEvent"
        headers:
          $ref: "#/components/schemas/HeadersNotDocumented"
    bindings:
      kafka: {}
  customerDeleted_publish:
    publish:
      operationId: "customerDeleted_publish_receive"
      description: "Auto-generated description"
      bindings:
        kafka:
          groupId:
            type: "string"
            enum:
            - "customer"
      message:
        name: "CustomerDeletedEvent"
        title: "CustomerDeletedEvent"
        payload:
          $ref: "#/components/schemas/CustomerDeletedEvent"
        headers:
          $ref: "#/components/schemas/HeadersNotDocumented"
    bindings:
      kafka: {}
  paymentRegistered_publish:
    publish:
      operationId: "paymentRegistered_publish_receive"
      description: "Auto-generated description"
      bindings:
        kafka:
          groupId:
            type: "string"
            enum:
            - "customer"
      message:
        name: "PaymentRegisteredEvent"
        title: "PaymentRegisteredEvent"
        payload:
          $ref: "#/components/schemas/PaymentRegisteredEvent"
        headers:
          $ref: "#/components/schemas/HeadersNotDocumented"
    bindings:
      kafka: {}
components:
  schemas:
    CustomerCreatedEvent:
      type: "object"
      exampleSetFlag: true
    HeadersNotDocumented:
      type: "object"
      properties: {}
      example: {}
      exampleSetFlag: true
      types:
      - "object"
    CustomerDeletedEvent:
      type: "object"
      exampleSetFlag: true
    PaymentRegisteredEvent:
      type: "object"
      properties:
        transactionId:
          type: "string"
          format: "uuid"
          exampleSetFlag: false
          types:
          - "string"
        customerId:
          type: "string"
          format: "uuid"
          exampleSetFlag: false
          types:
          - "string"
        totalPaidAmount:
          type: "number"
          format: "double"
          exampleSetFlag: false
          types:
          - "number"
        itemList:
          type: "object"
          additionalProperties:
            type: "integer"
            format: "int32"
            exampleSetFlag: false
            types:
            - "integer"
          exampleSetFlag: false
          types:
          - "object"
          jsonSchema:
            type: "object"
      example:
        transactionId: "3fa85f64-5717-4562-b3fc-2c963f66afa6"
        customerId: "3fa85f64-5717-4562-b3fc-2c963f66afa6"
        totalPaidAmount: 1.100000023841858
        itemList:
          additionalProp1: 0
          additionalProp2: 0
          additionalProp3: 0
      exampleSetFlag: true
tags: []
```

## References
[Springwolf](https://github.com/springwolf/springwolf-core)

[Async API](https://www.asyncapi.com/)

This tool is very inspired by the springwolf project, which is an open source tool. ❤️
