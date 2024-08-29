package services;

import io.restassured.response.Response;
import models.CreateUserModel;


public class GoRestService extends BaseService {

    public static Response createUser(final CreateUserModel createUserModel){
        return defaultRequestSpecification()
                .body(createUserModel)
                .when()
                .post("/public/v1/users");
    }
	
	// PUT request to update the entire user resource
	public static Response updateUserPut(int userId, final UpdateUserModel updateUserModel) {
        return defaultRequestSpecification()
                .body(updateUserModel)
                .when()
                .put("/public/v2/users/" + userId);
    }
	
	// PATCH request to update parts of the user resource
    public static Response updateUserPatch(int userId, final UpdateUserModel updateUserModel) {
        return defaultRequestSpecification()
                .body(updateUserModel)
                .when()
                .patch("/public/v2/users/" + userId);
    }
}
