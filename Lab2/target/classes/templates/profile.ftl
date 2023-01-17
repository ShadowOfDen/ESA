<#import "macros/common.ftl" as com>

<@com.page>
    <h5>${username}</h5>
    ${message?ifExists}
    <form method="post">
            <div class="form-group row">
                <label class="col-sm-2 col-form-label">Адрес электронной почты:</label>
                <div class="col-sm-6">
                    <input type="email" name="email" class="form-control" placeholder="email@domain.ru" value="${email!''}" />
                </div>
            </div>
        <div class="form-group row">
            <label class="col-sm-2 col-form-label">Пароль:</label>
            <div class="col-sm-6">
                <input type="password" name="password" class="form-control" placeholder="Пароль" />
            </div>
        </div>
        <input type="hidden" name="_csrf" value="${_csrf.token}" />
        <button class="btn btn-primary ml-2" type="submit">Сохранить</button>
    </form>
</@com.page>