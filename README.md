# CRM Project

## Описание проекта

CRM Project — это RESTful веб-приложение, разработанное на языке Java с использованием Spring Boot. Приложение предназначено для управления информацией о продавцах и транзакциях, а также предоставляет аналитические возможности для оценки эффективности продавцов.

## Функциональность

- **Управление продавцами**: создание, получение, обновление и удаление информации о продавцах.
- **Управление транзакциями**: создание и получение связанных с продавцами.
- **Аналитика**:
  - Получение топ-продавца за определенный период.
  - Получение списка продавцов с суммой транзакций меньше заданной.
  - Определение лучшего периода продаж для конкретного продавца.

## Технологии и зависимости

- **Java 23**: Язык программирования.
- **Spring Boot**: Фреймворк для создания веб-приложений.
- **Spring Data JPA**: Для работы с базой данных.
- **Hibernate**: ORM для взаимодействия с базой данных.
- **H2 Database**: Встроенная база данных для разработки и тестирования.
- **Lombok**: Упрощение кода с помощью аннотаций.
- **Swagger/OpenAPI**: Документация и тестирование API.
- **JUnit 5**: Фреймворк для написания тестов.
- **Mockito**: Мокирование объектов в тестах.
- **JaCoCo**: Отчет о покрытии кода тестами.
- **Docker**: Контейнеризация приложения.

## Инструкции по настройке базы данных

По умолчанию приложение настроено на использование базы данных PostgreSQL в памяти. Настройки базы данных находятся в файле `src/main/resources/application.properties`:

Вы можете создать у себя в postgers базу данных под именем crm_db или изменить в файле имя дазы данных на свою в файле application.properties.

```yaml
spring.application.name=${SPRING_APPLICATION_NAME:crm-project}
spring.datasource.url=${SPRING_DATASOURCE_URL:jdbc:postgresql://localhost:5432/crm_db} //Можно поменять на свое имя
spring.datasource.username=${SPRING_DATASOURCE_USERNAME:postgres}
spring.datasource.password=${SPRING_DATASOURCE_PASSWORD:}
spring.jpa.hibernate.ddl-auto=${SPRING_JPA_HIBERNATE_DDL_AUTO:update}
spring.jpa.show-sql=true


```

## Инструкции по сборке и запуску

### Предварительные требования

- **Java 23** установлен на вашем компьютере.

- **PostgreSQL** установлен на вашем компьютере.

- **Docker Desktop**  установлены (для запуска через Docker).
### Сборка проекта

1. **Клонируйте репозиторий**:

   ```bash
   git clone https://github.com/yourusername/crm_project.git
   ```

2. **Перейдите в директорию проекта**:

   ```bash
   cd crm_project
   ```

3. **Соберите проект с помощью Gradle**:

   ```bash
   ./gradlew clean build
   ```

### Запуск вручную

> [!IMPORTANT]
> Убедитесь что PostgreSQL установлен и запущен и что в нем создана БД с именем crm_db или тем что вы указали в application.properties.
1. **Клонируйте репозиторий**:

   ```bash
   git clone https://github.com/yourusername/crm_project.git
   ```
   

2. **Перейдите в директорию проекта**:

   ```bash
   cd crm_project
   ```

3. **Соберите проект с помощью Gradle**:

   ```bash
   ./gradlew clean build
   ```

### Запуск приложения

1. **Запустите приложение**:

   ```bash
   ./gradlew bootRun
   ```

2. Приложение будет доступно по адресу: `http://localhost:8080`

### Просмотр документации API

- **Swagger UI** доступен по адресу: `http://localhost:8080/swagger-ui/index.html`

## Примеры использования API

### 1. Управление продавцами

#### Получить список всех продавцов

- **Запрос**:

  ```
  GET /sellers
  ```

- **Ответ**:

  ```json
  [
    {
      "id": 1,
      "name": "Иван Иванов",
      "contactInfo": "+7 (123) 456-78-90",
      "registrationDate": "2024-10-15T12:34:56"
    }
  ]
  ```

#### Создать нового продавца

- **Запрос**:

  ```
  POST /sellers
  Content-Type: application/json

  {
    "name": "Иван Иванов",
    "contactInfo": "+7 (987) 654-32-10"
  }
  ```

- **Ответ**:

  ```json
  {
    "id": 1,
    "name": "Иван Иванов",
    "contactInfo": "+7 (123) 456-78-90",
    "registrationDate": "2024-10-15T12:34:56"
  }
  ```

### 2. Управление транзакциями

#### Создать новую транзакцию

- **Запрос**:

  ```
  POST /transactions
  Content-Type: application/json

  {
    "sellerId": 1,
    "amount": 1500.00,
    "paymentType": "CARD"
  }
  ```

- **Ответ**:

  ```json
  {
    "id": 1,
    "sellerId": 1,
    "amount": 1500,
    "paymentType": "CARD",
    "transactionDate": "2024-10-15T12:45:00"
  }
  ```

#### Получить все транзакции продавца

- **Запрос**:

  ```
  GET /transactions/seller/{sellerId}
  ```

- **Ответ**:

  ```json
  [
    {
      "id": 1,
      "sellerId": 1,
      "amount": 1500,
      "paymentType": "CARD",
      "transactionDate": "2024-10-15T12:45:00"
    }
  ]
  ```

### 3. Аналитические запросы

#### Получить топ-продавца за последний месяц

- **Запрос**:

  ```
  GET /analytics/top-seller?period=month
  ```

- **Ответ**:

  ```json
  {
    "id": 1,
    "name": "Иван Иванов",
    "contactInfo": "+7 (123) 456-78-90",
    "registrationDate": "2024-10-15T12:34:56",
    "totalAmount": 15000
  }
  ```

#### Получить продавцов с суммой транзакций меньше 10,000 за указанный период

- **Запрос**:

  ```
  GET /analytics/underperforming-sellers?amount=10000&startDate=2024-01-01T00:00:00&endDate=2024-12-31T23:59:59
  ```

- **Ответ**:

  ```json
  [
    {
      "id": 1,
      "name": "Иван Иванов",
      "contactInfo": "+7 (123) 456-78-90",
      "registrationDate": "2024-10-15T12:34:56",
      "totalAmount": 15000
    }
  ]
  ```


## Тестирование

### Запуск юнит-тестов и генерация отчета о покрытии кода

1. **Запустите тесты с генерацией отчета JaCoCo**:

   ```bash
   ./gradlew clean test jacocoTestReport
   ```

2. **Отчет о покрытии кода** будет доступен по пути:

   ```
   build/reports/jacoco/test/html/index.html
   ```


## Зависимости

Проект использует следующие зависимости:

- **Spring Boot Starter Web**: Создание веб-приложений.
- **Spring Boot Starter Data JPA**: Работа с базой данных.
- **H2 Database**: Встроенная база данных для разработки и тестирования.
- **Lombok**: Упрощение кода с помощью аннотаций.
- **Swagger/OpenAPI**: Автоматическая генерация документации API.
- **JUnit 5**: Фреймворк для модульного тестирования.
- **Mockito**: Инструмент для создания моков в тестах.
- **JaCoCo**: Генерация отчетов о покрытии кода тестами.

### Контакты

- **Email**: vova-gorohov04@mail.ru
- **GitHub**: [VGorHub](https://github.com/VGorHub)
