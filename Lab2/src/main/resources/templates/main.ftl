<#import "macros/common.ftl" as com>
<#import "macros/messageEdit.ftl" as me>

<@com.page>
    <div class="form-row">
        <div class="form-group col-md-6">
            <form method="get" action="/main" class="form-inline">
                <input type="text" name="filter" class="form-control" value="${filter?ifExists}" placeholder="Поиск по тэгу">
                <button type="submit" class="btn btn-primary ml-2">Поиск</button>
            </form>
        </div>
    </div>

    <@me.edit false/>

    <#include "macros/messageList.ftl" />

</@com.page>