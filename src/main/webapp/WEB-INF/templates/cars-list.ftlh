<#import "base.ftlh" as layout>

<@layout.base pageTitle="Мои автомобили">
    <div style="max-width: 800px; margin: 0 auto;">
        <nav style="margin-bottom: 1rem;">
            <a href="${contextPath}/profile" class="btn">← Назад в профиль</a>
            <a href="${contextPath}/cars/new" class="btn btn-success">+ Добавить автомобиль</a>
        </nav>

        <h1>Мои автомобили</h1>

        <#if cars?? && cars?size gt 0>
            <div class="card">
                <div style="display: grid; gap: 1rem;">
                    <#list cars as car>
                        <div style="border: 1px solid #ddd; padding: 1rem; border-radius: 8px;">
                            <div style="display: flex; justify-content: space-between; align-items: center;">
                                <div>
                                    <h3 style="margin: 0;">${car.brand!''} ${car.model!''}</h3>
                                    <p style="margin: 0.5rem 0 0 0; color: #666;">
                                        Год: ${car.year!''}
                                    </p>
                                </div>
                                <div>
                                    <form action="${contextPath}/cars/delete" method="post" style="display: inline;">
                                        <input type="hidden" name="id" value="${car.id}">
                                        <button type="submit" class="btn" style="background: #e74c3c; padding: 6px 12px;"
                                                onclick="return confirm('Удалить автомобиль?')">
                                            Удалить
                                        </button>
                                    </form>
                                </div>
                            </div>
                        </div>
                    </#list>
                </div>
            </div>
        <#else>
            <div class="card" style="text-align: center; padding: 3rem;">
                <div style="font-size: 4rem; margin-bottom: 1rem;">🚗</div>
                <h3 style="color: #666;">У вас пока нет автомобилей</h3>
                <p>Добавьте свой первый автомобиль</p>
                <a href="${contextPath}/cars/new" class="btn btn-success">Добавить автомобиль</a>
            </div>
        </#if>
    </div>
</@layout.base>