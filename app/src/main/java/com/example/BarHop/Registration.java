package com.example.BarHop;

import java.util.*;
import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.model.AttributeDefinition;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.amazonaws.services.dynamodbv2.model.ComparisonOperator;
import com.amazonaws.services.dynamodbv2.model.Condition;
import com.amazonaws.services.dynamodbv2.model.CreateTableRequest;
import com.amazonaws.services.dynamodbv2.model.KeySchemaElement;
import com.amazonaws.services.dynamodbv2.model.KeyType;
import com.amazonaws.services.dynamodbv2.model.ProvisionedThroughput;
import com.amazonaws.services.dynamodbv2.model.PutItemRequest;
import com.amazonaws.services.dynamodbv2.model.PutItemResult;
import com.amazonaws.services.dynamodbv2.model.ScalarAttributeType;
import com.amazonaws.services.dynamodbv2.model.ScanRequest;
import com.amazonaws.services.dynamodbv2.model.ScanResult;
import com.amazonaws.services.dynamodbv2.util.TableUtils;

public class Registration {
	static AmazonDynamoDB dynamoDB;
	public static final String TABLE = "Registration";
	private static void init() {
		BasicAWSCredentials awsCreds = new BasicAWSCredentials("", ""); // TODO: provide AWS credentials

		dynamoDB = AmazonDynamoDBClientBuilder.standard()
				.withCredentials(new AWSStaticCredentialsProvider(awsCreds))
				.withRegion("us-east-1")
				.build();
	}

	private static void createTable() {
		if(dynamoDB == null) {
			init();
		}
		try {
			// Create Registration table with a primary key 'name' and additional 'password' attribute
			CreateTableRequest request = new CreateTableRequest()
					.withTableName(TABLE)
					.withAttributeDefinitions(
							new AttributeDefinition("name", ScalarAttributeType.S))
					.withKeySchema(
							new KeySchemaElement("name", KeyType.HASH))
					.withProvisionedThroughput(new ProvisionedThroughput()
							.withReadCapacityUnits(3L)
							.withWriteCapacityUnits(3L));
			TableUtils.createTableIfNotExists(dynamoDB, request);
			TableUtils.waitUntilActive(dynamoDB, TABLE);
		} catch(AmazonServiceException ase) {
			System.out.println("Caught an AmazonServiceException, which means your request made it "
					+ "to AWS, but was rejected with an error response for some reason.");
			System.out.println("Error Message:    " + ase.getMessage());
			System.out.println("HTTP Status Code: " + ase.getStatusCode());
			System.out.println("AWS Error Code:   " + ase.getErrorCode());
			System.out.println("Error Type:       " + ase.getErrorType());
			System.out.println("Request ID:       " + ase.getRequestId());
		} catch(AmazonClientException ace) {
			System.out.println("Caught an AmazonClientException, which means the client encountered "
					+ "a serious internal problem while trying to communicate with AWS, "
					+ "such as not being able to access the network.");
			System.out.println("Error Message: " + ace.getMessage());
		} catch(Exception e) {
			System.out.println("Encountered an error during table creation: " + e);
		}
	}

	// create a new user entry in the Registration table, provided that
	// the table does not yet contain a user with the @param name username;
	// returns true if the operation is successful
	public static boolean registerUser(String name, String password) {
		if(dynamoDB == null) {
			init();
		}
		if(!userExists(name)) {
			Map<String, AttributeValue> user = createUser(name, password);
			try {
				// Add an item
				PutItemRequest putItemRequest = new PutItemRequest(TABLE, user);
				PutItemResult putItemResult = dynamoDB.putItem(putItemRequest);
				System.out.println("Result: " + putItemResult);
				return true;
			} catch(Exception e) {
				System.out.println("Could not add " + name + " to table: " + e);
				return false;
			}
		} else {
			System.out.println("Username '" + name + "' is already registered!");
			return false;
		}
	}

	// returns true if the specified user exists, and the corresponding @password
	// attribute matches the entry for the specified @param name
	public static boolean verifyUser(String name, String password) {
		if(dynamoDB == null) {
			init();
		}
		HashMap<String, Condition> scanFilter = new HashMap<String, Condition>();
		Condition nameCondition = new Condition()
				.withComparisonOperator(ComparisonOperator.EQ)
				.withAttributeValueList(new AttributeValue(name));
		Condition passCondition = new Condition()
				.withComparisonOperator(ComparisonOperator.EQ)
				.withAttributeValueList(new AttributeValue(password));
		scanFilter.put("name", nameCondition);
		scanFilter.put("password", passCondition);
		ScanRequest scanRequest = new ScanRequest(TABLE).withScanFilter(scanFilter);
		ScanResult scanResult = dynamoDB.scan(scanRequest);
		return scanResult.getCount() > 0;
	}

	// returns true if the username @param name exists in the Registration table
	private static boolean userExists(String name) {
		if(dynamoDB == null) {
			init();
		}
		HashMap<String, Condition> scanFilter = new HashMap<String, Condition>();
		Condition condition = new Condition()
				.withComparisonOperator(ComparisonOperator.EQ)
				.withAttributeValueList(new AttributeValue(name));
		scanFilter.put("name", condition);
		ScanRequest scanRequest = new ScanRequest(TABLE).withScanFilter(scanFilter);
		ScanResult scanResult = dynamoDB.scan(scanRequest);
		return scanResult.getCount() > 0;
	}

	// create map with user key/value pairs for Membership table attributes "name" and "password"
	private static Map<String, AttributeValue> createUser(String name, String password) {
		Map<String, AttributeValue> user = new HashMap<String, AttributeValue>();
		user.put("name", new AttributeValue(name));
		user.put("password", new AttributeValue(password));
		return user;
	}

}
