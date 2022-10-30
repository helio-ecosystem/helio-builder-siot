<#assign data=providers("FileProvider", {"file" : "./src/test/resources/data-samples/lightposts-madrid/20220408_DATOS_ABIERTOS_UNIDAD_LUMINOSA_.csv"})>

<#list data?split("\n") as row>
	<#list row?split(";") as column>
	ex:lightstreet/resource/[=row?index] ontology:term[=row?index] "[=column]" .
	</#list>
	<#if row?index gt 100>
		<#break>
	</#if>
</#list>