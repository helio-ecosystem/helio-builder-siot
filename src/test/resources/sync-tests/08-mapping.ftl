<#assign file = 'sample2.json'>
<#assign config = '{ "file" : "./src/test/resources/data-samples/'+file+'"}' >
<#assign data2=providers("FileProvider", config)>
{
  "@context": "https://www.w3.org/2019/wot/td/v1",
  "id": "urn:dev:ops:32473-WoTLamp-",
  "dummy": [=data2]
}