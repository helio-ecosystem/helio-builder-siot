<#assign jpath=handlers("JsonHandler")> 
<#assign data1=providers("FileProvider", { "file" : "./src/test/resources/data-samples/sample1.json" })> 
<#assign data3=providers("DummyProvider",  { "asQueue" : true})> 

[=jpath.filter('$.@context.array.[0]',data1)]:<#if data3?has_content>[=data3[0]]</#if>
