<#include '../inc/macros.ftl' />

<#if providers?size == 0>
    <div class="alert alert-primary" role="alert">
        We couldn't find any providers! Please modify your search criteria and try again.
    </div>
<#else>
    <#list providers as p>
        <@provider p />
    </#list>
</#if>
