#MatchBot

Бот знакомств который позволяет просматривать и оценивать анкеты других людей.

##Развертываем бота на [Heroku](https://heroku.com/)
1. Форкаем себе этот репозитори.
2. Регистрируемся на [Heroku](https://heroku.com/)
3. Входим и создаем новое приложение. Нажимаем New и выбираем __Create new app__
   ![altText](https://sun9-88.userapi.com/impg/lEnSUhgBGx4u2tiDValtI4xI28XgsDPSa8WdVQ/940WFGd5Xws.jpg?size=1920x269&quality=96&sign=67867f1971849cd2e9e69ab7f46676f8&type=album)
   ![altText](https://sun9-59.userapi.com/impg/Spy4zCVNjyYDkGzJi6Cv6UwloySEkN7KQAPiUQ/Kw9luIONmis.jpg?size=1920x234&quality=96&sign=11c315370ab0863a081dae1bc3d79198&type=album)
4. Пишем любой имя и выбираем регион. Нажимаем __Create App__
5. Теперь нам надо добавить переменные окружения, бот их использует:
    * BOT_NAME тут будет имя бота
    * BOT_TOKEN токен который вы получили у @BotFather
    * GRADLE_TASK = shadowJar это нужно для самого [Heroku](https://heroku.com/)

   Для этого в меню приложения приложения переходим в Settings
   ![altText](https://sun9-42.userapi.com/impg/8VboJSblTKSUo56C7c5dSKpooI4Jqm-iS9cRSA/8vQC_zrSMLg.jpg?size=1920x258&quality=96&sign=eb893a581d8fd8746fa4f872b3aede95&type=album)
   и добавляемм соответствующие переменные в Config Vars   
   ![altText](https://sun9-34.userapi.com/impg/gKDKXwTA0SLQB-YbGWKrtmSP8D47Nng-kDmX3A/1qHS4G-jAic.jpg?size=1878x517&quality=96&sign=b5450f81e8c044b26b833e61eaee8838&type=album)

6. меню приложения переходим на вкладку Deploy и Deployment Method выбираем GitHub,
   ![altText](https://sun9-60.userapi.com/impg/EO6saEXi5-8hmZr7km305M_mgebYQ2QfMRxf5A/gNOl49T_CQc.jpg?size=1811x619&quality=96&sign=f36b85b559106d7cdfbf384315a129a2&type=album)
   ниже  нажимаем __Connect to GitHub__ и подключаем свой аккаунт
   ![altText](https://sun9-33.userapi.com/impg/Twhy-kgXuc17zGX3DELlDAvILWWBorMlWjAgsg/I2ca7N_Z9rc.jpg?size=1879x601&quality=96&sign=712dbd1b7a3c61ac1fad82f697f53898&type=album)
7. В поле Connect to GitHub ищем репозитори, который в начале форкнули  и нажимаем __Connect__
   ![altText](https://sun9-19.userapi.com/impg/VmY5eiDrYrvyGuFKwE6FrIwkYA3s2lSUmm9jqg/9w73B5-5x_o.jpg?size=1590x281&quality=96&sign=2414cde728f8417c7d3ebb72e1094989&type=album)
8. В Manual Deploy выбираем master и нажимаем __Deploy Branch__
   ![altText](https://sun9-74.userapi.com/impg/Iiz1oCqFUEoZVB3mvly_hnr7blWCQDOfMEOpGw/4k8yNU_br8g.jpg?size=1883x232&quality=96&sign=67744eea17a35e7fc23b38cb85ff9247&type=album)
9. Перходим на вкладку Resources нажимаем на карандашик возле ползунка, передвигаем ползунок вправо и нажимаем __Confirm__
![altText](https://sun9-49.userapi.com/impg/Sk4yFNIGMCUqqWfBbhdI5yIWOIvk9kJRsfVU2Q/FjhGU_HMgXE.jpg?size=1666x241&quality=96&sign=4553fefbce8f395105e050d01d74d5f9&type=album)
![altText](https://sun9-63.userapi.com/impg/6rckr6yXZdT22ZE7OOVPdKVtooM6KTSa-nZCPg/vxpvwfQeZEQ.jpg?size=1648x150&quality=96&sign=6bdd75d913b7199b04eb89c8f5cc62a8&type=album)
10. Готово!