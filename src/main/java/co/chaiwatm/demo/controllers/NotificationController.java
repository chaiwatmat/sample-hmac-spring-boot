package co.chaiwatm.demo.controllers;

import java.io.IOException;
import java.util.concurrent.CopyOnWriteArrayList;

import javax.validation.Valid;

import org.apache.logging.log4j.util.Timer;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter.SseEventBuilder;

import co.chaiwatm.demo.models.GenericResponse;
import co.chaiwatm.demo.models.NotificationMessage;
import co.chaiwatm.demo.models.RequestHeaderModel;
import co.chaiwatm.demo.utilities.SignatureUtility;

@RestController
public class NotificationController {

    private final CopyOnWriteArrayList<SseEmitter> emitters = new CopyOnWriteArrayList<>();

    @RequestMapping(path = "/stream", method = RequestMethod.GET)
    public SseEmitter stream() throws IOException {
        SseEmitter emitter = new SseEmitter(60000L);

        // java.util.Timer timer = new java.util.Timer();

        try {
            emitters.add(emitter);

            SseEventBuilder eventBuilder = SseEmitter.event();

            emitter.send(eventBuilder.data("{\"name\":\"chaiwat\"}").name("dataSet-created").id("100"));

            // timer.scheduleAtFixedRate(task, delay, period);
            // emitter.send("{\"name\":\"chaiwat\"}", MediaType.APPLICATION_JSON);

            RequestHeaderModel headerModel = new RequestHeaderModel();
            headerModel.setRequestuid("ssss");

            GenericResponse response = new GenericResponse<RequestHeaderModel>();
            response.setResponseUid("rrrrr");
            response.setData(headerModel);

            HttpHeaders headers = SignatureUtility.GetResponseHeader(response);

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