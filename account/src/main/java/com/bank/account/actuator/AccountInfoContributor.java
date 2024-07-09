package com.bank.account.actuator;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.actuate.info.Info;
import org.springframework.boot.actuate.info.InfoContributor;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

/**
 * Компонент, добавляющий в актуатор информацию о приложении (имя, артефакт, версия, контекстный путь, время запуска)
 * для мониторинга и управления приложением.
 *
 * Поля:
 * - {@code appName}: имя приложения, считывается из настроек.
 * - {@code artifactId}: идентификатор артефакта, считывается из настроек.
 * - {@code version}: версия сборки, считывается из настроек.
 * - {@code contextPath}: контекстный путь сервера, считывается из настроек.
 * - {@code startupTime}: время запуска приложения, устанавливается при старте.
 */
@Component
public class AccountInfoContributor implements InfoContributor {
    @Value("${spring.application.name}")
    private String appName;

    @Value("${info.build.artifact}")
    private String artifactId;

    @Value("${info.build.version}")
    private String version;

    @Value("${server.servlet.context-path}")
    private String contextPath;

    private Instant startupTime;

    /**
     * Устанавливает время запуска приложения при его инициализации.
     */
    @PostConstruct
    public void onStartup() {
        this.startupTime = Instant.now();
    }

    /**
     * Добавляет данные о приложении в билдер информации.
     *
     * @param builder билдер информации, в который добавляются данные о приложении.
     *                Билдер используется для сборки информации, отображаемой в актуаторе.
     */
    @Override
    public void contribute(Info.Builder builder) {
        final Map<String, Object> appDetails = new HashMap<>();
        appDetails.put("name", appName);
        appDetails.put("artifactId", artifactId);
        appDetails.put("version", version);
        appDetails.put("contextPath", contextPath);
        appDetails.put("startupTime", startupTime);

        builder.withDetail("app", appDetails);
    }
}
