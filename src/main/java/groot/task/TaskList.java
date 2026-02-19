package groot.task;

import groot.Storage;
import java.io.IOException;
import java.util.ArrayList;

public class TaskList {
    private ArrayList<Task> listOfTasks;

    public TaskList(ArrayList<Task> listOfTasks) {
        this.listOfTasks = listOfTasks;
    }

    public ArrayList<Task> get() {
        return this.listOfTasks;
    }

    public Task getTask(int index) {
        return listOfTasks.get(index);
    }

    public Task deleteTask(Storage storage, int index) throws IOException {
        Task task = listOfTasks.get(index);
        listOfTasks.remove(index);
        Task.reduceTask();

        for (int i = index; i < listOfTasks.size(); i++) {
            listOfTasks.get(i).reduceIndex();
        }

        storage.updateDataFile(this);
        return task;
    }

    public void addTask(Storage storage, Task task) throws IOException {
        this.listOfTasks.add(task);
        storage.updateDataFile(this);
    }

    public Task markAsDone(Storage storage, int index) throws IOException {
        Task task = listOfTasks.get(index);
        task.markAsDone();
        storage.updateDataFile(this);
        return task;
    }

    public Task unmarkAsDone(Storage storage, int index) throws IOException {
        Task task = listOfTasks.get(index);
        task.unmarkAsDone();
        storage.updateDataFile(this);
        return task;
    }

    public ArrayList<Task> findTask(String searchedName) {
        ArrayList<Task> resultList = new ArrayList<>();
        for (Task task : listOfTasks) {
            if (task.name.toLowerCase().contains(searchedName.trim().toLowerCase())) {
                resultList.add(task);
            }
        }
        return resultList;
    }

    public ArrayList<Task> findTaskByTag(String tag) {
        ArrayList<Task> resultList = new ArrayList<>();
        String tagLower = tag.trim().toLowerCase();
        for (Task task : listOfTasks) {
            if (task.hasTag(tagLower)) {
                resultList.add(task);
            }
        }
        return resultList;
    }
}