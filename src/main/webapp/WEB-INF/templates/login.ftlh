<#import "base.ftlh" as layout>

<@layout.base pageTitle="Вход в систему">
    <div style="max-width: 400px; margin: 0 auto;">
        <div class="card">
            <h1>Вход в систему</h1>

            <form action="${contextPath}/auth/check" method="post">
                <div class="form-group">
                    <label for="username">Имя пользователя:</label>
                    <input type="text" id="username" name="username" required>
                </div>

                <div class="form-group">
                    <label for="password">Пароль:</label>
                    <input type="password" id="password" name="password" required>
                </div>

                <button type="submit" style="width: 100%;">Войти</button>
            </form>

            <p style="margin-top: 1rem; text-align: center;">
                Нет аккаунта? <a href="${contextPath}/auth/register">Зарегистрируйтесь</a>
            </p>
        </div>
    </div>
</@layout.base>