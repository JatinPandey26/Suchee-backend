package com.suchee.app.notification;

import com.suchee.app.entity.Notification;
import com.suchee.app.enums.NotificationStatus;
import com.suchee.app.logging.Trace;
import com.suchee.app.service.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.util.List;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class NotificationProcessor {

    private final List<NotificationSender> notificationSenders;
    private final TemplateEngine templateEngine;
    private final NotificationService notificationService;

    /**
     * Processes the given NotificationRequest by creating Notification objects for each NotificationType
     * specified in the request, resolving message bodies via templates or raw messages, and sending
     * notifications through the appropriate NotificationSender implementations.
     *
     * <p>
     * This method performs the following checks and logs errors using Trace:
     * <ul>
     *   <li>Ensures that for each NotificationType, either a template or a raw message is provided, but not both.</li>
     *   <li>Validates that template variables satisfy the required placeholders in the template.</li>
     *   <li>Logs errors when required templates or variables are missing.</li>
     * </ul>
     * </p>
     *
     * @param notificationRequest the request containing recipients, notification types, templates, messages, and subjects
     * @throws IllegalArgumentException if a template is specified without required variables, or variables are missing
     */
    public void doProcess(NotificationRequest notificationRequest,boolean persistNotifications) {

        Trace.log("Starting notification processing for recipients: " + notificationRequest.getRecipients());

        for (NotificationType notificationT : notificationRequest.getNotificationTypes()) {
            if(Trace.notification) {
                Trace.log("Processing notification type: " + notificationT.name());
            }
            Notification notification = new Notification();
            notification.setRecipients(notificationRequest.getRecipients());
            notification.setType(notificationT);
            notification.setStatus(NotificationStatus.PENDING);

            boolean isTemplateBased = notificationRequest.getTemplateNames() != null
                    && notificationRequest.getTemplateNames().containsKey(notificationT);
            boolean isMessageBased = notificationRequest.getMessages() != null
                    && notificationRequest.getMessages().containsKey(notificationT);

            String body = "";

            if (!isMessageBased && !isTemplateBased) {
                Trace.error("No template or message provided for Notification Type: " + notificationT.name());
                continue;
            } else if (isTemplateBased && isMessageBased) {
                Trace.error("Both template and raw message provided for Notification Type: " + notificationT.name()
                        + ". Only one allowed.");
                // TODO: consider throwing an exception or persisting failed notifications for retry
                continue;
            } else if (isTemplateBased) {
                String templateName = notificationRequest.getTemplateNames().get(notificationT);
                Map<String, Object> templateVars = notificationRequest.getTemplateVariables() != null
                        ? notificationRequest.getTemplateVariables().get(notificationT)
                        : null;

                if (templateName == null) {
                    Trace.error("Template name is null for Notification Type: " + notificationT.name());
                    throw new IllegalArgumentException("Template not found for notification type: " + notificationT);
                }

                if (templateVars == null || templateVars.isEmpty()) {
                    Trace.error("Template variables are missing or empty for Notification Type: " + notificationT.name());
                    throw new IllegalArgumentException("Template variables are missing for notification type: " + notificationT);
                }

                TemplatesRegistry.validateTemplateVariables(templateName, templateVars);

                body = this.templateEngine.process(templateName, new Context(null, templateVars));
                notification.setTemplate(templateName);

                if(Trace.notification) {
                    Trace.log("Generated message body from template '" + templateName + "' for Notification Type: " + notificationT.name());
                }

            } else if (isMessageBased) {
                body = notificationRequest.getMessages().get(notificationT);
                if(Trace.notification) {
                    Trace.log("Using raw message for Notification Type: " + notificationT.name());
                }
            }

            notification.setMessage(body);
            notification.setSubject(notificationRequest.getSubjects() != null ? notificationRequest.getSubjects().get(notificationT) : null);

            if(persistNotifications){
                this.notificationService.save(notification);
            }

            // Dispatch notification to all supporting senders
            for (NotificationSender sender : notificationSenders) {
                if (sender.supports(notificationT)) {
                    if(Trace.notification) {
                        Trace.log("Sending notification of type " + notificationT.name() + " via sender: " + sender.getClass().getSimpleName());
                    }
                    try {
                        sender.send(notification);
                        if(persistNotifications){
                            notification.setStatus(NotificationStatus.SENT);
                            this.notificationService.save(notification);
                        }
                    }
                    catch (Exception e)
                    {
                        e.printStackTrace();
                        Trace.error("Notification send failed : " + e.getMessage());
                        if(persistNotifications){
                            notification.setStatus(NotificationStatus.FAILED);
                            notification.setFailureReason(e.getMessage());
                            this.notificationService.save(notification);
                        }
                    }
                }
            }
        }

        Trace.log("Completed notification processing.");
    }

}
