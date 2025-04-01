# Gestión Académica - Práctica 3

Este proyecto utiliza **Maven Wrapper (MVNW)** para gestionar las dependencias y la compilación sin necesidad de instalar Maven manualmente.

## 🚀 Cómo manejar el proyecto con Maven Wrapper

### 1️⃣ Abrir una terminal en Eclipse
- Atajo: **Shift + Ctrl + Alt + T**

### 2️⃣ Navegar hasta la carpeta del proyecto
```sh
cd ~/eclipse-workspace/prac3-gestion-academica
```

### 3️⃣ Dar permisos de ejecución a Maven Wrapper
```sh
chmod +x ./mvnw*
```

### 4️⃣ Verificar la versión de Maven
```sh
./mvnw -v
```
Si ves un mensaje similar al siguiente, significa que todo está bien y puedes continuar con el paso **8**:
```sh
Apache Maven 3.9.9 (8e8579a9e76f7d015ee5ec7bfcdc97d260186937)
Maven home: /home/usuario/.m2/wrapper/dists/apache-maven-3.9.9/3477a4f1
Java version: 21.0.6, vendor: Ubuntu, runtime: /usr/lib/jvm/java-21-openjdk-amd64
OS name: "linux", version: "6.11.0-17-generic", arch: "amd64"
```
Si **no ves esto**, pasa al paso **5**.

### 5️⃣ Crear manualmente el archivo `maven-wrapper.properties`
Si hay errores al ejecutar `./mvnw -v`, crea la estructura necesaria:
```sh
mkdir -p .mvn/wrapper
```

### 6️⃣ Crear el archivo con la URL de distribución
```sh
echo "distributionUrl=https://repo.maven.apache.org/maven2/org/apache/maven/apache-maven/3.9.9/apache-maven-3.9.9-bin.zip" > .mvn/wrapper/maven-wrapper.properties
```

### 7️⃣ Volver a verificar Maven
```sh
./mvnw -v
```
Si todo está bien, puedes continuar con el siguiente paso.

### 8️⃣ Ejecutar una clase con `main`
```sh
./mvnw exec:java -Dexec.mainClass="run.InsertarHelper"
```

### 9️⃣ Crear un JAR del proyecto (saltando los tests unitarios)
```sh
./mvnw clean -Dmaven.test.skip=true package
```

### 🔟 Ejecutar los tests
```sh
./mvnw test
```

### 1️⃣1️⃣ Generar el reporte de cobertura de tests
```sh
./mvnw verify
```

## 📌 Notas
- **Asegúrate de tener Java instalado** (versión 21 en este caso).
- **Si hay problemas con dependencias, limpia el caché de Maven** con:
  ```sh
  ./mvnw clean dependency:purge-local-repository
  ```

📌 ¡Listo! Ahora puedes compilar y ejecutar el proyecto sin problemas 🚀