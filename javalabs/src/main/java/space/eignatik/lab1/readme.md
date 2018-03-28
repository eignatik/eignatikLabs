# How to run this application?

## From InelliJ IDEA

- open `lab1` package and find here `Main` class. It's where it all can be started. 
- specify in run configuration program parameters. (e.g. 1.0 2.0 0.2) Params have to be placed without any delimiters or another characters. Just `double` formatted.
- run application 
- result will be copied into file `aggregationResults.csv` which will be placed in directory root.

## Standard approach. 

If you don't have IDE and Vim is your speciality and you'd like to execute that through console you just can simply do that using `javac` and then `java` console command with arguments as command line arguments.

E.g. `javac lab1/Main.java`,  `java Main 1.0 2.0 0.2` and be happy.


Task

> Task_1:
  > 	Составить программу для вычисления значений функции F(x) на отрезке [а, b] с шагом h. Размеры отрезка и параметры задаются в качестве параметров в консоли.
  > 	Результат представить в виде таблицы, первый столбец которой – значения аргумента, второй - соответствующие значения функции:
  > 	
  > 	F(x) = tg(2x) - 3