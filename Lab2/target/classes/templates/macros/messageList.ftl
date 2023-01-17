<#include "security.ftl">

<div class="card-columns">
    <#list messages as message>
        <div class="card my-3">
            <div class="card-footer text-muted">
                <a href="/user-messages/${message.author.id}">${message.authorName}</a>
            </div>
            <#if message.filename??>
                <img src="/img/${message.filename}" class="card-img-top">
            </#if>
            <div class="m-2">
                <span>${message.text}</span>
            </div>
            <div class="card-footer text-muted">
                <i>#${message.tag}</i>
                <#if message.author.id == currentUserId>
                    <a class="btn btn-primary" href="/user-messages/${message.author.id}?message=${message.id}">
                        Изменить
                    </a>
                    <a class="btn btn-primary" href="/user-messages-delete/${message.author.id}?message=${message.id}">
                        Удалить
                    </a>
                </#if>
            </div>
        </div>
    <#else>
        Сообщений пока что нет, вы будете первым, кто его добавит)))
    </#list>
</div>