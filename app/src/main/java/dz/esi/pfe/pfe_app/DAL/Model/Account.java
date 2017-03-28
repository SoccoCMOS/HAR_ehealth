package dz.esi.pfe.pfe_app.DAL.Model;

/**
 * Created by DUALCOMPUTER on 3/27/2017.
 */
public class Account {
    String userID;
    String email;
    String password_hash;

    public Account(String userID, String email) {
        this.userID = userID;
        this.email = email;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword_hash() {
        return password_hash;
    }

    public void setPassword_hash(String password_hash) {
        this.password_hash = password_hash;
    }
}
