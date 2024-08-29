import models.CreateUserModel;
import org.junit.jupiter.api.Test;
import services.GoRestService;

import static org.apache.http.HttpStatus.SC_CREATED;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;

public class CreateUserTests {

    @Test
    public void Users_CreateUsers_Success(){

        final CreateUserModel createUserModel = new CreateUserModel("Gino Paloma", "Male", "qatest@test.com", "Active");
        GoRestService.createUser(createUserModel)
                .then()
                .statusCode(SC_CREATED)
                .body("data.id", notNullValue())
                .body("data.name", equalTo(createUserModel.getName()));
    }
	
	@Test
    public void Users_CreateUser_MissingFieldName() {
        final CreateUserModel createUserModel = new CreateUserModel("", "Male", "qatest@test.com", "Active");
        GoRestService.createUser(createUserModel)
            .then()
            .statusCode(SC_UNPROCESSABLE_ENTITY)
			.body("[0].field", equalTo("name"))
			.body("[0].message", equalTo("can't be blank"))
			.body("[1].field", equalTo("email"))
			.body("[1].message", equalTo("has already been taken"));
    }
	
	@Test
    public void Users_CreateUser_MissingFieldGender() {
        final CreateUserModel createUserModel = new CreateUserModel("A", "qatest1@test.com", "Active");
        GoRestService.createUser(createUserModel)
            .then()
            .statusCode(SC_UNPROCESSABLE_ENTITY)
			.body("[0].field", equalTo("gender"))
			.body("[0].message", equalTo("can't be blank, can be male of female"))
    }
	
	@Test
    public void Users_CreateUser_MissingFieldStatus() {
        final CreateUserModel createUserModel = new CreateUserModel("Gino Paloma", "Male", "qatest2@test.com");
        GoRestService.createUser(createUserModel)
            .then()
            .statusCode(SC_UNPROCESSABLE_ENTITY)
			.body("[0].field", equalTo("status"))
			.body("[0].message", equalTo("can't be blank"))
    }
	
	@Test
    public void Users_CreateUser_InvalidEmailFormat() {
        // Invalid email format
        CreateUserModel createUserModel = new CreateUserModel("John Doe", "Male", "invalid-email", "Active");
        GoRestService.createUser(createUserModel)
                .then()
                .statusCode(SC_UNPROCESSABLE_ENTITY)
                .body("data.message", containsString("is invalid"));
    }
	
	@Test
	public void Users_CreateUser_DuplicateEmail() {
    // Create first user
    CreateUserModel createUserModel1 = new CreateUserModel("John Doe", "Male", "duplicate@test.com", "Active");
    GoRestService.createUser(createUserModel1).then().statusCode(SC_CREATED);

    // Try creating another user with the same email
    CreateUserModel createUserModel2 = new CreateUserModel("Jane Doe", "Female", "duplicate@test.com", "Active");
    GoRestService.createUser(createUserModel2)
            .then()
            .statusCode(SC_UNPROCESSABLE_ENTITY)
            .body("[0].field", equalTo("email"))
            .body("[0].message", equalTo("has already been taken"));
	}
	
	@Test
	public void Users_CreateUser_InvalidGenderValue() {
    CreateUserModel createUserModel = new CreateUserModel("Alex Doe", "InvalidGender", "invalidgender@test.com", "Active");
    GoRestService.createUser(createUserModel)
            .then()
            .statusCode(SC_UNPROCESSABLE_ENTITY)
            .body("[0].field", equalTo("gender"))
            .body("[0].message", equalTo("can't be blank, can be male of female"));
	}
	
	@Test
	public void Users_CreateUser_InvalidStatusValue() {
    CreateUserModel createUserModel = new CreateUserModel("Sam Smith", "Male", "sam@test.com", "UnknownStatus");
    GoRestService.createUser(createUserModel)
            .then()
            .statusCode(SC_UNPROCESSABLE_ENTITY)
            .body("[0].field", equalTo("status"))
            .body("[0].message", equalTo("can't be blank"));
	}
	
	@Test
	public void Users_CreateUser_NameTooLong() {
    String longName = "ThisIsAVeryLongNameThatExceedsTheMaximumzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzz22222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222zzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzAllowedLengthForAUserNameInTheSystem";
    CreateUserModel createUserModel = new CreateUserModel(longName, "Male", "longname255@test.com", "Active");
    GoRestService.createUser(createUserModel)
            .then()
            .statusCode(SC_UNPROCESSABLE_ENTITY)
            .body("[0].field", equalTo("name"))
            .body("[0].message", equalTo("is too long (maximum is 200 characters)"));
	}
	
	@Test
	public void Users_CreateUser_EmptyEmail() {
    CreateUserModel createUserModel = new CreateUserModel("Emily Doe", "Female", "", "Active");
    GoRestService.createUser(createUserModel)
            .then()
            .statusCode(SC_UNPROCESSABLE_ENTITY)
            .body("[0].field", equalTo("email"))
            .body("[0].message", equalTo("can't be blank"));
	}
	
	@Test
	public void Users_CreateUser_InactiveUserCreation() {
    CreateUserModel createUserModel = new CreateUserModel("Inactive User", "Female", "inactive@test.com", "Inactive");
    GoRestService.createUser(createUserModel)
            .then()
            .statusCode(SC_CREATED)
            .body("data.status", equalTo("Inactive"));
	}
	
	@Test
	public void Users_CreateUser_MultipleFieldsMissing() {
    CreateUserModel createUserModel = new CreateUserModel("", "", "invalid@test.com", "");
    GoRestService.createUser(createUserModel)
            .then()
            .statusCode(SC_UNPROCESSABLE_ENTITY)
            .body("[0].field", equalTo("name"))
            .body("[0].message", equalTo("can't be blank"))
            .body("[1].field", equalTo("gender"))
            .body("[1].message", equalTo("can't be blank, can be male of female"))
            .body("[2].field", equalTo("status"))
            .body("[2].message", equalTo("can't be blank"));
}





	
	


}
