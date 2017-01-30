# Git Repo for Yellow Pipeline Web-App

This is a web-app which will utilize various 3rd party apps/scripts for extracting entity information from documents.

## Version 1.2

- Shared Code with all Pipeline Projects

## Downloading

```sh
git clone https://github.com/artistech-inc/pipeline-base.git
cd pipeline-base
mvn clean install
cd ..
git clone https://github.com/artistech-inc/yellow-pipeline-web.git
cd yellow-pipeline-web
git checkout v1.2
mvn clean package
```

## Configuration

Update the [WEB-INF/web.xml](https://github.com/artistech-inc/yellow-pipeline-web/blob/master/src/main/webapp/WEB-INF/web.xml) file. Each Servlet that utilizes an external application/script/process must have the path to the application set. For now, this includes:

- camr
- LiberalExtractor

Along with the web.xml file, the [META-INF/context.xml](https://github.com/artistech-inc/yellow-pipeline-web/blob/master/src/main/webapp/META-INF/context.xml) must be configured. The `data_path` value must be somewhere that Tomcat can write to.

## Compilation

This project should be able opened using Netbeans as a Maven Web-App. It will automatically detect the type of project.

The project can also be compiled on the command line directly using maven.

```sh
cd yellow-pipeline-web
mvn clean package
```

## Dependencies

- [ArtisTech's camr yellow-pipeline fork/branch](https://github.com/artistech-inc/camr/tree/yellow-pipeline)

## Deployment

The output from compilation is in the `target/` directory as `yellow-pipeline-web-1.2.war`. This war can be deployed to Tomcat's `webapps` directory. Once deployed, it can be accessed via `http://<ip_address:port>/yellow-pipeline-web-1.2/`.

## Bugs

- Issues and bugs can be e-mailed or registered.

## TODO

- Add LiberalExtractor Functionality
- Add any additiona steps
- Output console
- Delete data with session timeout

## Future (possibly not in scope)

- Database
- Users
