# solvro-backend-task
Szybkie [zadanie rekrutacyjne](https://github.com/Solvro/rekrutacja) na backend do koła naukowego Solvro.
## Overview
Celem było napisanie backendu do małej aplikacji imitującej znane **JakDojade** gdzie dostając mapę miasta mamy zwracać użytkownikowi najkrótszą
trasę pomiędzy dwoma z przystanków. Tutaj sugerując się specyfikacją podaną w treści zadania założyłem, że nazwy przystanków są unikalne
skoro podajemy je w query do wyznaczenia trasy z czym niestety koliduje przykładowa mapa podana w treści gdyż niektóre nazwy powtarzają się tam
kilkukrotnie. W takim przypadku albo mogłem nie zastosować się do specyfikacji i znajdować przystanki po id albo założyć, że nazwy przystanków
nie powinny się powtarzać. A tak poza tym to kod otestowany, dijkstra zaimplementowany, swagger podpięty.

SwaggerUI na `localhost:8080/swagger-ui.html`
## Building
* Klasycznie:\
  Lecisz z `mvnw package`, czekasz aż wszystkie zależności się dociągną i projekt się zbuduje i odpalasz plik `.jar` w folderze `/target`
* Docker:\
  Od wersji spring boota 2.3.x możesz jedną komendą wygenerować sobie obraz: `mvnw spring-boot:build-image` (Przy generowaniu musisz mieć włączonego dockera). 
  Za pierwszym razem może to trochę potrwać bo docker musi sobie dociągnąć trochę rzeczy.
## Tech stack
* [Spring Boot](https://spring.io/projects/spring-boot) z kilkoma podstawowymi dla niego zależnościami
* [Spring Security](https://spring.io/projects/spring-security) do autentyfikacji
* [Hibernate Validator](https://hibernate.org/validator/) do walidacji pól
* [Lombok](https://projectlombok.org/) do zredukowania javowego boilerplate'u
