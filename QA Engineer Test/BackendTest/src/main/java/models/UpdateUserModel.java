package models;

public class UpdateUserModel {
    private String name;
    private String email;
    private String status;

    public UpdateUserModel(String name, String email, String status,String gender) {
        this.name = name;
        this.email = email;
        this.status = status;
		this.gender = gender;
    }

    // Getters and setters
    public String getName() { return name; }
    public String getEmail() { return email; }
	public String getGender() { return gender; }
    public String getStatus() { return status; }

    public void setName(String name) { this.name = name; }
    public void setEmail(String email) { this.email = email; }
	public void setGender(String email) { this.gender = gender; }
    public void setStatus(String status) { this.status = status; }
}