package customerservice;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.document.Table;
import com.amazonaws.services.dynamodbv2.model.ScanRequest;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import customerservice.dto.Customer;

import java.util.List;
import java.util.stream.Collectors;

public class CustomerLambda {

    public APIGatewayProxyResponseEvent create(APIGatewayProxyRequestEvent requestEvent) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        Customer customer = objectMapper.readValue(requestEvent.getBody(), Customer.class);
        DynamoDB dynamoDB = new DynamoDB(AmazonDynamoDBClientBuilder.defaultClient());
        Table table = dynamoDB.getTable(System.getenv("CUSTOMERS_TABLE"));
        table.putItem(new Item()
                .withPrimaryKey("id", customer.getId())
                .withString("firstName", customer.getFirstName())
                .withString("lastName", customer.getLastName())
                .withInt("rewardPoints", customer.getRewardPoints()));
        return new APIGatewayProxyResponseEvent().withStatusCode(201)
                .withBody(requestEvent.getBody());

    }

    public APIGatewayProxyResponseEvent read(APIGatewayProxyRequestEvent requestEvent) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        AmazonDynamoDB amazonDynamoDB = AmazonDynamoDBClientBuilder.defaultClient();
        List<Customer> customers = amazonDynamoDB.scan(
                new ScanRequest().withTableName(System.getenv("CUSTOMERS_TABLE")))
                .getItems()
                .stream()
                .map(item -> new Customer(Integer.parseInt(item.get("id").getN()), item.get("firstName").getS(), item.get("lastName").getS(), Integer.parseInt(item.get("rewardPoints").getN())))
                .collect(Collectors.toList());
        String response = objectMapper.writeValueAsString(customers);
        return new APIGatewayProxyResponseEvent().withStatusCode(200).withBody(response);
    }
}
