### sprint6-my-mart-app
Practical work for yp - sprint 6.

1. Для сборки выполнить: **gradle bootJar**
2. Результирующий **yp-mjava-pws-s5-s8-0.0.1-SNAPSHOT.jar** будет доступен здесь: **${project.basedir}/docker-compose/app**
3. Для деплоя выполнить: **docker compose -p "sprint6-pw" up**
   В результате будет развернуто 4 сервиса:

   -**yp-db** - это PostgreSQL, connection к бд можно в docker-compose.yaml посмотреть как и все остальные параметры.. Доступен во внутр сети докера по **yp-db:5432** и внешне по: **localhost:5432**

   -**yp-pgadmin** - UI для PostgreSQL. Внешний доступ: **localhost/pgadmin4** (прокси через yp-gw), креды в UI смотрим в docker-compose.yaml

   -**yp-app** - само приложение. Внешний доступ: **localhost:8080**.

   -**yp-gw** - nginx. Внешний досуп: **localhost**.

### После деплоя можно проверять функциональность:

[!screenshot 1](https://github.com/alko0925/yp-mjava-pws-s5-s8/tree/module_two_sprint_six_branch/images/s6_pw_1.png)   
[!screenshot 2](https://github.com/alko0925/yp-mjava-pws-s5-s8/tree/module_two_sprint_six_branch/images/s6_pw_2.png)   
[!screenshot 3](https://github.com/alko0925/yp-mjava-pws-s5-s8/tree/module_two_sprint_six_branch/images/s6_pw_3.png)   
[!screenshot 4](https://github.com/alko0925/yp-mjava-pws-s5-s8/tree/module_two_sprint_six_branch/images/s6_pw_4.png)   
[!screenshot 5](https://github.com/alko0925/yp-mjava-pws-s5-s8/tree/module_two_sprint_six_branch/images/s6_pw_5.png)   
[!screenshot 6](https://github.com/alko0925/yp-mjava-pws-s5-s8/tree/module_two_sprint_six_branch/images/s6_pw_6.png)   
[!screenshot 7](https://github.com/alko0925/yp-mjava-pws-s5-s8/tree/module_two_sprint_six_branch/images/s6_pw_7.png)

### Дополнения:
1. Функциональность - пункт 9: реализовано здесь: **/product/add**
2. Кассаемо комментариев по ревью от спринта 5:
   1. _Вместо for (var cp : cart.getCartProducts()) лучше использовать java stream api. Таким образом получится убрать всю работу со списками и boilerpate с ней связанный_ - **Поправил**.
   2. _Дополнительно, лучше в классе ItemDto создать статический метод конвертации (который будет принимать на вход отображаемую сущность и возвращать экземпляр класса). Или дополнительный конструктор. В этом может помочь lombok._ - **Это уже постараюсь учесть в 7-ом спринте**.
   3. _В "боевом" окружении, Статику лучше не отдавать через java - там есть оптимизации, но это не сильно производительно. Лучше рассмотреть что-то вроде nginx_ - **Тоже попробую посмотреть уже в рамках 7-ого спринта**.

