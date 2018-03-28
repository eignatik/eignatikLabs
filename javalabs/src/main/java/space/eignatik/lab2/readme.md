# How to run

Specify through idea run configuration or just through command line how many products you want to get in this runtime.

E.g. `java Main 50`

> Task_2:
  > 	
  > 1) Создать приложение с 2 классами - Book, Shoe  и  интерфейсом Product. 
  Интерфейс содержит метод whoAmI, который будет реализован в имплементирующих классах. 
  >
  > 2) Определить новый интерфейс Present, расширяющий интерфейс Product и содержащий  метод itCanBePresented(), который может возвращать строку.
  >
  > 3) Объявить 2 новых класса (например Toy, Picture), реализующих  интерфейс Present.
  >
  > 4) В приложении создать массив объектов Product - (Book, Shoe, Toy, Picture), состоящий из количества элементов, заданного параметром.
  >
  > 5) Найти в массиве те объекты, которые реализуют интерфейс Present и выполнить для них метод itCanBePresented().
  >
  > Вывод должен содержать названия всех продуктов из массива, затем только тех продуктов, которые реализуют интерфейс Present.
  
  ## Output example
  
  > Ho-ho, these Classes implement interface Present. Let's see how polymorphism works.
    
    space.eignatik.lab2.Picture
    	I implement Present interface
    space.eignatik.lab2.Toy
    I implement Present interface
    space.eignatik.lab2.Picture
    	I implement Present interface
    space.eignatik.lab2.Toy
    I implement Present interface
    space.eignatik.lab2.Toy
    I implement Present interface
    space.eignatik.lab2.Picture
    	I implement Present interface
    space.eignatik.lab2.Picture
    	I implement Present interface
    space.eignatik.lab2.Picture
    	I implement Present interface
    space.eignatik.lab2.Toy
    I implement Present interface
    space.eignatik.lab2.Picture
    	I implement Present interface
    space.eignatik.lab2.Toy
    I implement Present interface
    space.eignatik.lab2.Picture
    	I implement Present interface
    space.eignatik.lab2.Toy
    I implement Present interface
    space.eignatik.lab2.Picture
    	I implement Present interface
    space.eignatik.lab2.Picture
    	I implement Present interface
    space.eignatik.lab2.Picture
    	I implement Present interface
    space.eignatik.lab2.Picture
    	I implement Present interface
    space.eignatik.lab2.Picture
    	I implement Present interface
    space.eignatik.lab2.Picture
    	I implement Present interface
    space.eignatik.lab2.Toy
    I implement Present interface
    space.eignatik.lab2.Toy
    I implement Present interface
    space.eignatik.lab2.Picture
    	I implement Present interface
    space.eignatik.lab2.Toy
    I implement Present interface
    space.eignatik.lab2.Toy
    I implement Present interface
    
    All objects:
    
    space.eignatik.lab2.Picture@4ec6a292
    space.eignatik.lab2.Toy@1b40d5f0
    space.eignatik.lab2.Picture@ea4a92b
    space.eignatik.lab2.Toy@3c5a99da
    space.eignatik.lab2.Book@47f37ef1
    space.eignatik.lab2.Toy@5a01ccaa
    space.eignatik.lab2.Picture@71c7db30
    space.eignatik.lab2.Picture@19bb089b
    space.eignatik.lab2.Shoe@4563e9ab
    space.eignatik.lab2.Picture@11531931
    space.eignatik.lab2.Toy@5e025e70
    space.eignatik.lab2.Shoe@1fbc7afb
    space.eignatik.lab2.Picture@45c8e616
    space.eignatik.lab2.Shoe@4cdbe50f
    space.eignatik.lab2.Toy@66d33a
    space.eignatik.lab2.Shoe@7cf10a6f
    space.eignatik.lab2.Picture@7e0babb1
    space.eignatik.lab2.Shoe@6debcae2
    space.eignatik.lab2.Toy@5ba23b66
    space.eignatik.lab2.Picture@2ff4f00f
    space.eignatik.lab2.Book@c818063
    space.eignatik.lab2.Book@3f0ee7cb
    space.eignatik.lab2.Picture@75bd9247
    space.eignatik.lab2.Shoe@7d417077
    space.eignatik.lab2.Picture@7dc36524
    space.eignatik.lab2.Picture@35bbe5e8
    space.eignatik.lab2.Shoe@2c8d66b2
    space.eignatik.lab2.Picture@5a39699c
    space.eignatik.lab2.Picture@3cb5cdba
    space.eignatik.lab2.Book@56cbfb61
    space.eignatik.lab2.Toy@1134affc
    space.eignatik.lab2.Book@d041cf
    space.eignatik.lab2.Book@129a8472
    space.eignatik.lab2.Shoe@1b0375b3
    space.eignatik.lab2.Shoe@2f7c7260
    space.eignatik.lab2.Toy@2d209079
    space.eignatik.lab2.Picture@6bdf28bb
    space.eignatik.lab2.Shoe@6b71769e
    space.eignatik.lab2.Toy@2752f6e2
    space.eignatik.lab2.Toy@e580929
    
    Objects which implement Present:
    
    space.eignatik.lab2.Picture@4ec6a292
    space.eignatik.lab2.Toy@1b40d5f0
    space.eignatik.lab2.Picture@ea4a92b
    space.eignatik.lab2.Toy@3c5a99da
    space.eignatik.lab2.Toy@5a01ccaa
    space.eignatik.lab2.Picture@71c7db30
    space.eignatik.lab2.Picture@19bb089b
    space.eignatik.lab2.Picture@11531931
    space.eignatik.lab2.Toy@5e025e70
    space.eignatik.lab2.Picture@45c8e616
    space.eignatik.lab2.Toy@66d33a
    space.eignatik.lab2.Picture@7e0babb1
    space.eignatik.lab2.Toy@5ba23b66
    space.eignatik.lab2.Picture@2ff4f00f
    space.eignatik.lab2.Picture@75bd9247
    space.eignatik.lab2.Picture@7dc36524
    space.eignatik.lab2.Picture@35bbe5e8
    space.eignatik.lab2.Picture@5a39699c
    space.eignatik.lab2.Picture@3cb5cdba
    space.eignatik.lab2.Toy@1134affc
    space.eignatik.lab2.Toy@2d209079
    space.eignatik.lab2.Picture@6bdf28bb
    space.eignatik.lab2.Toy@2752f6e2
    space.eignatik.lab2.Toy@e580929
