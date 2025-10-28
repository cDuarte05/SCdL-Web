@echo off
title Instalador de dependências Angular - Henrique
color 0a

echo ==================================================
echo  🚀 INSTALADOR DE DEPENDÊNCIAS DO PROJETO ANGULAR
echo ==================================================
echo.

:: Verifica a versão do Node.js instalada
echo 🔍 Verificando Node.js...
node -v
IF %ERRORLEVEL% NEQ 0 (
    echo ❌ Node.js não encontrado. Instale a versão recomendada: 20.3.1
    echo Baixe em: https://nodejs.org/en/download/prebuilt-installer
    pause
    exit /b
)

:: Mostra a versão atual
echo ✅ Node.js encontrado!
node -v
echo.

:: Atualiza o npm
echo 🔄 Atualizando npm...
npm install -g npm
echo.

:: Instala o Angular CLI (versão do seu projeto)
echo ⚙️ Instalando Angular CLI versão 20.3.7...
npm install -g @angular/cli@20.3.7
echo.

:: Instala dependências principais do Angular
echo 📦 Instalando pacotes principais...
npm install @angular-devkit/build-angular@17.2.3 ^
@angular/animations@17.2.4 ^
@angular/cli@20.3.7 ^
@angular/common@17.2.4 ^
@angular/compiler-cli@17.2.4 ^
@angular/compiler@17.2.4 ^
@angular/core@17.2.4 ^
@angular/forms@17.2.4 ^
@angular/platform-browser-dynamic@17.2.4 ^
@angular/platform-browser@17.2.4 ^
@angular/router@17.2.4
echo.

:: Instala bibliotecas adicionais
echo 🧩 Instalando bibliotecas adicionais...
npm install ngx-toastr@18.0.0 rxjs@7.8.1 tslib@2.6.2 zone.js@0.14.4
echo.

:: Instala dependências de teste
echo 🧪 Instalando dependências de teste...
npm install -D @types/jasmine@5.1.4 jasmine-core@5.1.2 ^
karma@6.4.3 karma-chrome-launcher@3.2.0 karma-coverage@2.2.1 ^
karma-jasmine@5.1.0 karma-jasmine-html-reporter@2.1.0 ^
typescript@5.3.3
echo.

echo ==================================================
echo ✅ Todas as dependências foram instaladas com sucesso!
echo ==================================================
echo.
pause
