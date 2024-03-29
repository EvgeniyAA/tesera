# Данный проект является собственной разработкой, копирующей функционал сайта о настольных играх - m.tesera.ru

* Проект делается с целью изучения новых технологий и подходах в разработке

## Используемые технологии

* Kotlin
* Dagger Hilt
* Coroutines, Flow
* Compose
* Coil
* OkHttp, Retrofit

## Архитектура и подход

* MVI архитектура, но на данный момент все еще не определился с правильной реализацией.
* Многомодульность, но domain и data слои превращаются в монолиты. 
* В дальнейшем планируется разделение и вынесение отдельных domain и data модулей под каждую фичу
* Навигация на данный момент тоже сделана не совсем корректно, собрана в одном месте (dashboard) и управляется с него. 
* Планируется реализация feature-api модулей для навигации между фичами.

--- 

## О проекте

* На данный момент есть желание сделать MVP с возможностью "просмотра" контента. 
* Уже сейчас можно смотреть детальную информацию о настольной игре, последние комментарии по игре, посмотреть/скачать файлы/правила и пр.
* В дальнейшем, планируется добавить функционал, которым обладает авторизованный пользователь.

---

### Следующие экраны еще не доделаны:

* Экран поиска (в процессе)
* Экран профиля пользователя (в процессе)
* Экран залогиненного пользователя (заглушка)
* Экран игровых отчетов
* Комментарии ("первичная" реализация)