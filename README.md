# Reactive Semantic Interoperable IoT (SIoTRx) mappings

The SIoTRx mappings are endowed for providing semantic interoperability among IoT infrastructures. They allow to define translation rules from heterogeneous data sources to RDF (including JSON-LD 1.1) and vice versa.

## Using and learning SIoTRx mapping

SIoTRx mappings can be used directly in the [Helio Playground](https://helio-playground.linkeddata.es/) that also counts with numerous tutorials to learn their syntax.


## Install

The SIoT software aretefact is distributed as the following Maven depencency. 

````xml
<dependency>
  <groupId>io.github.helio-ecosystem</groupId>
  <artifactId>helio-builder-siot</artifactId>
  <version>0.1.10</version>
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

### Acknowledgements
This project has been partially funded by:

 | Project       | Grant |
 |   :---:      |      :---      |
 | <img src="https://github.com/helio-ecosystem/helio-ecosystem/assets/4105186/96d6a9bc-b92d-43fe-a921-c2c4cd811a30" height="80"/>  | The European project [VICINITY](https://vicinity2020.eu/index.html) from the European Union's Horizont 2020 research and innovation programme under grant agreement Nº688467. |
 | <img src="https://github.com/helio-ecosystem/helio-ecosystem/assets/4105186/fa127b1d-3b26-46c6-bae7-b193d6753071" height="80"/>  | The European project [BIMERR](https://bimerr.eu/) from the European Union's Horizont 2020 research and innovation programme under grant agreement Nº820621. |
 | <img src="https://github.com/helio-ecosystem/helio-ecosystem/assets/4105186/4475dd8d-fc4d-416c-84e7-ed16b34c86e7" height="80"/>  | The European project [DELTA](https://www.delta-h2020.eu/) from the European Union's Horizont 2020 research and innovation programme under grant agreement Nº688467. |
 | <img src="https://github.com/helio-ecosystem/helio-ecosystem/assets/4105186/c9081c01-69ed-4ba3-aa1a-fddbaaee19c1" height="80"/>   | The European project [AURORAL](https://www.auroral.eu/) from the European Union's Horizont 2020 research and innovation programme under grant agreement Nº101016854. |
 | <img src="https://github.com/helio-ecosystem/helio-ecosystem/assets/4105186/f1cde449-266f-45f4-a5da-e9c6006f5f3f" height="80"/>  | The European project [COGITO](https://cogito-project.eu/) from the European Union's Horizont 2020 research and innovation programme under grant agreement Nº958310. |

