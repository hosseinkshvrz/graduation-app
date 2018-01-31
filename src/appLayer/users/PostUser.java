package appLayer.users;


public class PostUser extends User {
    private String personnelID;
    private String departmentID;


    public PostUser(String personnelID, String firstName, String lastName, String password, String departmentID, String email) {
        super(firstName, lastName, password, email);
        this.personnelID = personnelID;
        this.departmentID = departmentID;
    }

    public String getPersonnelID() {
        return personnelID;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getPassword() {
        return password;
    }

    public String getDepartmentID() {
        return departmentID;
    }

    public String getEmail() {
        return email;
    }
}
