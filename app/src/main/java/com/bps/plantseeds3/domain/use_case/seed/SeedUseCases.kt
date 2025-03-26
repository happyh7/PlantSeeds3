package com.bps.plantseeds3.domain.use_case.seed

import javax.inject.Inject

data class SeedUseCases @Inject constructor(
    val getSeeds: GetSeedsUseCase,
    val searchSeeds: SearchSeedsUseCase,
    val getSeed: GetSeedUseCase,
    val addSeed: AddSeedUseCase,
    val updateSeed: UpdateSeedUseCase,
    val deleteSeed: DeleteSeedUseCase
) 