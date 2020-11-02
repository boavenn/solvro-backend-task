package io.github.boavenn.solvrobackendtask.city;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class CityController
{
    public static final String DEFAULT_CITY = "solvro_city";
    private final CityService cityService;

    @GetMapping("/stops")
    public ResponseEntity<?> getStops() {
        return ResponseEntity.ok(cityService.getStops(DEFAULT_CITY));
    }

    @GetMapping("/path")
    public ResponseEntity<?> getPath(@RequestParam(name = "source") String source,
                                     @RequestParam(name = "target") String target) {
        return ResponseEntity.ok(cityService.getPath(DEFAULT_CITY, source, target));
    }
}
