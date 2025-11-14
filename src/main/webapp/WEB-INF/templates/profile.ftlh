<#import "base.ftlh" as layout>

<@layout.base pageTitle="Мой профиль">
    <div style="max-width: 1200px; margin: 0 auto;">
        <nav style="margin-bottom: 1rem;">
            <a href="${contextPath}/events" class="btn" style="padding: 6px 12px;">← Все мероприятия</a>
            <#if user??>
                <a href="${contextPath}/events/my" class="btn" style="padding: 6px 12px;">Мои мероприятия</a>
            </#if>
        </nav>

        <div style="display: grid; grid-template-columns: 1fr 2fr; gap: 2rem;">
            <div>
                <div class="card">
                    <h3>👤 Мой профиль</h3>
                    <div style="text-align: center; margin-bottom: 1rem;">
                        <div style="font-size: 3rem; margin-bottom: 0.5rem;">🚗</div>
                        <h4>${user.username!''}</h4>
                        <p style="color: #666; font-size: 0.9em;">${user.email!''}</p>
                    </div>

                    <div style="display: grid; grid-template-columns: 1fr 1fr; gap: 0.5rem; text-align: center;">
                        <div style="background: #e3f2fd; padding: 0.5rem; border-radius: 8px;">
                            <div style="font-size: 1.5em; font-weight: bold;">${userEvents?size!0}</div>
                            <div style="font-size: 0.8em;">Создано</div>
                        </div>
                        <div style="background: #e8f5e8; padding: 0.5rem; border-radius: 8px;">
                            <div style="font-size: 1.5em; font-weight: bold;">${registeredEvents?size!0}</div>
                            <div style="font-size: 0.8em;">Участвую</div>
                        </div>
                    </div>
                </div>

                <div class="card">
                    <h4>Быстрые действия</h4>
                    <div style="display: flex; flex-direction: column; gap: 0.5rem;">
                        <a href="${contextPath}/events/new" class="btn btn-success">Создать мероприятие</a>
                        <a href="${contextPath}/cars" class="btn">Мои автомобили</a>
                        <a href="${contextPath}/auth/logout" class="btn" style="background: #95a5a6;">🚪 Выйти</a>
                    </div>
                </div>
            </div>

            <div>
                <div class="card" style="margin-bottom: 2rem;">
                    <div style="display: flex; justify-content: space-between; align-items: center; margin-bottom: 1rem;">
                        <h3>🎯 Мои мероприятия</h3>
                        <a href="${contextPath}/events/new" class="btn btn-success" style="padding: 6px 12px;">+ Создать</a>
                    </div>

                    <#if userEvents?? && userEvents?size gt 0>
                        <div style="display: grid; gap: 1rem;">
                            <#list userEvents as event>
                                <div style="border: 1px solid #e9ecef; border-radius: 8px; padding: 1rem;">
                                    <div style="display: flex; justify-content: space-between; align-items: flex-start;">
                                        <div style="flex: 1;">
                                            <h4 style="margin: 0 0 0.5rem 0;">${event.title!''}</h4>
                                            <p style="color: #666; margin: 0 0 0.5rem 0;">
                                                <#if event.eventDate??>
                                                    📅 ${event.eventDate.toString()}
                                                <#else>
                                                    📅 Дата не установлена
                                                </#if>
                                            </p>
                                            <p style="margin: 0; font-size: 0.9em;">
                                                📍 ${event.location!''}
                                            </p>
                                        </div>
                                        <div style="flex-shrink: 0; display: flex; gap: 0.5rem;">
                                            <a href="${contextPath}/events/${event.id}" class="btn" style="padding: 4px 8px; font-size: 0.8em;">
                                                👀
                                            </a>
                                            <a href="${contextPath}/events/edit/${event.id}" class="btn" style="padding: 4px 8px; font-size: 0.8em;">
                                                ✏️
                                            </a>
                                        </div>
                                    </div>
                                </div>
                            </#list>
                        </div>
                    <#else>
                        <div style="text-align: center; padding: 2rem; color: #6c757d;">
                            <div style="font-size: 3rem; margin-bottom: 1rem;">📭</div>
                            <p>Вы еще не создали ни одного мероприятия</p>
                            <a href="${contextPath}/events/new" class="btn btn-success">Создать первое мероприятие</a>
                        </div>
                    </#if>
                </div>

                <div class="card" style="margin-bottom: 2rem;">
                    <div style="display: flex; justify-content: space-between; align-items: center; margin-bottom: 1rem;">
                        <h3>✅ Участвую в мероприятиях</h3>
                    </div>

                    <#if registeredEvents?? && registeredEvents?size gt 0>
                        <div style="display: grid; gap: 1rem;">
                            <#list registeredEvents as event>
                                <div style="border: 1px solid #e9ecef; border-radius: 8px; padding: 1rem;">
                                    <div style="display: flex; justify-content: space-between; align-items: flex-start;">
                                        <div style="flex: 1;">
                                            <h4 style="margin: 0 0 0.5rem 0;">${event.title!''}</h4>
                                            <p style="color: #666; margin: 0 0 0.5rem 0;">
                                                <#if event.eventDate??>
                                                    📅 ${event.eventDate.toString()}
                                                <#else>
                                                    📅 Дата не установлена
                                                </#if>
                                            </p>
                                            <p style="margin: 0; font-size: 0.9em;">
                                                📍 ${event.location!''}
                                            </p>
                                        </div>
                                        <div style="flex-shrink: 0;">
                                            <a href="${contextPath}/events/${event.id}" class="btn" style="padding: 4px 8px; font-size: 0.8em;">
                                                👀 Подробнее
                                            </a>
                                        </div>
                                    </div>
                                </div>
                            </#list>
                        </div>
                    <#else>
                        <div style="text-align: center; padding: 2rem; color: #6c757d;">
                            <div style="font-size: 3rem; margin-bottom: 1rem;">🎪</div>
                            <p>Вы еще не записаны ни на одно мероприятие</p>
                            <a href="${contextPath}/events" class="btn">Найти мероприятия</a>
                        </div>
                    </#if>
                </div>

                <div class="card">
                    <div style="display: flex; justify-content: space-between; align-items: center; margin-bottom: 1rem;">
                        <h3>🚙 Мои автомобили</h3>
                        <a href="${contextPath}/cars/new" class="btn btn-success" style="padding: 6px 12px;">+ Добавить</a>
                    </div>

                    <#if userCars?? && userCars?size gt 0>
                        <div style="display: grid; grid-template-columns: repeat(auto-fill, minmax(200px, 1fr)); gap: 1rem;">
                            <#list userCars as car>
                                <div style="border: 1px solid #e9ecef; border-radius: 8px; padding: 1rem; text-align: center;">
                                    <div style="font-size: 2rem; margin-bottom: 0.5rem;">🚗</div>
                                    <h4 style="margin: 0 0 0.25rem 0;">${car.brand!''}</h4>
                                    <p style="margin: 0 0 0.25rem 0; color: #666;">${car.model!''}</p>
                                    <p style="margin: 0; font-size: 0.9em; color: #999;">${car.year!''} год</p>
                                </div>
                            </#list>
                        </div>
                    <#else>
                        <div style="text-align: center; padding: 2rem; color: #6c757d;">
                            <div style="font-size: 3rem; margin-bottom: 1rem;">🚙</div>
                            <p>У вас пока нет добавленных автомобилей</p>
                            <a href="${contextPath}/cars/new" class="btn btn-success">Добавить первый автомобиль</a>
                        </div>
                    </#if>
                </div>
            </div>
        </div>
    </div>
</@layout.base>