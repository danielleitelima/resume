# Feature modules

These are the modules that will contain the source code for a specific portion of the app.
The partition of the app into feature should be done considering:

- The development team structure
- UI/UX design
- Temporary and permanent features
- User roles and permissions
- User action flows

In general, a good approach is to consider the point of view of the user and divide the app based on the screens used to perform specific actions in order to achieve a goal in the application.
This goal needs to be aligned with the business goals and the user needs. In our case the app will be divided into the following features:

- Home: The main screen of the app, where the Resume information is displayed.
- Sample code *: Each sample code card in the home screen will represent a feature module.

Each feature can have 3 modules:

- data: Contains the data sources and repositories.
- domain: Contains the use cases and domain models.
- presentation: Contains the UI components and view models.

![Layer Dependencies](../../../docs/assets/module_dependency_structure