# examen
Examen primer modulo

Como probar la aplicación en dockers balanceados con haproxy:

1. Abrir la terminal

2. Clonar el proyecto:

$ git clone https://github.com/dgpecurso07/examen.git

3. Correr la aplicación en docker 6 veces

$ docker run -d -e PBA=01 -p 8081:8080 -v /{RutaDondeDescargoProyecto}/examen:/codigo kebblar/jdk18-utf8-debug-maven java -jar

$ docker run -d -e PBA=02 -p 8082:8080 -v /{RutaDondeDescargoProyecto}/examen:/codigo kebblar/jdk18-utf8-debug-maven java -jar /codigo/target/sample-1.0-SNAPSHOT-fat.jar

$ docker run -d -e PBA=03 -p 8083:8080 -v /{RutaDondeDescargoProyecto}/examen:/codigo kebblar/jdk18-utf8-debug-maven java -jar /codigo/target/sample-1.0-SNAPSHOT-fat.jar

$ docker run -d -e PBA=04 -p 8084:8080 -v /{RutaDondeDescargoProyecto}/examen:/codigo kebblar/jdk18-utf8-debug-maven java -jar /codigo/target/sample-1.0-SNAPSHOT-fat.jar

$ docker run -d -e PBA=05 -p 8085:8080 -v /{RutaDondeDescargoProyecto}/examen:/codigo kebblar/jdk18-utf8-debug-maven java -jar /codigo/target/sample-1.0-SNAPSHOT-fat.jar

$ docker run -d -e PBA=06 -p 8086:8080 -v /{RutaDondeDescargoProyecto}/examen:/codigo kebblar/jdk18-utf8-debug-maven java -jar /codigo/target/sample-1.0-SNAPSHOT-fat.jar

6. Verificar que haproxy este detenido

$ sudo service haproxy status

Si no es así detenerlo

$ sudo service haproxy stop

5. Editar el archivo haproxy.cnf

$ sudo nano /etc/haproxy/haproxy.cfg

El archivo debe quedar de la siguiente manera:

	#
	global
		log /dev/log	local0
		log /dev/log	local1 notice
		chroot /var/lib/haproxy
		stats socket /run/haproxy/admin.sock mode 660 level admin expose-fd listeners
		stats timeout 30s
		user haproxy
		group haproxy
		daemon

	# Default SSL material locations
	ca-base /etc/ssl/certs
	crt-base /etc/ssl/private

	# Default ciphers to use on SSL-enabled listening sockets.
	# For more information, see ciphers(1SSL). This list is from:
	#  https://hynek.me/articles/hardening-your-web-servers-ssl-ciphers/
	# An alternative list with additional directives can be obtained from
	#  https://mozilla.github.io/server-side-tls/ssl-config-generator/?server=haproxy
	ssl-default-bind-ciphers ECDH+AESGCM:DH+AESGCM:ECDH+AES256:DH+AES256:ECDH+AES128:DH+AES:RSA+AESGCM:RSA+AES:!aNULL:!MD5:!DSS
	ssl-default-bind-options no-sslv3

	defaults
		log	global
		mode	http
		option	httplog
		option	dontlognull
	        timeout connect 5000
	        timeout client  50000
	        timeout server  50000
		errorfile 400 /etc/haproxy/errors/400.http
		errorfile 403 /etc/haproxy/errors/403.http
		errorfile 408 /etc/haproxy/errors/408.http
		errorfile 500 /etc/haproxy/errors/500.http
		errorfile 502 /etc/haproxy/errors/502.http
		errorfile 503 /etc/haproxy/errors/503.http
		errorfile 504 /etc/haproxy/errors/504.http
	frontend www
		bind localhost:9090
		default_backend site-backend
	backend site-backend
		mode http
		stats enable
		stats uri /haproxy?stats
		balance roundrobin
		server lamp1 localhost:8081 check
		server lamp2 localhost:8082 check
		server lamp3 localhost:8083 check
		server lamp4 localhost:8084 check
		server lamp5 localhost:8085 check
		server lamp6 localhost:8086 check

5. Iniciar el haproxy

$ sudo service haproxy start

6. Probar las aplicaciones en un explorador con las siguientes ligas:

http://localhost:9090/calc/suma?a=4&b=2

http://localhost:9090/calc/resta?a=4&b=2

http://localhost:9090/calc/mult?a=4&b=2

http://localhost:9090/calc/div?a=4&b=2

