package fpoly.vunvph33438.warehousemanagement.Model;

public class ThuKho {
    private int id_thuKho;
    private String username;
    private String password;
    private String fullname;
    private String email;
    private int role;

    public ThuKho() {
    }

    public ThuKho(int id_thuKho, String username, String password, String fullname, String email, int role) {
        this.id_thuKho = id_thuKho;
        this.username = username;
        this.password = password;
        this.fullname = fullname;
        this.email = email;
        this.role = role;
    }

    public int getId_thuKho() {
        return id_thuKho;
    }

    public void setId_thuKho(int id_thuKho) {
        this.id_thuKho = id_thuKho;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getRole() {
        return role;
    }

    public void setRole(int role) {
        this.role = role;
    }
}
