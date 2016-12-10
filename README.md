# dns-update-client
A ddclient-like tools for keeping a remote record of a computer's public IP, as an alternative to a DNS service. Uses [ipify](https://www.ipify.org/). Currently checks and updates every 5 minutes.

# Usage
1. Update ```application.properties``` to point at an instance of ```dns-update-service```.
2. Build with ```mvn -U clean package```
3. Copy the ```target/dns-update-client-<version>.jar``` file to a machine with a non-fixed ip.
4. Run with ```java -jar <jar file>```
5. The application will log to a file, next to the jar ```dns-update-client.log```.

# Future improvements
* Security
* Further error handling
* Further unit tests
