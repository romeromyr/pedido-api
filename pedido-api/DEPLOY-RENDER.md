# Cómo subir pedido-api a Render — Paso a paso

Guía sencilla para subir tu API a internet usando Render. Cada paso dice **qué hacer** y **dónde hacerlo**.

---

## Antes de empezar

Necesitas:

1. **Tu proyecto en GitHub** (o GitLab/Bitbucket). Si no está subido:
   - Crea una cuenta en github.com y un repositorio nuevo (por ejemplo "pedido-api").
   - En la carpeta de tu proyecto (donde está el archivo `pom.xml`), abre la terminal y ejecuta:
   ```bash
   git init
   git add .
   git commit -m "Primer commit"
   git remote add origin https://github.com/TU_USUARIO/pedido-api.git
   git push -u origin main
   ```
   (Cambia TU_USUARIO y pedido-api por tu usuario y nombre del repo.)

2. **Una base de datos MySQL en internet.** Render no da MySQL. Puedes usar:
   - **PlanetScale** (gratis): entra en planetscale.com, crea una base de datos y anota: host, usuario, contraseña y nombre de la base.
   - O cualquier otro servicio que te dé MySQL (Railway, etc.).

3. **Cuenta en Render:** entra en [render.com](https://render.com) y regístrate (es gratis).

---

# PARTE 1: Crear el servicio en Render

## Paso 1 — Entrar al panel de Render

1. Abre [https://dashboard.render.com](https://dashboard.render.com).
2. Inicia sesión si no lo has hecho.

**Dónde:** Es la página principal después de iniciar sesión. Verás una lista de tus proyectos (puede estar vacía).

---

## Paso 2 — Crear un nuevo Web Service

1. Arriba a la derecha verás un botón azul que dice **"New +"**.
2. Haz clic en **"New +"**.
3. En el menú que se abre, elige **"Web Service"**.

**Dónde:** El botón "New +" está en la esquina superior derecha del dashboard.

---

## Paso 3 — Conectar tu repositorio de GitHub

1. Render te mostrará una lista de repositorios (GitHub, GitLab, etc.).
2. Si no has conectado GitHub, haz clic en **"Connect account"** o **"Configure account"** y autoriza Render para ver tus repos.
3. Busca tu repositorio **pedido-api** (o como lo hayas llamado) en la lista.
4. Haz clic en **"Connect"** al lado de ese repositorio.

**Dónde:** En la pantalla "Create a new Web Service", la primera sección es "Connect a repository". Ahí eliges el repo.

---

## Paso 4 — Configurar el nombre y la región

Después de conectar el repo, verás un formulario con varias opciones.

1. **Name** (nombre del servicio)  
   - Arriba del todo hay un campo **"Name"**.  
   - Escribe algo como: `pedido-api`.  
   - Ese nombre aparecerá en la URL (por ejemplo: pedido-api-xxxx.onrender.com).

2. **Region**  
   - Más abajo verás **"Region"**.  
   - Elige la más cercana a ti (por ejemplo "Oregon" o "Frankfurt").

**Dónde:** Son los primeros campos del formulario de configuración del Web Service.

---

## Paso 5 — Indicar la carpeta del proyecto (Root Directory)

Tu código puede estar en una carpeta dentro del repo. Render tiene que saber **en qué carpeta** está el archivo `pom.xml`.

1. Busca la opción **"Root Directory"** en el formulario.
2. Si tu repositorio tiene esta estructura:
   - carpeta `pedido-api` y dentro otra carpeta `pedido-api` con el `pom.xml`  
   entonces escribe en **Root Directory**:  
   **`pedido-api`**
3. Si el `pom.xml` está en la raíz del repositorio (al abrir el repo solo ves `pom.xml`, `src`, etc.), deja **Root Directory** vacío.

**Dónde:** En la misma página de configuración, suele estar debajo de "Branch". Es un campo de texto que puede decir "Leave blank to use repository root".

---

## Paso 6 — Elegir la rama (Branch)

1. Busca el campo **"Branch"**.
2. Escribe: **`main`** (o la rama donde subes tu código, por ejemplo `master`).

**Dónde:** Cerca de "Root Directory". Es el nombre de la rama de Git que Render usará para desplegar.

---

## Paso 7 — Comando de build (Build Command)

Render tiene que **compilar** tu proyecto. Eso se hace con un solo comando.

1. Busca la casilla **"Build Command"**.
2. Borra lo que haya y escribe exactamente:
   ```bash
   ./mvnw clean package -DskipTests
   ```
3. No quites ni añadas espacios. Así Render usará Maven para generar el archivo JAR.

**Dónde:** En la sección "Build & Deploy" del formulario. Verás "Build Command" con un cuadro de texto.

---

## Paso 8 — Comando de inicio (Start Command)

Cuando el build termine, Render tiene que **arrancar** tu aplicación.

1. Busca la casilla **"Start Command"**.
2. Borra lo que haya y escribe exactamente:
   ```bash
   java -Dspring.profiles.active=prod -jar target/pedido-api-0.0.1-SNAPSHOT.jar
   ```

**Dónde:** Justo debajo de "Build Command", en la misma sección "Build & Deploy".

---

## Paso 9 — Plan (gratis)

1. Busca **"Instance Type"** o **"Plan"**.
2. Elige el plan **"Free"** si solo quieres probar.

**Dónde:** Suele estar en la parte inferior del formulario, antes del botón de crear.

---

## Paso 10 — Crear el servicio (pero sin desplegar todavía)

1. Baja hasta el final de la página.
2. Haz clic en el botón **"Create Web Service"** (o "Advanced" si quieres ver más opciones; luego "Create Web Service").
3. Render empezará el primer despliegue. Puede fallar si aún no has puesto la base de datos; no te preocupes, la configuramos en los siguientes pasos.

**Dónde:** El botón azul grande al final del formulario.

---

# PARTE 2: Configurar la base de datos (variables de entorno)

## Paso 11 — Abrir la configuración del servicio

1. Después de crear el servicio, estarás en la página de ese servicio (verás "Logs", "Metrics", etc.).
2. En el menú lateral izquierdo (o en pestañas arriba) busca **"Environment"** y haz clic.

**Dónde:** Dentro del servicio que acabas de crear. "Environment" es donde se añaden las variables (usuario BD, contraseña, etc.).

---

## Paso 12 — Añadir las variables de entorno

Aquí le dices a tu API **dónde está la base de datos** y **cómo conectarse**. Añade estas variables una por una.

1. Haz clic en **"Add Environment Variable"** o **"Add Variable"**.

2. **Primera variable:**
   - **Key:** `SPRING_PROFILES_ACTIVE`  
   - **Value:** `prod`  
   Luego guarda (Add / Save).

3. **Segunda variable:**
   - **Key:** `DATABASE_URL`  
   - **Value:** La URL completa de tu MySQL. Formato:
     ```
     jdbc:mysql://TU_HOST:3306/NOMBRE_DE_LA_BD?useSSL=true&serverTimezone=UTC
     ```
     Ejemplo si tu host es `abc123.mysql.planetscale.com` y la base se llama `pedido_db`:
     ```
     jdbc:mysql://abc123.mysql.planetscale.com:3306/pedido_db?useSSL=true&serverTimezone=UTC
     ```
   (Sustituye TU_HOST y NOMBRE_DE_LA_BD por los datos que te dio el servicio de MySQL.)

4. **Tercera variable:**
   - **Key:** `DATABASE_USERNAME`  
   - **Value:** El usuario de la base de datos (el que te dio PlanetScale o tu proveedor).

5. **Cuarta variable:**
   - **Key:** `DATABASE_PASSWORD`  
   - **Value:** La contraseña de la base de datos.  
   (Render suele marcar esta como "Secret" para que no se vea en los logs.)

**Dónde:** En la página "Environment" del servicio. Cada variable tiene un campo "Key" y un campo "Value".

---

## Paso 13 — Guardar y volver a desplegar

1. Asegúrate de que las cuatro variables están guardadas.
2. Ve a la pestaña **"Manual Deploy"** o al botón **"Deploy"** / **"Deploy latest commit"**.
3. Haz clic en **"Deploy"** para que Render vuelva a compilar y arrancar la app con la base de datos ya configurada.

**Dónde:** Arriba en la página del servicio suele haber "Manual Deploy" o un botón "Deploy". Ahí se lanza un nuevo despliegue.

---

# PARTE 3: Comprobar que está en línea

## Paso 14 — Ver el estado del despliegue

1. En la página del servicio verás la sección **"Events"** o **"Logs"**.
2. Espera a que el **Build** termine (verás líneas de Maven compilando).
3. Después empezará el **Deploy**. Cuando termine, el estado pasará a **"Live"** (verde).

**Dónde:** En la misma página del servicio. Los logs se actualizan solos. Si algo falla, el error aparecerá en rojo en los logs.

---

## Paso 15 — Abrir tu API en el navegador

1. Arriba de la página del servicio verás la **URL** del servicio, algo como:
   `https://pedido-api-xxxx.onrender.com`
2. Haz clic en esa URL o cópiala y ábrela en el navegador.
3. Si tu API tiene una ruta como `/api/productos`, prueba:  
   `https://pedido-api-xxxx.onrender.com/api/productos`

**Dónde:** La URL aparece en la parte superior de la página del servicio, a la derecha. Suele decir "Your service is live at..." cuando todo va bien.

---

## Resumen rápido — Qué poner y dónde

| Dónde en Render        | Qué poner |
|------------------------|-----------|
| Root Directory         | `pedido-api` (si tu `pom.xml` está dentro de esa carpeta; si no, vacío) |
| Build Command          | `./mvnw clean package -DskipTests` |
| Start Command          | `java -Dspring.profiles.active=prod -jar target/pedido-api-0.0.1-SNAPSHOT.jar` |
| Variable 1             | Key: `SPRING_PROFILES_ACTIVE` → Value: `prod` |
| Variable 2             | Key: `DATABASE_URL` → Value: `jdbc:mysql://host:3306/nombre_bd?useSSL=true&serverTimezone=UTC` |
| Variable 3             | Key: `DATABASE_USERNAME` → Value: tu usuario MySQL |
| Variable 4             | Key: `DATABASE_PASSWORD` → Value: tu contraseña MySQL |

---

## Si algo falla

- **Build falla:** Revisa que "Root Directory" sea correcto y que el "Build Command" esté escrito tal cual (con `./mvnw`).
- **Error al conectar con la base de datos:** Revisa en "Environment" que `DATABASE_URL`, `DATABASE_USERNAME` y `DATABASE_PASSWORD` sean los que te dio tu proveedor de MySQL. En PlanetScale a veces hay que activar "Connect from anywhere" o similar.
- **Página no carga (502 / error):** Abre la pestaña "Logs" del servicio y mira el final del log; ahí suele salir el error de Java (por ejemplo falta una variable o la URL de la BD está mal).

Si quieres, puedes copiar el error que te salga y te ayudo a interpretarlo.
