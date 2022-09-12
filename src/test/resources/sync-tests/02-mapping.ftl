<#assign jpath=handlers("JsonHandler")>
<#assign data1=providers("FileProvider", { "file"  : "./src/test/resources/data-samples/sample1.json" })>
<#assign data2=providers("FileProvider", { "file" : "./src/test/resources/data-samples/sample2.json"})>
{
  "@context": "https://www.w3.org/2019/wot/td/v1",
  "id": "urn:dev:ops:32473-WoTLamp-",
  "injected": "[=jpath.filter('$.@context.helio',data1)]",
  "dummy": [=data2]
}