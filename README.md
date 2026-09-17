# Asistente de Compras (Android / Kotlin)

Aplicación Android nativa (Kotlin + XML, sin Jetpack Compose) que funciona como
asistente para cálculos básicos de compras y presupuesto.

## Cómo abrir el proyecto

1. Abre **Android Studio** (versión Koala/2024.1 o superior recomendada).
2. `File > Open` y selecciona la carpeta `AsistenteCompras` (la raíz, donde está `settings.gradle.kts`).
3. Deja que Gradle sincronice (descargará las dependencias de Material Components, AndroidX, etc.).
4. Ejecuta la app en un emulador o dispositivo físico (`minSdk 21`, `targetSdk 34`).

No es necesario tener Gradle instalado localmente: el proyecto no incluye el
wrapper binario (`gradlew`/`gradlew.bat`) por tamaño, pero Android Studio lo
genera automáticamente al abrir el proyecto (`File > Sync Project with Gradle Files`
si no aparece solo). Si prefieres generarlo por línea de comandos:

```bash
gradle wrapper --gradle-version 8.7
```

## Estructura del proyecto

```
AsistenteCompras/
├── app/
│   ├── build.gradle.kts
│   └── src/main/
│       ├── AndroidManifest.xml
│       ├── java/com/example/asistentecompras/
│       │   ├── MainActivity.kt              -> Pantalla de registro (inicio de la app)
│       │   ├── MainTabsActivity.kt          -> Pantalla principal con las 3 pestañas
│       │   ├── TabsPagerAdapter.kt          -> Adapter del ViewPager2 (3 fragments)
│       │   ├── DescuentosFragment.kt        -> Tab 1: cálculo de descuentos
│       │   ├── DividirCuentaFragment.kt     -> Tab 2: dividir cuenta entre personas
│       │   ├── ComprasCuotasFragment.kt     -> Tab 3: simulación de compra a cuotas
│       │   └── Utils.kt                     -> Formato de moneda y parsing de inputs
│       └── res/
│           ├── layout/
│           │   ├── activity_main.xml            -> Formulario de registro
│           │   ├── activity_main_tabs.xml        -> Toolbar + TabLayout + ViewPager2
│           │   ├── fragment_descuentos.xml
│           │   ├── fragment_dividir_cuenta.xml
│           │   └── fragment_compras_cuotas.xml
│           └── values/ (strings.xml, colors.xml, themes.xml)
├── build.gradle.kts
├── settings.gradle.kts
└── gradle.properties
```

## Flujo de la aplicación

1. **Registro (`MainActivity`)**: solicita Nombre y Correo electrónico.
   Valida que ambos campos no estén vacíos y que el correo tenga un formato
   válido (usando `Patterns.EMAIL_ADDRESS`). Al registrarse correctamente,
   navega a `MainTabsActivity` pasando el nombre y correo por `Intent`.

2. **Pantalla principal (`MainTabsActivity`)**: muestra un saludo con el
   nombre del usuario registrado y un `TabLayout` + `ViewPager2` con 3 pestañas:

   - **Descuentos**: ingresa precio original y % de descuento → calcula
     valor del descuento y precio final. Valida precio > 0 y descuento
     entre 0 y 100.
   - **Dividir cuenta**: ingresa valor total, número de personas y % de
     propina → calcula propina, total con propina y valor por persona.
     Valida número de personas > 0 y valores no negativos.
   - **Compra a cuotas**: ingresa valor del producto, número de cuotas y
     tasa de interés mensual → calcula el total financiado (interés
     compuesto mensual) y el valor aproximado de cada cuota. Valida
     producto > 0, cuotas > 0 y tasa ≥ 0.

Todos los resultados monetarios se muestran formateados como pesos
colombianos (por ejemplo `$ 170.000`) usando `NumberFormat` con `Locale("es", "CO")`.

## Notas técnicas

- Se usa **View Binding** (no `findViewById`) en todas las Activities y Fragments.
- La validación de formularios se hace con `TextInputLayout.error` para dar
  feedback visual inmediato al usuario.
- El cálculo de cuotas usa interés compuesto mensual:
  `total = valorProducto * (1 + tasa/100) ^ numeroCuotas`,
  `valorCuota = total / numeroCuotas`.
