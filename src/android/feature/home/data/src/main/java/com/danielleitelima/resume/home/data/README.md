## Data layer

This part of the application is responsible for fetching remote sources as well as storing data locally.
It is implemented using the Repository pattern, that can access multiple data sources, such as a database, a network, or the local cache to return Domain-defined objects to the domain layer.

Some examples of the logic contained in this layer are:

- Managing the network calls
    - Using Retrofit for interfacing with the REST API
    - Using Apollo for managing GraphQL queries
- Storing data locally
    - Using Room for a local database
    - Using SharedPreferences for simple key-value storage
- Syncing data between the local and remote sources
- Converting a remote model into a domain model