# Semantic Interoperable IoT (SIoT) mapping builder

The SIoT mappings are endowed for providing semantic interoperability among IoT infrastructures. They allow to define translation rules from heterogeneous data sources to RDF (including JSON-LD 1.1) and vice versa.

## Install

The SIoT software aretefact is distributed as the following Maven depencency. 

````xml
<dependency>
  <groupId>io.github.helio-ecosystem</groupId>
  <artifactId>helio-builder-siot</artifactId>
  <version>0.1.9</version>
</dependency>
````
Check this link for the whole [list of versions](https://search.maven.org/artifact/io.github.helio-ecosystem/helio-builder-siot)

## Usage

  1. First load the Helio components that are used:
  ````java
  Components.registerAndLoad("https://github.com/helio-ecosystem/helio-provider-files/releases/download/v0.1.1/helio-provider-files-0.1.1.jar", "helio.providers.files.FileProvider", ComponentType.PROVIDER);
  Components.registerAndLoad("https://github.com/helio-ecosystem/helio-handler-jayway/releases/download/v0.1.1/helio-handler-jayway-0.1.1.jar", "handlers.JsonHandler", ComponentType.HANDLER);
  Components.registerAndLoad("https://github.com/helio-ecosystem/helio-providers-web/releases/download/v0.1.1/helio-providers-web-0.1.1.jar", "helio.providers.HttpProvider", ComponentType.PROVIDER);
  // Keep loading components required
  ````
  2. Read the mapping into a variable (the function 
  ````java
  String mapping = readMapping("./src/test/resources/sync-tests/01-mapping.ldmap");
  ````
  3. Parse the mapping and build the translation units. The SIoT builder always generates one TranslationUnit per mapping provided:
    
    ```java
    UnitBuilder builder = new SIoTBuilder();
    Set<TranslationUnit> list = builder.parseMapping(mapping);
    TranslationUnit unit = list.iterator().next()
    ```
  4. Run the translation unit for obtaining the results
  ````java
    ExecutorService service = Executors.newFixedThreadPool(4);
    Future<?> f = service.submit(unit.getTask());
    f.get();
    String result = unit.getDataTranslated().get(0); // data translated
    f.cancel(true);
	service.shutdown();
  ````
  
Note that this is a simple usage, soon new documentation for complex operations will be uploaded
