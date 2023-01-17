<#import "macros/common.ftl" as com>
<#import "macros/login.ftl" as log>

<@com.page>
<div class="mb-1">Регистрация нового пользователя</div>
${message?ifExists}
<@log.login "/registration" true/>
</@com.page>