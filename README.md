# spring-cloud-demo


Current:

- Eureka for service discovery. 
- Spring Cloud Config for centralised configuration management. 
- Hystrix for circuit breaking.
- RestTemplate for "Eureka Aware HTTPClient"
- Ribbon for client side load-balancing.   
- Actuator - for metrics & management endpoints
- Micrometer for statsd metrics - only configured for Consumer.
 
Services:

Config Server. 
 - Loads config from github. Currently does not support automatic refreshing of clients. 
Service Registry.
 
ThingProducer 
 - /thing/{name} will produce a thing with the passed name. 
 - Will delay response using ${delay} value from configuration

StuffProducer 
 - /stuff/{size} will produce a stuff with the pass size (size is an int)  
 - Will delay response using ${delay} value from configuration
 
Consumer 
 - /stuffnthing/{name}/{size} calls to both ThingProducer & StuffProducer  
 - Client calls to Producers are wrapped in Hystrix calls. 
 - Hystrix timeouts are configured by Config Server
 - Clients use ConfigurationManager for configuration of Hystrix timeout, and use @RefreshScope bean, enabling live refreshing of Hystrix timeouts
 - After changing a config value in Git, force reloading config value by: ```curl -vvv -X POST localhost:8080/actuator/refresh```
 - Clients use Ribbon enabled RestTemplate so use service-id, as opposed to hostname:port for URL. 
 

Currently "Discovery First" - The Config Server registers itself with the Service Registry. 
Each service need only know the location of the service registry from which it can locate the Config Server, 
and bootstrap itself. The assumption being that the Eureka Service Registry (or registries) are located by DNS. 
Everything else uses service discovery to find dependents.  
 
Further Work:

- Swap RestTemplate for Feign
- Auto refreshing of config clients with rabbitmq on config change (requires route from GitHub -> ConfigServer)
- Add Zuul as frontend 
- Use FIT to inject failure etc. 