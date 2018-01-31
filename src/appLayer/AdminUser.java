package appLayer;


public class AdminUser extends User {
    private int id;

    public AdminUser(int id, String firstName, String lastName, String password, String email) {
        super(firstName, lastName, password, email);
        this.id = id;
    }

    public int getId() {
        return id;
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

    public String getEmail() {
        return email;
    }
}
