package com.bps.plantseeds3.domain.model

import android.util.Log

enum class PlantCategory {
    VEGETABLE,
    FRUIT,
    HERB,
    FLOWER,
    TREE,
    SHRUB,
    OTHER;

    val displayName: String
        get() = when (this) {
            VEGETABLE -> "Grönsak"
            FRUIT -> "Frukt"
            HERB -> "Ört"
            FLOWER -> "Blomma"
            TREE -> "Träd"
            SHRUB -> "Buske"
            OTHER -> "Övrigt"
        }

    companion object {
        fun fromDisplayName(displayName: String): PlantCategory {
            return when (displayName) {
                "Grönsak" -> VEGETABLE
                "Frukt" -> FRUIT
                "Ört" -> HERB
                "Blomma" -> FLOWER
                "Träd" -> TREE
                "Buske" -> SHRUB
                else -> OTHER
            }
        }

        fun fromName(name: String): PlantCategory {
            Log.d("PlantCategory", "Konverterar namn till kategori: $name")
            return try {
                // Försök först med exakt matchning
                valueOf(name)
            } catch (e: IllegalArgumentException) {
                // Om det misslyckas, försök mappa liknande kategorier
                when (name.uppercase()) {
                    "SEED", "SEEDLING", "PLANT", "CUTTING", "BULB" -> OTHER
                    "SUCCULENT", "CACTUS", "FERN", "GRASS", "CLIMBER", 
                    "GROUND_COVER", "AQUATIC", "TROPICAL", "MEDITERRANEAN", 
                    "ALPINE", "BONSAI", "INDOOR", "OUTDOOR" -> OTHER
                    "ANNUAL", "BIENNIAL", "PERENNIAL", "EVERGREEN", 
                    "DECIDUOUS", "CONIFER" -> OTHER
                    "BAMBOO", "PALM", "CYCAD", "GINGER", "BROMELIAD", 
                    "ORCHID", "CARNIVOROUS", "AIR_PLANT" -> OTHER
                    "MOSS", "LICHEN", "FUNGUS", "ALGAE" -> OTHER
                    else -> {
                        Log.w("PlantCategory", "Ogiltigt kategorinamn: $name, använder OTHER")
                        OTHER
                    }
                }
            }
        }
    }
} 