<#import "base.ftlh" as layout>

<@layout.base pageTitle="CarEvents - Автомобильные мероприятия">
    <div style="max-width: 1000px; margin: 0 auto;">
        <div class="card" style="text-align: center; padding: 3rem; background: linear-gradient(135deg, #667eea 0%, #764ba2 100%); color: white;">
            <h1 style="font-size: 3rem; margin-bottom: 1rem;">AutoSquad</h1>
            <p style="font-size: 1.5rem; margin-bottom: 2rem;">
                Найдите автомобильные мероприятия рядом с вами
            </p>
            <div style="display: flex; gap: 1rem; justify-content: center;">
                <a href="${contextPath}/events" class="btn" style="background: white; color: #333; padding: 12px 24px;">
                    Смотреть мероприятия
                </a>
                <#if !user??>
                    <a href="${contextPath}/auth/register" class="btn" style="background: transparent; border: 2px solid white; color: white; padding: 12px 24px;">
                        Присоединиться
                    </a>
                </#if>
            </div>
        </div>

        <div style="display: grid; grid-template-columns: repeat(auto-fit, minmax(250px, 1fr)); gap: 2rem; margin: 3rem 0;">
            <div class="card" style="text-align: center;">
                <div style="font-size: 3rem; margin-bottom: 1rem;">🎯</div>
                <h3>Создавайте события</h3>
                <p>Организуйте автомобильные встречи, гонки, выставки</p>
            </div>

            <div class="card" style="text-align: center;">
                <div style="font-size: 3rem; margin-bottom: 1rem;">👥</div>
                <h3>Находите единомышленников</h3>
                <p>Знакомьтесь с автолюбителями в вашем городе</p>
            </div>

            <div class="card" style="text-align: center;">
                <div style="font-size: 3rem; margin-bottom: 1rem;">🚙</div>
                <h3>Показывайте автомобили</h3>
                <p>Добавляйте свои автомобили в профиль</p>
            </div>
        </div>

        <#if !user??>
            <div class="card" style="text-align: center; background: #f8f9fa;">
                <h2>Присоединяйтесь к сообществу</h2>
                <p style="margin-bottom: 2rem;">Создавайте мероприятия, находите друзей, делитесь опытом</p>
                <div style="display: flex; gap: 1rem; justify-content: center;">
                    <a href="${contextPath}/auth/register" class="btn btn-success" style="padding: 12px 24px;">
                        Зарегистрироваться
                    </a>
                    <a href="${contextPath}/auth/login" class="btn" style="padding: 12px 24px;">
                        Войти
                    </a>
                </div>
            </div>
        </#if>
    </div>
</@layout.base>