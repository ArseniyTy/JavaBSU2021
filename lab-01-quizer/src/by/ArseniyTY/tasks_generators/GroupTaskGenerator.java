package by.ArseniyTY.tasks_generators;

import by.ArseniyTY.exceptions.NotEnoughTasksException;
import by.ArseniyTY.exceptions.NotHandledEnumElementException;
import by.ArseniyTY.quizer.Task;
import by.ArseniyTY.quizer.TaskGenerator;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class GroupTaskGenerator implements TaskGenerator {
    private List<TaskGenerator> generators;

    public GroupTaskGenerator(TaskGenerator... generators) {
        this(List.of(generators));
    }

    public GroupTaskGenerator(List<TaskGenerator> generators) {
        setGenerators(generators);
    }

    private void setGenerators(List<TaskGenerator> generators) {
        if (generators.isEmpty()) {
            throw new IllegalArgumentException();
        }
        this.generators = generators;
    }

    @Override
    public Task generate() throws NotHandledEnumElementException, NotEnoughTasksException {
        return generators.get(ThreadLocalRandom.current().nextInt(0, generators.size())).generate();
    }
}