package com.amazonaws.lambda.demo;

import java.text.SimpleDateFormat;
import java.util.TimeZone;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.document.PutItemOutcome;
import com.amazonaws.services.dynamodbv2.document.spec.PutItemSpec;
import com.amazonaws.services.dynamodbv2.model.ConditionalCheckFailedException;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

public class RecordingDeviceInfoHandler implements RequestHandler<Thing, String> {
	private DynamoDB dynamoDb;
	private String DYNAMODB_TABLE_NAME = "DeviceData";

	@Override
	public String handleRequest(Thing input, Context context) {
		this.initDynamoDbClient();
		persistData(input);
		return "Success	in	storing	to	DB!";
	}
	
	// input 으로 받은 Thing 객체에대한 정보를 AWS dynamoDb에 전달하는 함수
	private PutItemOutcome persistData(Thing thing) throws ConditionalCheckFailedException {
		// Epoch Conversion Code: https://www.epochconverter.com
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd	HH:mm:ss"); // /update/accepted 로 전달받을 때 "timestamp" : 1604467313으로 오는데 이걸 사람이 보기 편하게 변경해준다.

		sdf.setTimeZone(TimeZone.getTimeZone("Asia/Seoul"));
		String timeString = sdf.format(new java.util.Date(thing.timestamp * 1000));
		return this.dynamoDb.getTable(DYNAMODB_TABLE_NAME) // AWS dynamoDB에 있는 해당 이름을 가진 테이블에 input정보로 받아온 Thing 객체에서 필요한 정보 뽑아서 전달 
				.putItem(new PutItemSpec().withItem(new Item().withPrimaryKey("time", thing.timestamp)
						.withString("temperature", thing.state.reported.temperature)
						.withString("LED", thing.state.reported.LED).withString("timestamp", timeString)));
	}

	private void initDynamoDbClient() {
		AmazonDynamoDB client = AmazonDynamoDBClientBuilder.standard().withRegion("ap-northeast-2").build();
		this.dynamoDb = new DynamoDB(client);
	}
}

// lambda 런타임이 JSON 형식의 입력 데이터(아두이노)를 해당되는 객체로 변환 "Thing" 객체로 변환
// JSON 데이터에서 state, timestamp 이외에도 metadata, version 도 있지만 이는  자바 런타임이 알아서 해줘서 무시함.
class Thing {
	public State state = new State();
	public long timestamp;

	public class State {
		public Tag reported = new Tag();
		public Tag desired = new Tag();

		public class Tag {
			public String temperature;
			public String LED;
		}
	}
}