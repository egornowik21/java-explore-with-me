
## <a id="title1">ExploreWithMe - афиша событий</a> 
#### В этой афише можно предложить какое-либо событие от выставки до похода в кино и собрать компанию для участия в нём.
<img width="800" alt="image" src="https://github.com/egornowik21/java-explore-with-me/assets/114665170/3361299d-37ed-441f-92b8-a66b83d4ef15">

------------
Это моя реализация backend части приложения афиши с REST API. Предполагается, что для полноценной работы приложения необходимо реализация внешнего интерфейса. 
### <a id="title1">Технологии</a>
- Java 11
- Maven
- Spring Boot
- Hibernate
- PostgreSQL
- Slf4j
- Lombok
- REST API
- Docker
- Git
- Postman
- Actuator
---------
### <a id="title1">Быстрый старт</a>
Для начала нужно собрать проект с помощью Maven
```
mvn clean package
```
Далее разверните проект с помощью Docker Compose
```
docker-compose up
```
<img width="408" alt="image" src="https://github.com/egornowik21/java-explore-with-me/assets/114665170/8e926c12-3fe9-4428-ae99-15d511ce4f4b">

### <a id="title1">Функциональность приложения</a>
### 1. Основной сервис:
- [x] Публичное API:
    - просмотр списка событий в краткой версии;
    - просмтор подробной информации о событии;
    - каждое событие относится к какой-либо закрепленной категории;
    - получение всех имеющихся категорий и подборок событий;
    - запросы с публичного API фиксируются в сервисе статистики.
- [x] - Приватное API:
    - авторизованные пользователи имею возможность добавлять в приложение новые мероприятия, редактировать их и просматривать после добавления;
    - настроена подача заявок на участие в интересующих мероприятиях;
    - создатель мероприятия имеет возможность подтверждать заявки, которые отправили другие пользователи сервиса.
- [x] - API администратора:
    - добавление, изменение и удаление категорий для событий;
    - добавление, удаление и закрепление на главной странице подборки мероприятий;
    - модерация событий, размещённых пользователями, — публикация или отклонение;
    - управление пользователями — добавление, активация, просмотр и удаление.
### 2. Сервис статистики
- [x] запись информации о том, что был обработан запрос к эндпоинту API;
- [x] предоставление статистики за выбранные даты по выбранному эндпоинту.
### <a id="title1">Пример запроса</a>
```
http://localhost:8080/users/:userId/events
```
Request
```
{
  "annotation": "Сплав на байдарках похож на полет.",
  "category": 2,
  "description": "Сплав на байдарках похож на полет. На спокойной воде — это парение. На бурной, порожистой — выполнение фигур высшего пилотажа. И то, и другое дарят чувство обновления, феерические эмоции, яркие впечатления.",
  "eventDate": "2024-12-31 15:10:05",
  "location": {
    "lat": 55.754167,
    "lon": 37.62
  },
  "paid": true,
  "participantLimit": 10,
  "requestModeration": false,
  "title": "Сплав на байдарках"
}
```
Response
```
{
  "annotation": "Эксклюзивность нашего шоу гарантирует привлечение максимальной зрительской аудитории",
  "category": {
    "id": 1,
    "name": "Концерты"
  },
  "confirmedRequests": 5,
  "createdOn": "2022-09-06 11:00:23",
  "description": "Что получится, если соединить кукурузу и полёт? Создатели \"Шоу летающей кукурузы\" испытали эту идею на практике и воплотили в жизнь инновационный проект, предлагающий свежий взгляд на развлечения...",
  "eventDate": "2024-12-31 15:10:05",
  "id": 1,
  "initiator": {
    "id": 3,
    "name": "Фёдоров Матвей"
  },
  "location": {
    "lat": 55.754167,
    "lon": 37.62
  },
  "paid": true,
  "participantLimit": 10,
  "publishedOn": "2022-09-06 15:10:05",
  "requestModeration": true,
  "state": "PUBLISHED",
  "title": "Знаменитое шоу 'Летающая кукуруза'",
  "views": 999
}
```

[Pull request link](https://github.com/egornowik21/java-explore-with-me/pull/5)
