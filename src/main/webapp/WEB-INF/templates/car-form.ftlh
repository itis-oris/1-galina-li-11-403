<#import "base.ftlh" as layout>

<@layout.base pageTitle="Добавить автомобиль">
    <div style="max-width: 500px; margin: 0 auto;">
        <nav style="margin-bottom: 1rem;">
            <a href="${contextPath}/cars" class="btn">← Назад к автомобилям</a>
        </nav>

        <div class="card">
            <h1>Добавить автомобиль</h1>

            <#if errorMessage??>
                <div class="alert alert-error">
                    ${errorMessage}
                </div>
            </#if>

            <form action="${contextPath}/cars" method="post">
                <div class="form-group">
                    <label for="brand">Марка *</label>
                    <input type="text" id="brand" name="brand" required>
                </div>

                <div class="form-group">
                    <label for="model">Модель *</label>
                    <input type="text" id="model" name="model" required>
                </div>

                <div class="form-group">
                    <label for="year">Год выпуска *</label>
                    <input type="number" id="year" name="year" required
                           min="1900" max="2030">
                </div>

                <div style="display: flex; gap: 1rem; margin-top: 2rem;">
                    <button type="submit" class="btn btn-success" style="flex: 1;">
                        Добавить автомобиль
                    </button>
                    <a href="${contextPath}/cars" class="btn">Отмена</a>
                </div>
            </form>
        </div>
    </div>
</@layout.base>