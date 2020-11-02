package io.github.boavenn.solvrobackendtask.city.exception;

import java.util.NoSuchElementException;

public class CityNotFoundException extends NoSuchElementException
{
    public CityNotFoundException(String name) {
        super("Cannot find city with name=" + name);
    }
}
