<#macro base pageTitle="CarEvents">
    <!DOCTYPE html>
    <html lang="ru">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>${pageTitle}</title>
        <style>
            * { margin: 0; padding: 0; box-sizing: border-box; }

            body {
                font-family: 'Arial', sans-serif;
                line-height: 1.6;
                color: #333;
                background-color: #f4f4f4;
            }

            .container {
                max-width: 1200px;
                margin: 0 auto;
                padding: 0 20px;
            }

            header {
                background: #2c3e50;
                color: white;
                padding: 1rem 0;
                box-shadow: 0 2px 5px rgba(0,0,0,0.1);
            }

            .header-content {
                display: flex;
                justify-content: space-between;
                align-items: center;
            }

            .logo {
                font-size: 1.5rem;
                font-weight: bold;
            }

            .nav-links a {
                color: white;
                text-decoration: none;
                margin-left: 1rem;
                padding: 0.5rem 1rem;
                border-radius: 4px;
                transition: background 0.3s;
            }

            .nav-links a:hover {
                background: #34495e;
            }

            main {
                padding: 2rem 0;
                min-height: calc(100vh - 140px);
            }

            .card {
                background: white;
                border-radius: 8px;
                padding: 1.5rem;
                margin-bottom: 1rem;
                box-shadow: 0 2px 10px rgba(0,0,0,0.1);
            }

            /* Forms */
            .form-group {
                margin-bottom: 1rem;
            }

            label {
                display: block;
                margin-bottom: 0.5rem;
                font-weight: bold;
            }

            input, select, textarea {
                width: 100%;
                padding: 0.75rem;
                border: 1px solid #ddd;
                border-radius: 4px;
                font-size: 1rem;
            }

            button, .btn {
                background: #3498db;
                color: white;
                padding: 0.75rem 1.5rem;
                border: none;
                border-radius: 4px;
                cursor: pointer;
                text-decoration: none;
                display: inline-block;
                transition: background 0.3s;
                font-size: 14px;
            }

            button:hover, .btn:hover {
                background: #2980b9;
            }

            .btn-success {
                background: #27ae60;
            }

            .btn-success:hover {
                background: #219a52;
            }

            .alert {
                padding: 1rem;
                border-radius: 4px;
                margin-bottom: 1rem;
            }

            .alert-error {
                background: #e74c3c;
                color: white;
            }

            .alert-success {
                background: #2ecc71;
                color: white;
            }

            footer {
                background: #34495e;
                color: white;
                text-align: center;
                padding: 1rem 0;
                margin-top: 2rem;
            }

            table {
                width: 100%;
                border-collapse: collapse;
                margin-top: 1rem;
            }

            th, td {
                padding: 12px;
                text-align: left;
                border-bottom: 1px solid #ddd;
            }

            th {
                background: #f8f9fa;
                font-weight: bold;
            }

            tr:hover {
                background: #f8f9fa;
            }

            .badge {
                display: inline-block;
                padding: 4px 12px;
                border-radius: 20px;
                font-size: 0.85em;
                font-weight: 500;
            }

            .badge-primary {
                background: #e3f2fd;
                color: #1976d2;
            }

            .text-center {
                text-align: center;
            }

            .mt-2 { margin-top: 1rem; }
            .mb-2 { margin-bottom: 1rem; }
        </style>
    </head>
    <body>
    <header>
        <div class="container">
            <div class="header-content">
                <div class="logo">AutoSquad</div>
                <nav class="nav-links">
                    <a href="${contextPath!""}/events">Мероприятия</a>
                    <#if user??>
                        <a href="${contextPath!""}/events/my">Мои мероприятия</a>
                        <a href="${contextPath!""}/profile">Профиль</a>
                        <a href="${contextPath!""}/auth/logout">Выйти (${user.username!""})</a>
                    <#else>
                        <a href="${contextPath!""}/auth/login">Войти</a>
                        <a href="${contextPath!""}/auth/register">Регистрация</a>
                    </#if>
                </nav>
            </div>
        </div>
    </header>

    <main class="container">
        <#if errorMessage??>
            <div class="alert alert-error">${errorMessage}</div>
        </#if>
        <#if successMessage??>
            <div class="alert alert-success">${successMessage}</div>
        </#if>

        <#nested>
    </main>

    <footer>
        <div class="container">
            <p>&copy; 2025 AutoSquad. Система управления автомобильными мероприятиями.</p>
        </div>
    </footer>

    <script>
        document.addEventListener('DOMContentLoaded', function() {
            const deleteForms = document.querySelectorAll('form[action*="delete"]');
            deleteForms.forEach(form => {
                form.addEventListener('submit', function(e) {
                    if (!confirm('Вы уверены, что хотите удалить?')) {
                        e.preventDefault();
                    }
                });
            });
        });
    </script>
    </body>
    </html>
</#macro>