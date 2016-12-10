# dns-update-client
A ddclient-like tools for keeping a remote record of a computer's public IP, as an alternative to a DNS service. Uses [ipify](https://www.ipify.org/). Currently checks and updates every 5 minutes.

# Usage
1. Update ```application.properties``` to point at an instance of ```dns-update-service``` (it's currently configured to point at an instance of the container deployed by ```dns-update-service-deploy``` )
2. Update the desired hostname in ```application.properties```.
3. Build with ```mvn -U clean package```
4. Copy the ```target/dns-update-client-<version>.jar``` file to a machine with a non-fixed ip.
5. Run with ```java -jar <jar file>```
6. The application will log to a file, next to the jar ```dns-update-client.log```.

# Future improvements
* Security
* Further error handling
* Further unit tests
