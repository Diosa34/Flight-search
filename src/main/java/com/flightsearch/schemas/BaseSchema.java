package com.flightsearch.schemas;

/**
 * Интерфейс класса описывающий базовые методы для схем (TDO) моделей,
 * которые имеют строитель. Интерфейс упрощает наследование.
 *
 * @param <Model> класс модели (сущности) для которой создается схема (TDO)
 * @param <ModelBuilder> класс строителя
 */
public interface BaseSchema<Model, ModelBuilder> extends ModelSchema<Model> {
    /**
     * Создает строитель и передает ему значения полей, которые находятся в схеме.
     * @return объект строителя.
     */
    EntityBuilder createAndFillModelBuilder();
}
