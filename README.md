# App - AWallet #

![Build](https://travis-ci.org/laravel/framework.svg "Build Status")
![MIT](https://img.shields.io/packagist/l/laravel/framework "License")

![Logo](/documentacion/awallet_logo.png)

Aplicación android nativa para llevar el registro de gastos e ingresos que puede tener una
persona natural a lo largo de la semana, clasificando según sus tipos de gastos para que
se puedan crear gráficas que muestren el uso de los ingresos del usuario y los
destinatarios de los mismos.

## Descripción ##

El problema se centra en las personas que no conllevan un orden financiero en sus
cuentas y gastos, con la aplicación se resuelve el desorden del usuario y organiza sus
cuentas y documenta todas las transacciones realizadas por el usuario teniendo un
control más detallado con reportes y gráficos estadísticos.

## Información de versionamiento en repositorio ##
Para el desarrollo inicial de AWallet se a planteado en 2 ramas principales, la rama 'main' que es donde se harán los despliegues finales de cada versión a publicar, y, la rama 'develop' que es la rama donde se realizará el desarrollo de los features a publicar.

## Requerimientos de dependecias y versiones ##
------
```
android {
    compileSdk 31

    defaultConfig {
        applicationId "com.llopez.awallet"
        minSdk 28
        targetSdk 31
        versionCode 1
        versionName "1.0"

        testInstrumentationRunner "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            minifyEnabled false
            proguardFiles getDefaultProguardFile('proguard-android-optimize.txt'), 'proguard-rules.pro'
        }
    }
    compileOptions {
        sourceCompatibility JavaVersion.VERSION_1_8
        targetCompatibility JavaVersion.VERSION_1_8
    }
}

dependencies {

    implementation 'androidx.appcompat:appcompat:1.4.1'
    implementation 'com.google.android.material:material:1.5.0'
    implementation 'androidx.constraintlayout:constraintlayout:2.1.3'
    implementation 'androidx.navigation:navigation-fragment:2.4.1'
    implementation 'androidx.navigation:navigation-ui:2.4.1'
    implementation 'androidx.legacy:legacy-support-v4:1.0.0'
    testImplementation 'junit:junit:4.+'
    androidTestImplementation 'androidx.test.ext:junit:1.1.3'
    androidTestImplementation 'androidx.test.espresso:espresso-core:3.4.0'

    // Declare the dependency for the Cloud Firestore library
    // When using the BoM, you don't specify versions in Firebase library dependencies
    implementation 'com.google.firebase:firebase-firestore'

    // Import the BoM for the Firebase platform
    implementation platform('com.google.firebase:firebase-bom:29.3.0')

    // Declare the dependency for the Firebase Authentication library
    // When using the BoM, you don't specify versions in Firebase library dependencies
    implementation 'com.google.firebase:firebase-auth'

    // Also declare the dependency for the Google Play services library and specify its version
    implementation 'com.google.android.gms:play-services-auth:20.1.0'

    implementation 'com.firebaseui:firebase-ui-firestore:7.1.1'

    implementation 'com.squareup.picasso:picasso:2.71828'

    def lottieVersion = "3.4.0"
    implementation "com.airbnb.android:lottie:$lottieVersion"
}
```

## Herramientas y recursos utilizadas para el desarrollo de AWallet ##

## Mockups de la aplicación ##

![Figma](/documentacion/figma.png)

El siguiente enlace se muestra el tablero de trabajo del equipo en figma [aquí](https://www.figma.com/file/xuDpHcuVH9y9lEAsUU3w8h/DSM---App)

## Manual de Usuario ##

![Usuario](documentation/portada_manual.PNG "Manual de Usuario")

El siguiente enlace se muestra el manual de usuario para la aplicación [aquí]()

## Manual Técnico ##

![Tecnico](documentation/portada_tecnico.PNG "Manual de Técnico")

El siguiente enlace se muestra el manual técnico de la aplicación [aquí]()

## Funcionamiento - Video en drive ##

[aquí]()

## Desarrolladores ##

* Aguilar Urquilla, Erick Gilberto                      AU171965   -  T03
* Lemus Torres, Diego Alberto                           LT171997    -  T03
* López Cañas, Leonardo Elenilson                       LC171998    -  T03
* Romero Quijano, Kevin Alegandro                       RQ172927    -  T03

## Licencias Creative Commons ##

![CC](https://co.creativecommons.net/wp-content/uploads/sites/27/2008/02/by-nc.png "CC-BY-NC")

CC-BY-NC: permite distribuir y hacer cambios en la obra siempre y cuando se incluya el
nombre del autor y la licencia. Sin embargo, no se permite su uso con fines comerciales.

## Otras Licencias ##

-------

    Copyright 2014 - 2020 Henning Dodenhof

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

        http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.
