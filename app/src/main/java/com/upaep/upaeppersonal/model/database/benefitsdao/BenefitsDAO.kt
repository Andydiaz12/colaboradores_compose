package com.upaep.upaeppersonal.model.database.benefitsdao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.upaep.upaeppersonal.model.entities.features.benefits.Benefit

@Dao
interface BenefitsDAO {
    @Query("SELECT * FROM Benefit")
    suspend fun getBenefits() : List<Benefit>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addBenefits(benefit: List<Benefit>)

    @Query("DELETE FROM Benefit")
    suspend fun deleteBenefits()
}