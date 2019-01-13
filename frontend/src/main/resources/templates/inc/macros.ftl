<#macro provider p>
<div class="provider-container">
    <div class="provider-header" style="background: ${p.iconBackgroundColor};">
        <img class="provider-icon" src="<#if p.iconUri?has_content>${p.iconUri}<#else>${properties.solidLogoUri}</#if>" alt="${p.title}" />
        <h3>${p.title}</h3>
    </div>
    <div class="provider-body">
        <#if p.description?length gt 180>
        <p>${p.description?substring(0,180)}</p>
        <#else>
        <p>${p.description}</p>
        </#if>
        <a href="${p.policyUri}" target="_blank">Privacy Policy</a>
    </div>
    <div class="provider-footer">
        <a href="${p.uri}"><button type="submit" class="btn btn-success provider-take-me-btn">TAKE ME THERE</button></a>
    </div>
</div>
</#macro>

<#macro selectWithLabel label id items>
<div class="input-container">
    <select id="${id}" class="filter-select">
        <option selected disabled>${label}</option>
        <option value="Any">Any</option>
        <#list items as i>
        <option value="${i}">${i}</option>
        </#list>
    </select>
</div>
</#macro>

<#macro select name options item>
<select class="form-control" name="${name}">
    <#list options as o>
        <option value="${o}" <#if item.type?has_content && item.type == o>selected</#if>>${o?cap_first}</option>
    </#list>
</select>
</#macro>

<#macro formError value="">
<#if value?has_content>
<div class="col-12">
    <div class="alert alert-danger" role="alert">
        ${value}
    </div>
</div>
</#if>
</#macro>