package mael.dev.rabbitmq;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/send")
public class senderController {

    private final QueueSender queueSender;

    public senderController(QueueSender queueSender) {
        this.queueSender = queueSender;
    }

    @RequestMapping("/group1/{message}")
    public String sendMessageToGroup1(@PathVariable String message) {
        queueSender.sendToGroup1(message);
        return "Message sent!";
    }

    @RequestMapping("/group2/{message}")
    public String sendMessageToGroup2(@PathVariable String message) {
        queueSender.sendToGroup2(message);
        return "Message sent!";
    }

    @RequestMapping("/groups/{message}")
    public String sendMessageToGroups(@PathVariable String message) {
        queueSender.sendToGroup1(message);
        queueSender.sendToGroup2(message);
        return "Message sent!";
    }
}
