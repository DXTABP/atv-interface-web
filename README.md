# MapaWebIrterface

Projeto web Java/Maven para cadastro e consulta de livros de uma biblioteca, usando Servlet, JSP, JSTL e JSF.

## Requisitos

- Java 11 ou superior
- Git
- IntelliJ IDEA ou outra IDE com suporte a Maven

O Maven Wrapper ja esta incluido no projeto, entao nao e necessario instalar Maven manualmente.

## Como rodar pelo terminal

No Windows:

```powershell
.\mvnw.cmd clean package
.\mvnw.cmd org.eclipse.jetty:jetty-maven-plugin:9.4.57.v20241219:run
```

Depois acesse:

```text
http://localhost:8080/livros
```

## Como rodar pelo IntelliJ

1. Abra a pasta do projeto no IntelliJ.
2. Aguarde a importacao do Maven.
3. Configure um servidor Tomcat 9 local ou use o comando Maven/Jetty acima no terminal integrado.
4. Se usar Tomcat, faca o deploy do artefato `MapaWebIrterface.war`.

Observacao: use Tomcat 9 para manter compatibilidade com `javax.*`. Tomcat 10 usa `jakarta.*`.

## Estrutura principal

- `src/main/java`: classes Java, Servlet, modelo e ManagedBean JSF
- `src/main/webapp`: JSP, XHTML, CSS e recursos web
- `pom.xml`: configuracao Maven
- `.mvn`, `mvnw`, `mvnw.cmd`: Maven Wrapper
