package by.ArseniyTY.tasks_generators;

import by.ArseniyTY.exceptions.NotEnoughTasksException;
import by.ArseniyTY.quizer.Task;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class GroupTaskGenerator implements Task.Generator {
    private List<Task.Generator> generators;

    public GroupTaskGenerator(Task.Generator... generators) {
        this(List.of(generators));
    }

    public GroupTaskGenerator(List<Task.Generator> generators) {
        setGenerators(generators);
    }

    private void setGenerators(List<Task.Generator> generators) {
        if (generators.isEmpty()) {
            throw new IllegalArgumentException();
        }
        this.generators = generators;
    }

    @Override
    public Task generate() throws NotEnoughTasksException {
        return generators.get(ThreadLocalRandom.current().nextInt(0, generators.size())).generate();
    }
}