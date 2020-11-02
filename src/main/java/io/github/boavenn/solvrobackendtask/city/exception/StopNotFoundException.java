package io.github.boavenn.solvrobackendtask.city.exception;

import java.util.NoSuchElementException;

public class StopNotFoundException extends NoSuchElementException
{
    public StopNotFoundException(String name) {
        super("Cannot find stop with name=" + name);
    }
}
