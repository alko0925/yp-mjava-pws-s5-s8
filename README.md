### sprint7: mart-app и pay-app
Practical work for yp - sprint 7.

1. Для сборки выполнить: **gradle bootJar**
2. Результирующий:
   - **mart-app-0.0.1-SNAPSHOT.jar** будет доступен здесь: **${project.basedir}/docker-compose/mart-app**
   - **pay-app-0.0.1-SNAPSHOT.jar** будет доступен здесь: **${project.basedir}/docker-compose/pay-app**
3. Для деплоя выполнить: **docker compose -p "sprint7-pw" up**
   В результате будет развернуто 6 сервисов:

   -**yp-db** - это PostgreSQL, connection к бд можно в docker-compose.yaml посмотреть как и все остальные параметры.. Доступен во внутр сети докера по **yp-db:5432** и внешне по: **localhost:5432**

   -**yp-redis** - Redis, доступен во внутр сети докера по **yp-redis:6379** и внешне по: **localhost:6379**

   -**yp-pgadmin** - UI для PostgreSQL. Внешний доступ: **localhost/pgadmin4** (прокси через yp-gw), креды в UI смотрим в docker-compose.yaml

   -**yp-mart-app** - основное приложение. Внешний доступ: **localhost:8080**.

   -**yp-pay-app** - сервис платежей. Внешний доступ: **localhost:8081**.

   -**yp-gw** - nginx. Внешний досуп: **localhost**.

### После деплоя можно проверять функциональность:

[!screenshot 1](https://github.com/alko0925/yp-mjava-pws-s5-s8/tree/module_two_sprint_seven_branch/images/s7_pw_1.png)   
[!screenshot 2](https://github.com/alko0925/yp-mjava-pws-s5-s8/tree/module_two_sprint_seven_branch/images/s7_pw_2.png)   
[!screenshot 3](https://github.com/alko0925/yp-mjava-pws-s5-s8/tree/module_two_sprint_seven_branch/images/s7_pw_3.png)   
[!screenshot 4](https://github.com/alko0925/yp-mjava-pws-s5-s8/tree/module_two_sprint_seven_branch/images/s7_pw_4.png)   
[!screenshot 5](https://github.com/alko0925/yp-mjava-pws-s5-s8/tree/module_two_sprint_seven_branch/images/s7_pw_5.png)   
[!screenshot 6](https://github.com/alko0925/yp-mjava-pws-s5-s8/tree/module_two_sprint_seven_branch/images/s7_pw_6.png)   
[!screenshot 7](https://github.com/alko0925/yp-mjava-pws-s5-s8/tree/module_two_sprint_seven_branch/images/s7_pw_7.png)   
[!screenshot 8](https://github.com/alko0925/yp-mjava-pws-s5-s8/tree/module_two_sprint_seven_branch/images/s7_pw_8.png)   
[!screenshot 9](https://github.com/alko0925/yp-mjava-pws-s5-s8/tree/module_two_sprint_seven_branch/images/s7_pw_9.png)   
[!screenshot 10](https://github.com/alko0925/yp-mjava-pws-s5-s8/tree/module_two_sprint_seven_branch/images/s7_pw_10.png)   
[!screenshot 11](https://github.com/alko0925/yp-mjava-pws-s5-s8/tree/module_two_sprint_seven_branch/images/s7_pw_11.png)   
[!screenshot 12](https://github.com/alko0925/yp-mjava-pws-s5-s8/tree/module_two_sprint_seven_branch/images/s7_pw_12.png)   
[!screenshot 13](https://github.com/alko0925/yp-mjava-pws-s5-s8/tree/module_two_sprint_seven_branch/images/s7_pw_13.png)

### Дополнения:
1. Функциональность - пункт 11: реализовано здесь: **/product/add**, есть переход кнопкой с главной страницы items
2. Функциональность - пункт 12/13: реализовано через:
   1. get **/api/account/1** для получения текущего баланса
   2. put **/api/account/1** для пополнения баланса и оплаты покупок
   3. на вкладке Корзина (cart) - кнопка покупки деактивируется если средств на балансе не достаточно или сервис не доступен по той или иной причине
   4. там же есть возможность проверить текущий баланс (/balance/check) и пополнить его (/balance/topup) - переход кнопкой "Проверить баланс"
3. Функциональность - Redis кеширование добавлено на уровне продуктов: ProductServiceImpl, при добавлении или апдейте продукта, кеши сбрасываются.

