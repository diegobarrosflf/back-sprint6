package br.com.rchlo.notifications;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;

@Service
public class NotificationsCreator {

    private final Configuration freemarker;

    public NotificationsCreator(Configuration freemarker) {
        this.freemarker = freemarker;
    }

    public String createFor(ConfirmedTransactionEvent transaction) {
        try{
            Template template = freemarker.getTemplate("expense-notification.ftl");
            Map<Object, Object> data = new HashMap<>();
            data.put("transaction", transaction);
            StringWriter out = new StringWriter();
            template.process(data, out);
            return out.toString();
        }catch (IOException | TemplateException ex) {
            throw new IllegalArgumentException(ex);
        }

    }
}
