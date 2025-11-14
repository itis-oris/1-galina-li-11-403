<#import "base.ftlh" as layout>

<@layout.base pageTitle=pageTitle!"Форма мероприятия">
    <div style="max-width: 800px; margin: 0 auto;">
        <nav style="margin-bottom: 1rem;">
            <a href="${contextPath}/events" class="btn">← Все мероприятия</a>
        </nav>

        <div class="card">
            <h1>${pageTitle!"Форма мероприятия"}</h1>

            <#if errorMessage??>
                <div class="alert alert-error">${errorMessage}</div>
            </#if>

            <#if event??>
                <form action="${contextPath}/events/update" method="POST">
                    <input type="hidden" name="id" value="${event.id}">

                    <div class="form-group">
                        <label>Название мероприятия *</label>
                        <input type="text" name="title" value="${event.title!''}" required
                               style="width: 100%; padding: 10px; border: 1px solid #ccc; border-radius: 4px; font-size: 16px;">
                    </div>

                    <div class="form-group">
                        <label>Описание</label>
                        <textarea name="description" rows="4"
                                  style="width: 100%; padding: 10px; border: 1px solid #ccc; border-radius: 4px; font-size: 16px;">${event.description!''}</textarea>
                    </div>

                    <div class="form-group">
                        <label>Место проведения *</label>
                        <input type="text" name="location" value="${event.location!''}" required
                               style="width: 100%; padding: 10px; border: 1px solid #ccc; border-radius: 4px; font-size: 16px;">
                    </div>

                    <div class="form-group">
                        <label>Дата и время мероприятия *</label>
                        <input type="text" name="eventDate" value="${event.eventDate!''}" required
                               style="width: 100%; padding: 10px; border: 1px solid #ccc; border-radius: 4px; font-size: 16px;"
                               placeholder="Пример: 2025-11-30T20:00">
                        <small>Формат: ГГГГ-ММ-ДДTЧЧ:MM</small>
                    </div>

                    <div class="form-group">
                        <label>Тип мероприятия *</label>
                        <select name="eventTypeId" required
                                style="width: 100%; padding: 10px; border: 1px solid #ccc; border-radius: 4px; font-size: 16px;">
                            <option value="">Выберите тип мероприятия</option>
                            <#list eventTypes as type>
                                <option value="${type.id}" <#if event.eventTypeId?? && event.eventTypeId == type.id>selected</#if>>
                                    ${type.name}
                                </option>
                            </#list>
                        </select>
                    </div>

                    <div style="display: flex; gap: 1rem; margin-top: 2rem;">
                        <button type="submit" class="btn btn-success" style="padding: 12px 24px; font-size: 16px;">
                            💾 Сохранить изменения
                        </button>
                        <a href="${contextPath}/events/${event.id}" class="btn" style="padding: 12px 24px; font-size: 16px;">
                            ❌ Отмена
                        </a>
                    </div>
                </form>


            <#else>
                <form action="${contextPath}/events/" method="POST">
                    <div class="form-group">
                        <label>Название мероприятия *</label>
                        <input type="text" name="title" required
                               style="width: 100%; padding: 10px; border: 1px solid #ccc; border-radius: 4px; font-size: 16px;"
                               placeholder="Введите название мероприятия">
                    </div>

                    <div class="form-group">
                        <label>Описание</label>
                        <textarea name="description" rows="4"
                                  style="width: 100%; padding: 10px; border: 1px solid #ccc; border-radius: 4px; font-size: 16px;"
                                  placeholder="Опишите ваше мероприятие"></textarea>
                    </div>

                    <div class="form-group">
                        <label>Место проведения *</label>
                        <input type="text" name="location" required
                               style="width: 100%; padding: 10px; border: 1px solid #ccc; border-radius: 4px; font-size: 16px;"
                               placeholder="Укажите место проведения">
                    </div>

                    <div class="form-group">
                        <label>Дата и время мероприятия *</label>
                        <input type="text" name="eventDate" required
                               style="width: 100%; padding: 10px; border: 1px solid #ccc; border-radius: 4px; font-size: 16px;"
                               placeholder="2025-11-30T20:00">
                        <small>Формат: ГГГГ-ММ-ДДTЧЧ:MM</small>
                    </div>

                    <div class="form-group">
                        <label>Тип мероприятия *</label>
                        <select name="eventTypeId" required
                                style="width: 100%; padding: 10px; border: 1px solid #ccc; border-radius: 4px; font-size: 16px;">
                            <option value="">Выберите тип мероприятия</option>
                            <#list eventTypes as type>
                                <option value="${type.id}">${type.name}</option>
                            </#list>
                        </select>
                    </div>

                    <div style="display: flex; gap: 1rem; margin-top: 2rem;">
                        <button type="submit" class="btn btn-success" style="padding: 12px 24px; font-size: 16px;">
                            Создать мероприятие
                        </button>
                        <a href="${contextPath}/events" class="btn" style="padding: 12px 24px; font-size: 16px;">
                            ❌ Отмена
                        </a>
                    </div>
                </form>
            </#if>
        </div>
    </div>
</@layout.base>