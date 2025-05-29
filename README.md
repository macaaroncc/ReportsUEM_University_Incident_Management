# UrbanFixer - Sistema de Gestión de Incidencias Universitarias

## 📄 Descripción breve
**UrbanFixer** es una aplicación desarrollada para la gestión, reporte y seguimiento de incidencias en los campus de la Universidad Europea. Permite a los usuarios registrar nuevas incidencias, consultar el estado de las existentes, marcarlas como favoritas y gestionar su cuenta de manera eficiente. Está orientada tanto a estudiantes como al personal de mantenimiento.

## 🛠 Tecnologías utilizadas
- Java (Swing)
- MySQL
- JDBC
- Patrón de diseño MVC (Modelo-Vista-Controlador)

## 💻 Requisitos previos
- Sistema operativo: Windows 10 o superior
- JDK 17 o superior
- RAM mínima: 4 GB
- Espacio en disco: 500 MB libres
- Resolución mínima: 1280x720
- Servidor MySQL 8.0
- IDE recomendado: IntelliJ IDEA o Eclipse

## 🧰 Instrucciones de instalación y ejecución

### 🔁 Clonar el repositorio
```bash
git clone https://github.com/DAM-UEM-2425/ud13-pi-final-purpurina.git
cd ud13-pi-final-purpurina
```

### ⚙️ Compilar y ejecutar
1. Abre el proyecto en tu IDE Java favorito.
2. Asegúrate de tener la base de datos configurada en MySQL según el archivo `estructura.sql`.
3. Ajusta la conexión en `modelo/ConexionBD.java` con tus credenciales.
4. Ejecuta la clase `controlador.Main.java`.

## 🗂 Estructura del proyecto

```
├── controlador/         # Lógica de navegación y control
├── modelo/              # Conexión a la base de datos y modelo de usuario
├── vista/               # Interfaces gráficas del sistema (Java Swing)
├── recursos/            # Imágenes y fondos
├── estructura.sql       # Script de creación de base de datos
└── README.md            # Documentación del proyecto
```

## 🚀 Ejemplo de uso básico

1. Accede a la aplicación como usuario registrado.
2. Desde la pantalla principal puedes:
   - Buscar incidencias por estado, fecha o relevancia.
   - Crear una nueva incidencia.
   - Consultar tus incidencias personales.
   - Añadir incidencias a favoritos.
   - Recuperar tu contraseña si la olvidas.



## 👥 Autores

- **Haowen**
- **Aaron**
- **Chen**
- **Bea**

## 👩‍🏫 Tutores del proyecto
- Pedro Camacho  
- Irene del Rincón

## 🏫 Universidad
**Universidad Europea de Madrid**

## 🎓 Ciclo
Ciclo Formativo de Grado Superior en Desarrollo de Aplicaciones Multiplataforma (DAM)

## 📆 Curso
2024 / 2025
