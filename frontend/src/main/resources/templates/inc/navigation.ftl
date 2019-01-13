<nav class="navbar navbar-expand-lg navbar navbar-dark bg-primary" id="global-navigation">
    <#--<a class="navbar-brand" href="#"></a>-->
    <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
        <span class="navbar-toggler-icon"></span>
    </button>

    <h1><a href="/" id="global-icon">Find Solid Pods</a></h1>

    <div class="collapse navbar-collapse" id="navbarSupportedContent">
        <ul class="navbar-nav mr-auto">
          <li class="nav-item">
          </li>
        </ul>
        <#if showRegister?? && showRegister>
            <a href="register"><button type="submit" class="btn btn-primary" id="register-btn">Register a Solid Pod</button></a>
        <#else>
            <a href="/"><button type="submit" class="btn btn-primary" id="register-btn">Search</button></a>
        </#if>
        <#if showRegister?? && showRegister><input id="global-search" class="form-control mr-sm-2" type="search" placeholder="search" aria-label="Search" value=""></#if>
    </div>
</nav>