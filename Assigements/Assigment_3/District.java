public class District {
    private int id;
    private String name;
    
    public District(int id, String name) {
        if (id < 0) {
            throw new IllegalArgumentException("ID must be greater or equal than 0.");
        }
        
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Name cannot be null or empty.");
        }
        
        this.id = id;
        this.name = name;
    }
    
    public int getId() {
        return this.id;
    }
    
    public String getName() {
        return this.name;
    }
}