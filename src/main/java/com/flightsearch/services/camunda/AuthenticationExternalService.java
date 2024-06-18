package com.flightsearch.services.camunda;

import com.flightsearch.models.Gender;
import com.flightsearch.schemas.user.UserRegister;
import com.flightsearch.services.UserService;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.camunda.bpm.client.task.ExternalTask;
import org.camunda.bpm.client.task.ExternalTaskService;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.logging.Logger;

@Service
@RequiredArgsConstructor
public class AuthenticationExternalService {

    private final UserService userService;
    private final ExternalTaskWorker externalTaskClientService;

    private final static Logger LOGGER = Logger.getLogger(AuthenticationExternalService.class.getName());

    @PostConstruct
    public void addCamundaTaskHandlers() {
        externalTaskClientService.addHandlerToTopic("user-register", this::register);
        externalTaskClientService.addHandlerToTopic("user-login", this::login);
    }

    private void register(ExternalTask externalTask, ExternalTaskService externalTaskService) {
        Map<String, Object> vars = externalTask.getAllVariables();

        UserRegister schema = new UserRegister();
            schema.setName(vars.get("name").toString());
            schema.setSurname(vars.get("surname").toString());
            schema.setPatronymic(vars.get("patronymic").toString());
            schema.setGender(Gender.MALE);
            schema.setTelephone(vars.get("telephone").toString());
            schema.setEmail(vars.get("email").toString());
            schema.setLogin(vars.get("login").toString());
            schema.setPassword(vars.get("password").toString());

        userService.register(schema);

        try {
            externalTaskService.complete(externalTask);
        } catch (Exception e) {
            externalTaskService.handleBpmnError(externalTask, "registerError");
        }

    }

    private void login(ExternalTask externalTask, ExternalTaskService externalTaskService) {
        LOGGER.info("It's log in");
        try {
            externalTaskService.complete(externalTask);
        } catch (Exception e) {
            externalTaskService.handleBpmnError(externalTask, "registerError");
        }

    }
}