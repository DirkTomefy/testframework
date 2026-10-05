#!/bin/bash

APP_NAME="test"
TOMCAT_PATH="/home/tomefy/Documents/deploy/tomcat"

echo "=========================================="
echo "  DEPLOIEMENT : $APP_NAME"
echo "=========================================="

echo "--- Étape 1 : Génération du WAR ---"
./mvnw clean package -DskipTests

if [ $? -ne 0 ]; then
    echo "❌ Erreur : La compilation Maven a échoué."
    exit 1
fi

echo "✅ Compilation réussie."

WAR_FILE=$(find target -maxdepth 1 -name "*.war" | head -n 1)

if [ -z "$WAR_FILE" ]; then
    echo "❌ Aucun fichier WAR trouvé dans target/"
    exit 1
fi

echo "WAR trouvé : $WAR_FILE"

echo "--- Étape 2 : Nettoyage ---"
rm -rf "$TOMCAT_PATH/webapps/$APP_NAME"
rm -f "$TOMCAT_PATH/webapps/$APP_NAME.war"

echo "--- Étape 3 : Déploiement ---"
cp "$WAR_FILE" "$TOMCAT_PATH/webapps/$APP_NAME.war"

echo "✅ Déploiement terminé."
echo "URL : http://localhost:8080/$APP_NAME/"