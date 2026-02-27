package command;

import groot.Storage;
import groot.Ui;
import groot.task.Task;
import groot.task.TaskList;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Adds one or more tags to the target task and persists the change.
 */
public class TagCommand extends Command {
    private final int index;
    private final List<String> tags;

    /**
     * Constructs a TagCommand for the given task index and tag list.
     *
     * @param index Zero-based index of the task to tag.
     * @param tags  Raw tags supplied by the user.
     */
    public TagCommand(int index, List<String> tags) {
        this.index = index;
        this.tags = tags;
    }

    @Override
    public String execute(TaskList tasks, Ui ui, Storage storage) throws Exception {
        int actualIndex = tasks.resolveIndexFromView(index);
        Task task = tasks.getTask(actualIndex);
        Set<String> normalizedTags = new LinkedHashSet<>();
        for (String tag : tags) {
            String normalized = normalize(tag);
            if (normalized == null) {
                continue;
            }
            normalizedTags.add(normalized);
            task.addTag(tag);
        }
        if (normalizedTags.isEmpty()) {
            return "I am Groot. No valid tags were provided.\n" + task.getStatus();
        }
        storage.updateDataFile(tasks);
        String formattedTags = normalizedTags.stream().map(tag -> "#" + tag).collect(Collectors.joining(" "));
        return "I am Groot. Tagged task as " + formattedTags + ":\n" + task.getStatus();
    }

    private String normalize(String tag) {
        if (tag == null || tag.isBlank()) {
            return null;
        }
        return tag.trim().toLowerCase().replaceFirst("^#+", "");
    }
}
