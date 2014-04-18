package trekbankDatabaseObjects;


/**
 *
 * @author Johnny
 */
public class Worker {
    private long id;
    private String name;

    /**
     * 
     * @param id is the primary key for the worker, this is a unique number
     * @param name is the name for the worker
     */
    public Worker(long id, String name) {
        this.id = id;
        this.name = name;
    }

    /**
     * 
     * @return returns the unique primary key for the worker
     */
    public long getId() {
        return id;
    }

    /**
     *
     * @return returns the name of the worker
     */
    public String getName() {
        return name;
    }

    /**
     *
     * @return returns a string representation of the user
     */
    @Override
    public String toString() {
        return "Worker{" + "id=" + id + ", name=" + name + '}';
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 23 * hash + (this.name != null ? this.name.hashCode() : 0);
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
        final Worker other = (Worker) obj;
        if ((this.name == null) ? (other.name != null) : !this.name.equals(other.name)) {
            return false;
        }
        return true;
    }

    
    
    
}
