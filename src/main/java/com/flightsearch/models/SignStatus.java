package com.flightsearch.models;

public enum SignStatus {
    ON_HOLD, // ожидает подтверждения или отклонения
    CONFIRMED, // Подписан
    REJECTED, // Отклонен
    THREE_DAYS_LEFT, // Осталось 3 дня до дедлайна (окончания ожидания подписания)
    MISSED_DEADLINE // Пропущен дедлайн подписания
}
