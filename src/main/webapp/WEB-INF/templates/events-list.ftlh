<#import "base.ftlh" as layout>

<@layout.base pageTitle=pageTitle!"Все мероприятия">
    <div style="display: flex; justify-content: space-between; align-items: center; margin-bottom: 2rem;">
        <h1>${pageTitle!"Все мероприятия"}</h1>
        <#if user??>
            <a href="${contextPath}/events/new" class="btn btn-success">+ Создать мероприятие</a>
        </#if>
    </div>

    <#if eventTypes?? && eventTypes?size gt 0>
        <div class="card mb-2">
            <h3>Фильтр по типу</h3>
            <div style="display: flex; gap: 1rem; flex-wrap: wrap;">
                <a href="${contextPath}/events/filter" class="btn"
                   style="background: <#if !selectedTypeId??>#3498db<#else>#95a5a6</#if>;">
                    Все типы
                </a>
                <#list eventTypes as type>
                    <a href="${contextPath}/events/filter?eventTypeId=${type.id}" class="btn"
                       style="background: <#if selectedTypeId?? && selectedTypeId == type.id>#3498db<#else>#95a5a6</#if>;">
                        ${type.name!""}
                    </a>
                </#list>
            </div>
        </div>
    </#if>

    <#if events?? && events?size gt 0>
        <div class="card">
            <div style="display: flex; justify-content: space-between; align-items: center; margin-bottom: 1rem;">
                <h3>Найдено мероприятий: ${events?size}</h3>
            </div>

            <table>
                <thead>
                <tr>
                    <th>Название</th>
                    <th>Тип</th>
                    <th>Место</th>
                    <th>Дата и время</th>
                    <th></th>
                </tr>
                </thead>
                <tbody>
                <#list events as event>
                    <tr>
                        <td style="min-width: 200px;">
                            <strong>${event.title!''}</strong>
                            <#if event.description?? && event.description != "">
                                <br>
                                <small style="color: #666;">${event.description}</small>
                            </#if>
                        </td>
                        <td>
                            <#-- ВАРИАНТ, КОТОРЫЙ РАБОТАЛ РАНЬШЕ -->
                            <#if event.eventType??>
                                <span class="badge badge-primary">${event.eventType.name!""}</span>
                            <#else>

                                <#assign eventTypeName = "">
                                <#if eventTypes?? && event.eventTypeId??>
                                    <#list eventTypes as type>
                                        <#if type.id == event.eventTypeId>
                                            <#assign eventTypeName = type.name>
                                            <#break>
                                        </#if>
                                    </#list>
                                </#if>
                                <span class="badge badge-primary">
                                        <#if eventTypeName != "">
                                            ${eventTypeName}
                                        <#else>
                                            Тип ID: ${event.eventTypeId!0}
                                        </#if>
                                    </span>
                            </#if>
                        </td>
                        <td>${event.location!''}</td>
                        <td style="min-width: 150px;">
                            <#if event.eventDate??>
                                <div style="font-weight: 500;">
                                    ${event.eventDate.toString()}
                                </div>
                            <#else>
                                <span style="color: #dc3545;">Не установлена</span>
                            </#if>
                        </td>
                        <td style="min-width: 120px;">
                            <a href="${contextPath}/events/${event.id!}" class="btn" style="padding: 6px 12px; font-size: 0.9em;">
                                Подробнее
                            </a>
                        </td>
                    </tr>
                </#list>
                </tbody>
            </table>
        </div>
    <#else>
        <div class="card text-center">
            <div style="padding: 3rem;">
                <div style="font-size: 4rem; margin-bottom: 1rem;">📭</div>
                <h3 style="color: #6c757d; margin-bottom: 1rem;">Мероприятий не найдено</h3>
                <#if user??>
                    <p>Будьте первым, кто создаст мероприятие!</p>
                    <a href="${contextPath}/events/new" class="btn btn-success mt-2">Создать мероприятие</a>
                <#else>
                    <p>Войдите в систему, чтобы создавать мероприятия</p>
                    <a href="${contextPath}/auth/login" class="btn mt-2">Войти</a>
                </#if>
            </div>
        </div>
    </#if>
</@layout.base>