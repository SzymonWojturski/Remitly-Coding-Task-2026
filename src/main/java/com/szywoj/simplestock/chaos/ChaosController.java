package com.szywoj.simplestock.chaos;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ChaosController {

    @PostMapping("/chaos")
    public ResponseEntity<String> chaos() {
        new Thread(() -> {
            try {
                Thread.sleep(50);
            } catch (InterruptedException ignored) {}

            System.exit(1);
        }).start();

        return ResponseEntity.accepted().body("killed");
    }


}
