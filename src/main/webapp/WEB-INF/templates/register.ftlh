<#import "base.ftlh" as layout>

<@layout.base pageTitle="Регистрация">
    <div style="max-width: 400px; margin: 0 auto;">
        <div class="card">
            <h1>Регистрация</h1>

            <form action="${contextPath}/auth/register" method="post">
                <div class="form-group">
                    <label for="username">Имя пользователя:</label>
                    <input type="text" id="username" name="username" required>
                </div>

                <div class="form-group">
                    <label for="email">Email:</label>
                    <input type="email" id="email" name="email" required>
                </div>

                <div class="form-group">
                    <label for="password">Пароль:</label>
                    <input type="password" id="password" name="password" required>
                </div>

                <button type="submit" style="width: 100%;">Зарегистрироваться</button>
            </form>

            <p style="margin-top: 1rem; text-align: center;">
                Уже есть аккаунт? <a href="${contextPath}/auth/login">Войдите</a>
            </p>
        </div>
    </div>
</@layout.base>