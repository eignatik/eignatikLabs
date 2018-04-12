# What the heck is here?

## Task
> Создать приложение с 3 потоками для следующей задачи: 
3 работника выполняют следующую работу: 
> - 1-ый копает яму,
>   
> - 2-ой сажает дерево, 
> 	 
>- 3-ий подвязывает саженец к кольям. 
> 	
>Работа идет строго по очереди. Число саженцев задается параметром.  Выводить на дисплей номер работника и номер саженца.

## Implementation

There is an interface CouchPotato which contains a single method "doWork". And there is three classes implemented this interface. Each class has method `doWork` because it has to be implemented and also implements Runnable interface which allow us to use them as Runnable task into Thread objects. 

Then it's a loop with iterations number according to size of plants to be planted. Every loop it's new Runnable task for every thread with workers. Each worker displays current plant number and his name.

1. Digger digs up a pit
2. Planter plants a plant
3. TieMan ties up the plant

## Run

To run these app you can just use tests (there are two examples with default value which is 10 and with custom value). Or you can run using `Main` class method `main` and pass arg into the command line args (by passing them through command line or just setting up IDE run properties). 

Without any arguments will be used default number of plants. On the other hand first argument which is required to be an integer value will be the plants number value. In case of wrong arguments default value will be used.