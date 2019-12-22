package com.lyj.ddalivery.ddalivery.api.rider.seoulbike.entity

import java.time.LocalDateTime
import javax.persistence.*

@Entity
data class BikeRentalPlace(
        @GeneratedValue(strategy = GenerationType.IDENTITY) @Id val bikeRentalPlaceId: Long,
        @Column(length = 30, nullable = false) val placeName: String,
        @Column(length = 30, nullable = false) val address: String,
        @Column(length = 5, nullable = false) val province: String,
        @Column( nullable = false) val longitude: Double,
        @Column( nullable = false) val latitude: Double,
        @Column(nullable = false) val registDt: LocalDateTime,
        @Column(nullable = false) val quntity: Int

)