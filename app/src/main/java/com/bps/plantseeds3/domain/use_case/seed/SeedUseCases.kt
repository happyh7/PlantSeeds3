package com.bps.plantseeds3.domain.use_case.seed

import javax.inject.Inject

data class SeedUseCases @Inject constructor(
    val getSeeds: GetSeedsUseCase,
    val getFavoriteSeeds: GetFavoriteSeedsUseCase,
    val searchSeeds: SearchSeedsUseCase,
    val getSeed: GetSeedUseCase,
    val addSeed: AddSeedUseCase,
    val updateSeed: UpdateSeedUseCase,
    val deleteSeed: DeleteSeedUseCase,
    val toggleFavorite: ToggleFavoriteUseCase,
    val getSeedsByCategory: GetSeedsByCategoryUseCase,
    val getSeedsByLifespan: GetSeedsByLifespanUseCase,
    val getSeedsByHardinessZone: GetSeedsByHardinessZoneUseCase,
    val getAllCategories: GetAllCategoriesUseCase,
    val updateInvalidCategories: UpdateInvalidCategoriesUseCase
) 