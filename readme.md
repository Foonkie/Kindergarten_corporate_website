##Kindergarten corporate website

*Java project*

###Идея проекта:
Данный проект представляет из себя движок для корпоративного сайта детского сада. 

###В проекте реализовано:
- Менеджер задач
- Новостной раздел 
- Доступ к тематической документации

####*Доступ к сайту*
Для того, чтобы получить доступ к сайту, требуется пройти аутенфикацию введя логин и пароль. Если же аккаунта пользвателя не существует, необходимо пройти регистрацию, перейдя со странички аутентификации. В зависимости от роли пользователя в системе, ему будет передана соответствующая главная страница.
####*Менеджер задач*
Пользователь с правами администратора (в дальнейшем администратор) имеет возможность ставить, редактировать и убирать задачи пользователям, контролировать выполнение задач

Каждая задача включает в себя до 5 подзадач, которые имеют статусы выполнено, либо не выполнено.

Пользователь с правами доступа педагога видит свои задачи и может менять статус у подзадач.

####*Новостной раздел*
Администратор создает и видит список новостей на сайте. Также он имеет возможность отредактировать или удалить уже размещенную новость на сайте.

Пользователь же видит весь список новостей, имея возможность ограничить кол-во выводимой информации с помощью страниц кол-ва новостей на страницу.

####*Доступ к тематической документации*
Администратор имеет возможность загружать на сайт доументацию, которая сортируется при создании в заданную категорию. Загруженный на сервер файл может быть скачан, как пользователем с правами педагога, так и администратором. Также для администратора сайта имеется возможность удаления ненужных файлов.

###Перспективы и цели проекта:
-Покрыть сайт тестами 
-Расширить количество кабинетов для других категорий пользователей.
-Осуществлять сбор и анализ статистики по задачам.
-Сделать интернационализацию сайта, так имеют место быть англоговорящие сотрудники.
-Для воспитателей групп организовать состав их групп и учет посещаемости детьми садика.
-Буду благодарен новым идеям и пожеланиям;)


###Применяемый стек технологий:
-Java Core, PostgreSQL, Spring (Core, Data, MVC, Security), Gradle, HTML
