# How it works

It works simple. Just call `PropertyHelper::getProperty` method with full path to file and param name. 

If params with this name exists then you can get this value using `Optional::orElse` statement (because it's Optional). 

If there will be no available values you get null.

In case of wrong file path you will get platform runtime exception with cause. If you didn't specify param name or path at all you also get the same exception.
See tests to be assured.

Example usage 
```java
PropertyHelper
        .getProperty("somePath/app.properties", "database")
        .orElse(null);
```

## Using from IDEA: 

- specify run configuration where enter file path and property name
`file/path/file.properties someProperty`

## Using through command line

`java Main pathToFile/filaname.properties propertyName`