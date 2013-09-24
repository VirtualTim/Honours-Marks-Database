package orm;

import java.util.PriorityQueue;

public class BaseStaff {
    private int staffId;
    private String name;
    private PriorityQueue<Mark> marks;
    
    public BaseStaff() {
        // Get DB connection.
    }
    
    public int getStaffId() {
        return staffId;
    }
    
    public void setStaffId(int staffId) {
        this.staffId = staffId;
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public PriorityQueue<Mark> getMarks() {
        return marks;
    }
    
    public void setMarks(PriorityQueue<Mark> marks) {
        this.marks = marks;
    }
}
