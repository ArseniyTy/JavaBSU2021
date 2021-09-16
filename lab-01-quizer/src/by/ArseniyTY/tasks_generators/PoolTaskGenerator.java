package by.ArseniyTY.tasks_generators;

import by.ArseniyTY.exceptions.NotEnoughTasksException;
import by.ArseniyTY.quizer.Task;
import by.ArseniyTY.quizer.TaskGenerator;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class PoolTaskGenerator implements TaskGenerator {
    private final boolean allowDuplicate;
    private ArrayList<Integer> availableTaskIndices;
    private List<Task> tasks;

    public PoolTaskGenerator(boolean allowDuplicate, Task... tasks) {
        this(allowDuplicate, List.of(tasks));
    }

    public PoolTaskGenerator(boolean allowDuplicate, List<Task> tasks) {
        this.allowDuplicate = allowDuplicate;
        setAvailableTaskIndices(tasks.size());
        setTasks(tasks);
    }

    private void setAvailableTaskIndices(int size) {
        availableTaskIndices = new ArrayList<>(size);
        for (int i = 0; i < size; i++) {
            availableTaskIndices.add(i);
        }
    }

    private void setTasks(List<Task> tasks) {
        if (tasks.isEmpty()) {
            throw new IllegalArgumentException();
        }
        this.tasks = tasks;
    }

    @Override
    public Task generate() throws NotEnoughTasksException {
        if (allowDuplicate) {
            return tasks.get(ThreadLocalRandom.current().nextInt(0, tasks.size()));
        }

        if (availableTaskIndices.isEmpty()) {
            throw new NotEnoughTasksException();
        }
        int idx = availableTaskIndices.remove(
                ThreadLocalRandom.current().nextInt(0, availableTaskIndices.size()));
        return tasks.get(idx);
    }
}
