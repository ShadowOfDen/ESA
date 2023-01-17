<#import "macros/common.ftl" as com>
<#import "macros/messageEdit.ftl" as me>

<@com.page>
    <#if isCurrentUser>
        <@me.edit true/>
    </#if>

    <#include "macros/messageList.ftl" />

</@com.page>