### sprint5-my-mart-app
Practical work for yp - sprint 5.

1. Для сборки выполнить: **gradle bootJar**
2. Результирующий **yp-mjava-pws-s5-s8-0.0.1-SNAPSHOT.jar** будет доступен здесь: **${project.basedir}/docker-compose/app**
3. Для деплоя выполнить: **docker compose -p "sprint5-pw" up**
   В результате будет развернуто 4 сервиса:

   -**yp-db** - это PostgreSQL, connection к бд можно в docker-compose.yaml посмотреть как и все остальные параметры.. Доступен во внутр сети докера по **yp-db:5432** и внешне по: **localhost:5432**

   -**yp-pgadmin** - UI для PostgreSQL. Внешний доступ: **localhost/pgadmin4** (прокси через yp-gw), креды в UI смотрим в docker-compose.yaml

   -**yp-app** - само приложение. Внешний доступ: **localhost:8080**.

   -**yp-gw** - nginx + front-app. Внешний досуп: **localhost**.

### После деплоя можно проверять функциональность:

[!screenshot 1](https://github.com/alko0925/yp-mjava-pws/tree/module_two_sprint_five_branch/images/s5_pw_1.png)   
[!screenshot 2](https://github.com/alko0925/yp-mjava-pws/tree/module_two_sprint_five_branch/images/s5_pw_2.png)   
[!screenshot 3](https://github.com/alko0925/yp-mjava-pws/tree/module_two_sprint_five_branch/images/s5_pw_3.png)   
[!screenshot 4](https://github.com/alko0925/yp-mjava-pws/tree/module_two_sprint_five_branch/images/s5_pw_4.png)   
[!screenshot 5](https://github.com/alko0925/yp-mjava-pws/tree/module_two_sprint_five_branch/images/s5_pw_5.png)   
[!screenshot 6](https://github.com/alko0925/yp-mjava-pws/tree/module_two_sprint_five_branch/images/s5_pw_6.png)   
[!screenshot 7](https://github.com/alko0925/yp-mjava-pws/tree/module_two_sprint_five_branch/images/s5_pw_7.png)   

### Дополнения:
1. Функциональность - пункт 9: реализовано здесь: **/product/add**
2. Возможно будут вопросы касаемо уровня покрытия тестами, тут постарался покрыть основные моменты без излишней детализации, учитываю тот момент что и так уже задержал выдачу.. Постараюсь добавить больше тестов в следующей итерации.
3. Ну и по стартовому наполнению ветрины, прошу строго не судить :)
