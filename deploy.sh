#!/bin/bash

CYAN='\033[0;36m'
RED='\033[0;31m'
GREEN='\033[0;32m'
YELLOW='\033[1;33m'
NC='\033[0m' # Sin color

# 1. Compilar el proyecto
echo -e "${CYAN}🚧 Construyendo el proyecto...${NC}"
./mvnw clean package -DskipTests

# Verificar si la compilación fue exitosa ($? es el código de salida en Linux)
if [ $? -ne 0 ]; then
    echo -e "${RED}❌ Error en la compilación. Deteniendo.${NC}"
    exit 1
fi

# 2. Subir el archivo al Droplet
echo -e "${CYAN}🚀 Subiendo backend.jar al servidor...${NC}"
scp target/emprendeStore-0.0.1-SNAPSHOT.jar root@104.131.13.14:/root/backend.jar

# 3. Aviso final
echo -e "${GREEN}✅ ¡Subida completada!${NC}"
echo -e "${YELLOW}⚠️  Ahora conéctate por SSH y reinicia el servidor manualmente.${NC}"
echo -e "Comandos sugeridos:"
echo -e "  ssh root@104.131.13.14"
echo -e "  pkill -f backend.jar; sleep 2; nohup ./start.sh > app.log 2>&1 &" #START.SH CONTIENE LAS VARIABLES DE ENTORNO
echo -e "  tail -f app.log"