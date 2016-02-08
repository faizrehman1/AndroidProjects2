package OfficeTaskAssets.Model;

/*
 * Created by Moosa on 8/6/2015.
 */
public class Task {
    String taskTitle;
    String taskDate;
    int importanceStarRating;
    String taskDescription;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Task task = (Task) o;

        if (importanceStarRating != task.importanceStarRating) return false;
        if (completed != task.completed) return false;
        if (taskTitle != null ? !taskTitle.equals(task.taskTitle) : task.taskTitle != null)
            return false;
        if (taskDate != null ? !taskDate.equals(task.taskDate) : task.taskDate != null)
            return false;
        return !(taskDescription != null ? !taskDescription.equals(task.taskDescription) : task.taskDescription != null);

    }

    @Override
    public int hashCode() {
        int result = taskTitle != null ? taskTitle.hashCode() : 0;
        result = 31 * result + (taskDate != null ? taskDate.hashCode() : 0);
        result = 31 * result + importanceStarRating;
        result = 31 * result + (taskDescription != null ? taskDescription.hashCode() : 0);
        result = 31 * result + (completed ? 1 : 0);
        return result;
    }

    boolean completed;

    public Task() {
    }

    public Task(String taskTitle, String taskDate, int importanceStarRating, String taskDescription, boolean completed) {
        this.taskTitle = taskTitle;
        this.taskDate = taskDate;
        this.importanceStarRating = importanceStarRating;
        this.taskDescription = taskDescription;
        this.completed = completed;
    }

    public String getTaskTitle() {
        return taskTitle;
    }

    public void setTaskTitle(String taskTitle) {
        this.taskTitle = taskTitle;
    }

    public String getTaskDate() {
        return taskDate;
    }

    public void setTaskDate(String taskDate) {
        this.taskDate = taskDate;
    }

    public int getImportanceStarRating() {
        return importanceStarRating;
    }

    public void setImportanceStarRating(int importanceStarRating) {
        this.importanceStarRating = importanceStarRating;
    }

    public String getTaskDescription() {
        return taskDescription;
    }

    public void setTaskDescription(String taskDescription) {
        this.taskDescription = taskDescription;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setIsCompleted(boolean isCompleted) {
        this.completed = isCompleted;
    }


}
