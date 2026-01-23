package New.config;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;

public class RequestSpecConfig {

	public static RequestSpecification getSpec() {
		return
				new RequestSpecBuilder()
						.setBaseUri("https://stellarburgers.education-services.ru/")
						.setContentType(ContentType.JSON)
//						.setAccept(ContentType.JSON)
						.log(LogDetail.ALL)  // Всегда логируем запросы
						.build();
	}
}
