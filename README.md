# PokemonTCGApp

This app is based on the [POKEMON TCG API](https://docs.pokemontcg.io/#documentationgetting_started), that returns information about Pokemon cards.

For the development of the app was used [Kotlin language](https://kotlinlang.org/) and [MVVM architecture](https://docs.google.com/presentation/d/1aBtccGraTyggnIP6Nn7m8uGfBgreKWIk-2JuLafKAds/edit#slide=id.p4) together with [Clean](https://proandroiddev.com/kotlin-clean-architecture-1ad42fcd97fa).
![alt text](https://github.com/MarcosCGlobant/PokemonTCGApp/blob/Marcos.Cifuentes/card-type-fragment-service/Clean%20%2B%20MVVM.png "Clean + MVVM")

The application performed dependency injections using the [Koin](https://insert-koin.io/) framework

Requests to the API are done via [Retrofit](https://square.github.io/retrofit/), and the information is handled with [Kotlin coroutines](https://kotlinlang.org/docs/reference/coroutines-overview.html).

The Main activity is shown using a View Pager with 3 tabs. This is done with the [ViewPager2](https://developer.android.com/jetpack/androidx/releases/viewpager2) library.

The information is shown using a [Recycler view](https://developer.android.com/guide/topics/ui/layout/recyclerview), with card views to contain the data.
The images were loaded using [Glide](https://github.com/bumptech/glide).

## Details on the technologies used:

### Model–view–viewmodel (MVVM) 
Software architectural pattern that facilitates the separation of the development of the graphical user interface (the view) – be it via a markup language or GUI code – from the development of the business logic or back-end logic (the model) so that the view is not dependent on any specific model platform. In this project it is use the MVVM ViewModel's centric (without a Model)

### Clean Architecture

Software design philosophy that separates the elements of a design into ring levels. The main rule of clean architecture is that code dependencies can only come from the outer levels inward. Code on the inner layers can have no knowledge of functions on the outer layers. 

Layers of Clean Architecture used in this project:

* Domain: Would execute business logic which is independent of any layer and is just a pure kotlin package with no android specific dependency.
* Data: Would dispense the required data for the application to the domain layer by implementing interface exposed by the domain.
* Presentation: Would include both domain and data layer and is android specific which executes the UI logic.

### Kotlin

General purpose, free, open source, statically typed “pragmatic” programming language initially designed for the JVM (Java Virtual Machine) and Android that combines object-oriented and functional programming features.

### Koin (Dependency Injections)

Lightweight dependency injection framework, that uses Kotlin’s DSLs to lazily resolve your dependency graph at runtime.
If you are unfamiliar with the concept of dependency injection, you can read [this article](https://www.freecodecamp.org/news/a-quick-intro-to-dependency-injection-what-it-is-and-when-to-use-it-7578c84fa88f/) to understand what it is and how it is used.

### Retrofit

REST Client for Java and Android. It makes it relatively easy to retrieve and upload JSON (or other structured data) via a REST based webservice. In Retrofit you configure which converter is used for the data serialization.

### Kotlin Coroutines

Coroutines are a Kotlin feature that converts async callbacks for long-running tasks, such as database or network access, into sequential code. Here is a code snippet to give you an idea of what you'll be doing. The callback-based code will be converted to sequential code using coroutines.

### Glide

Fast and efficient open source media management and image loading framework for Android that wraps media decoding, memory and disk caching, and resource pooling into a simple and easy to use interface.

This is the result so far:

![](https://github.com/MarcosCGlobant/PokemonTCGApp/blob/Marcos.Cifuentes/card-type-fragment-service/pokemon_app_readme_gif.gif)
