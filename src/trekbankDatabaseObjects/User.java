package trekbankDatabaseObjects;


/**
 *
 * @author Johnny
 */
public class User {
    private long id;
    private String name;
    private String password;

    /**
     *
     * @param id is the primary key for the user, this is a unique number
     * @param name is the name for the user 
     * @param password is the password for the user
     */
    public User(long id, String name, String password) {
        this.id = id;
        this.name = name;
        this.password = password;
    }

    /**
     *
     * @return returns the unique primary key for the user
     */
    public long getId() {
        return id;
    }

    /**
     *
     * @return returns the name of the user
     */
    public String getName() {
        return name;
    }

    /**
     *
     * @return returns the password of the user
     */
    public String getPassword() {
        return password;
    }

    /**
     *
     * @return returns a string representation of the user
     */
    @Override
    public String toString() {
        return "User{" + "id=" + id + ", name=" + name + ", password=" + password + '}';
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 67 * hash + (this.name != null ? this.name.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final User other = (User) obj;
        if ((this.name == null) ? (other.name != null) : !this.name.equals(other.name)) {
            return false;
        }
        return true;
    }

    
    
    
}
