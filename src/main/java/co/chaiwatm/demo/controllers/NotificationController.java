package co.chaiwatm.demo.controllers;

import java.io.IOException;
import java.util.concurrent.CopyOnWriteArrayList;

import javax.validation.Valid;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import co.chaiwatm.demo.models.NotificationMessage;

@RestController
public class NotificationController {

    private final CopyOnWriteArrayList<SseEmitter> emitters = new CopyOnWriteArrayList<>();

    @RequestMapping(path = "/stream", method = RequestMethod.GET)
    public SseEmitter stream() throws IOException {
        SseEmitter emitter = new SseEmitter(60000L);

        try {
            emitters.add(emitter);
            emitter.onCompletion(() -> this.emitters.remove(emitter));
            emitter.onTimeout(() -> this.emitters.remove(emitter));
        } catch (Exception e) {
            e.printStackTrace();
        }

        return emitter;
    }

    @RequestMapping(path = "/chat", method = RequestMethod.POST)
    public NotificationMessage sendMessage(@Valid @RequestBody NotificationMessage message) {
        emitters.forEach((SseEmitter emitter) -> {
            try {
                emitter.send(message, MediaType.APPLICATION_JSON);
            } catch (IOException e) {
                emitter.complete();
                emitters.remove(emitter);
                e.printStackTrace();
            }
        });

        return message;
    }
}