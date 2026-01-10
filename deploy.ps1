# 1. Compilar el proyecto
Write-Host "🚧 Construyendo el proyecto..." -ForegroundColor Cyan
./mvnw clean package -DskipTests

# Verificamos si la compilación fue exitosa antes de seguir
if ($LASTEXITCODE -ne 0) {
    Write-Host "❌ Error en la compilación. Deteniendo." -ForegroundColor Red
    exit
}

# 2. Subir el archivo al Droplet
Write-Host "🚀 Subiendo backend.jar al servidor..." -ForegroundColor Cyan
scp target/emprendeStore-0.0.1-SNAPSHOT.jar root@104.131.13.14:/root/backend.jar

# 3. Aviso final
Write-Host "✅ ¡Subida completada!" -ForegroundColor Green
Write-Host "⚠️  Ahora conéctate por SSH y reinicia el servidor manualmente." -ForegroundColor Yellow

# ssh root@104.131.13.14
# pkill -f backend.jar; sleep 2; nohup ./start.sh > app.log 2>&1 &
# probar si arranco: tail -f app.log