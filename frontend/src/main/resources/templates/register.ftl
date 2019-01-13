<#include 'inc/header.ftl' />
<link rel="stylesheet" href="css/register.css" type="text/css">

<div id="register-service-container">

    <div class="container">
        <div class="row">
            <div class="col-md-12">
                <div class="card margin-bottom">
                    <div class="card-header">Register your Solid POD service</div>
                    <div class="card-body">
                        <#if item.success>
                            <div class="alert alert-success" role="alert">
                                Thank you for submitting your POD service.
                            </div>
                        <#else>
                            <#if item.duplicateError??>
                                <div class="alert alert-danger" role="alert">
                                ${item.duplicateError}
                                </div>
                            <#elseif item.containsError>
                                <div class="alert alert-danger" role="alert">
                                Please resolve the errors listed below.
                                </div>
                            </#if>
                            <form name="registerForm" action="register" method="post">

                                <div id="registration-required-section">
                                    <div class="form-group row">
                                        <div class="col-3"><label class="col-form-label right">Title*</label></div>
                                        <div class="col-9"><input name="title" class="form-control" type="text" value="${item.title!""}" minlength="1" maxlength="20" required></div>
                                        <@formError value=item.titleError />
                                    </div>

                                    <div class="form-group row">
                                        <div class="col-3"><label class="col-form-label right">Description*</label></div>
                                        <div class="col-9"><input name="description" class="form-control" type="text" value="${item.description!""}" maxlength="140" required></div>
                                        <@formError value=item.descriptionError />
                                    </div>

                                    <div class="form-group row">
                                        <div class="col-3"><label class="col-form-label right">URL*</label></div>
                                        <div class="col-9"><input name="uri" class="form-control" type="url" value="${item.uri!""}" minlength="1" required></div>
                                        <@formError value=item.uriError />
                                    </div>

                                    <div class="form-group row">
                                        <div class="col-3"><label class="col-form-label right">Policy URL*</label></div>
                                        <div class="col-9"><input name="policyUri" class="form-control" type="url" value="${item.policyUri!""}" minlength="1" required></div>
                                        <@formError value=item.policyUriError />
                                    </div>

                                    <div class="form-group row">
                                        <div class="col-3"><label class="col-form-label right"></label></div>
                                        <div class="col-9">* denotes required fields.</div>
                                    </div>
                                </div>

                                <div id="registration-optional-section">
                                    <div class="form-group row">
                                        <div class="col-3"><label class="col-form-label right">Type</label></div>
                                        <div class="col-9">
                                            <@select name="type" options=types item=item/>
                                        </div>
                                    </div>

                                    <div class="form-group row">
                                        <div class="col-3"><label class="col-form-label right">Country code</label></div>
                                        <div class="col-9">
                                            <@select name="countryCode" options=countryCodes item=item/>
                                        </div>
                                    </div>

                                    <div class="form-group row">
                                        <div class="col-3"><label class="col-form-label right">Icon URL(100x100px)</label></div>
                                        <div class="col-9"><input name="iconUri" class="form-control" type="text" value="${item.iconUri!""}"></div>
                                        <@formError value=item.iconUriError />
                                    </div>

                                    <div class="form-group row">
                                        <div class="col-3"><label class="col-form-label right">Title color</label></div>
                                        <div class="col-9"><input name="titleColor" class="form-control" type="text" value="${item.titleColor!properties.titleColor}"></div>
                                        <@formError value=item.titleColorError />
                                    </div>

                                    <div class="form-group row">
                                        <div class="col-3"><label class="col-form-label right">Icon background color</label></div>
                                        <div class="col-9"><input name="iconBackgroundColor" class="form-control" type="text" value="${item.iconBackgroundColor!properties.iconBackgroundColor}"></div>
                                        <@formError value=item.iconBackgroundColorError />
                                    </div>

                                    <div class="form-group row">
                                        <div class="col-3"><label class="col-form-label right">Button color</label></div>
                                        <div class="col-9"><input name="buttonColor" class="form-control" type="text" value="${item.buttonColor!properties.buttonColor}"></div>
                                        <@formError value=item.buttonColorError />
                                    </div>

                                    <div class="form-group row">
                                        <div class="col-3"><label class="col-form-label right">Button background color</label></div>
                                        <div class="col-9"><input name="buttonBackgroundColor" class="form-control" type="text" value="${item.buttonBackgroundColor!properties.buttonBackgroundColor}"></div>
                                        <@formError value=item.buttonBackgroundColorError />
                                    </div>

                                    <div class="form-group row">
                                        <div class="col-3"><label class="col-form-label right">ReCaptcha*</label></div>
                                        <div class="col-9"><div class="g-recaptcha" data-sitekey="${properties.captchaSiteKey}"></div></div>
                                        <@formError value=item.captchaError />
                                    </div>

                                </div>

                                <button class="btn btn-primary btn-sm float-right">Create</button>
                            </form>
                        </#if>
                    </div>
                </div>
            </div>
        </div>
    </div>

</div>

<#include 'inc/footer.ftl' />
<script src="js/register.js"></script>
