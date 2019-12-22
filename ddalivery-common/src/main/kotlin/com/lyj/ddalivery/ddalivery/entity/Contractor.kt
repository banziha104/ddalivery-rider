package com.lyj.ddalivery.ddalivery.entity

import javax.persistence.*

@Entity
/***
 * 제휴업체
 */
data class Contractor(
        @GeneratedValue(strategy = GenerationType.IDENTITY) @Id val contractorId : Long,
        @Column(nullable = false, length = 20) val name : String,
        @Column(length = 30) val address : String
)