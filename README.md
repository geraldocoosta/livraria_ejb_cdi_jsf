# Configurando o projeto

Para esse projeto, foram usadas as especificações JSP com hibernate, JSF, CDI, EJB, com autenticação e autorização;

## Configurando

Usar com WildFly 13.
Configurar o modulo do mysql, entrando nas pastas: modules\system\layers\base\com
Dentro dessa pasta, criar: mysql\main
Após isso, baixar o driver do mysql [mysql-connector-java-8.0.15.jar](http://central.maven.org/maven2/mysql/mysql-connector-java/8.0.15/mysql-connector-java-8.0.15.jar);

Criar um arquivo chamado module.xml com o seguinte conteudo:

```
<?xml version="1.0" encoding="UTF-8"?>

<module xmlns="urn:jboss:module:1.1" name="com.mysql">
	<resources>

		<resource-root path="mysql-connector-java-8.0.15.jar"/>
	</resources>
	<dependencies>
		<module name="javax.api"/>
		<module name="javax.transaction.api"/>
		<module name="javax.servlet.api" optional="true"/>
	</dependencies>
</module>
```

Após a criação desse arquivo, deve ser criado um banco de dados mysql chamado livraria_jsf. (Obviamente é preciso MySql)

Após isso, entrar no arquivo standalone\configuration\standalone.xml a partir da raiz do servidor WildFly.

Buscar no arquivo as tag &#60;datasorces&#62;, dentro dessa tag colocar o seguinte código:

```
				<datasource jndi-name="java:/livraria-ds" pool-name="livrariaDS" enabled="true" use-java-context="true">
					<connection-url>jdbc:mysql://localhost:3306/livraria_jsf?useTimezone=true&amp;serverTimezone=UTC</connection-url>
					<driver>com.mysql</driver>
					<pool>
						<min-pool-size>10</min-pool-size>
						<max-pool-size>100</max-pool-size>
						<prefill>true</prefill>
					</pool>
					<security>
						<user-name>root</user-name>
					</security>
				</datasource>
```  

Lembrando que em user-name deve-se colocar o login do banco, se tiver senha é preciso colocar a essa entre as tags &#60;password&#62;. Essa tag deve ficar junto com a &#60;user-name&#62;.

Dentro da tag &#60;drivers&#62;, colocar o seguinte código:
```
					<driver name="com.mysql" module="com.mysql">
						<xa-datasource-class>com.mysql.jdbc.jdbc2.optional.MysqlXADataSource</xa-datasource-class>
					</driver>
```

Após isso, compilar o projeto com mvn package, e colocar o .war dentro do diretorio standalone\deployments a partir da raiz do servidor de aplicação.

Agora é só iniciar o servidor abrindo o seguinte arquivo no windows, a partir da raiz do servidor de aplicação:

bin\standalone.bat


Seguindo o curso JSF da alura.