package com.foonk.Kindergarten_corporate_website.mapper;

/*Интерфейс, который задает общее поведение всем Мапперам, преобразующим dto в сущность или наоборот*/
public interface Mapper<F, T> {
/*Метод возвращает новый класс по переданному объекту в аргументах класса*/
    T map(F object);
/*Метод возвращает переводит данные из одного объекта в другой и возвращает измененный объект*/
    default T map(F fromObject, T toObject) {
        return toObject;
    }
}
