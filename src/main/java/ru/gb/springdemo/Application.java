package ru.gb.springdemo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Application {

	// http://localhost:8080/swagger-ui.html

	/*
	 * План занятия:
     -- 0. Анонс группы в телеграме
     -- 1. Поговорить про стандартную структуру пакетов и "слои" в spring-web приложениях
     2. Поговорить про swagger и его подключение к приложению
     -- 3. Поговорить про REST-соглашения путей
     -- 4. Чуть подробнее рассмотреть исполнение входящего HTTP-запроса (https://mossgreen.github.io/Servlet-Containers-and-Spring-Framework/)
     -- 5. Чуть подробнее поговорить про жизненный цикл бина (и аннотацию PostConstruct)
     -- 6. Чуть подробнее поговорить про жизненный цикл поднятия контекста (и аннотацию EventListener)
     7. Без объяснений показать шаблон взаимодействия с БД (для использования в ДЗ)
     8. TODO
	 */

	/*
	 * слои spring-приложения
	 *
	 * 1. controllers (api)
	 * 2. сервисный слой (services)
	 * 3. репозитории (repositories, dao (data access objects), ...)
	 * 4. jpa-сущности (entity, model, domain)
	 *
	 *
	 * Сервер, отвечающий за выдачу книг в библиотеке.
	 * Нужно напрограммировать ручку, которая либо выдает книгу читателями, либо отказывает в выдаче.
	 *
	 * /book/** - книга
	 * GET /book/25 - получить книгу с идентификатором 25
	 *
	 * /reader/** - читатель
	 * /issue/** - информация о выдаче
	 * POST /issue {"readerId": 25, "bookId": 57} - выдать читателю с идентификатором 25 книгу с идентификатором 57
	 *
	 *

	/*
			Tomcat - контейнер сервлетов (веб-сервер)

			/student/...
			spring-student.war -> tomcat
			/api/...
			spring-api.war -> tomcat

			spring-web.jar
	 */

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);

//      @PostConstruct
//    public void generateData() {
//      bookRepository.save(new Book("Война и мир"));
//      bookRepository.save(new Book("Мертвые души"));
//      bookRepository.save(new Book("Чистый код"));
//      bookRepository.save(new Book("Декамерон"));
//      bookRepository.save(new Book("Горе от ума"));
//      bookRepository.save(new Book("Дракула"));
//      bookRepository.save(new Book("Капитал"));
//      bookRepository.save(new Book("Воскресенье"));
//  }

		//      @PostConstruct
//      public void generateData() {
//          readerRepository.save(new Reader("Игорь Смирнов"));
//          readerRepository.save(new Reader("Андрей Иванов"));
//          readerRepository.save(new Reader("Вася Петров"));
//          readerRepository.save(new Reader("Петя Кузнецов"));
//          readerRepository.save(new Reader("Оксана Семенова"));
//          readerRepository.save(new Reader("Ирина Новикова"));
//  }

		//      @PostConstruct
//    public void generateData() {
//        issueRepository.save(new Issue(5, 2));
//        issueRepository.save(new Issue(2, 1));
//        issueRepository.save(new Issue(4, 6));
//        issueRepository.save(new Issue(8, 1));
//        issueRepository.save(new Issue(1, 3));
//        issueRepository.save(new Issue(7, 4));
//        issueRepository.save(new Issue(3, 5));
//        issueRepository.save(new Issue(6, 1));
//    }
	}

}
