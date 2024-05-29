package com.flightsearch.models;

public enum SignStatus {
    ON_HOLD, // ожидает подтверждения или отклонения
    CONFIRMED, // Подписан
    REJECTED, // Отклонен
    MISSED_DEADLINE // Пропущен дедлайн подписания
}
