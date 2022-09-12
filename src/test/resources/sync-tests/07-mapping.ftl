 <#assign data=providers("FileProvider", {"file" : "./src/test/resources/data-samples/csv9.csv"})>
  @prefix s4city: <https://saref.etsi.org/saref4city/> .
  @prefix s4envi: <https://saref.etsi.org/saref4envi/> .
  @prefix saref: <https://saref.etsi.org/core/> .
  @prefix sch: <http://schema.org/> .
  @prefix ex: <http://example.org/Lamppost/resource/> .
  @prefix geo: <http://www.w3.org/2003/01/geo/wgs84_pos#> .
  @prefix rdfs: <http://www.w3.org/2000/01/rdf-schema#> .
 <#list data?split("\n") as row>
    <#assign columns=row?split(";")>
    <#if columns?size == 12 && row?index gt 0>

    ex:[= row?index] a s4envi:Lamppost .
    ex:[= row?index] saref:hasManufacturer "[=columns[0]]" .

        <#if columns[1]=='DESCARGA'>
    ex:[= row?index] s4envi:hasShield "true" .
    ex:[= row?index] s4envi:projectsLight _:b0 .
    _:b0 a s4envi:Light .
    _:b0 s4envi:hasFlash false .
    _:b0 s4envi:hasProjectionAngle 90 .
        <#elseif  columns[1]=='LED-DESCARGA'>
    ex:[= row?index] s4envi:hasShield "true" .
    ex:[= row?index]0 s4envi:projectsLight _:b0 .
    _:b0 a s4envi:Light .
    _:b0 s4envi:hasFlash false  .
    _:b0 s4envi:hasProjectionAngle 180 .
        <#else>
    ex:[= row?index] s4envi:hasShield "false" .
    ex:[= row?index] s4envi:projectsLight .
    _:b0 a s4envi:Light .
    _:b0 s4envi:hasFlash true .
    _:b0 s4envi:hasProjectionAngle 180 .
        </#if>
        
    ex:[= row?index]  s4envi:hasLightPoint _:b1 .
    _:b1 a s4envi:LightPoint .
    
    <#if columns[1]=='DESCARGA' || columns[1]=='LED-DESCARGA' ||  columns[1]=='LED'>
    _:b1 s4envi:projectsLight _:b0 .
    </#if>
        
    ex:[= row?index] geo:location _:b2 .
    _:b2 a s4city:District .
    _:b2 a sch:Place .
    _:b2 sch:address "[=columns[2]] [=columns[3]] [=columns[4]], [=columns[5]] [=columns[6]]" .
    _:b2 sch:address "Area code [=columns[7]], district code [=columns[8]], neighbourhood code [=columns[9]]" .
    _:b2 rdfs:label "ED50 coordinates X:[=columns[10]] and Y:[=columns[11]] " .

    </#if>
 </#list>