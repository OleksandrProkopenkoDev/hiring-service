package ua.com.hiringservice.service.impl;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;

import freemarker.template.TemplateException;
import jakarta.mail.MessagingException;
import java.io.IOException;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ua.com.hiringservice.util.email.service.EmailSenderService;

/**
 * Unit tests for the {@link EmailNotificationServiceImpl} class.
 *
 * @author Artem Dreveckyi
 */
@ExtendWith(MockitoExtension.class)
class EmailNotificationServiceImplTest {

  @Mock private EmailSenderService emailSenderService;

  @InjectMocks private EmailNotificationServiceImpl emailService;

  @Test
  void testSendEmailAppFormProcessing_shouldInvokeServiceWithExpectedSubjectAndHtml()
      throws MessagingException, IOException, TemplateException {
    final UUID keycloakId = UUID.randomUUID();
    emailService.sendEmailAppFormProcessing(keycloakId);
    verify(emailSenderService)
        .sendEmail(
            eq(keycloakId),
            eq(EmailNotificationServiceImpl.SUBJECT_APP_FORM_PROCESSING),
            eq(EmailNotificationServiceImpl.HTML_APP_FORM_PROCESSING));
  }

  @Test
  void testSendEmailAppFormAccepted_shouldInvokeServiceWithExpectedSubjectAndHtml()
      throws MessagingException, IOException, TemplateException {
    final UUID keycloakId = UUID.randomUUID();
    emailService.sendEmailAppFormAccepted(keycloakId);
    verify(emailSenderService)
        .sendEmail(
            eq(keycloakId),
            eq(EmailNotificationServiceImpl.SUBJECT_APP_FORM_ACCEPTED),
            eq(EmailNotificationServiceImpl.HTML_APP_FORM_ACCEPTED));
  }

  @Test
  void testSendEmailAppFormRejected_shouldInvokeServiceWithExpectedSubjectAndHtml()
      throws MessagingException, IOException, TemplateException {
    final UUID keycloakId = UUID.randomUUID();
    emailService.sendEmailAppFormRejected(keycloakId);
    verify(emailSenderService)
        .sendEmail(
            eq(keycloakId),
            eq(EmailNotificationServiceImpl.SUBJECT_APP_FORM_REJECTED),
            eq(EmailNotificationServiceImpl.HTML_APP_FORM_REJECTED));
  }

  @Test
  void testSendEmailQuizAnswerReceived_shouldInvokeServiceWithExpectedSubjectAndHtml()
      throws MessagingException, IOException, TemplateException {
    final UUID keycloakId = UUID.randomUUID();
    emailService.sendEmailQuizAnswerReceived(keycloakId);
    verify(emailSenderService)
        .sendEmail(
            eq(keycloakId),
            eq(EmailNotificationServiceImpl.SUBJECT_QUIZ_ANSWER_RECEIVED),
            eq(EmailNotificationServiceImpl.HTML_QUIZ_ANSWER_RECEIVED));
  }

  @Test
  void testSendEmailQuizPassed_shouldInvokeServiceWithExpectedSubjectAndHtml()
      throws MessagingException, IOException, TemplateException {
    final UUID keycloakId = UUID.randomUUID();
    emailService.sendEmailQuizPassed(keycloakId);
    verify(emailSenderService)
        .sendEmail(
            eq(keycloakId),
            eq(EmailNotificationServiceImpl.SUBJECT_QUIZ_PASSED),
            eq(EmailNotificationServiceImpl.HTML_QUIZ_PASSED));
  }

  @Test
  void testSendEmailQuizNotPassed_shouldInvokeServiceWithExpectedSubjectAndHtml()
      throws MessagingException, IOException, TemplateException {
    final UUID keycloakId = UUID.randomUUID();
    emailService.sendEmailQuizNotPassed(keycloakId);
    verify(emailSenderService)
        .sendEmail(
            eq(keycloakId),
            eq(EmailNotificationServiceImpl.SUBJECT_QUIZ_NOT_PASSED),
            eq(EmailNotificationServiceImpl.HTML_QUIZ_NOT_PASSED));
  }

  @Test
  void testSendEmailInterviewScheduled_shouldInvokeServiceWithExpectedSubjectAndHtml()
      throws MessagingException, IOException, TemplateException {
    final UUID keycloakId = UUID.randomUUID();
    emailService.sendEmailInterviewScheduled(keycloakId);
    verify(emailSenderService)
        .sendEmail(
            eq(keycloakId),
            eq(EmailNotificationServiceImpl.SUBJECT_INTERVIEW_SCHEDULED),
            eq(EmailNotificationServiceImpl.HTML_INTERVIEW_SCHEDULED));
  }
}
