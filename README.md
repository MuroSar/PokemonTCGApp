# PokemonTCGApp

Hello, my name is Marcos Cifuentes, and I'm currently a trainee at Globant in Mobile Studio.

As part of my training, my task was to create an app based on the [POKEMON TCG API](https://docs.pokemontcg.io/#documentationgetting_started), that returns information abut Pokemon cards.

For the development of the app was used [Kotlin language](https://kotlinlang.org/) and [MVVM architecture](https://docs.google.com/presentation/d/1aBtccGraTyggnIP6Nn7m8uGfBgreKWIk-2JuLafKAds/edit#slide=id.p4) together with [Clean](https://proandroiddev.com/kotlin-clean-architecture-1ad42fcd97fa).
![alt text](https://github.com/egek92/Kotlin-MVVM-JetPack/blob/master/images/architecture_diagram.png "Clean + MVVM")

Requests to the API are done via [Retrofit](https://square.github.io/retrofit/), and the information is handled with [Kotlin coroutines](https://kotlinlang.org/docs/reference/coroutines-overview.html).

The Main activity is shown using a View Pager with 3 tabs. This is done with the [ViewPager2](https://developer.android.com/jetpack/androidx/releases/viewpager2) library.

The information is shown using a [Recycler view](https://developer.android.com/guide/topics/ui/layout/recyclerview), with card views to contain the data.
The images were loaded using [Glide](https://github.com/bumptech/glide). 

This is the result so far:

![](https://github.com/MarcosCGlobant/PokemonTCGApp/blob/Marcos.Cifuentes/card-type-fragment-service/app/src/main/res/raw/pokemon_app_readme_gif.gif)
