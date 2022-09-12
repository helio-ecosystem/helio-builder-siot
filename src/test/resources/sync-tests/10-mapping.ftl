<#assign source = providers("DummyProvider", { "asQueue" : true})>
<#list source as data>
[=label]:[=data], 
</#list>