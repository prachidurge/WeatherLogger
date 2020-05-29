WeatherLogger:

1) The project is built on MVVM architecture.
2) Language: Kotlin
3) UI features - 1) Fragment Navigation, 2) Recyclerview
4) Libraries - 1) Room database - database
               2) Retrofit - network calls
               3) Google gms - location
               4) kodein (tried first time)

5) Tasks to be spilled: 1) Save button and time managemet.
                           Time check method is prepared in isFetchCurrentNeeded() method in WeatherRepositoryImpl.kt
                        2) Some minor warnings.
                        3) If city name gives IOException, default city is set for "Delhi"
                        4) OnViewClicked for Recyclerview is coded. The event just shows a toast. Similar architecture would be followed,
                           just that data will be fetched from Database instead of network call.



