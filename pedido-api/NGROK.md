# Cómo exponer pedido-api con ngrok — Paso a paso

**ngrok** crea un túnel desde internet hasta tu computadora. Así puedes compartir tu API que corre en **localhost** (por ejemplo en el puerto 8081) y que otras personas o apps accedan con una URL pública (como `https://abc123.ngrok.io`).

---

## Paso 1 — Instalar ngrok

1. Entra en [https://ngrok.com](https://ngrok.com).
2. Regístrate o inicia sesión (cuenta gratuita).
3. En la web, ve a **Download** o a [https://ngrok.com/download](https://ngrok.com/download).
4. Descarga la versión para **Windows**.
5. Descomprime el archivo (por ejemplo en `C:\ngrok` o en tu carpeta de usuario).
6. **(Opcional pero recomendado)** Conecta tu cuenta para no tener límites tan estrictos:
   - En el panel de ngrok ([dashboard.ngrok.com](https://dashboard.ngrok.com)) ve a **Your Authtoken**.
   - Copia el token.
   - En PowerShell o CMD, en la carpeta donde está `ngrok.exe`, ejecuta:
     ```bash
     ngrok config add-authtoken TU_TOKEN_AQUI
     ```

**Dónde:** Todo en ngrok.com → Download → ejecutar/comando anterior en la carpeta de ngrok.

---

## Paso 2 — Arrancar tu API en local

ngrok solo “reenvía” el tráfico a tu PC; tu aplicación tiene que estar corriendo en tu máquina.

1. Abre una terminal (PowerShell o CMD) en la carpeta del proyecto (donde está el `pom.xml`).
2. Ejecuta:
   ```bash
      
   ```
   En Windows también puedes usar:
   ```bash
   mvnw.cmd spring-boot:run
   ```
3. Espera a que aparezca algo como “Started PedidoApiApplication” y que diga que está escuchando en el puerto **8081** (o el que tengas en `application.properties`).
4. Deja esta terminal abierta; no la cierres mientras quieras usar ngrok.

**Dónde:** Carpeta del proyecto (por ejemplo `pedido-api\pedido-api`). Es la misma donde ejecutas Maven.

---

## Paso 3 — Abrir ngrok y crear el túnel

1. Abre **otra** terminal (PowerShell o CMD).
2. Ve a la carpeta donde está el ejecutable de ngrok (por ejemplo `C:\ngrok`):
   ```bash
   cd C:\ngrok
   ```
   (O la ruta donde descomprimiste ngrok.)
3. Ejecuta ngrok apuntando al **mismo puerto** donde corre tu API. Tu app usa el puerto **8081**:
   ```bash
   ngrok http 8081
   ```
4. ngrok mostrará una pantalla con:
   - Una URL **HTTPS** tipo: `https://xxxx-xx-xx-xx-xx.ngrok-free.app`
   - Una URL **HTTP** tipo: `http://xxxx-xx-xx-xx-xx.ngrok-free.app`

**Dónde:** Esa URL es la que debes usar desde fuera para acceder a tu API (navegador, Postman, otra app, etc.).

---

## Paso 4 — Probar tu API desde fuera

1. Copia la URL que te dio ngrok (mejor la **HTTPS**).
2. Ábrela en el navegador o en Postman.
3. Si tu API tiene rutas como `/api/productos`, prueba por ejemplo:
   - `https://TU_URL_DE_NGROK.ngrok-free.app/api/productos`

**Dónde:** Cualquier navegador o cliente HTTP. La URL es la que aparece en la terminal de ngrok.

---

## Resumen rápido

| Qué hacer | Dónde / Comando |
|-----------|------------------|
| Instalar ngrok | [ngrok.com/download](https://ngrok.com/download) → Windows → descomprimir |
| Opcional: authtoken | `ngrok config add-authtoken TU_TOKEN` en la carpeta de ngrok |
| Arrancar la API | En la carpeta del proyecto: `mvnw.cmd spring-boot:run` (dejar abierta la terminal) |
| Crear el túnel | En otra terminal, donde está ngrok: `ngrok http 8081` |
| URL pública | La que muestra ngrok (ej. `https://xxxx.ngrok-free.app`) |

---

## Notas importantes

- **Mientras uses ngrok, tu PC debe estar encendida y la API corriendo.** Si cierras la app o apagas el PC, la URL de ngrok dejará de funcionar.
- En la cuenta **gratuita**, la URL de ngrok **cambia** cada vez que reinicias ngrok (al cerrar y volver a abrir `ngrok http 8081`). Si necesitas una URL fija, ngrok lo ofrece en planes de pago.
- A veces ngrok muestra una **página de aviso** la primera vez que entras en la URL; suele haber un botón “Visit Site” para continuar.
- Si tu API usa **CORS**, puede que desde un frontend en otro dominio tengas que configurar CORS en tu Spring Boot para permitir ese origen (la URL de ngrok).

---

## Si algo no funciona

- **“command not found” o “ngrok no se reconoce”:** Ejecuta ngrok desde la carpeta donde está `ngrok.exe` o añade esa carpeta al PATH de Windows.
- **“Connection refused”:** Tu API no está corriendo o no está en el puerto 8081. Comprueba en la terminal donde ejecutaste `spring-boot:run` que diga que está escuchando en 8081.
- **Puerto equivocado:** Si cambiaste el puerto en `application.properties`, usa ese mismo número en el comando: `ngrok http NUMERO_PUERTO`.

Si quieres, en el siguiente paso podemos ver cómo configurar CORS en tu API para que un frontend pueda llamarla usando la URL de ngrok.
