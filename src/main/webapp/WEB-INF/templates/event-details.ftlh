<#import "base.ftlh" as layout>

<@layout.base pageTitle=event.title!"" + " - Детали мероприятия">
    <div style="display: flex; justify-content: space-between; align-items: flex-start; gap: 2rem; margin-bottom: 2rem;">
        <div style="flex: 1;">
            <nav style="margin-bottom: 1rem;">
                <a href="${contextPath}/events" class="btn" style="padding: 6px 12px;">← Все мероприятия</a>
                <#if user??>
                    <a href="${contextPath}/events/my" class="btn" style="padding: 6px 12px;">Мои мероприятия</a>
                </#if>
            </nav>
            <h1>${event.title!''}</h1>
        </div>

        <#if user??>
            <div style="flex-shrink: 0;">
                <#if isOrganizer?? && isOrganizer>
                    <a href="${contextPath}/events/edit/${event.id}" class="btn" style="margin-right: 0.5rem;">✏️ Редактировать</a>
                    <form action="${contextPath}/events/delete" method="post" style="display: inline;">
                        <input type="hidden" name="id" value="${event.id}">
                        <button type="submit" class="btn" style="background: #e74c3c;" onclick="return confirm('Удалить мероприятие?')">🗑️ Удалить</button>
                    </form>
                <#else>
                    <#if isRegistered?? && isRegistered>
                        <form action="${contextPath}/events/registration/cancel" method="post" style="display: inline;">
                            <input type="hidden" name="eventId" value="${event.id}">
                            <button type="submit" class="btn" style="background: #e74c3c;">❌ Отменить запись</button>
                        </form>
                    <#else>
                        <form action="${contextPath}/events/registration" method="post" style="display: inline;">
                            <input type="hidden" name="eventId" value="${event.id}">
                            <button type="submit" class="btn btn-success">✅ Записаться</button>
                        </form>
                    </#if>
                </#if>
            </div>
        </#if>
    </div>

    <div style="display: grid; grid-template-columns: 2fr 1fr; gap: 2rem;">
        <div class="card">
            <h3>📋 Информация о мероприятии</h3>

            <div style="display: grid; grid-template-columns: auto 1fr; gap: 1rem; align-items: start;">
                <strong>Описание:</strong>
                <div>${event.description!''}</div>

                <strong>Место:</strong>
                <div>${event.location!''}</div>

                <strong>Дата и время:</strong>
                <div>
                    <#if event.eventDate??>
                        <div style="font-size: 1.1em; font-weight: 500;">
                            ${event.eventDate.toString()}
                        </div>
                    <#else>
                        <span style="color: #dc3545;">Не установлена</span>
                    </#if>
                </div>

                <strong>Тип мероприятия:</strong>
                <div>
                    <#assign eventTypeName = "">
                    <#if event.eventType??>
                        <#assign eventTypeName = event.eventType.name>
                    <#elseif event.eventTypeId?? && eventTypes??>
                        <#list eventTypes as type>
                            <#if type.id == event.eventTypeId>
                                <#assign eventTypeName = type.name>
                                <#break>
                            </#if>
                        </#list>
                    </#if>

                    <#if eventTypeName != "">
                        <span class="badge badge-primary">${eventTypeName}</span>
                    <#elseif event.eventTypeId??>
                        <span class="badge badge-primary">Тип ID: ${event.eventTypeId}</span>
                    <#else>
                        <span class="badge" style="background: #f8f9fa; color: #6c757d;">Не указан</span>
                    </#if>
                </div>
            </div>
        </div>

        <div>
            <div class="card" style="margin-bottom: 1rem;">
                <h4>👤 Организатор</h4>
                <#if event.userId??>
                    <p>ID организатора: ${event.userId}</p>
                <#else>
                    <p>Организатор не указан</p>
                </#if>
            </div>

            <#if user??>
                <div class="card">
                    <h4>📝 Ваш статус</h4>
                    <#if isOrganizer?? && isOrganizer>
                        <p style="color: #27ae60; font-weight: 500;">🎯 Вы организатор</p>
                        <small>Вы можете редактировать и управлять этим мероприятием</small>
                    <#elseif isRegistered?? && isRegistered>
                        <p style="color: #3498db; font-weight: 500;">✅ Вы записаны</p>
                        <small>Вы участвуете в этом мероприятии</small>
                    <#else>
                        <p style="color: #e67e22; font-weight: 500;">⏳ Не записаны</p>
                        <small>Нажмите "Записаться" чтобы участвовать</small>
                    </#if>
                </div>
            </#if>
        </div>
    </div>


    <#if !user??>
        <div class="card" style="background: #e3f2fd; border-left: 4px solid #2196F3;">
            <h4>🔐 Войдите в систему</h4>
            <p>Чтобы записаться на мероприятие или создавать свои, необходимо войти в систему.</p>
            <a href="${contextPath}/auth/login" class="btn">Войти</a>
            <a href="${contextPath}/auth/register" class="btn" style="background: #95a5a6;">Регистрация</a>
        </div>
    </#if>
</@layout.base>