## Domain layer

This part is the core of the application, it contains the business logic and should be a dependency for all the other layers.
It is responsible for defining the rules and the data models that are used globally.

Some examples of the logic contained in this layer are:

- Defining the data models
- Defining the business rules through Use Case classes
- Defining the repositories interfaces

### Use Case

A use case is a class that contains the business logic for a specific action in the application. It is a single responsibility class that can be easily tested and reused.
It should be the access point for the presentation layer to interact with the domain layer.


