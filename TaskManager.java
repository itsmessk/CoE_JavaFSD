import java.util.Comparator;
import java.util.PriorityQueue;

public class TaskManager {

    PriorityQueue<Task> task = new PriorityQueue<>(Comparator.comparingInt(Task::getPriority).reversed());

    public void addTask(String id, String description, int priority) {
        Task t = new Task(id, description, priority);
        task.add(t);
    }

    
    public void removeTask(String id) {
        Task toRemove = null;
        
            
        for (Task t : task) {
            if (t.getId().equals(id)) {
                toRemove = t;
                break; 
            }
        }
        
            
        if (toRemove != null) {
                task.remove(toRemove);
        } else {
            System.out.println("Task with ID " + id + " not found.");
        }
    }

    public Task getHighestPriorityTask() {
        return task.peek();
    }
        
    

    


    public static void main(String[] args) {
        TaskManager manager = new TaskManager();
        manager.addTask("1", "Complete project report", 3);
        manager.addTask("2", "Fix critical bug", 5);
        manager.addTask("3", "Respond to emails", 2);

        System.out.println("Highest Priority Task: " + manager.getHighestPriorityTask());

        manager.removeTask("2");

        System.out.println("Highest Priority Task: " + manager.getHighestPriorityTask());
        
    }
}


class Task{
    private String id;
    private String description;
    private int priority;
    
    public Task(String id, String description, int priority) {
        this.id = id;
        this.description = description;
        this.priority = priority;
    }

    public String getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    public int getPriority() {
        return priority;
    }

    @Override
    public String toString() {
        return "Task[ID=" + id + ", Description=" + description + ", Priority=" + priority + "]";
    }
}

