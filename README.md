# PokemonTCGApp

Hello, my name is Marcos Cifuentes, and I'm currently a trainee at Globant in Mobile Studio.

As part of my training, my task was to create an app based on the [POKEMON TCG API](https://docs.pokemontcg.io/#documentationgetting_started), that returns information abut Pokemon cards.

For the development of the app was used [Kotlin language](https://kotlinlang.org/) and [MVVM architecture](https://www.journaldev.com/20292/android-mvvm-design-pattern) toghether with [Clean](https://blog.cleancoder.com/uncle-bob/2012/08/13/the-clean-architecture.html).

Request to the API are done via [Retrofit](https://square.github.io/retrofit/), and the information is handled with [Kotlin coroutines](https://kotlinlang.org/docs/reference/coroutines-overview.html).

The Main activity is shown using a View Pager with 3 tabs. This is done with the [ViewPager2](https://developer.android.com/jetpack/androidx/releases/viewpager2) library.

The information is shown using a [Recycler view](https://developer.android.com/guide/topics/ui/layout/recyclerview), with card views to contain the data.
The images were loaded using [Glide](https://github.com/bumptech/glide). 

This is the result so far:

![](https://github.com/MarcosCGlobant/PokemonTCGApp/blob/Marcos.Cifuentes/card-type-fragment-service/app/src/main/res/raw/pokemon_app_readme_gif.gif)
